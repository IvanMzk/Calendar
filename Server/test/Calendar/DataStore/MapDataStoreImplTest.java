package Calendar.DataStore;

import Calendar.Event.Event;
import Calendar.Participant.Participant;
import Calendar.Participant.Person;
import org.junit.Test;

import java.util.*;

import static org.junit.Assert.*;

public class MapDataStoreImplTest {

    @Test
    public void testAddEvent() throws Exception {
        //init inputs
        UUID id = UUID.randomUUID();
        Event inputValue = new Event.EventBuilder("test title",id)
                .description("test description")
                .startDate(new GregorianCalendar(2015, Calendar.APRIL, Calendar.TUESDAY, 9,0))
                .endDate(new GregorianCalendar(2015, Calendar.APRIL, Calendar.TUESDAY, 11, 0))
                .participants(new HashSet<Participant>(Arrays.asList(new Person.PersonBuilder("Ivan", "M").build())))
                .build();


        Event expectedValue = new Event.EventBuilder("test title",id)
                .description("test description")
                .startDate(new GregorianCalendar(2015, Calendar.APRIL, Calendar.TUESDAY, 9,0))
                .endDate(new GregorianCalendar(2015, Calendar.APRIL, Calendar.TUESDAY, 11, 0))
                .participants(new HashSet<Participant>(Arrays.asList(new Person.PersonBuilder("Ivan", "M").build())))
                .build();

        //init class to test
        DataStore dataStore = new MapDataStoreImpl();

        //invoke method to test
        dataStore.addEvent(inputValue);

        //assert
        Event returnedValue = dataStore.getEventByID(inputValue.getId());
        assertEquals(expectedValue, returnedValue);

    }

    @Test
    public void testGetEventByID() throws Exception {

        //init inputs
        UUID inputValue = UUID.randomUUID();
        Event event = new Event.EventBuilder("test title",inputValue)
                .description("test description")
                .startDate(new GregorianCalendar(2015, Calendar.APRIL, Calendar.TUESDAY, 9,0))
                .endDate(new GregorianCalendar(2015, Calendar.APRIL, Calendar.TUESDAY, 11,0))
                .participants(new HashSet<Participant>(Arrays.asList(new Person.PersonBuilder("Ivan", "M").build())))
                .build();



        Event expectedValue = new Event.EventBuilder("test title",inputValue)
                .description("test description")
                .startDate(new GregorianCalendar(2015, Calendar.APRIL, Calendar.TUESDAY, 9,0))
                .endDate(new GregorianCalendar(2015, Calendar.APRIL, Calendar.TUESDAY, 11,0))
                .participants(new HashSet<Participant>(Arrays.asList(new Person.PersonBuilder("Ivan", "M").build())))
                .build();

        //init class to test
        DataStore dataStore = new MapDataStoreImpl();
        dataStore.addEvent(event);

        //invoke method to test
        Event returnedValue = dataStore.getEventByID(inputValue);

        //assert
        assertEquals(expectedValue, returnedValue);

    }

    @Test
    public void testRemoveEventByID() throws Exception {

        //init inputs
        UUID inputValue = UUID.randomUUID();
        Event event = new Event.EventBuilder("test title", inputValue)
                .description("test description")
                .startDate(new GregorianCalendar(2015, Calendar.APRIL, Calendar.TUESDAY, 9,0))
                .endDate(new GregorianCalendar(2015, Calendar.APRIL, Calendar.TUESDAY, 11,0))
                .participants(new HashSet<Participant>(Arrays.asList(new Person.PersonBuilder("Ivan", "M").build())))
                .build();




        Event expectedValue = new Event.EventBuilder("test title", inputValue)
                .description("test description")
                .startDate(new GregorianCalendar(2015, Calendar.APRIL, Calendar.TUESDAY, 9,0))
                .endDate(new GregorianCalendar(2015, Calendar.APRIL, Calendar.TUESDAY, 11,0))
                .participants(new HashSet<Participant>(Arrays.asList(new Person.PersonBuilder("Ivan", "M").build())))
                .build();

        //init class to test
        DataStore dataStore = new MapDataStoreImpl();
        dataStore.addEvent(event);

        //invoke method to test
        Event returnedValue = dataStore.removeEventByID(inputValue);
        //local code review (vtegza): not used @ 04.05.15
        Event returnedNullValue = dataStore.removeEventByID(inputValue);

        //assert
        assertEquals(expectedValue, returnedValue);
        //assertNull(returnedNullValue);
    }

    @Test
    public void testGetEventByTitle() throws Exception {
        //init inputs
        UUID id = UUID.randomUUID();
        Event event = new Event.EventBuilder("test title",id)
                .description("test description")
                .startDate(new GregorianCalendar(2015, Calendar.APRIL, Calendar.TUESDAY, 9,0))
                .endDate(new GregorianCalendar(2015, Calendar.APRIL, Calendar.TUESDAY, 11,0))
                .participants(new HashSet<Participant>(Arrays.asList(new Person.PersonBuilder("Ivan", "M").build())))
                .build();

        String inputValue = "test title";


        List<Event> expectedValue = Arrays.asList(event);


        //init class to test
        DataStore dataStore = new MapDataStoreImpl();
        dataStore.addEvent(event);

        //invoke method to test
        List<Event> returnedValue = dataStore.getEventByTitle(inputValue);

        //assert
        assertEquals(expectedValue, returnedValue);
    }
}