package ssm;

import com.amazonaws.SdkClientException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.UncheckedIOException;

public class ParameterStoreFactory {
    private static final Logger LOGGER = LogManager.getLogger(ParameterStoreFactory.class);
    private static ParameterStore singleton;

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
        ParameterStore parameterStore = create();
        parameterStore.setDefaultEnvironment(environment);
        return parameterStore;
    }

    public synchronized static ParameterStore getSingleton() {
        if (singleton == null) {
            singleton = create();
        }
        return singleton;
    }
}
