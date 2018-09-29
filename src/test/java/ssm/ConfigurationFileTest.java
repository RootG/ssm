package ssm;

import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import static org.junit.Assert.assertEquals;

public class ConfigurationFileTest {
    @Test
    public void testConfigure() {
        InputStream inputStream = new ByteArrayInputStream(
                ("[{\"name\":\"name1\",\"environment\":\"TEST\",\"value\":\"value1\"}," +
                        "{\"name\":\"name2\",\"environment\":\"TEST\",\"value\":\"value2\"}," +
                        "{\"name\":\"name3\",\"environment\":\"TEST\",\"value\":\"value3\"}]").getBytes());
        ParameterStore parameterStore = new ConfigurationFile(inputStream);
        parameterStore.setDefaultEnvironment(Environment.TEST);
        assertEquals("value1", parameterStore.getValue("name1"));
        assertEquals("value2", parameterStore.getValue("name2"));
        assertEquals("value3", parameterStore.getValue("name3", Environment.TEST));
        assertEquals("value3", parameterStore.getValue("name3", Environment.TEST));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testMissingName() {
        InputStream inputStream = new ByteArrayInputStream(
                ("[{\"environment\":\"TEST\",\"value\":\"value1\"}]").getBytes());
        new ConfigurationFile(inputStream);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testMissingEnvironment() {
        InputStream inputStream = new ByteArrayInputStream(("{\"name\":\"name1\",\"value\":\"value1\"}").getBytes());
        new ConfigurationFile(inputStream);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testInvalidEnvironment() {
        InputStream inputStream = new ByteArrayInputStream(
                ("[{\"name\":\"name1\",\"environment\":\"INVALID\",\"value\":\"value1\"}]").getBytes());
        new ConfigurationFile(inputStream);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testInvalidSyntax() {
        InputStream inputStream = new ByteArrayInputStream(("invalid syntax").getBytes());
        new ConfigurationFile(inputStream);
    }

    /**
     * Test of getValue method(String), of ConfigurationFile. Tests ParameterNotFoundException.
     */
    @Test(expected = ParameterNotFoundException.class)
    public void getValueParameterNotFoundException() {
        InputStream inputStream = new ByteArrayInputStream((
                "[{\"name\":\"name1\",\"environment\":\"TEST\",\"value\":\"value1\"}]").getBytes());
        ParameterStore parameterStore = new ConfigurationFile(inputStream);
        parameterStore.getValue("invalid name");
    }

    /**
     * Test of getValue method(String, Environment), of ConfigurationFile. Tests ParameterNotFoundException with
     * wrong environment.
     */
    @Test(expected = ParameterNotFoundException.class)
    public void getValueParameterNotFoundExceptionWrongEnvironment() {
        InputStream inputStream = new ByteArrayInputStream(
                ("[{\"name\":\"name1\",\"environment\":\"TEST\",\"value\":\"value1\"}]").getBytes());
        ParameterStore parameterStore = new ConfigurationFile(inputStream);
        parameterStore.getValue("name", Environment.STAGING);
    }
}
