package ssm;

import com.google.gson.Gson;

import java.util.HashMap;
import java.util.Objects;

public class Parameter {
    private final String name;
    private final Environment environment;
    private String value;

    public Parameter(String name, Environment environment) {
        this.name = name;
        this.environment = environment;
    }

    public Parameter(String name, Environment environment, String value) {
        this.name = name;
        this.environment = environment;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public Environment getEnvironment() {
        return environment;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    /**
     * Two Parameter are considered to be equal if they have the same name and same environment.
     *
     * @param object The object to compare this {@code Parameter} against.
     * @return True if the given object represents a Parameter equivalent to this Parameter, false otherwise.
     */
    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (!(object instanceof Parameter)) {
            return false;
        }
        Parameter parameter = (Parameter) object;
        if (hashCode() != parameter.hashCode()) {
            return false;
        }
        return name.equals(parameter.getName()) && environment.equals(parameter.getEnvironment());
    }

    /**
     * Returns the hash code value for the object. This method is supported for the benefit of hash data structures
     * such as those provided by {@link HashMap}.
     *
     * @return The hash code value for the object.
     */
    @Override
    public int hashCode() {
        int hash = 17;
        hash = 157 * hash + Objects.hash(name, environment);
        return hash;
    }

    @Override
    public String toString() {
        Gson gson = new Gson();
        return gson.toJson(this);
    }
}
