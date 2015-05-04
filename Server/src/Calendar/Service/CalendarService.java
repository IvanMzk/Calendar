package Calendar.Service;

import Calendar.Event.Event;
import Calendar.Participant.Participant;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Set;
import java.util.UUID;

/**
 * Created by ivann on 27.04.15.
 */
public interface CalendarService extends Remote {

    void addEvent(Event event) throws RemoteException;
    Event addEvent(UUID id, String title, String description, GregorianCalendar startDate, GregorianCalendar endDate, Set<Participant> participants) throws RemoteException;
    Event addEventOnDay(UUID id, String title, String description, Integer year, Integer month, Integer dayOfMonth, Integer daysLong, Set<Participant> participants) throws RemoteException;
    List<Event> getEventByTitle(String title) throws RemoteException;
    List<Event> getEventByParticipant(Participant participant, GregorianCalendar date) throws RemoteException;
    Event addParticipant(Event event, Participant participant) throws RemoteException;
    boolean isFree(Participant participant, GregorianCalendar date) throws RemoteException;


}
