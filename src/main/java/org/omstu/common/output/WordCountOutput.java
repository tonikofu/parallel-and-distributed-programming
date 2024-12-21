package org.omstu.common.output;

import java.util.Map;

public class WordCountOutput implements IOutput {
    private final Map<?, ?> countInfo;

    public WordCountOutput(final Object... args) {
        this.countInfo = (Map<?, ?>) args[0];
    }

    public void execute() {
        for (Map.Entry<?, ?> entry : countInfo.entrySet()) {
            System.out.printf("[%s]: %s%n", entry.getKey(), entry.getValue());
        }
    }
}
