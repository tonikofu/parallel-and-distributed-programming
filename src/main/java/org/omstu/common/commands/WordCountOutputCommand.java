package org.omstu.common.commands;

import org.omstu.interfaces.ICommand;

import java.util.Map;

public class WordCountOutputCommand implements ICommand {
    Map<?, ?> countInfo;

    public WordCountOutputCommand(Object... args) {
        this.countInfo = (Map<?, ?>) args[0];
    }

    public void execute() {
        for (Map.Entry<?, ?> entry : countInfo.entrySet()) {
            System.out.printf("[%s]: %s%n", entry.getKey(), entry.getValue());
        }
    }
}
