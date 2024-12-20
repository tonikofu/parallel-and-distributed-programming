package org.omstu.common.output;

import org.omstu.common.interfaces.IOutput;

import java.util.List;
import java.util.Map;

public class DescentOutputCommand implements IOutput {
    Map<String, List<String>> descent;

    public DescentOutputCommand(Object... args) {
        this.descent = (Map<String, List<String>>) args[0];
    }

    public void execute() {
        System.out.println("Reverse class descent index:");

        descent.forEach((key, value) -> System.out.println(key + " -> " + (value.isEmpty() ? "no subclasses" : String.join(", ", value))));
    }
}
