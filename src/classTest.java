
import javax.tools.JavaCompiler;
import javax.tools.StandardJavaFileManager;
import javax.tools.ToolProvider;
import java.io.*;
import java.util.Arrays;

/**
 * Created by surfacepc on 2016/2/26.
 */
public class classTest {
    public static void main(String []args) throws IOException {



        // 1.创建需要动态编译的代码字符串
        String nr = "\r\n"; //回车
        String source =
                " public class  Hello{" + nr +
                " public static void main (String[] args){" + nr +
                " System.out.println(\"HelloWorld! 1\");" + nr +
                " }" + nr +
                " }";
        // 2.将欲动态编译的java代码写入文件中
        String currentPath = System.getProperty("user.dir"); //获取当前路径
        //动态代码存储的目标路径
        String targetPath = currentPath+File.separator+
                "resource";
        //编译后的代码存储路径
        String classpath = "C:\\Users\\surfacepc\\Desktop";

        File dir = new File(targetPath);
        // 如果 目标文件夹 不存在 就创建
        if (!dir.exists()) {
            dir.mkdir();
        }
        File tagetFile=new File(dir,"Hello.java");//在目标文件夹中创建 hello.java 这个文件
        FileWriter writer = new FileWriter(tagetFile);//包装流
        writer.write(source);
        writer.flush();
        writer.close();

        // 3.取得当前系统的编译器
        JavaCompiler javaCompiler = ToolProvider.getSystemJavaCompiler();
        // 4.获取一个文件管理器
        StandardJavaFileManager javaFileManager = javaCompiler.getStandardFileManager(null, null, null);
        // 5.文件管理器根与文件连接起来
        Iterable it = javaFileManager.getJavaFileObjects(new File(dir,"Hello.java"));
        // 6.创建编译任务  Arrays.asList("-d", "./temp") 此处采用-d , ./temp意思为设置指定生成类文件的目录在当前文件夹下的temp子文件夹下
        JavaCompiler.CompilationTask task = javaCompiler.getTask(null, javaFileManager, null, Arrays.asList("-d", classpath), null, it);
        // 7.执行编译
        boolean result = task.call();
        javaFileManager.close();

        if (result){
            System.out.println("编译成功");
        }




//       // 8.运行程序
//       Runtime run = Runtime.getRuntime();
//       Process process = run.exec("java -cp ./DDDmodel DDDmodel/com/Hello");
//       InputStream in = process.getInputStream();
//       BufferedReader reader = new BufferedReader(new InputStreamReader(in));
//       String info  = "";
//       while ((info = reader.readLine()) != null) {
//           System.out.println(info);
//
//       }
    }
}
