package Calendar.DataStore;

import Calendar.Event.Event;
import Calendar.Event.EventXmlAdapter;

import javax.xml.bind.*;
import java.io.File;

/**
 * Created by ivann on 13.05.15.
 */
public class XmlStoreImpl implements FileSystemStore {

    //path to directory to store xml files
    private String pathToStore;

    public XmlStoreImpl(String pathToStore) {
        this.pathToStore = pathToStore;
    }

    @Override
    public void setStorePath(String path) {
        pathToStore = path;
    }

    @Override
    public String getStorePath() {
        return pathToStore;
    }

    @Override
    public void writeEvent(EventXmlAdapter event) {

        String fullFileName  = pathToStore + event.getId().toString()+".xml";
        File file = new File(fullFileName);
        try{
            JAXBContext context = JAXBContext.newInstance(EventXmlAdapter.class);
            Marshaller marshaller = context.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            marshaller.marshal(event, file);
            marshaller.marshal(event, System.out);

        }catch(JAXBException e){
            e.printStackTrace();
        }
    }

    @Override
    public void readEvents(DataStore dataStore) {

    }
}
