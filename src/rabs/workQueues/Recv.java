package rabs.workQueues;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;

import rabs.*;

public class Recv {
    public static void main(String[] args) throws IOException, TimeoutException {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost(VariableProvider.getVariable(EnvMap.hostname));
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();

        String queuename = VariableProvider.getVariable(EnvMap.queueName);
        channel.queueDeclare(queuename, false, false, false, null);
        System.out.println(" [*] Waiting for messages. To exit press CTRL+C");

        DeliverCallback callback = (consumerTag, delivery) -> {
            String message = new String(delivery.getBody(), "UTF-8");
            System.out.println(" [x] Received '" + message + "'");
        };
        channel.basicConsume(queuename, true, callback, consumerTag -> { });
    }
}
