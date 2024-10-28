package org.omstu;

import org.omstu.common.executors.GetClassDescentExecutor;
import org.omstu.common.executors.GetJavaFileExecutor;
import org.omstu.common.objects.IOC;
import org.omstu.common.output.DescentOutput;
import org.omstu.common.output.WordCountOutput;
import org.omstu.common.strategies.*;

public class IOCInitializer {
    public static void initialize() {
        IOC.register("file-read-strategy", (args) -> new FileReadStrategy().execute(args));
        IOC.register("word-count-strategy", (args) -> new WordCountStrategy().execute(args));
        IOC.register("directory-read-strategy", (args) -> new DirectoryReadStrategy().execute(args));
        IOC.register("initialize-handler-strategy", (args) -> new InitializeHandlerStrategy().execute(args));

        IOC.register("get-java-file-executor", (args) -> new GetJavaFileExecutor(args).get());
        IOC.register("get-class-descent-executor", (args) -> new GetClassDescentExecutor(args).get());

        IOC.register("descent-output", DescentOutput::new);
        IOC.register("word-count-output", WordCountOutput::new);
    }
}
