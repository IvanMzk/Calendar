package Calendar.Event;

import Calendar.DataStore.DataStore;
import Calendar.DataStore.MapDataStoreImpl;
import org.junit.Test;

import javax.xml.crypto.Data;
import java.util.UUID;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class EventTest {

    @Test
    public void testPublish() throws Exception {

        //init class to test
        Event testClass = new Event.EventBuilder("lunch", UUID.randomUUID()).build();

        //init mock
        DataStore dataStore = mock(MapDataStoreImpl.class);


        //call test method
        testClass.publish(dataStore);

        //assert
        verify(dataStore).addEvent(testClass);
    }
}