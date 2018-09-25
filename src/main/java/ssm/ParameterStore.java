package ssm;

public interface ParameterStore {
    /**
     * Returns the value of the parameter by using default environment for it's environment.
     *
     * @param name The name of the parameter.
     * @return The value.
     * @throws ParameterNotFoundException Throws if a parameter can not be found.
     * @see ParameterStore#setDefaultEnvironment(Environment)
     */
    String getValue(String name) throws ParameterNotFoundException;

    /**
     * Returns the value of the parameter by using the given environment for it's environment.
     *
     * @param name        The name of the parameter.
     * @param environment The environment of the parameter.
     * @return The value
     * @throws ParameterNotFoundException Throws if a parameter can not be found.
     */
    String getValue(String name, Environment environment) throws ParameterNotFoundException;

    /**
     * Sets the default environment for this {@code ParameterStore} object. Subsequent invocations of
     * {@link ParameterStore#getValue(String)} will use the given environment.
     *
     * @return The environment of {@code ParameterStore} object.
     */
    Environment getDefaultEnvironment();

    /**
     * Sets the default environment for this {@code ParameterStore} object. Subsequent invocations of
     * {@link ParameterStore#getValue(String)} will use the given environment.
     *
     * @param environment The environment to set.
     */
    void setDefaultEnvironment(Environment environment);
}
