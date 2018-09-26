package ssm.dropwizard.configuration;

import org.apache.commons.text.StringSubstitutor;
import ssm.ParameterStore;

public class ParameterStoreSubstitutor extends StringSubstitutor {
    public ParameterStoreSubstitutor(ParameterStore parameterStore) {
        super(new ParameterStoreLookup(parameterStore));
    }
}
