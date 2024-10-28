package org.omstu.common.strategies;

import org.omstu.common.interfaces.IStrategy;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class FileReadStrategy implements IStrategy {
    public Object execute(Object... args) {
        Path path = (Path) args[0];

        String text;
        try {
            text = new String(Files.readAllBytes(path));
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }

        return text;
    }
}
