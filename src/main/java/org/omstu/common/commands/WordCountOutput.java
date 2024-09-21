package org.omstu.common.commands;

import org.omstu.interfaces.IOutput;

import java.util.Map;

public class WordCountOutput implements IOutput {
    Map<?, ?> countInfo;

    public WordCountOutput(Object... args) {
        this.countInfo = (Map<?, ?>) args[0];
    }

    public void execute() {
        for (Map.Entry<?, ?> entry : countInfo.entrySet()) {
            System.out.printf("[%s]: %s%n", entry.getKey(), entry.getValue());
        }
    }
}
