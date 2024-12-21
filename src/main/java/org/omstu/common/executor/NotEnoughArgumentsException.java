package org.omstu.common.executor;

public class NotEnoughArgumentsException extends RuntimeException {
    public NotEnoughArgumentsException(final String message) {
        super(message);
    }
}
