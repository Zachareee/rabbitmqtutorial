package rabs.pubSub;

import java.io.IOException;
import java.util.Scanner;
import java.util.concurrent.TimeoutException;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import rabs.*;

public class Pub {
    public static void main(String[] args) throws IOException, TimeoutException {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost(VariableProvider.getVariable(EnvMap.hostname));
        String exchangeName = VariableProvider.getVariable(EnvMap.exchangeName);

        try (Connection conn = factory.newConnection();
                Channel channel = conn.createChannel();
                Scanner sc = new Scanner(System.in)) {
            channel.exchangeDeclare(exchangeName, "fanout");

            System.out.println("Type your message: ");
            String msg = sc.nextLine();
            channel.basicPublish(exchangeName, "", null, msg.getBytes());
            System.out.println(" [x] Sent '" + msg + "'");
        }
    }
}
