package org.omstu;

import org.omstu.common.objects.IOC;
import org.omstu.common.objects.JavaClassFile;
import org.omstu.common.output.DescentOutput;
import org.omstu.common.strategies.*;

public class IOCInitializer {
    public static void initialize() {
        IOC.register("FileRead", (args) -> new FileReadStrategy().execute(args));
        IOC.register("WordCount", (args) -> new WordCountStrategy().execute(args));
        IOC.register("DirectoryRead", (args) -> new DirectoryReadStrategy().execute(args));
        IOC.register("BuildDescent", (args) -> new BuildDescentStrategy().execute(args));
        IOC.register("InitializeHandler", (args) -> new InitializeHandlerStrategy().execute(args));

        IOC.register("ConsoleOutput", DescentOutput::new);
        IOC.register("GetJavaFile", JavaClassFile::new);
    }
}
