package ftpFileSystem;

import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ResourceListener {
    private static ExecutorService fixedThreadPool = Executors.newFixedThreadPool(5);
    private WatchService ws;
    private String listenerPath;


    private ResourceListener(String path) {
        try {
            ws = FileSystems.getDefault().newWatchService();
            this.listenerPath = path;
            start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void addListener(String path) throws IOException {

        ResourceListener resourceListener = new ResourceListener(path);
        Path p = Paths.get(path);
        p.register(
                resourceListener.ws,
                StandardWatchEventKinds.ENTRY_CREATE,
                StandardWatchEventKinds.OVERFLOW
        );

    }

    public static void main(String[] args) throws IOException {

        ResourceListener.addListener("C:\\test");

    }

    private void start() {
        fixedThreadPool.execute(new Listner(ws, this.listenerPath));
    }

}

class Listner implements Runnable {

    private WatchService service;
    private String rootPath;

    public Listner(WatchService service, String rootPath) {
        this.service = service;
        this.rootPath = rootPath;
    }

    public void run() {
        try {
            while (true) {
                WatchKey watchKey = service.take();
                List<WatchEvent<?>> watchEvents = watchKey.pollEvents();
                for (WatchEvent<?> event : watchEvents) {
                    //TODO 根据事件类型采取不同的操作。。。。。。。
                    System.out.println("[" + rootPath + "/" + event.context() + "]文件发生了[" + event.kind() + "]事件");
                    String path  = rootPath+ File.separator+event.context().toString();

                    System.out.println(path);

////                    String result = RequestCaseUtil.requestPostCase("http://localhost:8080/ftpfiles",path);
//
//                    File file = new File(path);
//
//                    file.delete();
//
//                    System.out.println(result);
//



                }
                watchKey.reset();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            System.out.println("fdsfsdf");
            try {
                service.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }


    }
}