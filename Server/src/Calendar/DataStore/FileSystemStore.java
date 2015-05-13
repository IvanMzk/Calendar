package Calendar.DataStore;

import Calendar.Event.Event;
import Calendar.Event.EventXmlAdapter;

import java.nio.file.Path;
import java.util.List;
import java.util.UUID;

/**
 * Created by ivann on 13.05.15.
 */
public interface FileSystemStore {

    void setStorePath(String path);
    String getStorePath();
    void writeEvent(EventXmlAdapter event);
    List<EventXmlAdapter> readEvents();
    EventXmlAdapter readEvent(Path path);
    EventXmlAdapter removeEvent(UUID id);
}
