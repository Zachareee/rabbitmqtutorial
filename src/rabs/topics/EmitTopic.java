package rabs.topics;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import rabs.*;

public class EmitTopic {
    public static void main(String[] args) throws IOException, TimeoutException {
        String exchangename = VariableProvider.getVariable(EnvMap.exchangeName);
        ChannelMaker.getInstance().lambdaChannel(Wrapper.wrap(channel -> {
            channel.exchangeDeclare(exchangename, "topic");

            Input.loopInputAndRun(new String[] { "Enter the route", "Enter the message" }, Wrapper.wrap(replies -> {
                String route = replies[0];
                String msg = replies[1];
                channel.basicPublish(exchangename, route, null, msg.getBytes());
                System.out.println(" [x] Sent '" + route + "':'" + msg + "'");
            }));
        }));
    }
}
