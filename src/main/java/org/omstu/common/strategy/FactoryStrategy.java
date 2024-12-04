package org.omstu.common.strategy;

import java.util.function.Function;

public class FactoryStrategy implements IStrategy {
    private final Function<Object[], ?> factoryFunction;

    public FactoryStrategy(final Function<Object[], ?> factoryFunction) {
        this.factoryFunction = factoryFunction;
    }

    public Object execute(final Object... args) {
        return factoryFunction.apply(args);
    }
}
