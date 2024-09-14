package org.omstu.common;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.logging.Logger;

public final class IoC {
    private static final Map<String, Function<Object[], Object>> FACTORY = new HashMap<>();
    private static final Logger LOGGER = Logger.getLogger(IoC.class.getName());

    private IoC() {};

    public static void register(final String key, final Function<Object[], Object> function) {
        FACTORY.put(key, function);
    }

    public static Object resolve(final String key, final Object... args) {
        Function<Object[], Object> function = FACTORY.get(key);

        if (function != null) {
            return function.apply(args);
        }

        String message = String.format("No command registered for key: %s", key);
        LOGGER.severe(message);
        throw new IllegalArgumentException(message);
    }
}
