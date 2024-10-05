package org.omstu;

import org.omstu.common.IoC;
import org.omstu.common.commands.WordCountOutput;
import org.omstu.common.strategies.FileReadStrategy;
import org.omstu.common.strategies.WordCountStrategy;
import org.omstu.interfaces.IOutput;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.nio.file.Paths;
import java.util.Map;

public class WordCounterTest {
    private static final String BASE_DIR = "src/main/resources";

    static {
        IoC.register("FileRead", (args) -> new FileReadStrategy().execute(args));
        IoC.register("WordCount", (args) -> new WordCountStrategy().execute(args));

        IoC.register("ConsoleOutput", WordCountOutput::new);
    }

    @Test
    public void successCountFirstTest() {
        var text = (String) IoC.resolve("FileRead", Paths.get(String.format("%s/one/word-counter-test-one.txt", BASE_DIR)));

        var result = (Map<?, ?>) IoC.resolve("WordCount", text);

        Assert.assertEquals(result.get("hello"), 3);
        Assert.assertEquals(result.get("my"), 3);
        Assert.assertEquals(result.get("love"), 1);

        var command = (IOutput) IoC.resolve("ConsoleOutput", result);
        command.execute();
    }

    @Test
    public void successCountSecondTest() {
        var text = (String) IoC.resolve("FileRead", Paths.get(String.format("%s/one/word-counter-test-two.txt", BASE_DIR)));

        var result = (Map<?, ?>) IoC.resolve("WordCount", text);

        Assert.assertEquals(result.get("bibendum"), 1);
        Assert.assertEquals(result.get("id"), 3);
        Assert.assertEquals(result.get("semper"), 3);

        var command = (IOutput) IoC.resolve("ConsoleOutput", result);
        command.execute();
    }
}
