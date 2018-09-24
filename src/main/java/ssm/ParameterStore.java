package ssm;

public interface ParameterStore {
    String getValue(String name);

    String getValue(String name, Environment environment);

    Environment getDefaultEnvironment();

    void setDefaultEnvironment(Environment defaultEnvironment);
}
