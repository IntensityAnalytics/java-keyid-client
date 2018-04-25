package com.intensityanalytics.keyid;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import okhttp3.Response;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;

public class KeyIDClient
{
    public KeyIDSettings settings;
    private KeyIDService service;

    /**
     * Get KeyIDSettings.
     * @return
     */
    public KeyIDSettings getSettings()
    {
        return settings;
    }

    /**
     * Set KeyIDSettings.
     * @param settings KeyID settings.
     */
    public void setSettings(KeyIDSettings settings)
    {
        this.settings = settings;
    }

    /**
     * KeyID services client.
     * @param settings KeyID settings.
     */
    public KeyIDClient(KeyIDSettings settings)
    {
        this.settings = settings;
        this.service = new KeyIDService(settings.getUrl(), settings.getLicense(), settings.getTimeout());
    }

    /**
     * KeyID services client.
     */
    public KeyIDClient()
    {
        this.service = new KeyIDService(settings.getUrl(), settings.getLicense(), settings.getTimeout());
    }

    /**
     * Saves a given KeyID profile entry.
     * @param entityID Profile name to save.
     * @param tsData Typing sample data to save.
     * @param sessionID Session identifier for logging purposes.
     * @return
     */
    public CompletableFuture<JsonObject> SaveProfile(String entityID, String tsData, String sessionID)
    {
        // try to save profile without a token
        return service.SaveProfile(entityID, tsData, "")
        .thenCompose(response ->
         {
             JsonObject data;
             try
             {
                 data = ParseResponse(response);
             }
             catch (Exception e)
             {
                 throw new CompletionException(e);
             }

             // token is required
             if (data.get("Error").getAsString().equals("New enrollment code required."))
             {
                 // get a save token
                 return service.SaveToken(entityID, tsData)
                 .thenCompose(tokenResponse ->
                  {
                      JsonObject tokenData;
                      try
                      {
                          tokenData = ParseResponse(tokenResponse);
                      }
                      catch (Exception e)
                      {
                          throw new CompletionException(e);
                      }
                      // try to save profile with a token
                      return service.SaveProfile(entityID, tsData, tokenData.get("Token").getAsString());
                  })
                 .thenApply(tokenResponse ->
                  {
                      JsonObject tokenData;
                      try
                      {
                          tokenData = ParseResponse(tokenResponse);
                      }
                      catch (Exception e)
                      {
                          throw new CompletionException(e);
                      }
                      return tokenData;
                  });
             }

             return CompletableFuture.completedFuture(data);
         });
    }

    /**
     * Removes a KeyID profile.
     * @param entityID Profile name to remove.
     * @param tsData Optional typing sample for removal authorization.
     * @param sessionID Session identifier for logging purposes.
     * @return
     */
    public CompletableFuture<JsonObject> RemoveProfile(String entityID, String tsData, String sessionID)
    {
        // get a removal token
        return service.RemoveToken(entityID, tsData)
        .thenCompose(response ->
         {
             JsonObject data;
             try
             {
                 data = ParseResponse(response);
             }
             catch (Exception e)
             {
                 throw new CompletionException(e);
             }

             // remove profile
             if (data.has("Token"))
             {
                 return service.RemoveProfile(entityID, data.get("Token").getAsString())
                 .thenCompose(removeResponse ->
                  {
                      JsonObject removeData;
                      try
                      {
                          removeData = ParseResponse(removeResponse);
                      }
                      catch(Exception e)
                      {
                          throw new CompletionException(e);
                      }
                      return CompletableFuture.completedFuture(removeData);
                  });
             }
             else
                 return CompletableFuture.completedFuture(data);
         });
    }

    /**
     * Evaluates a KeyID profile.
     * @param entityID Profile name to evaluate.
     * @param tsData Typing sample to evaluate against profile.
     * @param sessionID Session identifier for logging purposes.
     * @return
     */
    public CompletableFuture<JsonObject> EvaluateProfile(String entityID, String tsData, String sessionID)
    {
        long nonceTime = DotNetTicks();

        return service.Nonce(nonceTime)
        .thenCompose(response ->
         {
             String token;
             try
             {
                 token = response.body().string();
             }
             catch (Exception e)
             {
                 throw new CompletionException(e);
             }

             return service.EvaluateSample(entityID, tsData, token);
         })
        .thenApply(response ->
           {
               JsonObject data;
               try
               {
                   data = ParseResponse(response);
               }
               catch (Exception e)
               {
                   throw new CompletionException(e);
               }

               // check for error before continuing
               if (data.get("Error").getAsString().equals(""))
               {
                   // coerce string to boolean
                   data.addProperty("Match", AlphaToBool(data.get("Match").getAsString()));
                   data.addProperty("IsReady", AlphaToBool(data.get("IsReady").getAsString()));

                   // evaluate match value using custom threshold if enabled
                   if (settings.isCustomThreshold())
                   {
                       boolean evalResult = EvalThreshold(data.get("Confidence").getAsDouble(), data.get("Fidelity").getAsDouble());
                       data.addProperty("Match", evalResult);
                   }
               }

               return data;
           });
    }

    /**
     * Evaluates a given profile and adds typing sample to profile.
     * @param entityID Profile to evaluate.
     * @param tsData Typing sample to evaluate and save.
     * @param sessionID Session identifier for logging purposes.
     * @return
     */
    public CompletableFuture<JsonObject> EvaluatePassiveEnrollment(String entityID, String tsData, String sessionID)
    {
        return EvaluateProfile(entityID, tsData, sessionID)
        .thenCompose(data ->
         {
            // in base case that no profile exists save profile and return early
            // in case that the profile has too little data or varies to much but the profile and the profile is not ready yet
            // ugly expression
            if (data.get("Error").getAsString().equals("EntityID does not exist."))
            {
                return SaveProfile(entityID, tsData, sessionID)
                .thenApply(saveData ->
                   {
                      // handle successful save
                      if (saveData.get("Error").getAsString().equals(""))
                      {
                          JsonObject evalData = data;
                          evalData.addProperty("Error", "");
                          evalData.addProperty("Match", true);
                          evalData.addProperty("IsReady", false);
                          evalData.addProperty("Confidence", "100.0");
                          evalData.addProperty("Fidelity", "100.0");
                          evalData.addProperty("Profiles", "1");
                          return evalData;
                      }
                      // handle unsuccessful save
                      // todo change this to throwing an exception?
                      else
                      {
                          JsonObject evalData = saveData;
                          evalData.addProperty("Match", false);
                          evalData.addProperty("IsReady", false);
                          evalData.addProperty("Confidence", 0);
                          evalData.addProperty("Fidelity", 0);
                          return evalData;
                      }
                   });
            }

            // if profile is not ready save profile and return early
            if (data.get("IsReady").getAsBoolean() == false)
            {
                return SaveProfile(entityID, tsData, sessionID)
                .thenApply(saveData ->
                {
                    // handle successful save
                    if (saveData.get("Error").getAsString().equals("") ||
                        saveData.get("Error").getAsString().equals("The profile has too little data for a valid evaluation.") ||
                        saveData.get("Error").getAsString().equals("The entry varied so much from the model, no evaluation is possible."))
                    {
                        JsonObject evalData = data;
                        evalData.addProperty("Error", "");
                        evalData.addProperty("Match", true);
                        return evalData;
                    }
                    // handle unsuccessful save
                    // todo change this to throwing an exception?
                    else
                    {
                        JsonObject evalData = saveData;
                        evalData.addProperty("Match", false);
                        evalData.addProperty("IsReady", false);
                        evalData.addProperty("Confidence", 0);
                        evalData.addProperty("Fidelity", 0);
                        return evalData;
                    }
                });
            }

            return CompletableFuture.completedFuture(data);
         });
    }

    /**
     * Courtesy function that choses normal evaluation or passive enrollment to simplify calling code.
     * @param entityID Profile to evaluate.
     * @param tsData Typing sample to evaluate and save.
     * @param sessionID Session identifier for logging purposes.
     * @return
     */
    public CompletableFuture<JsonObject> Login(String entityID, String tsData, String sessionID)
    {
        if(settings.isPassiveEnrollment())
            return EvaluatePassiveEnrollment(entityID, tsData, sessionID);
        else
            return EvaluateProfile(entityID, tsData, sessionID);
    }

    /**
     * Returns profile information without modifying the profile.
     * @param entityID Profile to inspect.
     * @return
     */
    public CompletableFuture<JsonObject> GetProfileInfo(String entityID)
    {
        return service.GetProfileInfo(entityID)
        .thenCompose(response ->
         {
             JsonObject data;
             try
             {
                 data = ParseGetProfileResponse(response);
             }
             catch (Exception e)
             {
                 throw new CompletionException(e);
             }

             return CompletableFuture.completedFuture(data);
         });
    }

    /**
     * Extracts a JSON value from a http_respons
     * @param response HTTP response
     * @return JSON value
     * @throws Exception
     */
    JsonObject ParseResponse(Response response) throws Exception
    {
        if (response.code() == 200)
        {
            String body = response.body().string();
            JsonObject obj = new JsonParser().parse(body).getAsJsonObject();
            return obj;
        }
        else
        {
            throw new Exception("HTTP response not 200 OK.");
        }
    }

    /**
     * Extracts a JSON value from the GetProfile() http_response
     * @param response
     * @return
     * @throws Exception
     */
    JsonObject ParseGetProfileResponse(Response response) throws Exception
    {
        if (response.code() == 200)
        {
            String body = response.body().string();
            JsonObject obj = new JsonParser().parse(body).getAsJsonObject();

            if (obj.isJsonArray())
                return obj.getAsJsonArray().get(0).getAsJsonObject();

            return obj;
        }
        else
        {
            throw new Exception("HTTP response not 200 OK.");
        }
    }

    /**
     * Converts a string value like 'true' to a boolean object.
     * @param input
     * @return True if input string equals "TRUE", otherwise false.
     */
    private boolean AlphaToBool(String input)
    {
        input = input.toUpperCase();

        if (input.equals("TRUE"))
            return true;
        else
            return false;
    }

    /**
     * Compares a given confidence and fidelity against pre-determined thresholds.
     *
     * @param confidence KeyID evaluation confidence.
     * @param fidelity KeyID evaluation fidelity.
     * @return True if confidence and fidelity are greater than the settings
     *         threshold value.
     */
    private boolean EvalThreshold(double confidence, double fidelity)
    {
        if (confidence >= settings.getThresholdConfidence() &&
            fidelity >= settings.getThresholdFidelity())
        {
            return true;
        }
        else
        {
            return false;
        }
    }

    /**
     * Converts current time to Microsoft .Net 'ticks'. A tick is 100 nanoseconds.
     * @return
     */
    private long DotNetTicks()
    {
        long TICKS_AT_EPOCH = 621355968000000000L;
        long ticks = System.currentTimeMillis()*10000 + TICKS_AT_EPOCH;
        return ticks;
    }
}