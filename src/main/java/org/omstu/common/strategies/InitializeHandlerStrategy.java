package org.omstu.common.strategies;

import org.omstu.common.interfaces.IStrategy;

import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class InitializeHandlerStrategy implements IStrategy {
    private static final Logger LOGGER = Logger.getLogger(InitializeHandlerStrategy.class.getName());

    public Object execute(Object... args) {
        FileHandler fileHandler = null;
        String fileName = (String) args[0];

        try {
            fileHandler = new FileHandler(String.format("logs/%s", fileName), true);
            fileHandler.setFormatter(new SimpleFormatter());
        } catch (IOException exception) {
            String message = String.format("Failed to initialize FileHandler: %s", exception);
            LOGGER.severe(message);
        }

        return fileHandler;
    }
}
