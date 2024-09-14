package org.example;

/**
 * If a triangle with the given three sides does not exist, an
 * <tt>NonExistentTriangleException</tt> will be thrown.
 */
public class NonExistentTriangleException extends Exception {
    /**
     * Exception constructor.
     * @param message Exception message.
     */
    public NonExistentTriangleException(final String message) {
        super(message);
    }
}
