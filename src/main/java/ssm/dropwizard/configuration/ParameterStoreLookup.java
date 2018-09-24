package ssm.dropwizard.configuration;

import org.apache.commons.text.lookup.StringLookup;
import ssm.ParameterStore;

public class ParameterStoreLookup implements StringLookup {
    private final ParameterStore parameterStore;

    public ParameterStoreLookup(ParameterStore parameterStore) {
        this.parameterStore = parameterStore;
    }

    @Override
    public String lookup(String s) {
        return parameterStore.getValue(s);
    }
}
