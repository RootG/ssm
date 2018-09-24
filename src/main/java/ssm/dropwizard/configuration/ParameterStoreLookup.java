package ssm.dropwizard.configuration;

import org.apache.commons.text.lookup.StringLookup;
import ssm.ParameterStoreAdapter;

public class ParameterStoreLookup implements StringLookup {
    private final ParameterStoreAdapter parameterStoreAdapter;

    public ParameterStoreLookup(ParameterStoreAdapter parameterStoreAdapter) {
        this.parameterStoreAdapter = parameterStoreAdapter;
    }

    @Override
    public String lookup(String s) {
        return parameterStoreAdapter.getValue(s);
    }
}
