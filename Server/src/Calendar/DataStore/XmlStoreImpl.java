package Calendar.DataStore;

import Calendar.Event.EventXmlAdapter;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

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
    public EventXmlAdapter removeEvent(UUID id) {

        String fullFileName  = pathToStore + id.toString()+".xml";
        Path path = Paths.get(fullFileName);
        EventXmlAdapter result = readEvent(path);
        if (null != result){
            try{
                Files.delete(path);
            }
            catch (IOException e){
                e.printStackTrace();
            }

            return result;
        }

        return null;
    }

    @Override
    public EventXmlAdapter readEvent(Path path) {

        EventXmlAdapter result;
        if (Files.exists(path)) {

            File file = path.toFile();
            try{
                JAXBContext context = JAXBContext.newInstance(EventXmlAdapter.class);
                Unmarshaller unmarshaller = context.createUnmarshaller();
                result = (EventXmlAdapter) unmarshaller.unmarshal(file);
                return result;
            }catch (JAXBException e){
                e.printStackTrace();
            }
        }

        return null;
    }

    @Override
    public List<EventXmlAdapter> readEvents() {

        final List<EventXmlAdapter> result = new LinkedList<EventXmlAdapter>();
        Path path = Paths.get(pathToStore);
        //local code review (vtegza): nt used variable @ 5/20/2015
        final Path xml_extension = Paths.get(".xml");
        try{
            Files.walkFileTree(path, new SimpleFileVisitor<Path>() {

                        @Override
                        public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException{


                            if (attrs.isRegularFile()){
                                result.add(readEvent(file));
                            }
                            return FileVisitResult.CONTINUE;

                        }
                    }
            );


        }catch (IOException e){
            e.printStackTrace();
        }
        return result;
    }
}
