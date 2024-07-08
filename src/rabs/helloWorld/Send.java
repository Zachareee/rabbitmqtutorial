package rabs.helloWorld;

import java.io.IOException;
import java.util.concurrent.TimeoutException;
import java.util.Scanner;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import rabs.*;

public class Send {
    public static void main(String[] args) throws IOException, TimeoutException {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost(VariableProvider.getVariable(EnvMap.hostname));

        try (Connection conn = factory.newConnection();
                Channel channel = conn.createChannel();
                Scanner sc = new Scanner(System.in)) {
            String queuename = VariableProvider.getVariable(EnvMap.queueName);
            channel.queueDeclare(queuename, false, false, false, null);

            System.out.print("Type your message to send: ");
            String message = sc.nextLine();
            channel.basicPublish("", queuename, null, message.getBytes());
            System.out.println(" [x] Sent '" + message + "'");
        }
    }
}
