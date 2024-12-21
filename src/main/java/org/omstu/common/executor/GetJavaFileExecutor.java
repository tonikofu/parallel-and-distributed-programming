package org.omstu.common.executor;

import org.omstu.common.object.JavaFile;

import java.nio.file.Path;

public class GetJavaFileExecutor implements IExecutor {
    private final Path path;

    public GetJavaFileExecutor(final Object... args) {
        if (args.length < 1) {
            throw new NotEnoughArgumentsException("Not enough arguments (needs 'file path')");
        }

        this.path = (Path) args[0];
    }

    public JavaFile execute() {
        return new JavaFile(path);
    }
}
