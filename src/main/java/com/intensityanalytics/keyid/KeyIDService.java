package com.intensityanalytics.keyid;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import okhttp3.Call;
import okhttp3.HttpUrl;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.net.URLEncoder;
import java.util.concurrent.CompletionException;

public class KeyIDService
{
    private String url;
    private String license;
    private OkHttpClient client;
    public static final MediaType URLENCODED = MediaType.parse("application/x-www-form-urlencoded; charset=utf-8");

    public KeyIDService(String url, String license, int timeoutMs)
    {
        this.url = url;
        this.license = license;
        client = new OkHttpClient();
    }

    public String getUrl()
    {
        return url;
    }

    public String getLicense()
    {
        return license;
    }

    public JsonObject encodeJSONProperties(JsonObject data)
    {
        JsonObject objEncoded = new JsonObject();

        try
        {
            for (Map.Entry<String, JsonElement> jsonTuple : data.entrySet())
            {
                String encodeData = URLEncoder.encode(jsonTuple.getValue().getAsString(), "UTF-8");
                objEncoded.addProperty(jsonTuple.getKey(), encodeData);
            }
        }
        catch (Exception e)
        {
            throw new CompletionException(e);
        }

        return objEncoded;
    }

    public CompletableFuture<Response> Post(String path, JsonObject data)
    {
        data.addProperty("License", license);
        JsonObject dataEncoded = encodeJSONProperties(data);
        String dataEncodedJSON = dataEncoded.toString();

        HttpUrl requestUrl = HttpUrl.parse(url).newBuilder()
                .addPathSegment(path)
                .build();

        RequestBody body = RequestBody.create(URLENCODED, "=[" + dataEncodedJSON + "]");

        Request request = new Request.Builder()
                .url(requestUrl)
                .post(body)
                .build();

        Call call = client.newCall(request);
        OkHttpResponseFuture result = new OkHttpResponseFuture(call);
        call.enqueue(result);

        return result.future;
    }

    public CompletableFuture<Response> Get(String path, JsonObject data)
    {
        HttpUrl requestUrl = HttpUrl.parse(url).newBuilder()
                .addPathSegment(path)
                .build();

        for (Map.Entry<String, JsonElement> jsonTuple : data.entrySet())
        {
            requestUrl = requestUrl.newBuilder()
                    .addQueryParameter(jsonTuple.getKey(), jsonTuple.getValue().getAsString())
                    .build();
        }

        Request request = new Request.Builder()
                .url(requestUrl)
                .build();

        Call call = client.newCall(request);
        OkHttpResponseFuture result = new OkHttpResponseFuture(call);
        call.enqueue(result);

        return result.future;
    }

    public CompletableFuture<Response> TypingMistake(String entityID, String mistype, String sessionID, String source, String action, String tmplate, String page )
    {
        JsonObject data = new JsonObject();
        data.addProperty("EntityID", entityID);
        data.addProperty("Mistype", mistype);
        data.addProperty("SessionID", sessionID);
        data.addProperty("Source", source);
        data.addProperty("Action", action);
        data.addProperty("Template", tmplate);
        data.addProperty("Page", page);

        return Post("typingmistake", data);
    }

    public CompletableFuture<Response> EvaluateSample(String entityID, String tsData, String nonce)
    {
        JsonObject data = new JsonObject();
        data.addProperty("EntityID", entityID);
        data.addProperty("tsData", tsData);
        data.addProperty("Nonce", nonce);
        data.addProperty("Return", "JSON");
        data.addProperty("Statistics", "extended");

        return Post("evaluate", data);
    }

    public CompletableFuture<Response> Nonce(long nonceTime)
    {
        JsonObject data = new JsonObject();
        data.addProperty("type", "nonce");
        String path = "token/" + nonceTime;
        return Get(path,data);
    }

    public CompletableFuture<Response> RemoveToken(String entityID, String tsData)
    {
        JsonObject data = new JsonObject();
        data.addProperty("Type", "remove");
        data.addProperty("Return", "value");
        String path = "token/" + entityID;

        return Get(path, data)
                .thenCompose(response -> {
                    JsonObject postData = new JsonObject();
                    String token;
                    try{
                        token = response.body().string();
                    }
                    catch (Exception e)
                    {
                        throw new CompletionException(e);
                    }

                    postData.addProperty("EntityID", entityID);
                    postData.addProperty("Token", token);
                    postData.addProperty("ReturnToken", "True");
                    postData.addProperty("ReturnValidation", tsData);
                    postData.addProperty("Type", "remove");
                    postData.addProperty("Return", "JSON");

                    return Post("token", postData);
                });
    }

    public CompletableFuture<Response> RemoveProfile(String entityID, String token)
    {
        JsonObject data = new JsonObject();
        data.addProperty("EntityID", entityID);
        data.addProperty("Code", token);
        data.addProperty("Action", "remove");
        data.addProperty("Return", "JSON");

        return Post("profile", data);
    }

    public CompletableFuture<Response> SaveToken(String entityID, String tsData){
        JsonObject data = new JsonObject();
        data.addProperty("Type", "enrollment");
        data.addProperty("Return", "value");

        String path = "token/" + entityID;
        return Get(path, data)
                .thenCompose(response -> {
                    JsonObject postData = new JsonObject();
                    String token;
                    try{
                        token = response.body().string();
                    }
                    catch (Exception e)
                    {
                        throw new CompletionException(e);
                    }

                    postData.addProperty("EntityID", entityID);
                    postData.addProperty("Token", token);
                    postData.addProperty("ReturnToken", "True");
                    postData.addProperty("ReturnValidation", tsData);
                    postData.addProperty("Type", "enrollment");
                    postData.addProperty("Return", "JSON");

                    return Post("token", postData);
                });
    }

    public CompletableFuture<Response> SaveProfile(String entityID, String tsData, String code)
    {
        JsonObject data = new JsonObject();
        data.addProperty("EntityID", entityID);
        data.addProperty("tsData", tsData);
        data.addProperty("Return", "JSON");
        data.addProperty("Action", "v2");
        data.addProperty("Statistics", "extended");

        if (code != "")
            data.addProperty("Code", code);

        return Post("profile", data);
    }

    public CompletableFuture<Response> GetProfileInfo(String entityID)
    {
        JsonObject data = new JsonObject();
        String path = "profile/" + entityID;
        return Get(path, data);
    }
}
