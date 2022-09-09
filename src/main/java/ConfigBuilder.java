import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.util.HashMap;


public class ConfigBuilder  {

    HashMap<String, Integer> counts;

    String name;
    Path path;

    File file;

    ConfigBuilder(Path path) {
        this.name = "config.txt";
        this.path = path;
        this.counts = new HashMap<>() {{
            put("DEV", 0);
            put("TEST", 0);
        }};
    }


    void create() {
        try {
            file = new File(String.format("%s/%s", path, name));

            if (!file.exists()) {
                file.createNewFile();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    void addCount(String folderName) {

        if (counts.containsKey(folderName)) {
            counts.put(folderName, counts.get(folderName) + 1);
        } else {
            counts.put(folderName, 1);
        }

        System.out.println(counts);
    }

    void write() {
        try {
            FileWriter write = new FileWriter(file, false);

            int countOfAll = 0;
            for (int value : counts.values()) {
                countOfAll += value;
            }


            write.write(String.format("Wszystkich %s, DEV: %s, TEST %s", countOfAll, counts.get("DEV"), counts.get("TEST")));
            write.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
