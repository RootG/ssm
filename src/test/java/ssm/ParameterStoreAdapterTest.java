package ssm;

import com.amazonaws.services.simplesystemsmanagement.AWSSimpleSystemsManagement;
import com.amazonaws.services.simplesystemsmanagement.model.GetParameterRequest;
import com.amazonaws.services.simplesystemsmanagement.model.GetParameterResult;
import com.amazonaws.services.simplesystemsmanagement.model.Parameter;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ParameterStoreAdapterTest {
    @Test
    public void testGetValue() {
        AWSSimpleSystemsManagement awsSimpleSystemsManagement = mock(AWSSimpleSystemsManagement.class);
        GetParameterResult getParameterResult = mock(GetParameterResult.class);
        Parameter parameter = mock(Parameter.class);
        when(parameter.getValue()).thenReturn("value");
        when(getParameterResult.getParameter()).thenReturn(parameter);
        when(awsSimpleSystemsManagement.getParameter(any(GetParameterRequest.class))).thenReturn(getParameterResult);
        ParameterStoreAdapter parameterStoreAdapter = new ParameterStoreAdapter(awsSimpleSystemsManagement);
        parameterStoreAdapter.setEnvironment(Environment.TEST);
        assertEquals("value", parameterStoreAdapter.getValue("key"));
    }

    @Test
    public void testGetValueConsecutive() {
        AWSSimpleSystemsManagement awsSimpleSystemsManagement = mock(AWSSimpleSystemsManagement.class);
        GetParameterResult getParameterResult = mock(GetParameterResult.class);
        Parameter parameter = mock(Parameter.class);
        when(parameter.getValue()).thenReturn("value");
        when(getParameterResult.getParameter()).thenReturn(parameter);
        when(awsSimpleSystemsManagement.getParameter(any(GetParameterRequest.class))).thenReturn(getParameterResult);
        ParameterStoreAdapter parameterStoreAdapter = new ParameterStoreAdapter(awsSimpleSystemsManagement);
        parameterStoreAdapter.setEnvironment(Environment.TEST);
        assertEquals("value", parameterStoreAdapter.getValue("key"));
        assertEquals("value", parameterStoreAdapter.getValue("key"));
    }
}
