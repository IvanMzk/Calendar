package Calendar.DataStore;

import Calendar.Event.EventXmlAdapter;

import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.*;

/**
 * Created by ivan on 21.05.2015.
 */
public class AsyncXmlStoreImpl extends XmlStoreImpl implements FileSystemStore {

    private final ExecutorService threadPool;

    public AsyncXmlStoreImpl(String path){
        super(path);
        threadPool = Executors.newCachedThreadPool();
    }

    class ReadEventTask implements Callable<EventXmlAdapter>{

        private final Path path;
        public ReadEventTask(Path path){
            this.path = path;
        }

        public EventXmlAdapter call(){
            return AsyncXmlStoreImpl.super.readEvent(this.path);
        }
    }


    @Override
    public List<EventXmlAdapter> readEvents() {

        final List<EventXmlAdapter> result = new LinkedList<EventXmlAdapter>();
        final List<Future<EventXmlAdapter>> futures = new LinkedList<Future<EventXmlAdapter>>();

        Path path = Paths.get(getStorePath());
        try{
            Files.walkFileTree(path, new SimpleFileVisitor<Path>() {

                        @Override
                        public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {


                            if (attrs.isRegularFile()) {
                                futures.add(threadPool.submit(new ReadEventTask(file)));
                            }
                            return FileVisitResult.CONTINUE;
                        }
                    }
            );

            for (Future<EventXmlAdapter> item : futures){
                try{
                    result.add(item.get());
                }
                catch (InterruptedException|ExecutionException e){

                }
            }
        }catch (IOException e){
            e.printStackTrace();
        }



        return result;


    }

    @Override
    public void writeEvent(EventXmlAdapter event) {
        super.writeEvent(event);
    }
}
