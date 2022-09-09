import java.io.IOException;
import java.nio.file.*;

public class DirectoryWatcher {
    EventManager events;
    Path path;
    WatchService watchService;

    DirectoryWatcher(String path) throws IOException {
        System.out.println(path);
        this.path = Paths.get(path);
        watchService = FileSystems.getDefault().newWatchService();
        this.events = new EventManager("create");
    }

    void listen()  {
        try{
            path.register(watchService, StandardWatchEventKinds.ENTRY_CREATE);

            WatchKey key;

            while((key=watchService.take()) != null){
                for(WatchEvent<?> event : key.pollEvents()){
                    events.notify("create", Paths.get(String.format("%s/%s",this.path.toString(),event.context().toString())));
                }
                key.reset();
            }

        } catch (IOException | InterruptedException e) {
            System.out.println(e.getMessage());
        }
    }
}
