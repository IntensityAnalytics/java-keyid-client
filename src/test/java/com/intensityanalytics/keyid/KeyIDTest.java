package com.intensityanalytics.keyid;
import org.junit.Test;
import static org.junit.Assert.*;

public class KeyIDTest
{
    @Test
    public void testString() throws Exception
    {
        KeyIDService service = new KeyIDService();
        String val = service.helloWorld();
        assertEquals(val, "hello world");
    }
}
