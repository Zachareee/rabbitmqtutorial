package com.helloWorld;

public class HostnameProvider {
    public final static String QUEUE_NAME = "Hello";

    public static String getHostname() {
        String hostname = System.getenv("rmqhostname");
        assert hostname != null && !hostname.isEmpty() : "Set the rmqhostname variable!";
        return hostname;
    }
}
