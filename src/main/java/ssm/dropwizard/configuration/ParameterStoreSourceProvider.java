package ssm.dropwizard.configuration;

import io.dropwizard.configuration.ConfigurationSourceProvider;
import ssm.Environment;
import ssm.ParameterStore;
import ssm.ParameterStoreFactory;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class ParameterStoreSourceProvider implements ConfigurationSourceProvider {
    private final ParameterStore parameterStore;
    private final ConfigurationSourceProvider delegate;
    private final ParameterStoreSubstitutor substitutor;

    public ParameterStoreSourceProvider(ConfigurationSourceProvider delegate, Environment environment) throws IOException {
        this.parameterStore = ParameterStoreFactory.create();
        parameterStore.setDefaultEnvironment(environment);
        this.delegate = delegate;
        this.substitutor = new ParameterStoreSubstitutor(parameterStore);
    }

    @Override
    public InputStream open(String path) throws IOException {
        try (Scanner scanner = new Scanner(delegate.open(path))) {
            String config = scanner.useDelimiter("\\A").next();
            String substituted = substitutor.replace(config);
            return new ByteArrayInputStream(substituted.getBytes(StandardCharsets.UTF_8));
        }
    }
}
