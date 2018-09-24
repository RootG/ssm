package ssm.dropwizard.configuration;

import org.apache.commons.text.StringSubstitutor;
import ssm.Environment;
import ssm.ParameterStore;
import ssm.ParameterStoreAdapter;

public class ParameterStoreSubstitutor extends StringSubstitutor {
    public ParameterStoreSubstitutor(Environment environment) {
        ParameterStore parameterStore = new ParameterStoreAdapter();
        parameterStore.setDefaultEnvironment(environment);
        setVariableResolver(new ParameterStoreLookup(parameterStore));
    }

    public ParameterStoreSubstitutor(ParameterStore parameterStore) {
        super(new ParameterStoreLookup(parameterStore));
    }
}
