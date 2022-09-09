import org.apache.commons.io.FilenameUtils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

public class FileSorter implements EventListener {
    List<DirectoryBuilder> directoryBuilderList;
    ConfigBuilder configBuilder;

    FileSorter(List<DirectoryBuilder> directoryBuilderList, ConfigBuilder configBuilder) {
        this.directoryBuilderList = directoryBuilderList;
        this.configBuilder = configBuilder;
    }

    @Override
    public void update(String eventType, Path path) {
        try {
            BasicFileAttributes attr = Files.readAttributes(path.toFile().toPath(), BasicFileAttributes.class);

            for (DirectoryBuilder directoryBuilder : directoryBuilderList) {


                var hours = attr.creationTime().to(TimeUnit.HOURS);

                var ext = FilenameUtils.getExtension(path.toString());

                if (Objects.equals(directoryBuilder.getName(), "DEV") && (hours % 2 == 0 && ext.equals("jar") || ext.equals("xml"))) {

                    if (directoryBuilder.moveFile(path)) {
                        configBuilder.addCount("DEV");

                        break;
                    }
                }

                if (Objects.equals(directoryBuilder.getName(), "TEST") && hours % 2 == 1 && ext.equals("jar")) {
                    if (directoryBuilder.moveFile(path)) {
                        configBuilder.addCount("TEST");
                        break;
                    }
                }

            }
            configBuilder.write();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
