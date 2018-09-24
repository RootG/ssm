package ssm;

import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import static org.junit.Assert.assertEquals;

public class ConfigurationFileTest {
    @Test
    public void testConfigure() throws IOException {
        InputStream inputStream = new ByteArrayInputStream(("key1:TEST=value1\n" +
                "key2:TEST=value2\n" +
                "key3:TEST=value3").getBytes());
        ParameterStore parameterStore = new ConfigurationFile(inputStream);
        parameterStore.setDefaultEnvironment(Environment.TEST);
        assertEquals("value1", parameterStore.getValue("key1"));
        assertEquals("value2", parameterStore.getValue("key2"));
        assertEquals("value3", parameterStore.getValue("key3", Environment.TEST));
        assertEquals("value3", parameterStore.getValue("key3", Environment.TEST));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testMissingEnvironment() throws IOException {
        InputStream inputStream = new ByteArrayInputStream(("key1=value1").getBytes());
        new ConfigurationFile(inputStream);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testInvalidEnvironment() throws IOException {
        InputStream inputStream = new ByteArrayInputStream(("key1:invalid=value1").getBytes());
        new ConfigurationFile(inputStream);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testInvalidSyntax() throws IOException {
        InputStream inputStream = new ByteArrayInputStream(("invalid syntax").getBytes());
        new ConfigurationFile(inputStream);
    }
}
