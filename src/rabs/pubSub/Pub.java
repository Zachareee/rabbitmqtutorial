package rabs.pubSub;

import java.io.IOException;
import java.util.Scanner;
import java.util.concurrent.TimeoutException;

import rabs.*;

public class Pub {
    public static void main(String[] args) throws IOException, TimeoutException {
        ChannelMaker.getInstance().lambdaChannel(Wrapper.wrap(channel -> {
            try (Scanner sc = new Scanner(System.in)) {
                String exchangeName = VariableProvider.getVariable(EnvMap.exchangeName);

                channel.exchangeDeclare(exchangeName, "fanout");

                System.out.println("Type your message: ");
                String msg = sc.nextLine();
                channel.basicPublish(exchangeName, "", null, msg.getBytes());
                System.out.println(" [x] Sent '" + msg + "'");
            }
        }));
    }
}
