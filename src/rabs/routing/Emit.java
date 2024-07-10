package rabs.routing;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import rabs.*;

public class Emit {
    public static void main(String[] args) throws IOException, TimeoutException {
        String exchangename = VariableProvider.getVariable(EnvMap.exchangeName);
        ChannelMaker.getInstance().lambdaChannel(Wrapper.wrap(channel -> {
            channel.exchangeDeclare(exchangename, "direct");

            Input.loopInputAndRun(new String[] { "Enter the severity", "Enter the message" }, Wrapper.wrap(replies -> {
                String severity = replies[0];
                String msg = replies[1];
                channel.basicPublish(exchangename, severity, null, msg.getBytes());
                System.out.println(" [x] Sent '" + severity + "':'" + msg + "'");
            }));
        }));
    }
}
