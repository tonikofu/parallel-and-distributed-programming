package org.omstu;

import org.omstu.common.executor.IExecutor;
import org.omstu.common.output.IOutput;
import org.omstu.common.ioc.IOC;
import org.omstu.common.ioc.IOCInitializer;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.nio.file.Path;
import java.util.List;
import java.util.Map;

public class ClassDescentTest {
    private static final String BASE_DIR = "src/main/resources";

    static {
        IOCInitializer.initialize();
    }

    @Test
    public void successClassDescentBuildFirstTest() {
        IExecutor fileExecutor = IOC.resolve("get-directory-files-list-executor", String.format("%s/two/first-test", BASE_DIR));
        var files = (List<Path>) fileExecutor.execute();

        IExecutor descentExecutor = IOC.resolve("get-class-descent-executor", files, 2);
        var descent = (Map<String, List<String>>) descentExecutor.execute();

        Assert.assertEquals(descent.get("Angle").size(), 1);
        Assert.assertEquals(descent.get("IPolygon").size(), 2);
        Assert.assertEquals(descent.get("Exception").size(), 1);

        var output = (IOutput) IOC.resolve("descent-output", descent);
        output.execute();
    }

    @Test
    public void successClassDescentBuildSecondTest() {
        IExecutor fileExecutor = IOC.resolve("get-directory-files-list-executor", String.format("%s/two/second-test", BASE_DIR));
        var files = (List<Path>) fileExecutor.execute();

        IExecutor descentExecutor = IOC.resolve("get-class-descent-executor", files, 1);
        var descent = (Map<String, List<String>>) descentExecutor.execute();

        Assert.assertEquals(descent.get("IJavaFile").size(), 1);
        Assert.assertEquals(descent.get("IOutput").size(), 3);
        Assert.assertEquals(descent.get("IStrategy").size(), 4);

        var output = (IOutput) IOC.resolve("descent-output", descent);
        output.execute();
    }

    @Test
    public void springFrameworkDescentTest() {
        IExecutor fileExecutor = IOC.resolve("get-directory-files-list-executor", "spring-framework-main");
        var files = (List<Path>) fileExecutor.execute();

        IExecutor descentExecutor = IOC.resolve("get-class-descent-executor", files, 16);
        var descent = (Map<String, List<String>>) descentExecutor.execute();

        Assert.assertEquals(descent.get("AsyncHandlerMethodReturnValueHandler").size(), 2);
        Assert.assertEquals(descent.get("GenericTableMetaDataProvider").size(), 4);
        Assert.assertEquals(descent.get("SerialFormat").size(), 2);

        var output = (IOutput) IOC.resolve("descent-output", descent);
        output.execute();
    }
}
