package org.omstu.common.ioc;

import org.omstu.common.executor.*;
import org.omstu.common.output.DescentOutput;
import org.omstu.common.output.WordCountOutput;
import org.omstu.common.processor.FileProcessor;
import org.omstu.common.strategy.FactoryStrategy;

public class IOCInitializer {
    public static void initialize() {
        IOC.register("get-class-descent-executor", new FactoryStrategy(GetClassDescentExecutor::new));
        IOC.register("get-directory-files-list-executor", new FactoryStrategy(GetDirectoryFilesListExecutor::new));
        IOC.register("get-file-data-executor", new FactoryStrategy(GetFileDataExecutor::new));
        IOC.register("get-java-file-executor", new FactoryStrategy(GetJavaFileExecutor::new));
        IOC.register("get-word-count-executor", new FactoryStrategy(GetWordCountExecutor::new));

        IOC.register("file-processor", new FactoryStrategy(FileProcessor::new));

        IOC.register("descent-output", new FactoryStrategy(DescentOutput::new));
        IOC.register("word-count-output", new FactoryStrategy(WordCountOutput::new));
    }
}
