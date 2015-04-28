package Calendar.Service;

import Calendar.DataStore.DataStore;
import Calendar.Event.Event;
import Calendar.Participant.Participant;

import java.util.GregorianCalendar;
import java.util.List;
import java.util.Set;
import java.util.UUID;

/**
 * Created by ivann on 27.04.15.
 */
public class CalendarServiceImpl implements CalendarService{


    private DataStore dataStore;

    public CalendarServiceImpl(DataStore dataStore) {
        this.dataStore = dataStore;
    }

    @Override
    public void addEvent(Event event) {
        event.publish(dataStore);
    }

    @Override
    public Event addEvent(UUID id, String title, String description, GregorianCalendar startDate, GregorianCalendar endDate, Set<Participant> participants) {

        Event event = new Event.EventBuilder(title, id)
                .description(description)
                .startDate(startDate)
                .endDate(endDate)
                .participants(participants)
                .build();

        addEvent(event);
        return event;
    }

    @Override
    public List<Event> getEventByTitle(String title) {

        return dataStore.getEventByTitle(title);
    }


}
