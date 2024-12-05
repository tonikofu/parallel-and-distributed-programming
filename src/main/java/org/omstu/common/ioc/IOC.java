package org.omstu.common.ioc;

import org.omstu.common.strategy.IStrategy;

import java.util.HashMap;
import java.util.Map;

public final class IOC {
    private static final Map<String, IStrategy> REGISTRY = new HashMap<>();

    private IOC() {}

    public static void register(final String key, final IStrategy strategy) {
        REGISTRY.put(key, strategy);
    }

    public static <T> T resolve(final String key, final Object... args) {
        IStrategy strategy = REGISTRY.get(key);
        if (strategy != null) {
            return (T) strategy.execute(args);
        }
        throw new ResolutionException("No command registered for key: " + key);
    }
}
