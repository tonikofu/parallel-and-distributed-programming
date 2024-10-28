package org.omstu.common.executors;

import org.omstu.common.exceptions.NotEnoughArgumentsException;
import org.omstu.common.interfaces.IExecutor;
import org.omstu.common.objects.IOC;
import org.omstu.common.objects.ItemStorage;
import org.omstu.common.objects.JavaClassFile;

import java.nio.file.Path;
import java.util.*;
import java.util.concurrent.*;

public class GetClassDescentExecutor implements IExecutor {
    private final List<Path> paths;
    private final ExecutorService executorService;

    public GetClassDescentExecutor(Object... args) {
        if (args.length < 2) {
            throw new NotEnoughArgumentsException("Not enough arguments (needs 'list of files' and 'number of threads')");
        }

        this.paths = (List<Path>) args[0];
        this.executorService = Executors.newFixedThreadPool((int) args[1]);
    }

    public Object get() {
        List<Future<ItemStorage>> futures = new ArrayList<>();
        paths.forEach(path -> {
            Future<ItemStorage> future = executorService.submit(() -> {
                JavaClassFile javaFile = (JavaClassFile) IOC.resolve("get-java-file-executor", path);
                String className = javaFile.getClassName();

                List<String> parentClasses = new ArrayList<>();
                Optional.ofNullable(javaFile.getClassExtends()).ifPresent(parentClasses::add);
                parentClasses.addAll(Arrays.asList(Optional.ofNullable(javaFile.getClassImplements()).orElse(new String[]{})));

                ItemStorage localStorage = new ItemStorage();
                parentClasses.forEach((parent) -> localStorage.put(parent, className));

                return localStorage;
            });

            futures.add(future);
        });

        ItemStorage storage = new ItemStorage();
        futures.forEach(future -> {
            try {
                ItemStorage result = future.get();
                storage.putAll(result.get());
            } catch (Exception exception) {
                throw new RuntimeException(exception);
            }
        });

        executorService.shutdown();

        return storage.get();
    }
}
