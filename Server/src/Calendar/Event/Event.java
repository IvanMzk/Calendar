package Calendar.Event;

import Calendar.DataStore.DataStore;
import Calendar.Participant.Participant;

import java.io.Serializable;
import java.util.GregorianCalendar;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

/**
 * Created by ivann on 27.04.15.
 */
public class Event implements Serializable {

    private final UUID id;
    private final String title;
    private final String description;
    private final GregorianCalendar startDate;
    private final GregorianCalendar endDate;
    private final Set<Participant> participants;


    private Event(EventBuilder builder) {
        this.id = builder.id;
        this.title = builder.title;
        this.description = builder.description;
        this.startDate = builder.startDate != null ? (GregorianCalendar) builder.startDate.clone() : null;
        this.endDate = builder.endDate != null ? (GregorianCalendar) builder.endDate.clone() : null;
        this.participants = builder.participants != null ? new HashSet<Participant>(builder.participants) : null;

    }

    public void publish(DataStore dataStore){
        dataStore.addEvent(this);
    }


    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public GregorianCalendar getStartDate() {
        return startDate != null ? (GregorianCalendar) startDate.clone() : null;
    }

    public GregorianCalendar getEndDate() {
        return endDate != null ? (GregorianCalendar) endDate.clone() : null;
    }

    public Set<Participant> getParticipants() {return participants != null ? new HashSet<Participant>(participants) : null;}

    public UUID getId() {
        return id;
    }

    public EventXmlAdapter getXmlAdapter(){
        return new EventXmlAdapter(this);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Event)) return false;

        Event event = (Event) o;

        if (description != null ? !description.equals(event.description) : event.description != null) return false;
        if (endDate != null ? !endDate.equals(event.endDate) : event.endDate != null) return false;
        if (!id.equals(event.id)) return false;
        if (participants != null ? !participants.equals(event.participants) : event.participants != null) return false;
        if (startDate != null ? !startDate.equals(event.startDate) : event.startDate != null) return false;
        if (!title.equals(event.title)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id.hashCode();
        result = 31 * result + title.hashCode();
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + (startDate != null ? startDate.hashCode() : 0);
        result = 31 * result + (endDate != null ? endDate.hashCode() : 0);
        result = 31 * result + (participants != null ? participants.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Event{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", participants=" + participants +
                '}';
    }

    public static class EventBuilder{

        private UUID id;
        private String title;
        private String description;
        private GregorianCalendar startDate;
        private GregorianCalendar endDate;
        private Set<Participant> participants;


        public EventBuilder(String title, UUID id) {
            this.title = title;
            this.id = id;
        }

        public EventBuilder(Event event){
            this.id = event.getId();
            this.title = event.getTitle();
            this.description = event.getDescription();
            this.startDate = event.getStartDate();
            this.endDate = event.getEndDate();
            this.participants = event.getParticipants();
        }

        public EventBuilder id(UUID id){
            this.id = id;
            return this;
        }

        public EventBuilder title(String title){
            this.title = title;
            return this;
        }

        public EventBuilder description(String description){
            this.description = description;
            return this;
        }

        public EventBuilder startDate(GregorianCalendar startDate){
            this.startDate = startDate;
            return this;
        }

        public EventBuilder endDate(GregorianCalendar endDate){
            this.endDate = endDate;
            return this;
        }

        public EventBuilder participants(Set<Participant> participants){
            this.participants = participants;
            return this;
        }

        public Event build(){
            return new Event(this);
        }
    }





}
