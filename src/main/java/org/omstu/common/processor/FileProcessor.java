package org.omstu.common.processor;

import org.omstu.common.executor.NotEnoughArgumentsException;
import org.omstu.common.executor.IExecutor;
import org.omstu.common.ioc.IOC;
import org.omstu.common.object.ItemStorage;
import org.omstu.common.object.JavaFile;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class FileProcessor {
    private final Path path;

    public FileProcessor(Object... args) {
        if (args.length < 1) {
            throw new NotEnoughArgumentsException("Not enough arguments (needs 'file path')");
        }

        this.path = (Path) args[0];
    }

    public ItemStorage process() {
        IExecutor javaFileExecutor = IOC.resolve("get-java-file-executor", path);
        var javaFile = (JavaFile) javaFileExecutor.execute();

        String className = javaFile.getClassName();

        List<String> parentClasses = new ArrayList<>();
        Optional.ofNullable(javaFile.getClassExtends()).ifPresent(parentClasses::add);
        parentClasses.addAll(Arrays.asList(Optional.ofNullable(javaFile.getClassImplements()).orElse(new String[]{})));

        ItemStorage localStorage = new ItemStorage();
        parentClasses.forEach((parent) -> localStorage.put(parent, className));

        return localStorage;
    }
}
