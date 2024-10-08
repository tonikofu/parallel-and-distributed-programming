package org.omstu.common.strategies;

import org.omstu.common.IoC;
import org.omstu.interfaces.IStrategy;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.logging.FileHandler;
import java.util.logging.Logger;

public class FileReadStrategy implements IStrategy {
    private static final Logger LOGGER = Logger.getLogger(FileReadStrategy.class.getName());

    static {
        var fileHandler = (FileHandler) IoC.resolve("InitializeHandler", "file.log");

        LOGGER.addHandler(fileHandler);
        LOGGER.setUseParentHandlers(false);
    }

    public Object execute(Object... args) {
        Path path = (Path) args[0];

        String text;
        try {
            text = new String(Files.readAllBytes(path));

            LOGGER.info(String.format("File %s was uploaded", path));
        } catch (IOException exception) {
            LOGGER.severe(String.format("Exception while reading %s file: %s", path, exception));

            throw new RuntimeException(exception);
        }

        return text;
    }
}
