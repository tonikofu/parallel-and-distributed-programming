package org.omstu.common.executor;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class GetDirectoryFilesListExecutor implements IExecutor {
    private final String path;

    public GetDirectoryFilesListExecutor(final Object... args) {
        if (args.length < 1) {
            throw new NotEnoughArgumentsException("Not enough arguments (needs 'directory path')");
        }

        this.path = (String) args[0];
    }

    public List<Path> execute() {
        List<Path> files = new ArrayList<>();

        try (Stream<Path> paths = Files.walk(Paths.get(path))) {
            paths.filter(Files::isRegularFile)
                    .filter(path -> path.toString().endsWith(".java"))
                    .forEach(files::add);
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }

        return files;
    }
}
