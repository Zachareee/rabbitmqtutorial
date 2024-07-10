package rabs;

import java.io.IOException;
import java.util.concurrent.TimeoutException;
import java.util.function.Consumer;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

public class ChannelMaker {
    private static ChannelMaker INSTANCE;
    private static ConnectionFactory factory;

    private ChannelMaker() {
        factory = new ConnectionFactory();
        factory.setHost(VariableProvider.getVariable(EnvMap.hostname));
    }

    public static ChannelMaker getInstance() {
        if (INSTANCE == null)
            INSTANCE = new ChannelMaker();
        return INSTANCE;
    }

    public Channel newChannel() throws IOException, TimeoutException {
        return factory.newConnection().createChannel();
    }

    public void lambdaChannel(Consumer<Channel> func) throws IOException, TimeoutException {
        try (Connection conn = factory.newConnection();
                Channel channel = conn.createChannel()) {
            func.accept(channel);
        }
    }
}
