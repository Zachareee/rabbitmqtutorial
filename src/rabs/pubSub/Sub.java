package rabs.pubSub;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import rabs.*;

public class Sub {
    public static void main(String[] args) throws IOException, TimeoutException {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost(VariableProvider.getVariable(EnvMap.hostname));
        String exchangeName = VariableProvider.getVariable(EnvMap.exchangeName);

        Connection conn = factory.newConnection();
        Channel channel = conn.createChannel();

        String queueName = channel.queueDeclare().getQueue();
        channel.queueBind(queueName, exchangeName, "");
    }
}
