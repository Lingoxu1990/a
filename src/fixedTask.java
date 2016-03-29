import java.io.*;
import java.util.Date;

/**
 * Created by surfacepc on 2016/3/18.
 */
public class fixedTask implements Runnable {
    private File file ;

    public fixedTask(String path) {
        this.file = new File(path) ;
    }

    @Override
    public void run() {
        File[] subs = file.listFiles();
        for(File sub : subs){
            System.out.println(sub.getName());

            if(sub.isDirectory()){
                File[] children = sub.listFiles();
                for (File child:children) {
                    System.out.println(child.getName());

                    FtpFile ftpFile = new FtpFile();
                    ftpFile.setFilename(child.getName());
                    ftpFile.setPath(child.getAbsolutePath());
                    ftpFile.setLastmodifiedtime(new Date(child.lastModified()));

                    long space = child.getTotalSpace();


                    System.out.println(child.getAbsolutePath());
                    byte[] filearr = new byte[(int) space];

                    FileInputStream fileInputStream = null;

                    ByteArrayOutputStream bos = new ByteArrayOutputStream(1000);


//                    fileInputStream = new FileInputStream(new File(child.getAbsolutePath()));
//                        System.out.println(space);
//                        fileInputStream.read(filearr);

//                        RequestCaseUtil.requestPostCase("http://localhost:8080/ftpfiles",filearr);


            }


            }
        }
    }
}
