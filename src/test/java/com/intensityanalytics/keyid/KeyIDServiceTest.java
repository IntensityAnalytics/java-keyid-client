package com.intensityanalytics.keyid;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.junit.Test;
import static org.junit.Assert.*;
import okhttp3.Response;

public class KeyIDServiceTest
{
    private static final String serverAddress = "http://keyidservicesvm.tickstream.com";
    private static final String licenseFile = "license.txt";

    @Test
    public void testGet() throws Exception
    {
        String license = KeyIDTest.readFile(licenseFile);
        KeyIDService service = new KeyIDService(serverAddress, license,0);

        JsonObject data = new JsonObject();
        Response response = service.Get("Values", data).get();
        String body = response.body().string();

        assertEquals("[\"value1\",\"value2\"]", body);
    }

    @Test
    public void testPost() throws Exception
    {
        String license = KeyIDTest.readFile(licenseFile);
        KeyIDService service = new KeyIDService(serverAddress, license,0);

        JsonObject data = new JsonObject();
        data.addProperty("tsData", "hZI7bsQwDETPkhtwKP5Ub7sIUu4BcoA06XT40KlMUcDChquHeeaQzsuGjZggHzIiRKeu7/V4rs/nev0/X48PkFRuirGtn53zEiceM0Drd8dsFmx6vvNkxYlr1hqn6qbG3ep2xwxBqnKyRuEYlOxh1hrn0ElvrW5wzbR3Vs88ZJtvrMECy910a6lkklBu42AFKueUSztYa5yLu2u3yh1TooFh1K07JkAcjkmpYAExRU/bMRY/nabeJ1VcSj3c3I6NsHlK44IpC6e0p22YyjDvabUQXIsd6PXWSRHC+T1gZQTO2q6ddqz8W7Y2CB37Aw==" );
        Response response = service.Post("validation", data).get();
        String body = response.body().string();

        assertEquals("[{\"Statistic\":\"OpinionFailureCount\",\"Value\":\"3\"},{\"Statistic\":\"OpinionFailureMessages\",\"Value\":\"(7) Text too short (10 min).\\r\\n(43) \\\"hello1234\\\" (1 message) PWD cannot be speed-checked.\\r\\n(7) Text too short (10 min).\"},{\"Statistic\":\"OpinionInfoCount\",\"Value\":\"2\"},{\"Statistic\":\"OpinionInfoMessages\",\"Value\":\"(20) Text contains all or parts of 2 (4-10 letter) common words: hell, hello\\r\\n(20) Text contains all or parts of 2 (4-10 letter) common words: hell, hello\"},{\"Statistic\":\"OpinionWarnCount\",\"Value\":\"10\"},{\"Statistic\":\"OpinionWarnMessages\",\"Value\":\"(19) Text contains easily-keyed sequences (\\\"1234\\\").\\r\\n(22) Text contains too-popular passwords segments (\\\"1234\\\").\\r\\n(22) Text contains too-popular passwords segments (\\\"hello\\\").\\r\\n(27) Text contains excessive number (1) of character type changes.\\r\\n(28) Text contains excessive number (1) of changes between alphas and numerics.\\r\\n(19) Text contains easily-keyed sequences (\\\"1234\\\").\\r\\n(22) Text contains too-popular passwords segments (\\\"1234\\\").\\r\\n(22) Text contains too-popular passwords segments (\\\"hello\\\").\\r\\n(27) Text contains excessive number (1) of character type changes.\\r\\n(28) Text contains excessive number (1) of changes between alphas and numerics.\"}]", body);
    }

    @Test
    public void testTypingMistake() throws Exception
    {
        String license = KeyIDTest.readFile(licenseFile);
        KeyIDService service = new KeyIDService(serverAddress, license,0);

        Response response = service.TypingMistake("jgtest1",
                              "thisismistype",
                              "session1",
                              "javatest",
                              "javatest",
                              "javatest",
                              "javatest").get();
        String body = response.body().string();

        assertEquals("\"\"", body);
    }

    @Test
    public void testNonce() throws Exception
    {
        String license = KeyIDTest.readFile(licenseFile);
        KeyIDService service = new KeyIDService(serverAddress, license,0);

        long TICKS_AT_EPOCH = 621355968000000000L;
        long tick = System.currentTimeMillis()*10000 + TICKS_AT_EPOCH;
        Response response = service.Nonce(tick).get();
        String body = response.body().string();

        assertNotNull(body);
        assertFalse(body.startsWith("ERR"));
    }

    @Test
    public void testRemoveToken() throws Exception
    {
        String license = KeyIDTest.readFile(licenseFile);
        KeyIDService service = new KeyIDService(serverAddress, license,0);

        Response response = service.RemoveToken("jgtest@intensityanalytics.com", "").get();
        String body = response.body().string();
        JsonObject json = new JsonParser().parse(body).getAsJsonObject();

        assertEquals("", json.get("Error").getAsString());
    }

    @Test
    public void testSaveToken() throws Exception
    {
        String license = KeyIDTest.readFile(licenseFile);
        KeyIDService service = new KeyIDService(serverAddress, license,0);

        Response response = service.SaveToken("jgtest@intensityanalytics.com", "").get();

        String body = response.body().string();
        JsonObject json = new JsonParser().parse(body).getAsJsonObject();

        assertEquals("", json.get("Error").getAsString());
    }
}