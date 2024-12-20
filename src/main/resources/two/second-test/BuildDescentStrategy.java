package org.omstu.common.strategies;

import org.omstu.common.objects.IOC;
import org.omstu.common.objects.JavaClassFile;
import org.omstu.common.interfaces.IStrategy;

import java.util.*;

public class BuildDescentStrategy implements IStrategy {
    public Object execute(Object... args) {
        List<?> paths = (List<?>) args[0];
        Map<String, List<String>> descent = new HashMap<>();

        for (Object path : paths) {
            JavaClassFile javaFile = (JavaClassFile) IOC.resolve("GetJavaFile", path);
            List<String> subclasses = new ArrayList<>(List.of());

            String className = javaFile.getClassName();

            String classExtends = javaFile.getClassExtends();
            if (classExtends != null) {
                subclasses.add(classExtends);
            }
            subclasses.addAll(Arrays.asList(Optional.ofNullable(javaFile.getClassImplements()).orElse(new String[]{})));

            descent.putIfAbsent(className, subclasses);
        }

        return descent;
    }
}
