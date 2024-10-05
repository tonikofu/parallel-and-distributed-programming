package org.omstu;

import org.omstu.common.IoC;
import org.omstu.common.JavaClassFile;
import org.omstu.common.commands.DescentOutput;
import org.omstu.common.strategies.BuildDescentStrategy;
import org.omstu.common.strategies.DirectoryReadStrategy;
import org.omstu.common.strategies.FileReadStrategy;
import org.omstu.common.strategies.WordCountStrategy;
import org.omstu.interfaces.IOutput;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.nio.file.Path;
import java.util.List;
import java.util.Map;

@SuppressWarnings(value="unchecked")
public class ClassDescentTest {
    private static final String BASE_DIR = "src/main/resources";

    static {
        IoC.register("FileRead", (args) -> new FileReadStrategy().execute(args));
        IoC.register("WordCount", (args) -> new WordCountStrategy().execute(args));
        IoC.register("DirectoryRead", (args) -> new DirectoryReadStrategy().execute(args));
        IoC.register("BuildDescent", (args) -> new BuildDescentStrategy().execute(args));

        IoC.register("ConsoleOutput", DescentOutput::new);
        IoC.register("GetJavaFile", JavaClassFile::new);
    }

    @Test
    public void successClassDescentBuildFirstTest() {
        var files = (List<Path>) IoC.resolve("DirectoryRead", String.format("%s/two/first-test", BASE_DIR));

        var descent = (Map<String, List<String>>) IoC.resolve("BuildDescent", files);

        Assert.assertEquals(descent.get("Angle").size(), 1);
        Assert.assertEquals(descent.get("IPolygon").size(), 2);
        Assert.assertEquals(descent.get("Exception").size(), 1);

        var command = (IOutput) IoC.resolve("ConsoleOutput", descent);
        command.execute();
    }

    @Test
    public void successClassDescentBuildSecondTest() {
        var files = (List<?>) IoC.resolve("DirectoryRead", String.format("%s/two/second-test", BASE_DIR));

        var descent = (Map<String, List<String>>) IoC.resolve("BuildDescent", files);

        Assert.assertEquals(descent.get("IJavaFile").size(), 1);
        Assert.assertEquals(descent.get("IOutput").size(), 2);
        Assert.assertEquals(descent.get("IStrategy").size(), 4);

        var command = (IOutput) IoC.resolve("ConsoleOutput", descent);
        command.execute();
    }
}
