package ssm;

import org.junit.Test;

public class HashMapParameterStoreTest {
    /**
     * Test of getValue method(String), of HashMapParameterStore. Tests ParameterNotFoundException.
     */
    @Test(expected = ParameterNotFoundException.class)
    public void getValueParameterNotFoundException() {
        HashMapParameterStore hashMapParameterStore = new HashMapParameterStore();
        hashMapParameterStore.getValue("name");
    }

    /**
     * Test of getValue method(String, Environment), of HashMapParameterStore. Tests ParameterNotFoundException with
     * wrong environment.
     */
    @Test(expected = ParameterNotFoundException.class)
    public void getValueParameterNotFoundExceptionWrongEnvironment() {
        HashMapParameterStore hashMapParameterStore = new HashMapParameterStore();
        hashMapParameterStore.addParameter(new Parameter("name", Environment.TEST));
        hashMapParameterStore.getValue("name", Environment.DEVELOPMENT);
    }
}
