package org.omstu;

import org.omstu.common.executor.IExecutor;
import org.omstu.common.output.IOutput;
import org.omstu.common.ioc.IOC;
import org.omstu.common.ioc.IOCInitializer;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.nio.file.Paths;
import java.util.Map;

public class WordCounterTest {
    private static final String BASE_DIR = "src/main/resources";

    static {
        IOCInitializer.initialize();
    }

    @Test
    public void successCountFirstTest() {
        IExecutor textExecutor = IOC.resolve("get-file-data-executor", Paths.get(String.format("%s/one/word-counter-test-one.txt", BASE_DIR)));
        var text = (String) textExecutor.execute();

        IExecutor countExecutor = IOC.resolve("get-word-count-executor", text);
        var count = (Map<?, ?>) countExecutor.execute();

        Assert.assertEquals(count.get("hello"), 3);
        Assert.assertEquals(count.get("my"), 3);
        Assert.assertEquals(count.get("love"), 1);

        var output = (IOutput) IOC.resolve("word-count-output", count);
        output.execute();
    }

    @Test
    public void successCountSecondTest() {
        IExecutor textExecutor = IOC.resolve("get-file-data-executor", Paths.get(String.format("%s/one/word-counter-test-two.txt", BASE_DIR)));
        var text = (String) textExecutor.execute();

        IExecutor countExecutor = IOC.resolve("get-word-count-executor", text);
        var count = (Map<?, ?>) countExecutor.execute();

        Assert.assertEquals(count.get("bibendum"), 1);
        Assert.assertEquals(count.get("id"), 3);
        Assert.assertEquals(count.get("semper"), 3);

        var output = (IOutput) IOC.resolve("word-count-output", count);
        output.execute();
    }
}
