package ssm;

public interface ParameterStore {
    String getValue(String name);

    String getValue(String name, Environment environment);

    Environment getEnvironment();

    void setEnvironment(Environment environment);
}
