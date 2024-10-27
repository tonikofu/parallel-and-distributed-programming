package org.omstu.common.objects;

import org.omstu.common.exceptions.ResolutionException;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

public final class IOC {
    private static final Map<String, Function<Object[], Object>> FACTORY = new HashMap<>();

    private IOC() {}

    public static void register(final String key, final Function<Object[], Object> function) {
        FACTORY.put(key, function);
    }

    public static Object resolve(final String key, final Object... args) {
        Function<Object[], Object> function = FACTORY.get(key);

        if (function != null) {
            return function.apply(args);
        }

        throw new ResolutionException(String.format("No command registered for key: %s", key));
    }
}
