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
    private Environment defaultEnvironment;

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
                if (matcher.matches()) {
                    String name = matcher.group(1);
                    if (matcher.group(2) == null) {
                        throw new IllegalArgumentException("Parameter definition's syntax is invalid: " + line + "\n" +
                                "Example: key:defaultEnvironment=value");
                    }
                    String value = matcher.group(3);
                    Parameter parameter = new Parameter(name, Environment.valueOf(matcher.group(2)), value);
                    parameters.put(parameter, parameter);
                } else {
                    throw new IllegalArgumentException("Parameter definition's syntax is invalid: " + line + "\n" +
                            "Example: key:environment=value");
                }
            }
        }
    }

    @Override
    public String getValue(String name) {
        return getValue(name, defaultEnvironment);
    }

    @Override
    public String getValue(String name, Environment environment) {
        Parameter parameter = parameters.get(new Parameter(name, environment));
        return parameter == null ? null : parameter.getValue();
    }

    @Override
    public Environment getDefaultEnvironment() {
        return defaultEnvironment;
    }

    @Override
    public void setDefaultEnvironment(Environment environment) {
        this.defaultEnvironment = environment;
    }
}
