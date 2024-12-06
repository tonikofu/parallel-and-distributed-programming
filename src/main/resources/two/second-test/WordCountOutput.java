package org.omstu.common.output;

import java.util.Map;

<<<<<<< HEAD:src/main/resources/two/second-test/WordCountOutputCommand.java
public class WordCountOutputCommand implements IOutput {
=======
public class WordCountOutput implements IOutput {
>>>>>>> main:src/main/resources/two/second-test/WordCountOutput.java
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
