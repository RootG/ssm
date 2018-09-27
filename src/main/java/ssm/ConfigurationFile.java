package ssm;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UncheckedIOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ConfigurationFile extends HashMapParameterStore {
    public ConfigurationFile(InputStream inputStream) {
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
                                "Example: name:environment=value");
                    }
                    String value = matcher.group(3);
                    Parameter parameter = new Parameter(name, Environment.valueOf(matcher.group(2)), value);
                    addParameter(parameter);
                } else {
                    throw new IllegalArgumentException("Parameter definition's syntax is invalid: " + line + "\n" +
                            "Example: name:environment=value");
                }
            }
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }
}
