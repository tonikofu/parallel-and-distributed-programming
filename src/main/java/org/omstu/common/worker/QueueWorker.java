package org.omstu.common.worker;

import org.omstu.common.ioc.IOC;
import org.omstu.common.object.ItemStorage;
import org.omstu.common.processor.FileProcessor;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.concurrent.BlockingQueue;

public class QueueWorker implements Runnable {
    private static final Path POISON_PILL = Paths.get("POISON_PILL");

    private final BlockingQueue<Path> tasks;
    private final BlockingQueue<ItemStorage> results;

    public QueueWorker(final BlockingQueue<Path> tasks,
                       final BlockingQueue<ItemStorage> results) {
        this.tasks = tasks;
        this.results = results;
    }

    public void run() {
        try {
            while (true) {
                Path task = tasks.take();
                if (POISON_PILL.equals(task)) {
                    break;
                }

                FileProcessor processor = IOC.resolve("file-processor", task);
                results.put(processor.process());
            }
        } catch (InterruptedException exception) {
            Thread.currentThread().interrupt();
        }
    }
}
