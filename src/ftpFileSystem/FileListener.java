package ftpFileSystem;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.*;

/**
 * Created by xulingo on 16/3/22.
 */
public class FileListener  {

    private  String rootpath;

    private  BlockingQueue<String> deleteFile =  new LinkedBlockingDeque<String>();
    private  BlockingQueue<String> faithFile = new LinkedBlockingDeque<String>();
    private  Map<String,Long> modifyTime = new HashMap<String, Long>();

    public FileListener(String rootpath){

        this.rootpath=rootpath;
        initMap();


//        modifyTime.put("0000123477/Basis_Data.db", (long) 1234567);

    }


    public  void LisnterStart (long inervalTime ){

        ScheduledExecutorService scheduledExecutorService = new ScheduledThreadPoolExecutor(1);

        scheduledExecutorService.scheduleWithFixedDelay(new FileRunnable(this.rootpath),inervalTime,inervalTime, TimeUnit.SECONDS);

    }



    private void initMap(){
        Map<String,Long> temp= new HashMap<String, Long>();

        //获取服务器上的文件信息
        String response = RequestCaseUtil.requestGetCase("http://localhost:8080/ftpfiles");

        //将响应转化为json数组

        JSONArray jsonArray = JSONObject.parseArray(response);


        if (jsonArray.size()>0){
            //便利json数组

            for (int i = 0; i <jsonArray.size() ; i++) {

                JSONObject fileMessage = (JSONObject) jsonArray.get(i);

                String name = (String) fileMessage.get("name");
                Long time = (long) fileMessage.get("time");
                temp.put(name,time);

            }

            modifyTime = temp;
        }



    }

    class FileRunnable implements Runnable{


        private String rootpath;

        public FileRunnable(String rootpath) {
            this.rootpath = rootpath;


        }

        @Override
        public void run() {


            File file = new File(rootpath);


            if(!file.exists()){
                file.mkdir();
            }

            File []  accountDirs = file.listFiles();



            if (accountDirs.length==0){
                return;
            }

            for (File account: accountDirs ) {


//                System.out.println(account.getAbsolutePath());

                if (".DS_Store".equals(account.getName())){
                    continue;
                }

                for (File dbfile : account.listFiles()) {

                    if (".DS_Store".equals(dbfile.getName())){
                        continue;
                    }
                    System.out.println(dbfile.getAbsolutePath());
                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yy-MM-dd HH:mm:ss");
                    String lastmodified  = simpleDateFormat.format(dbfile.lastModified());

//                    System.out.println("dbfileName: " +dbfile.getName());
//
//                    System.out.println("accountName: " +dbfile.getParentFile().getName());

                    String relativePath = dbfile.getParentFile().getName()+File.separator+dbfile.getName();

                    Long temDate = modifyTime.get(relativePath);


                    System.out.println("db文件服务器保存的最后修改时间 :"+temDate);
                    System.out.println("db文件ftp保存的最后修改时间:" +dbfile.lastModified());

                    //当缓冲中存在对应的文件信息,并且缓存中的文件修改时间等于本地文件,不上传,不更新缓存;
                    //当缓存中存在对应的文件信息,并且缓存中的文件修改时间晚于本地文件最后修改时间(云服务器的数据是最新的,本地上传数据是旧的),不上传,不更新缓存
                    if(temDate!=null && (dbfile.lastModified() <= temDate)){

                        System.out.println("不上传");
                          continue;

                    }else {
                       //只要缓存中没有文件信息,就表示该文件一定是最新的,上传数据,更新缓存
                        //如果缓存中有文件信息,只有在缓存文件的修改时间早于本地文件的最后修改时间(云服务器的数据是旧的,本地上传的数据是最新的,上传数据,更新缓存)
                            String response = RequestCaseUtil.requestPostCase("http://localhost:8080/ftpfiles",dbfile.getAbsolutePath(),dbfile.lastModified());
                            Map<String,String> map = JsonUtil.getJson(response);

                        modifyTime.put(relativePath,dbfile.lastModified());
                        System.out.println("上传");
                    }

                }


            }
//
//            System.out.println("文件根目录轮询完毕,已经成功发送给服务起,并且需要删除的文件数量为:" +deleteFile.size());
//
//
//            int index  = 0;
//
//            int length = deleteFile.size();
//            for (int i = 0; i <length ; i++) {
//
//                String  deletePath = deleteFile.poll();
//
//                boolean flag = new File(deletePath).delete();
//
//                if (!flag){
//                    faithFile.offer(deletePath);
//                }else {
//                    index+=1;
//                }
//            }
//
//            System.out.println("删除队列轮训完毕,删除已发送的本地文件个数为:" + index);
//
//
//            for (int i = 0; i <faithFile.size() ; i++) {
//
//                String faithPath = faithFile.poll();
//                deleteFile.offer(faithPath);
//            }

            System.out.println("完成一次文件扫描");

        }


    }



    public static void main(String[] args) {
        FileListener fileListener = new FileListener("/Users/xulingo/ftproot");
        fileListener.LisnterStart(10);
    }
}
