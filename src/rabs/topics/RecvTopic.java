package rabs.topics;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import com.rabbitmq.client.Channel;

import rabs.*;

public class RecvTopic {
    public static void main(String[] args) throws IOException, TimeoutException {
        Channel channel = ChannelMaker.getInstance().newChannel();

        String queuename = channel.queueDeclare().getQueue();
        String exchangename = VariableProvider.getVariable(EnvMap.exchangeName);
        channel.exchangeDeclare(exchangename, "topic");

        for (String severity : args) {
            channel.queueBind(queuename, exchangename, severity);
        }

        System.out.println(" [*] Waiting for messages. To exit press CTRL+C");

        channel.basicConsume(queuename, true, (consumerTag, delivery) -> {
            String key = delivery.getEnvelope().getRoutingKey();
            String msg = new String(delivery.getBody(), "UTF-8");

            System.out.println(" [x] Received '" + key + "':'" + msg + "'");
        }, consumerTag -> {
        });
    }
}
