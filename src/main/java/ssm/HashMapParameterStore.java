package ssm;

import java.util.HashMap;
import java.util.Map;

public class HashMapParameterStore extends AbstractParameterStore {
    private final Map<Parameter, Parameter> parameters = new HashMap<>();

    @Override
    public String getValue(String name, Environment environment) {
        Parameter parameter = parameters.get(new Parameter(name, environment));
        return parameter == null ? null : parameter.getValue();
    }

    public void addParameter(Parameter parameter) {
        parameters.put(parameter, parameter);
    }
}
