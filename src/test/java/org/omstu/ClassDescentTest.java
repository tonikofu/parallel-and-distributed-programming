package org.omstu;

import org.omstu.common.interfaces.IOutput;
import org.omstu.common.objects.IOC;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.List;
import java.util.Map;

@SuppressWarnings(value="unchecked")
public class ClassDescentTest {
    private static final String BASE_DIR = "src/main/resources";

    static {
        IOCInitializer.initialize();
    }

    @Test
    public void successClassDescentBuildFirstTest() {
        var files = (List<?>) IOC.resolve("DirectoryRead", String.format("%s/two/first-test", BASE_DIR));

        var descent = (Map<String, List<String>>) IOC.resolve("BuildDescent", files);

        Assert.assertEquals(descent.get("Angle").size(), 1);
        Assert.assertEquals(descent.get("IPolygon").size(), 2);
        Assert.assertEquals(descent.get("Exception").size(), 1);

        var command = (IOutput) IOC.resolve("ConsoleOutput", descent);
        command.execute();
    }

    @Test
    public void successClassDescentBuildSecondTest() {
        var files = (List<?>) IOC.resolve("DirectoryRead", String.format("%s/two/second-test", BASE_DIR));

        var descent = (Map<String, List<String>>) IOC.resolve("BuildDescent", files);

        Assert.assertEquals(descent.get("IJavaFile").size(), 1);
        Assert.assertEquals(descent.get("IOutput").size(), 3);
        Assert.assertEquals(descent.get("IStrategy").size(), 4);

        var command = (IOutput) IOC.resolve("ConsoleOutput", descent);
        command.execute();
    }

    @Test
    public void springFrameworkDescentTest() {
        var files = (List<?>) IOC.resolve("DirectoryRead", "spring-framework-main");

        var descent = (Map<String, List<String>>) IOC.resolve("BuildDescent", files);

        Assert.assertEquals(descent.get("AsyncHandlerMethodReturnValueHandler").size(), 2);
        Assert.assertEquals(descent.get("GenericTableMetaDataProvider").size(), 4);
        Assert.assertEquals(descent.get("SerialFormat").size(), 2);

        var command = (IOutput) IOC.resolve("ConsoleOutput", descent);
        command.execute();
    }
}
