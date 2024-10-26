package org.omstu.common.strategies;

import org.omstu.common.objects.IOC;
import org.omstu.common.objects.ItemStorage;
import org.omstu.common.objects.JavaClassFile;
import org.omstu.common.interfaces.IStrategy;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CountDownLatch;

public class BuildDescentStrategy implements IStrategy {
    public Object execute(Object... args) {
        List<?> paths = (List<?>) args[0];
        CountDownLatch latch = new CountDownLatch(paths.size());
        ItemStorage storage = new ItemStorage();

        paths.forEach(path -> {
            Thread thread = new Thread(() -> {
                JavaClassFile javaFile = (JavaClassFile) IOC.resolve("GetJavaFile", path);
                String className = javaFile.getClassName();

                List<String> parentClasses = new ArrayList<>();
                Optional.ofNullable(javaFile.getClassExtends()).ifPresent(parentClasses::add);
                parentClasses.addAll(Arrays.asList(Optional.ofNullable(javaFile.getClassImplements()).orElse(new String[]{})));

                parentClasses.forEach((parent) -> {
                    storage.put(parent, className);
                });

                latch.countDown();
            });
            thread.start();
        });

        try {
            latch.await();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        return storage.get();
    }
}
