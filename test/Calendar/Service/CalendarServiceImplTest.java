package Calendar.Service;

import Calendar.DataStore.DataStore;
import Calendar.DataStore.MapDataStoreImpl;
import Calendar.Event.Event;
import Calendar.Participant.Participant;
import Calendar.Participant.Person;
import org.junit.Test;

import java.util.*;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class CalendarServiceImplTest {

    @Test
    public void testAddEvent() throws Exception {
        //init inputs
        String title = "training";
        String description = "java training at diosoft";
        GregorianCalendar startDate = new GregorianCalendar(2015, Calendar.APRIL, Calendar.TUESDAY, 9, 0);
        GregorianCalendar endDate = new GregorianCalendar(2015, Calendar.APRIL, Calendar.TUESDAY, 11, 0);
        Set<Participant> participants = new HashSet<Participant>(Arrays.asList(new Person.PersonBuilder("Ivan", "M").build()));

        Event expectedValue = new Event.EventBuilder(title)
                .description(description)
                .startDate(startDate)
                .endDate(endDate)
                .participants(participants)
                .build();

        //init mock

        //init class to test
        DataStore dataStore = new MapDataStoreImpl();
        CalendarService calendarService = new CalendarServiceImpl(dataStore);
        CalendarService testClass = spy(calendarService);

        //ivoke method to test
        Event returnedValue = testClass.addEvent(title, description, startDate, endDate,participants);

        //assert returned value
        assertEquals(returnedValue, expectedValue);

        //verify mock
        verify(testClass).addEvent(expectedValue);
    }

    @Test
    public void testAddEvent1() throws Exception {
        //init inputs
        Event inputValue = new Event.EventBuilder("testing").build();

        //init mock
        Event event = spy(inputValue);

        //init class to test
        DataStore dataStore = new MapDataStoreImpl();
        CalendarService testClass = new CalendarServiceImpl(dataStore);

        //invoke method to test
        testClass.addEvent(event);

        //verify mock
        verify(event).publish(dataStore);
    }

    @Test
    public void testGetEventByTitle() throws Exception {
        //init inputs
        String inputValue = "test title";
        List<Event> expectedValue = Arrays.asList(new Event.EventBuilder("test title").build());

        //init mock
        DataStore dataStore = mock(MapDataStoreImpl.class);
        when(dataStore.getEventByTitle(inputValue)).thenReturn(expectedValue);

        //init class to test
        CalendarService calendarService= new CalendarServiceImpl(dataStore);

        //ivoke method to test
        List<Event> returnedValue = calendarService.getEventByTitle(inputValue);

        //assert
        assertEquals(returnedValue, expectedValue);

    }
}