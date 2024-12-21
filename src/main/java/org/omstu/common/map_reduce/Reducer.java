package org.omstu.common.map_reduce;

import org.omstu.common.object.ItemStorage;

import java.util.concurrent.BlockingQueue;

public class Reducer {
    private final BlockingQueue<ItemStorage> resultQueue;

    public Reducer(final BlockingQueue<ItemStorage> resultQueue) {
        this.resultQueue = resultQueue;
    }

    public ItemStorage reduce(final int numResults) {
        ItemStorage storage = new ItemStorage();
        try {
            for (int i = 0; i < numResults; i++) {
                ItemStorage localStorage = resultQueue.take();
                storage.putAll(localStorage);
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException("Reducer interrupted", e);
        }
        return storage;
    }
}
