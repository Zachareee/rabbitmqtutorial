package rabs;

public class VariableProvider {
    public static String getVariable(EnvMap env) {
        String variable = System.getenv("rmqhostname");
        assert variable != null && !variable.isEmpty() : "Set the rmqhostname variable!";
        return variable;
    }
}