package org.omstu.common.executor;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class GetFileDataExecutor implements IExecutor {
    private final Path path;

    public GetFileDataExecutor(Object... args) {
        if (args.length < 1) {
            throw new NotEnoughArgumentsException("Not enough arguments (needs 'file path')");
        }

        this.path = (Path) args[0];
    }

    public Object execute() {
        String text;
        try {
            text = new String(Files.readAllBytes(path));
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }

        return text;
    }
}
