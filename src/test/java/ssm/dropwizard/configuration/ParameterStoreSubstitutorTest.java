package ssm.dropwizard.configuration;

import org.junit.Test;
import ssm.Environment;
import ssm.HashMapParameterStore;
import ssm.Parameter;
import ssm.ParameterNotFoundException;

import static org.junit.Assert.assertEquals;

public class ParameterStoreSubstitutorTest {
    @Test
    public void testSuccess() {
        HashMapParameterStore hashMapParameterStore = new HashMapParameterStore();
        hashMapParameterStore.setDefaultEnvironment(Environment.TEST);
        hashMapParameterStore.addParameter(new Parameter("name", Environment.TEST, "value"));
        ParameterStoreSubstitutor parameterStoreSubstitutor = new ParameterStoreSubstitutor(hashMapParameterStore);
        assertEquals("value", parameterStoreSubstitutor.replace("${name}"));
    }


    @Test(expected = ParameterNotFoundException.class)
    public void getValueParameterNotFoundException() {
        HashMapParameterStore hashMapParameterStore = new HashMapParameterStore();
        ParameterStoreSubstitutor parameterStoreSubstitutor = new ParameterStoreSubstitutor(hashMapParameterStore);
        assertEquals("value", parameterStoreSubstitutor.replace("${name}"));
    }

    /**
     * Tests parameter not found exception when environment does not match.
     */
    @Test(expected = ParameterNotFoundException.class)
    public void getValueParameterNotFoundExceptionWrongEnvironment() {
        HashMapParameterStore hashMapParameterStore = new HashMapParameterStore();
        hashMapParameterStore.setDefaultEnvironment(Environment.PRODUCTION);
        hashMapParameterStore.addParameter(new Parameter("name", Environment.TEST, "value"));
        ParameterStoreSubstitutor parameterStoreSubstitutor = new ParameterStoreSubstitutor(hashMapParameterStore);
        assertEquals("value", parameterStoreSubstitutor.replace("${name}"));
    }
}
