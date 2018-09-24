package ssm;

public abstract class AbstractParameterStore implements ParameterStore {
    private Environment defaultEnvironment;

    @Override
    public String getValue(String name) {
        return getValue(name, defaultEnvironment);
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
