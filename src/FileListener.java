import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Date;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

/**
 * Created by surfacepc on 2016/3/18.
 */
public class FileListener {

    private ConcurrentHashMap<String,Object> concurrentHashMap=null;
    private String path;
    private File file;


    public FileListener(String path){
        this.concurrentHashMap = new ConcurrentHashMap<String ,Object>();
        this.path = path;
        this.file=initFile(path);
    }

    public File initFile(String path){
        return new File(path);
    }
    public void startListener (){

        ScheduledThreadPoolExecutor scheduledThreadPoolExecutor =new ScheduledThreadPoolExecutor(1);

        scheduledThreadPoolExecutor.scheduleWithFixedDelay(new fixedTask(path),5,60, TimeUnit.SECONDS);

    }

    public static void main(String[] args) {
        new FileListener("C:\\test").startListener();
    }


}


