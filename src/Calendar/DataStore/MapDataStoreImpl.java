package Calendar.DataStore;

import Calendar.Event.Event;

import java.util.*;

/**
 * Created by ivann on 27.04.15.
 */
public class MapDataStoreImpl implements DataStore{

    private final Map<UUID, Event> store = new HashMap<UUID, Event>();
    private final Map<String, List<UUID>> titleIndex = new HashMap<String, List<UUID>>();


    public void addEvent(Event event){
        store.put(event.getId(), event);
        addToTitleIndex(event);
    }

    public Event getEventByID(UUID id){
        if (store.containsKey(id))
        {return store.get(id);}
        return null;
    }

    public Event removeEventByID(UUID id){
        Event event = store.get(id);
        store.remove(id);
        removeFromIndex(event);
        return event;
    }

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


    private void addToTitleIndex(Event event){
        UUID id = event.getId();
        String title = event.getTitle();
        if (null == titleIndex.get(title))
        {titleIndex.put(title, new LinkedList<UUID>());}
        titleIndex.get(title).add(id);
    }

    //!!!should be modified if new index were added
    private void removeFromIndex(Event event){

        //remove from title index
        String title = event.getTitle();
        List<UUID> idList = titleIndex.get(title);
        idList.remove(event.getId());
        if (idList.isEmpty()){titleIndex.remove(title);}


    }

}
