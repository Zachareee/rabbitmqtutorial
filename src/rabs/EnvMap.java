package rabs;

public enum EnvMap {
    queueName("queueName"),
    hostname("rmqhostname");

    public final String envName;
    private EnvMap(String envName) {
        this.envName = envName;
    }
}