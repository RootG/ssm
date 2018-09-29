package ssm;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.stream.JsonReader;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;

public class ConfigurationFile extends HashMapParameterStore {
    public ConfigurationFile(InputStream inputStream) {
        InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
        JsonReader jsonReader = new JsonReader(inputStreamReader);
        Gson gson = new Gson();
        Parameter[] parameters;
        try {
            parameters = gson.fromJson(jsonReader, Parameter[].class);
        } catch (JsonSyntaxException e) {
            Parameter[] examples = new Parameter[2];
            examples[0] = new Parameter("name1", Environment.TEST, "value1");
            examples[1] = new Parameter("name2", Environment.PRODUCTION, "value2");
            throw new IllegalArgumentException(e.getMessage() + "\nExample syntax: " + Arrays.toString(examples));
        }
        for (Parameter parameter : parameters) {
            if (parameter.getName() == null) {
                throw new IllegalArgumentException("Parameter name is missing.");
            }
            if (parameter.getEnvironment() == null) {
                throw new IllegalArgumentException("Parameter environment is missing.");
            }
            addParameter(parameter);
        }
    }
}
