package Calendar;

import Calendar.Event.Event;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import Calendar.Service.CalendarService;

import java.rmi.RemoteException;
import java.util.UUID;

/**
 * Created by ivan on 04.05.2015.
 */
public class ClientMain {

    public static void main(String[] args) {

        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("clientContext.xml");
        CalendarService calendarService = (CalendarService) applicationContext.getBean("remoteCalendarService");

        String[] titles = {"t1","t2","t2","t4","t2"};
        String[] descriptions = {"d1","d2","d3","d4","d5"};
        int i = 0;
        Event event;

        try{
            for (String title : titles){
                UUID id = UUID.randomUUID();
                event = calendarService.addEvent(id, title, descriptions[i], null, null, null);
                i++;
                System.out.println(event.toString());
            }
            System.out.println(calendarService.getEventByTitle("t2").toString());
        }catch (RemoteException e){
            System.out.println(e.getStackTrace());
        }
    }
}
