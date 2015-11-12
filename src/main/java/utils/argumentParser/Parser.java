package utils.argumentParser;

import com.beust.jcommander.Parameter;
import utils.argumentParser.validation.DirectoryValidation;
import utils.argumentParser.validation.PortValidation;
import utils.argumentParser.validation.Threads;

public class Parser {
    @Parameter(names = {"-r"}, description = "root directory", validateWith = DirectoryValidation.class)
    private String rootDir = "./static/";

    @Parameter(names = {"-p"}, description = "port", validateWith = PortValidation.class)
    private int port = 80;

    @Parameter(names = {"-t"}, description = "number of threads", validateWith = Threads.class)
    private int threadsNum = 20;

    public String getRootDir() {
        return rootDir;
    }

    public int getPort() {
        return port;
    }

    public int getThreadsNum() {
        return threadsNum;
    }
}
