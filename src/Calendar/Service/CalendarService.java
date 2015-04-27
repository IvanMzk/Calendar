package Calendar.Service;

import Calendar.Event.Event;
import Calendar.Participant.Participant;

import java.util.GregorianCalendar;
import java.util.List;
import java.util.Set;

/**
 * Created by ivann on 27.04.15.
 */
public interface CalendarService {

    void addEvent(Event event);
    void addEvent(String title, String description, GregorianCalendar startDate, GregorianCalendar endDate, Set<Participant> participants);
    List<Event> getEventByTitle(String title);


}
