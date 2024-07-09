package rabs;

import java.util.function.Consumer;

public class Wrapper {
    @FunctionalInterface
    public interface ThrowingConsumer<T, E extends Exception> {
        void accept(T t) throws E;
    }

    public static <T> Consumer<T> wrap(ThrowingConsumer<T, Exception> consumer) {
        return i -> {
            try {
                consumer.accept(i);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        };
    }
}
