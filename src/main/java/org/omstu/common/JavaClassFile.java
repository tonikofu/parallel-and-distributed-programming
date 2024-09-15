package org.omstu.common;

import org.omstu.interfaces.IJavaFile;

import java.nio.file.Path;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class JavaClassFile implements IJavaFile {
    private static final Pattern CLASS_PATTERN = Pattern.compile("(?<=class\\s|interface\\s)([A-Z][a-zA-Z\\d]*)");
    private static final Pattern EXTENDS_PATTERN = Pattern.compile("extends\\s+([A-Z][a-zA-Z]*) ");
    private static final Pattern IMPLEMENTS_PATTERN = Pattern.compile("implements\\s+((?:[A-Z][a-zA-Z0-9]*(?:,\\s*)?|(?:\\s+|,)?)+)");

    Path path;
    String data;

    public JavaClassFile(Object... args) {
        this.path = (Path) args[0];

        this.data = (String) IoC.resolve("FileRead", path);
    }

    public String getClassName() {
        Matcher classMatcher = CLASS_PATTERN.matcher(data);

        if (classMatcher.find()) {
            return classMatcher.group(0);
        }

        return null;
    }

    public String getClassExtends() {
        Matcher extendsMatcher = EXTENDS_PATTERN.matcher(data);

        if (extendsMatcher.find()) {
            return extendsMatcher.group(1);
        }

        return null;
    }

    public String[] getClassImplements() {
        Matcher implementsMatcher = IMPLEMENTS_PATTERN.matcher(data);
        String[] interfaces;

        if (implementsMatcher.find()) {
            interfaces = implementsMatcher.group(1).trim().split(",\\s*");
            return interfaces;
        }

        return null;
    }
}
