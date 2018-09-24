package ssm;

import com.amazonaws.ClientConfiguration;
import com.amazonaws.services.simplesystemsmanagement.AWSSimpleSystemsManagement;
import com.amazonaws.services.simplesystemsmanagement.AWSSimpleSystemsManagementClientBuilder;
import com.amazonaws.services.simplesystemsmanagement.model.GetParameterRequest;
import com.amazonaws.services.simplesystemsmanagement.model.GetParameterResult;

import java.util.HashMap;
import java.util.Map;

public class ParameterStoreAdapter implements ParameterStore {
    private final Map<Parameter, Parameter> parameters = new HashMap<>();
    private AWSSimpleSystemsManagement awsSimpleSystemsManagement;
    private Environment defaultEnvironment;

    public ParameterStoreAdapter() {
        ClientConfiguration clientConfiguration = new ClientConfiguration();
        clientConfiguration.setConnectionTimeout(5000);
        clientConfiguration.setSocketTimeout(5000);
        awsSimpleSystemsManagement = AWSSimpleSystemsManagementClientBuilder.standard()
                .withClientConfiguration(clientConfiguration).build();
    }

    public ParameterStoreAdapter(AWSSimpleSystemsManagement awsSimpleSystemsManagement) {
        this.awsSimpleSystemsManagement = awsSimpleSystemsManagement;
    }

    @Override
    public String getValue(String name) {
        return getValue(name, defaultEnvironment);
    }

    /**
     * Retrieves the value of a parameter by it's name and label.
     *
     * @param name        The name of the parameter.
     * @param environment The label of the parameter.
     * @return The value of the parameter.
     */
    @Override
    public String getValue(String name, Environment environment) {
        Parameter parameter = parameters.get(new Parameter(name, environment));
        if (parameter != null) {
            return parameter.getValue();
        }
        return getRemoteValue(name, environment);
    }

    private String getRemoteValue(String name, Environment environment) {
        GetParameterRequest getParameterRequest = new GetParameterRequest();
        getParameterRequest.withName(name + ":" + environment.name()).setWithDecryption(true);
        GetParameterResult getParameterResult = awsSimpleSystemsManagement.getParameter(getParameterRequest);
        String value = getParameterResult.getParameter().getValue();
        Parameter parameter = new Parameter(name, environment, value);
        parameters.put(parameter, parameter);
        return value;
    }

    @Override
    public Environment getDefaultEnvironment() {
        return defaultEnvironment;
    }

    /**
     * Sets the default label for this ParameterStoreAdapter.
     *
     * @param defaultEnvironment
     */
    @Override
    public void setDefaultEnvironment(Environment defaultEnvironment) {
        this.defaultEnvironment = defaultEnvironment;
    }
}
