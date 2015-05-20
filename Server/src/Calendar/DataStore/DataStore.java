//local code review (vtegza): packages name should be in lower case, ex: calendar.datastore @ 04.05.15
//local code review (vtegza): implement existing code review comments @ 5/20/2015
package Calendar.DataStore;

import Calendar.Event.Event;
import Calendar.Participant.Participant;

import java.util.List;
import java.util.UUID;

/**
 * Created by ivann on 27.04.15.
 */
public interface DataStore {

    void addEvent(Event event);
    Event removeEventByID(UUID id);
    Event getEventByID(UUID id);
    List<Event> getEventByTitle(String title);
    List<Event> getEventByParticipant(Participant participant);
    void loadEvents(FileSystemStore fileSystemStore);

}
