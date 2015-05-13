package Calendar.DataStore;

import Calendar.Event.Event;
import Calendar.Event.EventXmlAdapter;

/**
 * Created by ivann on 13.05.15.
 */
public interface FileSystemStore {

    void setStorePath(String path);
    String getStorePath();
    void writeEvent(EventXmlAdapter event);
    void readEvents(DataStore dataStore);
}
