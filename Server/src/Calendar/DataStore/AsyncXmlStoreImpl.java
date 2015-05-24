package Calendar.DataStore;

import Calendar.Event.EventXmlAdapter;

import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.*;

/**
 * Created by ivan on 21.05.2015.
 */
public class AsyncXmlStoreImpl extends XmlStoreImpl implements FileSystemStore {

    private final ExecutorService readThreadPool;
    private ExecutorService writeThreadPool;

    public AsyncXmlStoreImpl(String path){
        super(path);
        readThreadPool = Executors.newCachedThreadPool();
        writeThreadPool = Executors.newCachedThreadPool();

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


    class WriteEventTask implements Runnable{

        private final EventXmlAdapter event;

        public WriteEventTask(EventXmlAdapter event){
            this.event = event;
        }

        public void run(){
            AsyncXmlStoreImpl.super.writeEvent(event);
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
                                futures.add(readThreadPool.submit(new ReadEventTask(file)));
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
                    Thread.currentThread().interrupt();
                    return result;
                }
            }
        }catch (IOException e){
            e.printStackTrace();
        }

        return result;
    }

    @Override
    public void writeEvent(EventXmlAdapter event) {

        if (this.writeThreadPool.isShutdown()) {
            this.writeThreadPool = Executors.newCachedThreadPool();
            writeThreadPool.submit(new WriteEventTask(event));
        }
        else{
            writeThreadPool.submit(new WriteEventTask(event));
        }

    }

    @Override
    public EventXmlAdapter removeEvent(UUID id) {

        EventXmlAdapter result = super.removeEvent(id);
        if (result != null){
            return result;
        }
        else{
            //file must exist, it might not be yet writing on file system
            //so wait for writing complete
            writeThreadPool.shutdown();
            try{
                writeThreadPool.awaitTermination(5000, TimeUnit.MILLISECONDS);
            }
            catch (InterruptedException e){
                Thread.currentThread().interrupt();
                return super.removeEvent(id);
            }
            return super.removeEvent(id);
        }
    }



}
