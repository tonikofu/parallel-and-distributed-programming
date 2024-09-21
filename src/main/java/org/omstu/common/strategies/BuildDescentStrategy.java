package org.omstu.common.strategies;

import org.omstu.common.IoC;
import org.omstu.common.JavaClassFile;
import org.omstu.interfaces.IStrategy;

import java.util.*;

public class BuildDescentStrategy implements IStrategy {
    public Object execute(Object... args) {
        List<?> paths = (List<?>) args[0];
        Map<String, List<String>> descent = new HashMap<>();

        paths.forEach((path) -> {
            JavaClassFile javaFile = (JavaClassFile) IoC.resolve("GetJavaFile", path);

            String className = javaFile.getClassName();

            List<String> parentClasses = new ArrayList<>();
            Optional.ofNullable(javaFile.getClassExtends()).ifPresent(parentClasses::add);
            parentClasses.addAll(Arrays.asList(Optional.ofNullable(javaFile.getClassImplements()).orElse(new String[]{})));

            parentClasses.forEach((parent) -> {
                List<String> children = descent.getOrDefault(parent, new ArrayList<>());
                children.add(className);
                descent.put(parent, children);
            });
        });

//            for (String parent : parentClasses) {
//                descent.computeIfAbsent(parent, k -> new ArrayList<>()).add(className);
//            }

        return descent;
    }
}
