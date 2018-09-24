package ssm;

import com.amazonaws.ClientConfiguration;
import com.amazonaws.services.simplesystemsmanagement.AWSSimpleSystemsManagement;
import com.amazonaws.services.simplesystemsmanagement.AWSSimpleSystemsManagementClientBuilder;
import com.amazonaws.services.simplesystemsmanagement.model.GetParameterRequest;
import com.amazonaws.services.simplesystemsmanagement.model.GetParameterResult;

public class ParameterStoreAdapter extends HashMapParameterStore {
    private final AWSSimpleSystemsManagement awsSimpleSystemsManagement;

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
    public String getValue(String name, Environment environment) {
        String value = super.getValue(name, environment);
        if (value != null) {
            return value;
        }
        return getRemoteValue(name, environment);
    }

    private String getRemoteValue(String name, Environment environment) {
        GetParameterRequest getParameterRequest = new GetParameterRequest();
        getParameterRequest.withName(name + ":" + environment.name()).setWithDecryption(true);
        GetParameterResult getParameterResult = awsSimpleSystemsManagement.getParameter(getParameterRequest);
        String value = getParameterResult.getParameter().getValue();
        Parameter parameter = new Parameter(name, environment, value);
        addParameter(parameter);
        return value;
    }
}
