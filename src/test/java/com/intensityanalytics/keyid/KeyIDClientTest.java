package com.intensityanalytics.keyid;
import com.google.gson.JsonObject;
import org.junit.Test;
import static org.junit.Assert.*;

public class KeyIDClientTest
{
    @Test
    public void testSaveProfile() throws Exception
    {
        String license = KeyIDTest.readFile("license.txt");

        KeyIDSettings settings = new KeyIDSettings();
        settings.setLicense(license);
        settings.setUrl("http://keyidservices.tickstream.com");

        KeyIDClient client = new KeyIDClient(settings);
        JsonObject json = client.SaveProfile("javatest1",
                           "hZK7bcRADERrcQcc/hlfejAcugAX4MTZFu+1o+OKgKBAycM8DUfBy8UlCxTqABmXrK/1eK735/r8fz4ebyCduO+Tix5XhUCsnxPzesUYHmw5WdG5CE69Wo+4dAmqqzX8FRMxCvhkzc75fk1de1xlmdxaNVlRcWvVgirXndUUpsWDtZ3E9xYx7gp0LpEZg7XFBbGq09WqHdtFSfRqPTCmIBsObNQwy73/0OHECqk5pLWmya4hGNIOLNR4OIi1Hz3N937DqieWgA6j9oNk0Kbyet7eNEv+2g5Yq1C7KUgGrH1bGfavZBfsFw==",
                           "").get();

        String error = json.get("Error").getAsString();
        assertEquals(error, "");
    }

    @Test
    public void testRemoveProfile() throws Exception
    {
        String license = KeyIDTest.readFile("license.txt");

        KeyIDSettings settings = new KeyIDSettings();
        settings.setLicense(license);
        settings.setUrl("http://keyidservices.tickstream.com");

        KeyIDClient client = new KeyIDClient(settings);
        JsonObject json = client.RemoveProfile("javatest1",
                                             "hZK7bcRADERrcQcc/hlfejAcugAX4MTZFu+1o+OKgKBAycM8DUfBy8UlCxTqABmXrK/1eK735/r8fz4ebyCduO+Tix5XhUCsnxPzesUYHmw5WdG5CE69Wo+4dAmqqzX8FRMxCvhkzc75fk1de1xlmdxaNVlRcWvVgirXndUUpsWDtZ3E9xYx7gp0LpEZg7XFBbGq09WqHdtFSfRqPTCmIBsObNQwy73/0OHECqk5pLWmya4hGNIOLNR4OIi1Hz3N937DqieWgA6j9oNk0Kbyet7eNEv+2g5Yq1C7KUgGrH1bGfavZBfsFw==",
                                             "").get();

        String error = json.get("Error").getAsString();
        assertEquals("", "");
    }

    @Test
    public void testLoginPassiveEnrollment() throws Exception
    {
        String license = KeyIDTest.readFile("license.txt");

        KeyIDSettings settings = new KeyIDSettings();
        settings.setLicense(license);
        settings.setUrl("http://keyidservices.tickstream.com");

        KeyIDClient client = new KeyIDClient(settings);

        client.RemoveProfile("javatest1","","").get();

        client.LoginPassiveEnrollment("javatest1",
                                               "hZI7bsQwDETPkhtwKP5Ub7sIUu4BcoA06XT40KlMUcDChquHeeaQzsuGjZggHzIiRKeu7/V4rs/nev0/X48PkFRuirGtn53zEiceM0Drd8dsFmx6vvNkxYlr1hqn6qbG3ep2xwxBqnKyRuEYlOxh1hrn0ElvrW5wzbR3Vs88ZJtvrMECy910a6lkklBu42AFKueUSztYa5yLu2u3yh1TooFh1K07JkAcjkmpYAExRU/bMRY/nabeJ1VcSj3c3I6NsHlK44IpC6e0p22YyjDvabUQXIsd6PXWSRHC+T1gZQTO2q6ddqz8W7Y2CB37Aw==",
                                               "").get();

        client.LoginPassiveEnrollment("javatest1",
                                      "hZO5bcRADEVrcQe8j3jTheFwC3ABTjab4pdwJM4QEARlD/+R1JfTMjaORHABVRRxWL/r8Vzfz/X6f34eXwjSORKGWH875y3OGNVV1nvHLDuWYJ6TFSfusPY4x6DkwerWMHOCedfoXBCADbu2uBBLjbizZg1HTLfWJCRKurNmcIr5YL2epLIxsE5yWhE7x4Gpg7XFoaA552mVjrkJOp/WHYsA9lOqcMWIOb1mO9J2TOooQzW1bUruSDp8/R1L5LrRmXYteiHhSlNax6pwKkOB+0GYtMo5FLhvykJY9RywtgLXqzGltdnq9zIKPLAP",
                                      "").get();

        client.LoginPassiveEnrollment("javatest1",
                                      "hZK7bcRADERrcQcc/hlfejAcugAX4MTZFu+1o+OKgKBAycM8DUfBy8UlCxTqABmXrK/1eK735/r8fz4ebyCduO+Tix5XhUCsnxPzesUYHmw5WdG5CE69Wo+4dAmqqzX8FRMxCvhkzc75fk1de1xlmdxaNVlRcWvVgirXndUUpsWDtZ3E9xYx7gp0LpEZg7XFBbGq09WqHdtFSfRqPTCmIBsObNQwy73/0OHECqk5pLWmya4hGNIOLNR4OIi1Hz3N937DqieWgA6j9oNk0Kbyet7eNEv+2g5Yq1C7KUgGrH1bGfavZBfsFw==",
                                      "").get();

        client.LoginPassiveEnrollment("javatest1",
                                      "hZO5bUQxDERrcQe8j3jTheFwC3ABTpypeMuOPj8JCwIUPcxwhpLTMjaORHBHx0ghWZ/r8Vzvz/X6Ox+PNwSpXKaIra87Z1mwTCOySQ6vXAAYJXQ5p4qpqev6/t81jHRfHXO7YmnGBDENFxPXh7vKEbCzEh1cCbdeOp9cCYkdz667k3AdKvFrJXsHaGDTIhArRwa79OYqRY4xkLdDk5uxnqFizCSYPYNCwUI5p33NWDPVklTw99CgdsMMJXRQo4KpuenQ7x3zBOBTvWIZmXgqRFJYmQesRNh/gYNiwMpsqrAfXTf9AQ==",
                                      "").get();

        client.LoginPassiveEnrollment("javatest1",
                                      "hZO7bQQxDERrcQf8f+JLD4ZDF+ACnDhT8cdztJQI7Cp9mOEMuU7L2DgSwVOABFlh/azHc30+1/f/+3p8IMjE/e6c5RXD9xcxyWHnqJ6eck4dYySz9XfjGiiicmJuV4w4UWHMGo0TQCUbhmtyXFGF486Vk9kr6p0rp2vgratIIMXk2ipRDgHmwRXbIlQIwgZX6XKZUfOdch0zIIBaxJlhwxxQhrXWgV0xVU8bTHfMPKuRw1RbUq86gIft75hwpRjU2mW6EpDmoLZhJRbDnffe3AyM6a4QD0JQHbAWIaDUcvgd+mxRW1U6sRc=",
                                      "").get();

        client.LoginPassiveEnrollment("javatest1",
                                      "hZK7bYMxDIRnyQY8vlm7NYKUGSADpEmn4S24Mi0iP1QIAj7c8agLXi4uWUzGCrNQpfWzbvf1eV/fz/N1+wBp5zzYaP2+c16vGAtDeZRD57Yi+SkXbTrWDAtaf/+7CqJIByy8Yb5viWm4bFwQk+cwXJNTDZb0K1djV2a/dDUhFb50taxknrK2lXi67ufgCkzc4apNLtQgU01m7MzQsd05Njkz7IK9YEkB+PBfM3aYWkuaVsCu16nWMSegdFBrzcxyuGJQe8PCQ+xqvUWM1KFKPWkhWbbaibUIpSUY19tmq4RU5YE9AA==",
                                      "").get();

        JsonObject json = client.EvaluateProfile("javatest1", "hZK7bcRADERrcQcc/hlfejAcugAX4MTZFu+1o+OKgKBAycM8DUfBy8UlCxTqABmXrK/1eK735/r8fz4ebyCduO+Tix5XhUCsnxPzesUYHmw5WdG5CE69Wo+4dAmqqzX8FRMxCvhkzc75fk1de1xlmdxaNVlRcWvVgirXndUUpsWDtZ3E9xYx7gp0LpEZg7XFBbGq09WqHdtFSfRqPTCmIBsObNQwy73/0OHECqk5pLWmya4hGNIOLNR4OIi1Hz3N937DqieWgA6j9oNk0Kbyet7eNEv+2g5Yq1C7KUgGrH1bGfavZBfsFw==", "").get();

        assertEquals(json.get("Confidence").getAsString(), "98");
        assertEquals(json.get("Fidelity").getAsString(), "96");
    }
}