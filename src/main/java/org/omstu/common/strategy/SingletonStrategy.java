package org.omstu.common.strategy;

import java.util.function.Function;

public class SingletonStrategy implements IStrategy {
    private final Object singletonInstance;

    public SingletonStrategy(final Function<Object[], ?> function, final Object... args) {
        this.singletonInstance = function.apply(args);
    }

    public Object execute(final Object... args) {
        return singletonInstance;
    }
}
