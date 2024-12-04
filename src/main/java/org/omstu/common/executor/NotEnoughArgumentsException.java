package org.omstu.common.executor;

public class NotEnoughArgumentsException extends RuntimeException {
    public NotEnoughArgumentsException(String message) {
        super(message);
    }
}
