package ssm;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ConfigurationFile implements ParameterStore {
    private final Map<Parameter, Parameter> parameters;
    private Environment environment;

    public ConfigurationFile() throws IOException {
        this(ConfigurationFile.class.getClassLoader().getResourceAsStream("ParameterStore"));
    }

    public ConfigurationFile(InputStream inputStream) throws IOException {
        parameters = new HashMap<>();
        InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
        try (BufferedReader bufferedReader = new BufferedReader(inputStreamReader)) {
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                Pattern pattern = Pattern.compile("([^:]+):?(.+)?=(.+)");
                Matcher matcher = pattern.matcher(line);
                if (matcher.find() && matcher.groupCount() == 4) {
                    String name = matcher.group(1);
                    Environment environment = Environment.valueOf(matcher.group(2));
                    String value = matcher.group(3);
                    Parameter parameter = new Parameter(name, environment, value);
                    parameters.put(parameter, parameter);
                } else {
                    throw new IllegalArgumentException("Parameter definition's syntax is invalid: " + line);
                }
            }
        }
    }

    @Override
    public String getValue(String name) {
        return getValue(name, environment);
    }

    @Override
    public String getValue(String name, Environment environment) {
        return parameters.get(new Parameter(name, environment)).getValue();
    }

    @Override
    public Environment getEnvironment() {
        return null;
    }

    @Override
    public void setEnvironment(Environment environment) {
        this.environment = environment;
    }
}
