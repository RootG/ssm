package ssm;

import com.amazonaws.services.simplesystemsmanagement.AWSSimpleSystemsManagement;
import com.amazonaws.services.simplesystemsmanagement.model.GetParameterRequest;
import com.amazonaws.services.simplesystemsmanagement.model.GetParameterResult;
import com.amazonaws.services.simplesystemsmanagement.model.Parameter;
import com.amazonaws.services.simplesystemsmanagement.model.ParameterVersionNotFoundException;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class ParameterStoreAdapterTest {
    /**
     * Test of getValue(String) method, of class ParameterStoreAdapter.
     */
    @Test
    public void testGetValue() {
        AWSSimpleSystemsManagement awsSimpleSystemsManagement = mock(AWSSimpleSystemsManagement.class);
        GetParameterResult getParameterResult = mock(GetParameterResult.class);
        Parameter parameter = mock(Parameter.class);
        when(parameter.getValue()).thenReturn("value");
        when(getParameterResult.getParameter()).thenReturn(parameter);
        when(awsSimpleSystemsManagement.getParameter(any(GetParameterRequest.class))).thenReturn(getParameterResult);
        ParameterStore parameterStore = new ParameterStoreAdapter(awsSimpleSystemsManagement);
        parameterStore.setDefaultEnvironment(Environment.TEST);
        assertEquals("value", parameterStore.getValue("name"));
        verify(awsSimpleSystemsManagement).getParameter(any(GetParameterRequest.class));
        verifyNoMoreInteractions(awsSimpleSystemsManagement);
        verify(getParameterResult).getParameter();
        verifyNoMoreInteractions(getParameterResult);
        verify(parameter).getValue();
        verifyNoMoreInteractions(parameter);
    }

    /**
     * Test of getValue(String) method, of class ParameterStoreAdapter. Tests the case where two consecutive calls to a
     * same parameter should not access to AWS Parameter Store twice.
     */
    @Test
    public void testGetValueConsecutive() {
        AWSSimpleSystemsManagement awsSimpleSystemsManagement = mock(AWSSimpleSystemsManagement.class);
        GetParameterResult getParameterResult = mock(GetParameterResult.class);
        Parameter parameter = mock(Parameter.class);
        when(parameter.getValue()).thenReturn("value");
        when(getParameterResult.getParameter()).thenReturn(parameter);
        when(awsSimpleSystemsManagement.getParameter(any(GetParameterRequest.class))).thenReturn(getParameterResult);
        ParameterStore parameterStore = new ParameterStoreAdapter(awsSimpleSystemsManagement);
        parameterStore.setDefaultEnvironment(Environment.TEST);
        assertEquals("value", parameterStore.getValue("name"));
        assertEquals("value", parameterStore.getValue("name"));
        verify(awsSimpleSystemsManagement).getParameter(any(GetParameterRequest.class));
        verifyNoMoreInteractions(awsSimpleSystemsManagement);
        verify(getParameterResult).getParameter();
        verifyNoMoreInteractions(getParameterResult);
        verify(parameter).getValue();
        verifyNoMoreInteractions(parameter);
    }

    /**
     * Test of getValue method(String), of ParameterStoreAdapter. Tests ParameterNotFoundException when
     * AWSSimpleSystemsManagement throws
     * com.amazonaws.services.simplesystemsmanagement.model.ParameterNotFoundException.
     */
    @Test
    public void getValueParameterNotFoundException() {
        AWSSimpleSystemsManagement awsSimpleSystemsManagement = mock(AWSSimpleSystemsManagement.class);
        com.amazonaws.services.simplesystemsmanagement.model.ParameterNotFoundException parameterNotFoundException =
                new com.amazonaws.services.simplesystemsmanagement.model.ParameterNotFoundException("Mock exception.");
        when(awsSimpleSystemsManagement.getParameter(any(GetParameterRequest.class))).thenThrow(
                parameterNotFoundException);
        ParameterStore parameterStore = new ParameterStoreAdapter(awsSimpleSystemsManagement);
        parameterStore.setDefaultEnvironment(Environment.TEST);
        try {
            parameterStore.getValue("name");
        } catch (ParameterNotFoundException ex) {
            assertEquals(parameterNotFoundException, ex.getCause());
        }
        verify(awsSimpleSystemsManagement).getParameter(any(GetParameterRequest.class));
        verifyNoMoreInteractions(awsSimpleSystemsManagement);
    }

    /**
     * Test of getValue method(String), of ParameterStoreAdapter. Tests ParameterNotFoundException when
     * AWSSimpleSystemsManagement throws ParameterVersionNotFoundException.
     */
    @Test
    public void getValueParameterNotFoundExceptionVersionNotFound() {
        AWSSimpleSystemsManagement awsSimpleSystemsManagement = mock(AWSSimpleSystemsManagement.class);
        ParameterVersionNotFoundException parameterVersionNotFoundException =
                new ParameterVersionNotFoundException("Mock exception.");
        when(awsSimpleSystemsManagement.getParameter(any(GetParameterRequest.class))).thenThrow(
                parameterVersionNotFoundException);
        ParameterStore parameterStore = new ParameterStoreAdapter(awsSimpleSystemsManagement);
        parameterStore.setDefaultEnvironment(Environment.TEST);
        try {
            parameterStore.getValue("name");
        } catch (ParameterNotFoundException ex) {
            assertEquals(parameterVersionNotFoundException, ex.getCause());
        }
        verify(awsSimpleSystemsManagement).getParameter(any(GetParameterRequest.class));
        verifyNoMoreInteractions(awsSimpleSystemsManagement);
    }
}
