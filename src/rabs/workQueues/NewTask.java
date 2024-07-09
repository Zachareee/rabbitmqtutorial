package rabs.workQueues;

import java.io.IOException;
import java.util.concurrent.TimeoutException;
import java.util.function.Consumer;
import java.util.Scanner;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.MessageProperties;

import rabs.*;

public class NewTask {
    private static final Scanner sc = new Scanner(System.in);

    public static void main(String[] args) throws IOException, TimeoutException {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost(VariableProvider.getVariable(EnvMap.hostname));

        try (Connection conn = factory.newConnection();
                Channel channel = conn.createChannel()) {
            String queuename = VariableProvider.getVariable(EnvMap.queueName);
            channel.queueDeclare(queuename, true, false, false, null);

            loopInputAndRun(Wrapper.wrap(message -> {
                channel.basicPublish("", queuename, MessageProperties.PERSISTENT_TEXT_PLAIN, message.getBytes());
                System.out.println(" [x] Sent '" + message + "'");
            }));
        }
    }

    private static <E extends Exception> void loopInputAndRun(Consumer<String> func) throws E {
        while (true) {
            System.out.print("Type your message to send: ");
            String message = sc.nextLine();
            func.accept(message);
        }
    }
}
