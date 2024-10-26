package org.omstu.common.executors;

import org.omstu.common.exceptions.NotEnoughArgumentsException;
import org.omstu.common.interfaces.IExecutor;
import org.omstu.common.objects.IOC;
import org.omstu.common.objects.ItemStorage;
import org.omstu.common.objects.JavaClassFile;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class GetClassDescentExecutor implements IExecutor {
    private final List<Path> paths;
    private final List<Future<?>> futures = new ArrayList<>();
    private final ExecutorService executorService;
    private final ItemStorage storage = new ItemStorage();

    public GetClassDescentExecutor(Object... args) {
        if (args.length < 2) {
            throw new NotEnoughArgumentsException("Not enough arguments (needs 'list of files' and 'number of threads')");
        }

        this.paths = (List<Path>) args[0];
        this.executorService = Executors.newFixedThreadPool((int) args[1]);
    }

    public Object get() {
        paths.forEach(path -> {
            Future<?> future = executorService.submit(() -> {
                JavaClassFile javaFile = (JavaClassFile) IOC.resolve("GetJavaFile", path);
                String className = javaFile.getClassName();

                List<String> parentClasses = new ArrayList<>();
                Optional.ofNullable(javaFile.getClassExtends()).ifPresent(parentClasses::add);
                parentClasses.addAll(Arrays.asList(Optional.ofNullable(javaFile.getClassImplements()).orElse(new String[]{})));

                parentClasses.forEach((parent) -> storage.put(parent, className));
            });
            futures.add(future);
        });

        futures.forEach(future -> {
            try {
                future.get();
            } catch (Exception exception) {
                throw new RuntimeException(exception);
            }
        });

        executorService.shutdown();

        return storage.get();
    }
}
