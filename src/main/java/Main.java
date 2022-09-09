import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) throws IOException {
        var mainPath=  Paths.get(System.getProperty("user.home"));

        DirectoryBuilder homeDirectory = new DirectoryBuilder("HOME", mainPath);
        homeDirectory.create();

        ConfigBuilder configBuilder = new ConfigBuilder(homeDirectory.getDirectory().toPath());
        configBuilder.create();
        configBuilder.write();


        DirectoryBuilder devDirectory = new DirectoryBuilder("DEV", mainPath);
        devDirectory.create();
        DirectoryBuilder testDirectory = new DirectoryBuilder("TEST", mainPath);
        testDirectory.create();

        List<DirectoryBuilder> directoryBuilderList = new ArrayList<>();
        directoryBuilderList.add(devDirectory);
        directoryBuilderList.add(testDirectory);

        DirectoryWatcher directoryWatcher = new DirectoryWatcher(homeDirectory.getDirectory().toString());
        directoryWatcher.events.subscribe("create", new FileSorter(directoryBuilderList,configBuilder));
        directoryWatcher.listen();


    }
}
