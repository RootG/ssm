package ssm;

import com.amazonaws.SdkClientException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

public class ParameterStoreFactory {
    private static final Logger LOGGER = LogManager.getLogger(ParameterStoreFactory.class);

    public static ParameterStore create() throws IOException {
        try {
            return new ParameterStoreAdapter();
        } catch (SdkClientException e) {
            LOGGER.warn("AWS Parameter Store Exception: " + e.getMessage());
        }
        LOGGER.warn("Initializing parameter store from configuration file.");
        InputStream inputStream = ConfigurationFile.class.getClassLoader().getResourceAsStream("ParameterStore");
        if (inputStream == null) {
            throw new FileNotFoundException("\"ParameterStore\" file can not be found in resources.");
        }
        return new ConfigurationFile(inputStream);
    }
}
