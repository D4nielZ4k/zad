import java.nio.file.Path;

public interface EventListener {
    void update(String eventType, Path path);
}
