package rabs.pubSub;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import rabs.*;

public class Pub {
    public static void main(String[] args) throws IOException, TimeoutException {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost(null);
        String exchangeName = VariableProvider.getVariable(EnvMap.exchangeName);

        try (Connection conn = factory.newConnection();
                Channel channel = conn.createChannel()) {
            channel.exchangeDeclare(exchangeName, "fanout");
            channel.basicPublish(exchangeName, "", null, "Hi".getBytes());
        }
    }
}
