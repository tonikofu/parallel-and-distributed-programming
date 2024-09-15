package org.omstu.common.strategies;

import org.omstu.common.IoC;
import org.omstu.common.JavaClassFile;
import org.omstu.interfaces.IStrategy;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CountDownLatch;

public class BuildDescentStrategy implements IStrategy {
    public Object execute(Object... args) {
        List<?> paths = (List<?>) args[0];
        Map<String, List<String>> descent = new ConcurrentHashMap<>();

        CountDownLatch latch = new CountDownLatch(paths.size());

        paths.forEach(path -> {
            Thread thread = new Thread(() -> {
                JavaClassFile javaFile = (JavaClassFile) IoC.resolve("GetJavaFile", path);
                String className = javaFile.getClassName();

                List<String> parentClasses = new ArrayList<>();
                Optional.ofNullable(javaFile.getClassExtends()).ifPresent(parentClasses::add);
                parentClasses.addAll(Arrays.asList(Optional.ofNullable(javaFile.getClassImplements()).orElse(new String[]{})));

                for (String parent : parentClasses) {
                    if (parent != null && !parent.isEmpty()) {
                        descent.computeIfAbsent(parent, k -> new ArrayList<>()).add(className);
                    }
                }
                latch.countDown();
            });
            thread.start();
        });

        try {
            latch.await();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        return descent;
    }
}
