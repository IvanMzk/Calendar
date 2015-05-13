package Calendar.Event;

import Calendar.Participant.Participant;
import Calendar.Participant.ParticipantXmlAdapter;
import Calendar.Participant.Person;
import Calendar.Participant.PersonXmlAdapter;

import javax.xml.bind.annotation.*;
import java.util.GregorianCalendar;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

/**
 * Created by ivann on 13.05.15.
 */

@XmlRootElement(name="event")
@XmlType(name="event", propOrder = {"id", "title", "description", "startDate", "endDate", "participants"})
public class EventXmlAdapter {

    private UUID id;
    private String title;
    private String description;
    private GregorianCalendar startDate;
    private GregorianCalendar endDate;
    //add type element after adding new kind of participant
    @XmlElementWrapper(name="participants")
    @XmlElements(value = {@XmlElement(name="person",type = PersonXmlAdapter.class)})
    private Set<ParticipantXmlAdapter> participants;


    public EventXmlAdapter(){}

    public EventXmlAdapter(Event event){

        id = event.getId();
        title = event.getTitle();
        description = event.getDescription();
        startDate = event.getStartDate();
        endDate = event.getEndDate();
        if (null != event.getParticipants()){
            participants = new HashSet<ParticipantXmlAdapter>();
            for(Participant item : event.getParticipants())
            {participants.add(item.getXmlAdapter());}
        }else {participants = null;}


    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public GregorianCalendar getStartDate() {
        return startDate;
    }

    public void setStartDate(GregorianCalendar startDate) {
        this.startDate = startDate;
    }

    public GregorianCalendar getEndDate() {
        return endDate;
    }

    public void setEndDate(GregorianCalendar endDate) {
        this.endDate = endDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof EventXmlAdapter)) return false;

        EventXmlAdapter that = (EventXmlAdapter) o;

        if (description != null ? !description.equals(that.description) : that.description != null) return false;
        if (endDate != null ? !endDate.equals(that.endDate) : that.endDate != null) return false;
        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (participants != null ? !participants.equals(that.participants) : that.participants != null) return false;
        if (startDate != null ? !startDate.equals(that.startDate) : that.startDate != null) return false;
        if (title != null ? !title.equals(that.title) : that.title != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (title != null ? title.hashCode() : 0);
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + (startDate != null ? startDate.hashCode() : 0);
        result = 31 * result + (endDate != null ? endDate.hashCode() : 0);
        result = 31 * result + (participants != null ? participants.hashCode() : 0);
        return result;
    }

    public Event getEvent(){

        Set<Participant> pureParticipants= new HashSet<Participant>();
        for (ParticipantXmlAdapter item: participants){
            pureParticipants.add(item.getParticipant());
        }

        Event result = new Event.EventBuilder(this.title, this.id)
                .description(this.description)
                .startDate(this.startDate)
                .endDate(this.endDate)
                .participants(pureParticipants)
                .build();

        return result;
    }
}
