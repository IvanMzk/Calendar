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

        UUID id = UUID.randomUUID();
        Event expectedValue = new Event.EventBuilder(title, id)
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
        Event returnedValue = testClass.addEvent(id, title, description, startDate, endDate,participants);

        //assert returned value
        assertEquals(returnedValue, expectedValue);

        //verify mock
        verify(testClass).addEvent(expectedValue);
    }

    @Test
    public void testAddEvent1() throws Exception {
        //init inputs
        UUID id = UUID.randomUUID();
        Event inputValue = new Event.EventBuilder("testing", id).build();

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
    public void testAddEventOnDay() throws Exception{

        //init inputs
        UUID id = UUID.randomUUID();
        String title = "training";
        String description = "java training at diosoft";
        Integer year = 2015;
        Integer month = Calendar.MAY;
        Integer dayOfMonth = 6;
        Integer daysLong = 2;
        GregorianCalendar startDate = new GregorianCalendar(year, month ,dayOfMonth);
        GregorianCalendar endDate = new GregorianCalendar(year, month ,dayOfMonth+daysLong);
        Set<Participant> participants = new HashSet<Participant>(Arrays.asList(new Person.PersonBuilder("Ivan", "M").build()));

        Event expectedValue = new Event.EventBuilder(title, id)
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
        Event returnedValue = testClass.addEventOnDay(id, title, description, year, month, dayOfMonth, daysLong, participants);

        //assert returned value
        assertEquals(returnedValue, expectedValue);

        //verify mock
        verify(testClass).addEvent(expectedValue);
    }

    @Test
    public void testGetEventByTitle() throws Exception {
        //init inputs
        String inputValue = "test title";
        UUID id = UUID.randomUUID();
        List<Event> expectedValue = Arrays.asList(new Event.EventBuilder("test title", id).build());

        //init mock
        DataStore dataStore = mock(MapDataStoreImpl.class);
        when(dataStore.getEventByTitle(inputValue)).thenReturn(expectedValue);

        //init class to test
        CalendarService calendarService= new CalendarServiceImpl(dataStore);

        //ivoke method to test
        List<Event> returnedValue = calendarService.getEventByTitle(inputValue);

        //verify mock
        verify(dataStore).getEventByTitle(inputValue);

        //assert
        assertEquals(returnedValue, expectedValue);

    }

    @Test
    public void testGetEventByParticipantForAllDates()throws Exception{

        //init inputs
        Participant inputValue = new Person.PersonBuilder("Ivan", "Ivanov")
                .email("ivanovi@yahoo.com")
                .phone("223-445-56")
                .build();

        Set<Participant> participants = new HashSet<Participant>();
        participants.add(inputValue);
        Event event = new Event.EventBuilder("lunch", UUID.randomUUID()).participants(participants).build();
        Event event1 = new Event.EventBuilder("dinner", UUID.randomUUID()).participants(participants).build();
        List<Event> expectedValue = Arrays.asList(event, event1);

        //init mock
        DataStore dataStore = mock(MapDataStoreImpl.class);
        when(dataStore.getEventByParticipant(inputValue)).thenReturn(expectedValue);
        //init class to test
        CalendarService calendarService= new CalendarServiceImpl(dataStore);

        //ivoke method to test
        List<Event> returnedValue = calendarService.getEventByParticipant(inputValue, null);

        //verify mock
        verify(dataStore).getEventByParticipant(inputValue);

        //assert
        assertEquals(returnedValue, expectedValue);
    }

    @Test
    public void testGetEventByParticipantOnDate()throws Exception{

        //init inputs
        Participant inputValue = new Person.PersonBuilder("Ivan", "Ivanov")
                .email("ivanovi@yahoo.com")
                .phone("223-445-56")
                .build();

        GregorianCalendar inputValueDate = new GregorianCalendar(2015, Calendar.MAY, 10, 13, 30);

        GregorianCalendar startDateLunch = new GregorianCalendar(2015, Calendar.MAY, 10, 13, 0);
        GregorianCalendar endDateLunch = new GregorianCalendar(2015, Calendar.MAY, 10, 14, 0);

        GregorianCalendar startDateDinner = new GregorianCalendar(2015, Calendar.MAY, 10, 19, 0);
        GregorianCalendar endDateDinner = new GregorianCalendar(2015, Calendar.MAY, 10, 20, 0);

        Set<Participant> participants = new HashSet<Participant>();
        participants.add(inputValue);
        Event event = new Event.EventBuilder("lunch", UUID.randomUUID()).startDate(startDateLunch).endDate(endDateLunch).participants(participants).build();
        Event event1 = new Event.EventBuilder("dinner", UUID.randomUUID()).startDate(startDateDinner).endDate(endDateDinner).participants(participants).build();
        List<Event> returnFromDataStore = new LinkedList<Event>(Arrays.asList(event, event1));
        List<Event> expectedValue = Arrays.asList(event);


        //init mock
        DataStore dataStore = mock(MapDataStoreImpl.class);
        when(dataStore.getEventByParticipant(inputValue)).thenReturn(returnFromDataStore);
        //init class to test
        CalendarService calendarService= new CalendarServiceImpl(dataStore);

        //ivoke method to test
        List<Event> returnedValue = calendarService.getEventByParticipant(inputValue, inputValueDate);

        //verify mock
        verify(dataStore).getEventByParticipant(inputValue);

        //assert
        assertEquals(returnedValue, expectedValue);
    }



    @Test
    public void testAddParticipantNewParticipant() throws Exception{

        //init inputs
        Participant inputValue = new Person.PersonBuilder("Ivan", "Ivanov")
                .email("ivanovi@yahoo.com")
                .phone("223-445-56")
                .build();

        Participant existingParticipant = new Person.PersonBuilder("Peter", "Petrov")
                .email("petrovptr@gmail.com")
                .phone("256-455-46")
                .build();

        Set<Participant> existingParticipants = new HashSet<Participant>();
        existingParticipants.add(existingParticipant);
        Event event = new Event.EventBuilder("lunch", UUID.randomUUID()).participants(existingParticipants).build();

        Set<Participant> expectedValue = new HashSet<Participant>(Arrays.asList(existingParticipant, inputValue));

        //init mock
        DataStore dataStore = mock(MapDataStoreImpl.class);
        when(dataStore.getEventByID(event.getId())).thenReturn(event);
        //init class to test
        CalendarService calendarService= new CalendarServiceImpl(dataStore);

        //ivoke method to test
        Event returnedValue = calendarService.addParticipant(event, inputValue);

        //verify mock
        verify(dataStore).removeEventByID(event.getId());

        //assert
        assertEquals(returnedValue.getParticipants(), expectedValue);

    }


    @Test
    public void testAddParticipantAlreadyExist() throws Exception{

        //init inputs
        Participant inputValue = new Person.PersonBuilder("Peter", "Petrov")
                .email("petrovptr@gmail.com")
                .phone("256-455-46")
                .build();

        Participant existingParticipant = new Person.PersonBuilder("Peter", "Petrov")
                .email("petrovptr@gmail.com")
                .phone("256-455-46")
                .build();

        Set<Participant> existingParticipants = new HashSet<Participant>();
        existingParticipants.add(existingParticipant);
        Event event = new Event.EventBuilder("lunch", UUID.randomUUID()).participants(existingParticipants).build();

        Set<Participant> expectedValue = new HashSet<Participant>(Arrays.asList(existingParticipant));

        //init mock
        DataStore dataStore = mock(MapDataStoreImpl.class);
        when(dataStore.getEventByID(event.getId())).thenReturn(event);
        //init class to test
        CalendarService calendarService= new CalendarServiceImpl(dataStore);

        //ivoke method to test
        Event returnedValue = calendarService.addParticipant(event, inputValue);

        //verify mock

        //assert
        assertEquals(returnedValue.getParticipants(), expectedValue);
        assertEquals(returnedValue, event);

    }



    @Test
    public void testIsFree() throws Exception{

        //init inputs
        Participant inputValue = new Person.PersonBuilder("Ivan", "Ivanov")
                .email("ivanovi@yahoo.com")
                .phone("223-445-56")
                .build();

        GregorianCalendar inputValueDate = new GregorianCalendar(2015, Calendar.MAY, 10, 14, 30);

        GregorianCalendar startDateLunch = new GregorianCalendar(2015, Calendar.MAY, 10, 13, 0);
        GregorianCalendar endDateLunch = new GregorianCalendar(2015, Calendar.MAY, 10, 14, 0);

        GregorianCalendar startDateDinner = new GregorianCalendar(2015, Calendar.MAY, 10, 19, 0);
        GregorianCalendar endDateDinner = new GregorianCalendar(2015, Calendar.MAY, 10, 20, 0);

        Set<Participant> participants = new HashSet<Participant>();
        participants.add(inputValue);
        Event event = new Event.EventBuilder("lunch", UUID.randomUUID()).startDate(startDateLunch).endDate(endDateLunch).participants(participants).build();
        Event event1 = new Event.EventBuilder("dinner", UUID.randomUUID()).startDate(startDateDinner).endDate(endDateDinner).participants(participants).build();
        List<Event> returnFromDataStore = new LinkedList<Event>(Arrays.asList(event, event1));

        boolean expectedValue = true;


        //init class to test
        DataStore dataStore = new MapDataStoreImpl();
        CalendarService calendarService= new CalendarServiceImpl(dataStore);
        calendarService.addEvent(event);
        calendarService.addEvent(event1);

        //init stub
        CalendarService testClass = spy(calendarService);

        //ivoke method to test
        boolean returnedValue = testClass.isFree(inputValue, inputValueDate);

        //verify mock
        verify(testClass).getEventByParticipant(inputValue, inputValueDate);

        //assert
        assertEquals(returnedValue, expectedValue);
    }


}