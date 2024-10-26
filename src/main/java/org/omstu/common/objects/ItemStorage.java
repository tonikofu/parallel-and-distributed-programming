package org.omstu.common.objects;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.locks.ReentrantLock;

public class ItemStorage {
    private final Map<String, List<String>> storage = new HashMap<>();
    private final ReentrantLock lock = new ReentrantLock();

    public void put(String key, String item) {
        if (item == null) return;
        lock.lock();
        try {
            if (key != null && !key.isEmpty()) {
                storage.computeIfAbsent(key, k -> new ArrayList<>()).add(item);
            }
        } finally {
            lock.unlock();
        }
    }

    public Map<String, List<String>> get() {
        lock.lock();
        try {
            return new HashMap<>(storage);
        } finally {
            lock.unlock();
        }
    }
}
