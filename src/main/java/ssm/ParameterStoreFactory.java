package ssm;

import com.amazonaws.SdkClientException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.UncheckedIOException;

public class ParameterStoreFactory {
    private static final Logger LOGGER = LogManager.getLogger(ParameterStoreFactory.class);

    public static ParameterStore create() {
        try {
            return new ParameterStoreAdapter();
        } catch (SdkClientException e) {
            LOGGER.warn("AWS Parameter Store Exception: " + e.getMessage());
        }
        LOGGER.warn("Initializing parameter store from configuration file.");
        InputStream inputStream = ConfigurationFile.class.getClassLoader()
                .getResourceAsStream("ParameterStore.json");
        if (inputStream == null) {
            throw new UncheckedIOException(
                    new FileNotFoundException("\"ParameterStore.json\" file can not be found in resources."));
        }
        return new ConfigurationFile(inputStream);
    }

    public static ParameterStore create(Environment environment) {
        try {
            ParameterStoreAdapter parameterStoreAdapter = new ParameterStoreAdapter();
            parameterStoreAdapter.setDefaultEnvironment(environment);
            return parameterStoreAdapter;
        } catch (SdkClientException e) {
            LOGGER.warn("AWS Parameter Store Exception: " + e.getMessage());
        }
        LOGGER.warn("Initializing parameter store from configuration file.");
        InputStream inputStream = ConfigurationFile.class.getClassLoader().getResourceAsStream("ParameterStore.json");
        if (inputStream == null) {
            throw new UncheckedIOException(
                    new FileNotFoundException("\"ParameterStore.json\" file can not be found in resources."));
        }
        ConfigurationFile configurationFile = new ConfigurationFile(inputStream);
        configurationFile.setDefaultEnvironment(environment);
        return configurationFile;
    }
}
