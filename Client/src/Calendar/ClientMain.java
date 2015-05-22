package Calendar;

import Calendar.Event.Event;
import Calendar.Participant.Participant;
import Calendar.Participant.Person;
import Calendar.Service.CalendarService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.rmi.RemoteException;
import java.util.*;

/**
 * Created by ivan on 04.05.2015.
 */
public class ClientMain {

    public static void main(String[] args) {

        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("clientContext.xml");
        CalendarService calendarService = (CalendarService) applicationContext.getBean("remoteCalendarService");
    //local code review (vtegza): clean up commented code @ 5/20/2015

        String[] titles = {"lunch","rest","meeting","work","meeting"};
        String[] descriptions = {"lunch with colleagues","in some restaurant","job","some project","some project"};

        GregorianCalendar startDate = new GregorianCalendar(2015, Calendar.MAY, 13, 12, 0);
        GregorianCalendar endDate = new GregorianCalendar(2015, Calendar.MAY, 13, 13, 0);
        GregorianCalendar middleDate = new GregorianCalendar(2015, Calendar.MAY, 13, 15, 30);

        Participant participant = new Person.PersonBuilder("Ivan", "Ivanov")
                .email("ivanovi@yahoo.com")
                .phone("223-445-56")
                .build();

        Participant participant1 = new Person.PersonBuilder("Peter", "Petrov")
                .email("petrovptr@gmail.com")
                .phone("256-455-46")
                .build();

        Participant participant2 = new Person.PersonBuilder("Sidor", "Sidorov")
                .email("sidorov@ukr.net")
                .phone("597-455-88")
                .build();

        Set<Participant> participants = new HashSet<Participant>(Arrays.asList(participant, participant1));
        Set<Participant> participants1 = new HashSet<Participant>(Arrays.asList(participant2));


        int i = 0;
        Event event;

        try{

            for (i = 0; i<10; i++){
                startDate.add(Calendar.DAY_OF_MONTH,1);
                endDate.add(Calendar.DAY_OF_MONTH, 1);
                event = calendarService.addEvent(UUID.randomUUID(), "Some deals", "business" , startDate, endDate, participants1);
            }
/*
            for (String title : titles){

                UUID id = UUID.randomUUID();
                startDate.add(Calendar.HOUR, i);
                endDate.add(Calendar.HOUR, i);
                event = calendarService.addEvent(id, title, descriptions[i], startDate, endDate, participants);
                i++;
                System.out.println(event.toString());
            }

            for (Event item : calendarService.getEventByTitle("rest")){
                calendarService.addParticipant(item, participant2);
            }
*/

        }catch (RemoteException e){
            System.out.println(e.getStackTrace());
        }

        try{
            for (Event item : calendarService.getEventByParticipant(participant2, middleDate)){
                if (item != null){
                    System.out.println(item.toString());
                }
            }


        }catch (RemoteException e){
            e.printStackTrace();
        }


    }
}
