package rabs.workQueues;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;

import rabs.*;

public class Worker {
    public static void main(String[] args) throws IOException, TimeoutException {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost(VariableProvider.getVariable(EnvMap.hostname));
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();

        String queuename = VariableProvider.getVariable(EnvMap.queueName);
        channel.queueDeclare(queuename, true, false, false, null);
        System.out.println(" [*] Waiting for messages. To exit press CTRL+C");

        channel.basicQos(1);

        DeliverCallback callback = (consumerTag, delivery) -> {
            String message = new String(delivery.getBody(), "UTF-8");
            System.out.println(" [x] Received '" + message + "'");
            try {
                doWork(message);
            } catch (InterruptedException e) {
            } finally {
                System.out.println(" [x] Done");
                channel.basicAck(delivery.getEnvelope().getDeliveryTag(), false);
            }
        };
        channel.basicConsume(queuename, false, callback, consumerTag -> {
        });
    }

    private static void doWork(String message) throws InterruptedException {
        int count = message.chars()
                .mapToObj(c -> (char) c)
                .reduce(0, (previous, c) -> {
                    return previous + (c == '.' ? 1 : 0);
                }, (a, b) -> a + b);
        Thread.sleep(count * 1000);
    }
}
