package Calendar;

import Calendar.DataStore.DataStore;
import Calendar.DataStore.MapDataStoreImpl;
import Calendar.Event.Event;
import Calendar.Service.CalendarService;
import Calendar.Service.CalendarServiceImpl;

import java.util.UUID;

/**
 * Created by ivann on 27.04.15.
 */
public class Main {


    public static void main(String[] args) {

        DataStore dataStore = new MapDataStoreImpl();
        CalendarService calendarService = new CalendarServiceImpl(dataStore);
        String[] titles = {"t1","t2","t2","t4","t2"};
        String[] descriptions = {"d1","d2","d3","d4","d5"};
        int i = 0;
        Event event;
        for (String title : titles)
        {
            UUID id = UUID.randomUUID();
            event = calendarService.addEvent(id, title, descriptions[i], null, null, null);
            i++;
            System.out.println(event.toString());
        }

        System.out.println(calendarService.getEventByTitle("t2").toString());



    }

}
