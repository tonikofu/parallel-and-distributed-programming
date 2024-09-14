package org.omstu.common.commands;

import org.omstu.interfaces.ICommand;

import java.util.List;
import java.util.Map;

@SuppressWarnings(value="unchecked")
public class DescentOutputCommand implements ICommand {
    Map<String, List<String>> descent;

    public DescentOutputCommand(Object... args) {
        this.descent = (Map<String, List<String>>) args[0];
    }

    public void execute() {
        System.out.printf("%-30s    %-40s%n", "Parent", "Children");
        System.out.println("-".repeat(70));
        descent.forEach((key, value) -> System.out.printf("%-30s -> %-40s%n", key, (value.isEmpty() ? "no subclasses" : String.join(", ", value))));
    }
}