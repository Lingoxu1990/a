import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;

/**
 * Created by surfacepc on 2016/2/28.
 */
public class loadtest {
    public static void main(String[] args) throws MalformedURLException, ClassNotFoundException, IllegalAccessException, InstantiationException {
        System.out.println(System.getProperty("user.dir"));



        URLClassLoader loader = new URLClassLoader(new URL[]{new URL("file:C:\\Users\\surfacepc\\IdeaProjects\\a\\out\\production\\a")});
        Object model = loader.loadClass("Hello").newInstance();
        String idStr = "YAya";
       /* Method[] method = model.getClass().getMethods();*/
        try {
            Method method = model.getClass().getMethod("main", String[].class);
            method.invoke(null, (Object) new String[]{idStr});
        } catch (Exception e) {
            e.printStackTrace();

        }

    }
}
