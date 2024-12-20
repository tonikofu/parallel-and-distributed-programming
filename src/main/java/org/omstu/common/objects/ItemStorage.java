package org.omstu.common.objects;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ItemStorage {
    private final Map<String, List<String>> storage = new HashMap<>();

    public void put(String key, String item) {
        if (item == null) return;

        if (key != null && !key.isEmpty()) {
            storage.computeIfAbsent(key, k -> new ArrayList<>()).add(item);
        }
    }

    public void putAll(Map<String, List<String>> map) {
        for (Map.Entry<String, List<String>> entry : map.entrySet()) {
            String key = entry.getKey();
            List<String> valuesToAdd = entry.getValue();

            if (storage.containsKey(key)) {
                storage.get(key).addAll(valuesToAdd);
            } else {
                storage.put(key, new ArrayList<>(valuesToAdd));
            }
        }
    }

    public Map<String, List<String>> get() {
        return new HashMap<>(storage);
    }
}
