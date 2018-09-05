package org.serverless.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;
import org.serverless.pojo.ServicePojo;
import org.serverless.service.SerService;
import org.serverless.utils.MasterUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.ui.ModelMap;


import java.io.*;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

@Controller

public class MasterController {
    private static Logger logger = Logger.getLogger(MasterController.class);

    @Autowired
    SerService serService;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String Master(ModelMap map) {
        map.addAttribute("index",serService.findAllService());
        return "index";
    }

    @RequestMapping(value = "/index2.html", method = RequestMethod.GET)
    public String Index2(ModelMap map) {
        return "index2";
    }

    @RequestMapping(value = "/master", method = RequestMethod.GET)
    @ResponseBody
    public String findService(){
        List<ServicePojo> spojos = serService.findAllService();
        String result = "";
        if (spojos.size() > 0)
            result = MasterUtils.MasterToJson(spojos);
        logger.info("===master==="+result);
        return result;
    }

    @RequestMapping(value = "/nextPage",method = RequestMethod.POST)
    @ResponseBody
    public String getNextPage(@RequestParam(value = "page")Integer page){
        HashMap hashMap = new HashMap();
        int start = (page-1)*3;//算出数据库第几条
        hashMap.put("start",start);
        hashMap.put("size",3);//取3条数据
        System.out.println(hashMap);
        List<ServicePojo> servicePojos = serService.getNextPage(hashMap);
        String result = "";
        if (servicePojos.size() > 0)
            result = MasterUtils.MasterToJson(servicePojos);
        logger.info("===master==="+result);
        return result;
    }

    @RequestMapping(value = "/search", method = RequestMethod.POST)
    @ResponseBody
    public String findServiceByServerName(String servername) {
        List<ServicePojo> pojos = serService.findServiceByService(servername);
        String result="";
        if (pojos.size()>0)
            result = MasterUtils.MasterToJson(pojos);
        logger.info(result);
        return result;
    }

    @RequestMapping(value = "/path",method = RequestMethod.POST)
    @ResponseBody
    public String findPath(String path) {
        File file = new File(path);
        if (file.exists()) {
            try {
                BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
                String line = null;
                StringBuffer buffer = new StringBuffer();
                while ((line = reader.readLine())!=null){
                    buffer.append(line);
                }
                String r =  buffer.toString();
                 buffer.delete(0,buffer.length());
                return r;
            } catch (IOException e) {
                logger.error("read file error!"+e);
            }
        }
        return "";
    }

    @RequestMapping(value = "/insert",method = RequestMethod.POST)
    @ResponseBody
    public String insertService(ServicePojo servicePojo){
        ServicePojo service = serService.findServiceByUUID(servicePojo.getUuid());
        if (service == null) {
            int res = serService.insertService(servicePojo);
            if (res != 0) {
                return "true";
            } else {
                return "false";
            }
        } else {
            if (service.getServername().equals(servicePojo.getServername()) && service.getServername().equals(servicePojo.getServername())) {
                int res = serService.updateService(servicePojo);
                if (res != 0) {
                    return "true";
                } else {
                    return "false";
                }
            }
        }
        return "false";
    }

    @RequestMapping(value = "/interface",method = RequestMethod.POST)
    @ResponseBody
    public String searchInterface(String path){
        File file = new File(path);
        StringBuffer sbuff = new StringBuffer();
        if (file.exists()) {
            try (FileInputStream inputStream = new FileInputStream(file)){
                FileChannel channel = inputStream.getChannel();
                ByteBuffer buffer = ByteBuffer.allocate(256);

                while (channel.read(buffer)!=-1){
                    buffer.flip();
                    Charset charset = Charset.forName("utf-8");
                    CharsetDecoder decoder = charset.newDecoder();
                    CharBuffer cbuff = decoder.decode(buffer);
                    sbuff.append(cbuff);
                    buffer.clear();
                }

                channel.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        String res = sbuff.toString();
        logger.info("========/interface read is : =========="+res);
        return "{\"data\":\""+res+"\"}";
    }

    @RequestMapping(value = "/import", method = RequestMethod.POST)
    @ResponseBody
    public String importService(@RequestParam(name = "servicename") String servicename,
                                @RequestParam(name = "url") String url,
                                @RequestParam(name = "port") int port,
                                @RequestParam(name = "method") String method,
                                @RequestParam(name = "parameter") String parameter) {
        List<ServicePojo> servicePojos = serService.findServiceByService(servicename);
        if (servicePojos.size() > 0) {
            for (ServicePojo pojo : servicePojos) {
                String uuid = pojo.getUuid();
                String me = method.toLowerCase();
                if (me.equals("GET")) {
                    String sendto = "http://47.107.64.157:" + port + url + "?" + parameter;
                    CloseableHttpClient client = HttpClients.createDefault();
                    HttpGet httpGet = new HttpGet(sendto);
                    JSONObject json = new JSONObject();
                    String content = "";
                    try {
                        CloseableHttpResponse res = client.execute(httpGet);
                        if (res.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                            content = EntityUtils.toString(res.getEntity());
                            res.close();
                        }
                    } catch (IOException e) {
                        logger.info("import get error : " + e);
                    }
                    JSONObject jsonObject = JSON.parseObject(content);
                    String status = jsonObject.toJSONString();
                    return status;
                } else if (me.equals("POST")) {
                    String sendto = "http://47.107.64.157:" + port + url;
                    CloseableHttpClient client = HttpClients.createDefault();
                    HttpPost httpPost = new HttpPost(sendto);
                    httpPost.setEntity(new StringEntity(parameter, "utf-8"));
                    httpPost.setHeader("Content-type", "json");
                    String content = "";
                    try {
                        CloseableHttpResponse rep = client.execute(httpPost);
                        if (rep.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                            content = EntityUtils.toString(rep.getEntity());
                            rep.close();
                        }
                    } catch (IOException e) {
                        logger.info("import post error : " + e);
                    }
                    JSONObject jsonObject = JSON.parseObject(content);
                    String status = jsonObject.toJSONString();
                    return status;
                }
            }
        } else {
            return "service is not empty!";
        }
        return "error";
    }
}
