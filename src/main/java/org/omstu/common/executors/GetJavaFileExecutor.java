package org.omstu.common.executors;

import org.omstu.common.interfaces.IExecutor;
import org.omstu.common.objects.JavaClassFile;

import java.nio.file.Path;

public class GetJavaFileExecutor implements IExecutor {
    Path path;

    public GetJavaFileExecutor(Object... args) {
        this.path = (Path) args[0];
    }

    public JavaClassFile get() {
        return new JavaClassFile(path);
    }
}
