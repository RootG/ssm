package ssm;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

public class ParameterTest {
    /**
     * Test of equals method, of class Parameter.
     */
    @Test
    public void testEquals() {
        Parameter parameter = new Parameter("name", Environment.TEST);
        assertEquals(parameter, parameter);
        assertEquals(new Parameter("name", Environment.TEST), new Parameter("name", Environment.TEST));
        assertNotEquals(new Parameter("name1", Environment.DEVELOPMENT), new Parameter("name2", Environment.DEVELOPMENT));
        assertNotEquals(new Parameter("name1", Environment.TEST), new Parameter("name2", Environment.PRODUCTION));
        assertNotEquals(new Parameter("name", Environment.TEST), new Parameter("name", null));
        assertNotEquals(new Parameter("name", null), new Parameter("name", Environment.TEST));
        assertEquals(new Parameter("name", Environment.TEST), new Parameter("name", Environment.TEST));
        assertNotEquals(new Parameter("name", Environment.STAGING), new Parameter("name", null));
        assertNotEquals(new Parameter("name", null), new Parameter("name", Environment.TEST));
        assertNotEquals(new Parameter("name", Environment.TEST), new Parameter("name", null));
        assertNotEquals(new Parameter("name", Environment.PRODUCTION), new Parameter("name", Environment.TEST));
        assertNotEquals(new Parameter("name", Environment.DEVELOPMENT), new Object());
    }
}
