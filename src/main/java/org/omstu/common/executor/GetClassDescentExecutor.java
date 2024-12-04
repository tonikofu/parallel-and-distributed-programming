package org.omstu.common.executor;

import org.omstu.common.processor.FileProcessor;
import org.omstu.common.ioc.IOC;
import org.omstu.common.object.ItemStorage;

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

    public Object execute() {
        List<Future<ItemStorage>> futures = new ArrayList<>();
        paths.forEach(path -> {
            Future<ItemStorage> future = executorService.submit(() -> {
                FileProcessor processor = IOC.resolve("file-processor", path);
                return processor.process();
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
