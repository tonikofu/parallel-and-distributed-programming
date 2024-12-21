package org.omstu.common.executor;

import org.omstu.common.map_reduce.Mapper;
import org.omstu.common.map_reduce.Reducer;
import org.omstu.common.object.ItemStorage;
import org.omstu.common.worker.QueueWorker;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class GetClassDescentExecutor implements IExecutor {
    private final List<Path> paths;
    private final int numWorkers;
    private static final int DEFAULT_CAPACITY = 10;

    public GetClassDescentExecutor(final Object... args) {
        if (args.length < 2) {
            throw new NotEnoughArgumentsException("Not enough arguments (needs 'list of files', 'number of threads')");
        }

        this.paths = (List<Path>) args[0];
        this.numWorkers = (int) args[1];
    }

    public Object execute() {
        BlockingQueue<Path> taskQueue = new ArrayBlockingQueue<>(DEFAULT_CAPACITY);
        BlockingQueue<ItemStorage> resultQueue = new ArrayBlockingQueue<>(DEFAULT_CAPACITY);

        Mapper mapper = new Mapper(paths, numWorkers, taskQueue);
        Thread producerThread = mapper.map();

        List<Thread> workers = new ArrayList<>();
        for (int i = 0; i < numWorkers; i++) {
            workers.add(new Thread(new QueueWorker(taskQueue, resultQueue)));
            workers.get(i).start();
        }

        Reducer reducer = new Reducer(resultQueue);
        ItemStorage finalStorage = reducer.reduce(paths.size());

        for (Thread worker : workers) {
            try {
                worker.join();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                throw new RuntimeException("Worker thread joining failed", e);
            }
        }

        try {
            producerThread.join();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException("Producer thread joining failed", e);
        }

        return finalStorage.get();
    }
}
