package org.omstu.common.strategies;

import org.omstu.common.interfaces.IStrategy;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class DirectoryReadStrategy implements IStrategy {
    public Object execute(Object... args) {
        String directoryPath = (String) args[0];

        List<Path> files = new ArrayList<>();

        try (Stream<Path> paths = Files.walk(Paths.get(directoryPath))) {
            paths.filter(Files::isRegularFile)
                    .filter(path -> path.toString().endsWith(".java"))
                    .forEach(files::add);
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }

        return files;
    }
}
