import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

public class DirectoryBuilder {
    String name;
    Path path;

    File directory;

    DirectoryBuilder(String name, Path path) {
        this.name = name;
        this.path = path;
    }

    void create() {
        directory = new File(String.format("%s/%s", path, name));

        if (!directory.exists()) {
            directory.mkdir();
        }
    }

    boolean moveFile(Path path) {
        try {
            Files.move(path, Paths.get(String.format("%s/%s",this.directory.toPath(), path.getFileName())), StandardCopyOption.REPLACE_EXISTING);
            return true;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    File getDirectory() {
        return this.directory;
    }

    String getName() {
        return this.name;
    }
}
