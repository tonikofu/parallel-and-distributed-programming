package org.omstu.common.strategies;

import org.omstu.common.interfaces.IStrategy;

import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.SimpleFormatter;

public class InitializeHandlerStrategy implements IStrategy {
    public Object execute(Object... args) {
        FileHandler fileHandler;
        String fileName = (String) args[0];

        try {
            fileHandler = new FileHandler(String.format("logs/%s", fileName), true);
            fileHandler.setFormatter(new SimpleFormatter());
        } catch (IOException exception) {
            throw new RuntimeException(String.format("Failed to initialize FileHandler: %s", exception));
        }

        return fileHandler;
    }
}
