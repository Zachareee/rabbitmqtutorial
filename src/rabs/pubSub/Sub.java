package rabs.pubSub;

import java.io.FileWriter;
import java.io.IOException;
import java.util.concurrent.TimeoutException;
import java.util.function.Consumer;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;

import rabs.*;

public class Sub {
    public static void main(String[] args) throws IOException, TimeoutException {
        System.out.println(args[0]);
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost(VariableProvider.getVariable(EnvMap.hostname));
        String exchangeName = VariableProvider.getVariable(EnvMap.exchangeName);

        Connection conn = factory.newConnection();
        Channel channel = conn.createChannel();

        channel.exchangeDeclare(exchangeName, "fanout");
        String queueName = channel.queueDeclare().getQueue();
        channel.queueBind(queueName, exchangeName, "");

        System.out.println(" [*] Waiting for messages. To exit press CTRL+C");

        DeliverCallback callback = (consumerTag, delivery) -> {
            logMethod(args[0]).accept(new String(delivery.getBody()));
        };
        channel.basicConsume(queueName, true, callback, consumertag -> {
        });
    }

    private static Consumer<String> logMethod(String method) {
        return switch (method) {
            case "print":
                yield msg -> System.out.println(" [x] Message received: '" + msg + "'");
            case "file":
                yield Wrapper.wrap(msg -> {
                    try (FileWriter writer = new FileWriter("logs.txt")) {
                        writer.write(msg);
                    }
                    System.out.println(" [x] Message written: '" + msg + "'");
                });
            default:
                yield null;
        };
    }
}
