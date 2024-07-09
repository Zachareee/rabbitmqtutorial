package rabs;

public class VariableProvider {
    public static String getVariable(EnvMap env) {
        return getVariable(env, String.class);
    }

    public static <E> E getVariable(EnvMap env, Class<E> clazz) {
        String variable = System.getenv(env.envName);
        assert variable != null && !variable.isEmpty() : "Set the " + env.envName + " variable!";
        return clazz.cast(variable);
    }
}