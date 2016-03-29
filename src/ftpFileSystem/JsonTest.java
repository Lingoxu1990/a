package ftpFileSystem;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by xulingo on 16/3/23.
 */
public class JsonTest {


    public static void main(String[] args) {

//        String jsonstr = "{\"xxx\":\"123123\"}";
//
//        JSONObject jsonObject = JSON.parseObject(jsonstr);
//
//        String velue = (String) jsonObject.get("xxx");
//
//
//        JSONArray jsonArray = jsonObject.getJSONArray("x");
//
//
//        int index  = 1;
//
//        JSONObject  jsonObject1= (JSONObject) jsonArray.get(1);
//
//        System.out.println(velue);


        Map<String,Date> modifyTime = new HashMap<String, Date>();


        System.out.println(modifyTime.get("aaa"));


    }
}
