package org.omstu.common.executor;

import org.omstu.common.object.ItemStorage;
import org.omstu.common.worker.QueueWorker;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;

public class GetClassDescentExecutor implements IExecutor {
    private static final Path POISON_PILL = Paths.get("POISON_PILL");

    private final List<Path> paths;
    private final ExecutorService executorService;
    private final BlockingQueue<Path> tasks;
    private final BlockingQueue<ItemStorage> results;
    private final int numWorkers;

    public GetClassDescentExecutor(Object... args) {
        if (args.length < 2) {
            throw new NotEnoughArgumentsException("Not enough arguments (needs 'list of files' and 'number of threads')");
        }

        this.paths = (List<Path>) args[0];
        this.numWorkers = (int) args[1];

        int capacity = paths.size() + numWorkers;
        this.tasks = new LinkedBlockingQueue<>(capacity);
        this.results = new LinkedBlockingQueue<>(capacity);

        this.executorService = Executors.newFixedThreadPool(numWorkers);

        for (Path path : paths) {
            try {
                tasks.put(path);
            } catch (InterruptedException exception) {
                Thread.currentThread().interrupt();
                throw new IllegalStateException("Task submission interrupted", exception);
            }
        }

        for (int i = 0; i < numWorkers; i++) {
            try {
                tasks.put(POISON_PILL);
            } catch (InterruptedException exception) {
                Thread.currentThread().interrupt();
                throw new IllegalStateException("Task submission interrupted", exception);
            }
        }
    }

    public Object execute() {
        // Map
        for (int i = 0; i < numWorkers; i++) {
            executorService.submit(new QueueWorker(tasks, results));
        }
        executorService.shutdown();

        // Reduce
        ItemStorage storage = new ItemStorage();
        try {
            for (int i = 0; i < paths.size(); i++) {
                ItemStorage localStorage = results.take();
                storage.putAll(localStorage);
            }
        } catch (InterruptedException exception) {
            throw new RuntimeException("Execution failed", exception);
        }

        return storage.get();
    }
}
