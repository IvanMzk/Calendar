package Calendar;

import Calendar.DataStore.DataStore;
import Calendar.DataStore.MapDataStoreImpl;
import Calendar.Event.Event;
import Calendar.Service.CalendarService;
import Calendar.Service.CalendarServiceImpl;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.UUID;

/**
 * Created by ivann on 27.04.15.
 */
public class Main {


    public static void main(String[] args) {

        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("applicationContext.xml");

    }

}
