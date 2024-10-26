package org.omstu;

import org.omstu.common.interfaces.IOutput;
import org.omstu.common.objects.IOC;
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
        var text = (String) IOC.resolve("FileRead", Paths.get(String.format("%s/one/word-counter-test-one.txt", BASE_DIR)));

        var result = (Map<?, ?>) IOC.resolve("WordCount", text);

        Assert.assertEquals(result.get("hello"), 3);
        Assert.assertEquals(result.get("my"), 3);
        Assert.assertEquals(result.get("love"), 1);

        var command = (IOutput) IOC.resolve("ConsoleOutput", result);
        command.execute();
    }

    @Test
    public void successCountSecondTest() {
        var text = (String) IOC.resolve("FileRead", Paths.get(String.format("%s/one/word-counter-test-two.txt", BASE_DIR)));

        var result = (Map<?, ?>) IOC.resolve("WordCount", text);

        Assert.assertEquals(result.get("bibendum"), 1);
        Assert.assertEquals(result.get("id"), 3);
        Assert.assertEquals(result.get("semper"), 3);

        var command = (IOutput) IOC.resolve("ConsoleOutput", result);
        command.execute();
    }
}
