package org.omstu.common.map_reduce;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.concurrent.BlockingQueue;

public class Mapper {
    private static final Path POISON_PILL = Paths.get("POISON_PILL");
    private final List<Path> paths;
    private final int numWorkers;
    private final BlockingQueue<Path> taskQueue;

    public Mapper(final List<Path> paths,
                  final int numWorkers,
                  final BlockingQueue<Path> taskQueue) {
        this.paths = paths;
        this.numWorkers = numWorkers;
        this.taskQueue = taskQueue;
    }

    public Thread map() {
        Thread producerThread = new Thread(() -> {
            try {
                for (Path path : paths) {
                    taskQueue.put(path);
                }

                for (int i = 0; i < numWorkers; i++) {
                    taskQueue.put(POISON_PILL);
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
