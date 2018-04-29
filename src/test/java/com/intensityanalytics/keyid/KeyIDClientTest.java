package com.intensityanalytics.keyid;
import com.google.gson.JsonObject;
import org.junit.Test;
import static org.junit.Assert.*;

public class KeyIDClientTest
{
    private static final String serverAddress = "http://keyidservicesvm.tickstream.com";
    private static final String licenseFile = "license.txt";

    @Test
    public void testSaveProfile() throws Exception
    {
        String license = KeyIDTest.readFile(licenseFile);
        KeyIDSettings settings = new KeyIDSettings();
        settings.setLicense(license);
        settings.setUrl(serverAddress);
        KeyIDClient client = new KeyIDClient(settings);

        client.RemoveProfile("javatest1","","").get();
        JsonObject json = client.SaveProfile("javatest1", "hZK7bcRADERrcQcc/hlfejAcugAX4MTZFu+1o+OKgKBAycM8DUfBy8UlCxTqABmXrK/1eK735/r8fz4ebyCduO+Tix5XhUCsnxPzesUYHmw5WdG5CE69Wo+4dAmqqzX8FRMxCvhkzc75fk1de1xlmdxaNVlRcWvVgirXndUUpsWDtZ3E9xYx7gp0LpEZg7XFBbGq09WqHdtFSfRqPTCmIBsObNQwy73/0OHECqk5pLWmya4hGNIOLNR4OIi1Hz3N937DqieWgA6j9oNk0Kbyet7eNEv+2g5Yq1C7KUgGrH1bGfavZBfsFw==", "").get();

        assertEquals("", json.get("Error").getAsString());
    }

    @Test
    public void testRemoveProfile() throws Exception
    {
        String license = KeyIDTest.readFile(licenseFile);
        KeyIDSettings settings = new KeyIDSettings();
        settings.setLicense(license);
        settings.setUrl(serverAddress);
        KeyIDClient client = new KeyIDClient(settings);

        JsonObject json = client.RemoveProfile("javatest1", "hZK7bcRADERrcQcc/hlfejAcugAX4MTZFu+1o+OKgKBAycM8DUfBy8UlCxTqABmXrK/1eK735/r8fz4ebyCduO+Tix5XhUCsnxPzesUYHmw5WdG5CE69Wo+4dAmqqzX8FRMxCvhkzc75fk1de1xlmdxaNVlRcWvVgirXndUUpsWDtZ3E9xYx7gp0LpEZg7XFBbGq09WqHdtFSfRqPTCmIBsObNQwy73/0OHECqk5pLWmya4hGNIOLNR4OIi1Hz3N937DqieWgA6j9oNk0Kbyet7eNEv+2g5Yq1C7KUgGrH1bGfavZBfsFw==", "").get();

        assertEquals("", "");
    }

    @Test
    public void testGetProfile() throws Exception
    {
        String license = KeyIDTest.readFile(licenseFile);
        KeyIDSettings settings = new KeyIDSettings();
        settings.setLicense(license);
        settings.setUrl(serverAddress);
        KeyIDClient client = new KeyIDClient(settings);

        client.RemoveProfile("javatest1","","").get();
        client.SaveProfile("javatest1", "hZK7bcRADERrcQcc/hlfejAcugAX4MTZFu+1o+OKgKBAycM8DUfBy8UlCxTqABmXrK/1eK735/r8fz4ebyCduO+Tix5XhUCsnxPzesUYHmw5WdG5CE69Wo+4dAmqqzX8FRMxCvhkzc75fk1de1xlmdxaNVlRcWvVgirXndUUpsWDtZ3E9xYx7gp0LpEZg7XFBbGq09WqHdtFSfRqPTCmIBsObNQwy73/0OHECqk5pLWmya4hGNIOLNR4OIi1Hz3N937DqieWgA6j9oNk0Kbyet7eNEv+2g5Yq1C7KUgGrH1bGfavZBfsFw==", "").get();

        JsonObject json = client.GetProfileInfo("javatest1").get();

        assertEquals("", json.get("Error").getAsString());
    }

    @Test
    public void testLoginPassiveEnrollment() throws Exception
    {
        String license = KeyIDTest.readFile(licenseFile);
        KeyIDSettings settings = new KeyIDSettings();
        settings.setLicense(license);
        settings.setLoginEnrollment(true);
        settings.setUrl(serverAddress);
        KeyIDClient client = new KeyIDClient(settings);

        client.RemoveProfile("javatest1","","").get();
        client.Login("javatest1","hZI7bsQwDETPkhtwKP5Ub7sIUu4BcoA06XT40KlMUcDChquHeeaQzsuGjZggHzIiRKeu7/V4rs/nev0/X48PkFRuirGtn53zEiceM0Drd8dsFmx6vvNkxYlr1hqn6qbG3ep2xwxBqnKyRuEYlOxh1hrn0ElvrW5wzbR3Vs88ZJtvrMECy910a6lkklBu42AFKueUSztYa5yLu2u3yh1TooFh1K07JkAcjkmpYAExRU/bMRY/nabeJ1VcSj3c3I6NsHlK44IpC6e0p22YyjDvabUQXIsd6PXWSRHC+T1gZQTO2q6ddqz8W7Y2CB37Aw==", "").get();
        client.Login("javatest1","hZO5bcRADEVrcQe8j3jTheFwC3ABTjab4pdwJM4QEARlD/+R1JfTMjaORHABVRRxWL/r8Vzfz/X6f34eXwjSORKGWH875y3OGNVV1nvHLDuWYJ6TFSfusPY4x6DkwerWMHOCedfoXBCADbu2uBBLjbizZg1HTLfWJCRKurNmcIr5YL2epLIxsE5yWhE7x4Gpg7XFoaA552mVjrkJOp/WHYsA9lOqcMWIOb1mO9J2TOooQzW1bUruSDp8/R1L5LrRmXYteiHhSlNax6pwKkOB+0GYtMo5FLhvykJY9RywtgLXqzGltdnq9zIKPLAP", "").get();
        client.Login("javatest1","hZK7bcRADERrcQcc/hlfejAcugAX4MTZFu+1o+OKgKBAycM8DUfBy8UlCxTqABmXrK/1eK735/r8fz4ebyCduO+Tix5XhUCsnxPzesUYHmw5WdG5CE69Wo+4dAmqqzX8FRMxCvhkzc75fk1de1xlmdxaNVlRcWvVgirXndUUpsWDtZ3E9xYx7gp0LpEZg7XFBbGq09WqHdtFSfRqPTCmIBsObNQwy73/0OHECqk5pLWmya4hGNIOLNR4OIi1Hz3N937DqieWgA6j9oNk0Kbyet7eNEv+2g5Yq1C7KUgGrH1bGfavZBfsFw==","").get();
        client.Login("javatest1","hZO5bUQxDERrcQe8j3jTheFwC3ABTpypeMuOPj8JCwIUPcxwhpLTMjaORHBHx0ghWZ/r8Vzvz/X6Ox+PNwSpXKaIra87Z1mwTCOySQ6vXAAYJXQ5p4qpqev6/t81jHRfHXO7YmnGBDENFxPXh7vKEbCzEh1cCbdeOp9cCYkdz667k3AdKvFrJXsHaGDTIhArRwa79OYqRY4xkLdDk5uxnqFizCSYPYNCwUI5p33NWDPVklTw99CgdsMMJXRQo4KpuenQ7x3zBOBTvWIZmXgqRFJYmQesRNh/gYNiwMpsqrAfXTf9AQ==","").get();
        client.Login("javatest1","hZO7bQQxDERrcQf8f+JLD4ZDF+ACnDhT8cdztJQI7Cp9mOEMuU7L2DgSwVOABFlh/azHc30+1/f/+3p8IMjE/e6c5RXD9xcxyWHnqJ6eck4dYySz9XfjGiiicmJuV4w4UWHMGo0TQCUbhmtyXFGF486Vk9kr6p0rp2vgratIIMXk2ipRDgHmwRXbIlQIwgZX6XKZUfOdch0zIIBaxJlhwxxQhrXWgV0xVU8bTHfMPKuRw1RbUq86gIft75hwpRjU2mW6EpDmoLZhJRbDnffe3AyM6a4QD0JQHbAWIaDUcvgd+mxRW1U6sRc=","").get();
        client.Login("javatest1","hZK7bYMxDIRnyQY8vlm7NYKUGSADpEmn4S24Mi0iP1QIAj7c8agLXi4uWUzGCrNQpfWzbvf1eV/fz/N1+wBp5zzYaP2+c16vGAtDeZRD57Yi+SkXbTrWDAtaf/+7CqJIByy8Yb5viWm4bFwQk+cwXJNTDZb0K1djV2a/dDUhFb50taxknrK2lXi67ufgCkzc4apNLtQgU01m7MzQsd05Njkz7IK9YEkB+PBfM3aYWkuaVsCu16nWMSegdFBrzcxyuGJQe8PCQ+xqvUWM1KFKPWkhWbbaibUIpSUY19tmq4RU5YE9AA==","").get();
        JsonObject json = client.EvaluateProfile("javatest1", "hZK7bcRADERrcQcc/hlfejAcugAX4MTZFu+1o+OKgKBAycM8DUfBy8UlCxTqABmXrK/1eK735/r8fz4ebyCduO+Tix5XhUCsnxPzesUYHmw5WdG5CE69Wo+4dAmqqzX8FRMxCvhkzc75fk1de1xlmdxaNVlRcWvVgirXndUUpsWDtZ3E9xYx7gp0LpEZg7XFBbGq09WqHdtFSfRqPTCmIBsObNQwy73/0OHECqk5pLWmya4hGNIOLNR4OIi1Hz3N937DqieWgA6j9oNk0Kbyet7eNEv+2g5Yq1C7KUgGrH1bGfavZBfsFw==", "").get();

        assertEquals("98", json.get("Confidence").getAsString());
        assertEquals("96", json.get("Fidelity").getAsString());
    }

    @Test
    public void testLoginPassiveEnrollment1() throws Exception
    {
        String license = KeyIDTest.readFile(licenseFile);
        KeyIDSettings settings = new KeyIDSettings();
        settings.setLicense(license);
        settings.setLoginEnrollment(true);
        settings.setUrl(serverAddress);
        KeyIDClient client = new KeyIDClient(settings);

        client.RemoveProfile("javatest1","","").get();
        client.Login("javatest1", "jdG9EcIwDIbhWdjAsmT9tNDmoGUABqCh8/AcqazYHxenfU6v5TB3ZdVCodKUrfxOf/Xb1u9bf+7f43rhJXv/Z8Kys8/EJDGvIHpgAqJtZI0JTFuyeVqKamGwQpqmVM8wIzlzN6N2ZlNzA1EdmQf6p0s2RxMLRQ9iiVmA6JLN0ZFZaQGinhiMZuZo08RI0PPGyGpVED0wB9HMgpbRLw==","").get();
        client.Login("javatest1", "jdMxDsIwDAXQs3CDuE6ceIW1gpUDcAAWthweiBji2F+0XTo8/Z98qcxdWCSRSm6Jcvo+/dEve7/u/T7e2/nEC6PBno7lmW1EIG1hG0hjy9pgL8eKYZ+vuHRh9cgV+DfIn1KudITlguYNmT+bYYUKKJWZSWJQGjJfWg1TBWmGjXHDNLGsgivYNMmANcMamjdk/myGNUbz6syUUenC0C9jmUpY+gY=","").get();
        client.Login("javatest1", "jdJBDgIhDIXhs3iDQqGUrW4nuvUAHsCNOw4vGTWh0KfOJLP6wk9hmJuwCIUqmTQVof60Wztt7by16/5ejgeeWNnZ/TvL/GKPhSXD+sePumyN5pFJYLDaxBJYzUTlHV1HMKsVQpMapvTX3j7sx6SaFERlZDWhO50YutORVYJ3WgzLaFKXrVHDAjxeNSyiqMvWqGWKonVkse/Oj04sgqhhnPz/7Qk=","").get();
        client.Login("javatest1", "jdJBEsIgDAXQs3gDQkggW912dOsBPIAbdxzeTh1HUvgV2L7Jz6dlrsqqgUyFjHMM66mPelnqdan37d7OJ94x3tjzmKX0Ya+OJcc0gNAdIxAqLZMoYNqQ9dNcqJQAKrhpSjTFzGZ2+7I/TbOi3bRlRdDzDlkfmltmjCp4ltA09SyDCuXH1l+SUOiQHVZYWZwLNfRNrWVUFOw2ZP1ujkUZh74B","").get();
        client.Login("javatest1", "jdM7EgIhEATQs3gDmB9MqumWm3oAD2BixuG1iJhZ21pIX03TFDAPY7NS3ZTcvZfvGs9x28Z9G4+59+uFE/PJXv8Yl9J0sveBSWBeQWhiBEJ1ZVUFTEtMwbQQSoVBhTCNGDUNjInOnI2JzzTl5iDUViadQWhiAkIDU0VNW2ANPaSf7BjaV2aC7i0xVCGEmlZQIUxrJID5ynpFTRNDXyayqY6hHw==","").get();
        client.Login("javatest1", "jdKxEcIwDAXQWdhAlmTZaqHNkZYBGICGzsOTCwWWZZ1J2nf/fzshakIikFQyac0Jjqc9221r9609zne/XmhgeLKXY9wzhRKkTZlPM6WKdLK3Y9kwwaB0YLQ+AgNgWZcejP/YxpAY1tt+zG8zDIGDUjFMo68wsBqUGkZFg9JiWFg6sKi09oylBmlT5tNMKRcJjmDSMmLAtGff3222bcr8Nst0fr0f","").get();
        client.Login("javatest1", "jdIxEgIxCEDRs3gDCISEVtsdbT2AB7Cx4/A6sQkGxmTbN/xlEiITEgFUqRWKKnyOPexy2PWw+/hu5xM5RjCOPf8wGdBeC2PHWhYN2RqtM2OmZFrI1mkuyorJCm5aRd5hgrDzb4K4s6lIS6Iys/atBtEfVpKoY51LEm2O1exOPZPsITmmWJNod4yyTUO2Rj3TLKoTQ9CeREO2Rh3DFj+kNw==","").get();
        client.Login("javatest1", "jdK7DQMhDIDhWbIB+E2btKekzQAZIE06hs+JCp+xBLSf+LEAsQuKlNqEpTat5Vz90x9Hfx79PfbrfsMLg8G+gdHEoIgmpy1ZPA0dUxrsFxjPrEI2woVtjVDVdqJQYIsZ7twNjJK7OYYkSVRmRlyS6JLFqGMM2aTqGFMSXbIYdez8cUnUHENJoheW/TfPTJNom5k2TqJLFqOOGa/f9A8=","").get();
        client.Login("javatest1", "jdGxDQMhDAXQWbIB2GDsNmlPuTYDZIA06Rg+6CoMfAWQqJ7+twVzFRYJ0SSXINFCO/VdH0d9HvV13fN+Y88oXuzzh5le7Dux1LNCAkqXbC7NjhUFaUs2p7lSTQxWcGkqssOMt2Yzto1N2xeg2cQxQ6UDQ6WOtReUlp5RJFC6ZHOpOqYM0gaWQJovNQIruDQWxKxnKUUw28DQpo5lWpf+AA==","").get();
        client.Login("javatest1", "jdFBDgIhDAXQs3gDaGlpt7qdOFsP4AHcuOPwGhITYPgOsH35vwXmoqwaoqsYmVr4nvIst63ct/Kod79eeGBe2es/c46VvQ8sdSyj0oGhUmkYB2aQNrAE0lLHzMAKXVokWmKeVmb7sZNNSRyUastYCZQOjEFpblmSANKm7JimPUPPay0TQn86ZScrCK2VugDmLVOPYLYpO87WsZzytPQD","").get();
        client.Login("javatest1", "jdMxEgIhDAXQs3iDhJBAWm13tPUAHsDGjsPrUDgb4O8stG/ySQCRZmJG7Kaeihj9Vnu129buW3v2/bheZGC1s/cxq5Q6+0wsByYZhA5MQagG5gSqLdlcLYS6MGghVPNCJ5iQ+omz/dlxp8JsINT2LDEayMAYhEbmaCBlz4TRnS7ZHFoDq+hZDgw9yxjqGbQQqmVD4/U904LudMnmswVmuv4yXw==","").get();
        client.Login("javatest1", "jdI9DsMgDAXgs/QGOP4Br+0aNWsP0AN06cbhG0UZMPCqEInp03t2BHM1Nkvk+yWZLe2nvutjrc+1vo5vu994yj7/WaF0sO/AJDAhUNoxBqXaMicBaR1TkBZKPRNYIaa5XmCail+YTZPThU2VJINSa9kiCyidsrE0MOYFlObAztnG0o4VUFpaJudDGtM6hv5bjszBCjGtCGDeMjUGs03ZOFtgxvOH9AM=","").get();
        client.Login("javatest1", "jdI9EgIhDAXgs3gDQv5Iq+2Oth7AA9jYcXidrQi7bwTab/LyGJi7sVmhMCOR2srv9Fe/bf2+9ed+H9cLZ8ZlZ+8/LGJnnwOTkSlXEHrKjqGaWDCYNjEB01KoaQEV0jRzXWHuZWU3d1pp2qqDUBtZVAKhE0PP6wPTQqjCxFAFywz9kJZYQz9kYrxUIWgllFQAi5FVFrDbxBTslhjTeegX","").get();
        client.Login("javatest1", "jdKxEcMgDIXhWbIBQiCkNml9cZsBMoAbdwyfHJcCAe9iu/1OP8JmrsIigUwkpsA5fJ/6ro+tPrf6au9+v/HASmPHH2ba2Dmx1DNKDKIDyyCaexYpgGkDIzDNRaMUsIKfZuEKY5UrZ2NF1+tY+n2sOSo9yxFd75LNUc8MRUvPhBREl2yOqmNKYNrA0Ao+qgZWcNNKRptazxT+vUs2n80xo3X0Aw==","").get();
        client.Login("javatest1", "jdJBDgIhDAXQs3iDQmmhW91OnK0H8ABu3HF4DdGEwvwMsH35vw0wV2VVCqbKzBToe+qz3rZ63+qj3f164YHFxl4nzKSx98RSz1LrPCodWACl4liOIG1gDNJcqTCBFVyaaFxhmpZm+7OTTTNlUKo9K2Sg1LPfK8yl2bGC0jwzlOZLrYAVSs8sox8yMPRDsme2UJpIAmDWsyAJzHbI5tkci+G49AM=","").get();
        client.Login("javatest1", "jdGxDQMhDAXQWbKBwWDjNmlPSZsBMkCa6xj+TqdEAmwLoH3y/wbESkgEQUhQMEY4T/3Ux1afW31f93W/ocm+iqWWJQjONJPpadgzudiuWO5YASfUZJMVcsoroZlhhRHjSrc/0906xtF7EGpZQe9PB+aFcsskeCsMLDnTulCJ3ruVjjE7oSabrVDiPPSs//sFzaRlAcnpNjC72wE=","").get();
        JsonObject json = client.EvaluateProfile("javatest1", "jdJBEsIgDAXQs3gDQkggW912dOsBPIAbdxzeTh1HUvgV2L7Jz6dlrsqqgUyFjHMM66mPelnqdan37d7OJ94x3tjzmKX0Ya+OJcc0gNAdIxAqLZMoYNqQ9dNcqJQAKrhpSjTFzGZ2+7I/TbOi3bRlRdDzDlkfmltmjCp4ltA09SyDCuXH1l+SUOiQHVZYWZwLNfRNrWVUFOw2ZP1ujkUZh74B", "").get();

        assertEquals("98", json.get("Confidence").getAsString());
        assertEquals("72", json.get("Fidelity").getAsString());
        assertEquals("15", json.get("Profiles").getAsString());
    }


    @Test
    public void testLoginPassiveEnrollmentBadData() throws Exception
    {
        String license = KeyIDTest.readFile(licenseFile);
        KeyIDSettings settings = new KeyIDSettings();
        settings.setLicense(license);
        settings.setLoginEnrollment(true);
        settings.setUrl(serverAddress);
        KeyIDClient client = new KeyIDClient(settings);

        client.RemoveProfile("javatest1","","").get();
        JsonObject json = client.Login("javatest1","M7asMTM2MzMwNDSxMDQxMLY0AIKa0hpnnxo/n5oIMPR3AgA=","").get();

        assertEquals("Error saving profile.", json.get("Error").getAsString());
        assertEquals("0", json.get("Confidence").getAsString());
        assertEquals("0", json.get("Fidelity").getAsString());
        assertEquals(false, json.get("Match").getAsBoolean());
    }

    @Test
    public void testSaveProfileBadData() throws Exception
    {
        String license = KeyIDTest.readFile(licenseFile);
        KeyIDSettings settings = new KeyIDSettings();
        settings.setLicense(license);
        settings.setUrl(serverAddress);
        KeyIDClient client = new KeyIDClient(settings);

        client.RemoveProfile("javatest1","","").get();
        JsonObject json = client.SaveProfile("javatest1","M7asMTM2MzMwNDSxMDQxMLY0AIKa0hpnnxo/n5oIMPR3AgA=","").get();

        assertEquals("{\"Error\":\"Profile cannot be updated, because of corrupt input data. See the error log for additional details.\"}", json.toString());
    }
}