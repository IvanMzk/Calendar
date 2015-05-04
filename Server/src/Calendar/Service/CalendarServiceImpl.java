package Calendar.Service;

import Calendar.DataStore.DataStore;
import Calendar.Event.Event;
import Calendar.Participant.Participant;

import java.util.*;

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

    @Override
    public Event addParticipant(Event event, Participant participant) {

        Event originEvent = dataStore.getEventByID(event.getId());
        if (null == originEvent)
        {return null;}
        Set<Participant> originEventParticipants = originEvent.getParticipants();
        if (!originEventParticipants.add(participant))
        {
            //participant already present for the event
            return originEvent;
        }

        //no such participant, so create new event with updated participants
        dataStore.removeEventByID(event.getId());
        UUID newId = UUID.randomUUID();
        Event newEvent = new Event.EventBuilder(originEvent)
                .id(newId)
                .participants(originEventParticipants)
                .build();
        newEvent.publish(dataStore);
        return newEvent;
    }

    /*
    *
    *returns list of Events for specified participant on date
    *if date is null returns all Events for participant
     */

    @Override
    public List<Event> getEventByParticipant(Participant participant, GregorianCalendar date) {

        List<Event> result = dataStore.getEventByParticipant(participant);
        if (result == null)
        {return null;}
        if (date == null)
        {return result;}
        for (Iterator<Event> iterator = result.iterator(); iterator.hasNext();){
            Event item = iterator.next();
            if (date.compareTo(item.getStartDate())<0 || date.compareTo(item.getEndDate())>0){
                iterator.remove();
            }
        }
        return result;
    }

    @Override
    public Event addEventOnDay(UUID id, String title, String description, Integer year, Integer month, Integer dayOfMonth, Integer daysLong, Set<Participant> participants) {
        GregorianCalendar startDate = new GregorianCalendar(year, month, dayOfMonth);
        GregorianCalendar endDate = new GregorianCalendar(year, month, dayOfMonth);
        endDate.add(Calendar.DAY_OF_MONTH, daysLong);

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
    public boolean isFree(Participant participant, GregorianCalendar date) {

        List<Event> eventsList = getEventByParticipant(participant, date);
        if (eventsList == null || eventsList.isEmpty()){
            return true;
        }
        return false;
    }
}
