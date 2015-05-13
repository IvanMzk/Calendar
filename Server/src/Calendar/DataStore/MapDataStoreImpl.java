package Calendar.DataStore;

import Calendar.Event.Event;
import Calendar.Participant.Participant;
import java.util.*;

/**
 * Created by ivann on 27.04.15.
 */
public class MapDataStoreImpl implements DataStore{

    private final Map<UUID, Event> store = new HashMap<UUID, Event>();
    private final Map<String, List<UUID>> titleIndex = new HashMap<String, List<UUID>>();
    private final Map<Participant, List<UUID>> participantIndex = new HashMap<Participant, List<UUID>>();

    private final FileSystemStore fileSystemStore = new XmlStoreImpl(".\\xml\\");


    @Override
    public void addEvent(Event event){
        store.put(event.getId(), event);
        addToTitleIndex(event);
        addToParticipantIndex(event);
        fileSystemStore.writeEvent(event.getXmlAdapter());
    }

    @Override
    public Event getEventByID(UUID id){
        if (store.containsKey(id))
        {return store.get(id);}
        return null;
    }

    @Override
    public Event removeEventByID(UUID id){
        if (store.containsKey(id)) {
            Event event = store.get(id);
            store.remove(id);
            removeFromTitleIndex(event);
            removeFromParticipantIndex(event);
            return event;
        }
        return null;
    }

    @Override
    public List<Event> getEventByTitle(String title){

        if (titleIndex.containsKey(title)) {
            List<UUID> idList = titleIndex.get(title);
            List<Event> result = new LinkedList<Event>();
            for (UUID item : idList)
            {result.add(store.get(item));}
            return result;
        }
        return null;
    }

    @Override
    public List<Event> getEventByParticipant(Participant participant) {
        if (participantIndex.containsKey(participant)){
            List<UUID> idList = participantIndex.get(participant);
            List<Event> result = new LinkedList<Event>();
            for (UUID item : idList)
            {result.add(store.get(item));}
            return result;
        }
        return null;
    }

    private void addToTitleIndex(Event event){
        UUID id = event.getId();
        String title = event.getTitle();
        if (null == titleIndex.get(title))
        {titleIndex.put(title, new LinkedList<UUID>());}
        titleIndex.get(title).add(id);
    }

    private void addToParticipantIndex(Event event){
        Set<Participant> participants = event.getParticipants();
        if (null == participants)
        {return;}
        for (Participant item : participants)
        {
            if (!participantIndex.containsKey(item))
            {participantIndex.put(item, new LinkedList<UUID>());}
            participantIndex.get(item).add(event.getId());
        }
    }


    private void removeFromTitleIndex(Event event){

        //removing from title index
        String title = event.getTitle();
        List<UUID> idList = titleIndex.get(title);
        if (null != idList)
        {idList.remove(event.getId());}
        if (idList.isEmpty()){titleIndex.remove(title);}
    }

    private void removeFromParticipantIndex(Event event){

        //removing from participant index
        Set<Participant> participants = event.getParticipants();
        if (null != participants)
        {
            for (Participant item : participants)
            {
                List<UUID> idList = participantIndex.get(item);
                if (null != idList)
                {idList.remove(event.getId());}
                if (idList.isEmpty()){participantIndex.remove(item);}
            }
        }

    }

}
