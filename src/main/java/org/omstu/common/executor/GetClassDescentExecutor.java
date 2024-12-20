package org.omstu.common.executor;

import org.omstu.common.object.ItemStorage;
import org.omstu.common.worker.QueueWorker;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

public class GetClassDescentExecutor implements IExecutor {
    private static final Path POISON_PILL = Paths.get("POISON_PILL");

    private final List<Path> paths;
    private final int numWorkers;
    private static final int DEFAULT_CAPACITY = 10;

    public GetClassDescentExecutor(Object... args) {
        if (args.length < 2) {
            throw new NotEnoughArgumentsException("Not enough arguments (needs 'list of files', 'number of threads')");
        }

        this.paths = (List<Path>) args[0];
        this.numWorkers = (int) args[1];
    }

    public Object execute() {
        // Map
        BlockingQueue<Path> tasks = new ArrayBlockingQueue<>(DEFAULT_CAPACITY);
        BlockingQueue<ItemStorage> results = new ArrayBlockingQueue<>(DEFAULT_CAPACITY);

        List<Thread> threads = new ArrayList<>();
        for (int i = 0; i < numWorkers; i++) {
            threads.add(new Thread(new QueueWorker(tasks, results)));
            threads.get(i).start();
        }

        Thread producerThread = getProducerThread(tasks);

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

        for (Thread thread : threads) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                throw new RuntimeException("Thread joining failed", e);
            }
        }

        try {
            producerThread.join();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException("Producer joining failed", e);
        }

        return storage.get();
    }

    private Thread getProducerThread(BlockingQueue<Path> tasks) {
        Thread producerThread = new Thread(() -> {
            try {
                for (Path path : paths) {
                    tasks.put(path); //
                }

                for (int i = 0; i < numWorkers; i++) {
                    tasks.put(POISON_PILL);
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                throw new RuntimeException("Producer interrupted", e);
            }
        });

        producerThread.start();

        return producerThread;
    }
}
