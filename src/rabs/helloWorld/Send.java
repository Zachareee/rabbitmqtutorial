package rabs.helloWorld;

import java.io.IOException;
import java.util.concurrent.TimeoutException;
import java.util.Scanner;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import rabs.HostnameProvider;

public class Send {
    public static void main(String[] args) throws IOException, TimeoutException {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost(HostnameProvider.getHostname());

        try (Connection conn = factory.newConnection();
                Channel channel = conn.createChannel();
                Scanner sc = new Scanner(System.in)) {
            channel.queueDeclare(HostnameProvider.QUEUE_NAME, false, false, false, null);

            System.out.print("Type your message to send: ");
            String message = sc.nextLine();
            channel.basicPublish("", HostnameProvider.QUEUE_NAME, null, message.getBytes());
            System.out.println(" [x] Sent '" + message + "'");
        }
    }
}
