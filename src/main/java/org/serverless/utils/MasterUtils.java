package org.serverless.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.serverless.pojo.ServicePojo;

import java.util.List;

public class MasterUtils {
    public static String MasterToJson(List<ServicePojo> list) {
        JSONArray jsonArray = new JSONArray();
        StringBuffer buffer = new StringBuffer();
        for( ServicePojo servicePojo : list){
            String email = servicePojo.getEmail();
            String servername = servicePojo.getServername();
            String path = servicePojo.getPath();
            buffer.append("{\"email\":\"")
                    .append(email)
                    .append("\", \"servername\":\"")
                    .append(servername)
                    .append("\",\"path\":\"")
                    .append(path)
                    .append("\"}");
            String arg = buffer.toString();
            JSONObject j = JSON.parseObject(arg);
            jsonArray.add(j);
            buffer.delete(0, buffer.length());
        }
        String result = jsonArray.toJSONString();
        return result;
    }
}
