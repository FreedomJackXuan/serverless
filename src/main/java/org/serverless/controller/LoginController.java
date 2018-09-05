package org.serverless.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;
import org.serverless.pojo.ServicePojo;
import org.serverless.pojo.User;
import org.serverless.service.EarnService;
import org.serverless.service.SerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

@Controller

public class LoginController {
    @Autowired
    private SerService serService;

    @Autowired
    private EarnService earnService;

    @RequestMapping(value = "/login.html")
    public String viewLogin(ModelMap map) {
        return "login";
    }

    private static Logger logger = Logger.getLogger(LoginController.class);
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    @ResponseBody
    public String login(@ModelAttribute User user) {
//        HttpSession session = request.getSession();
        logger.info("----------------------------------------------");
//        session.setAttribute("email",user.getEmail());
//        session.setAttribute("passwd",user.getPasswd());
//        logger.info(session.getId());
        logger.info(user.getEmail());
        logger.info(user.getPasswd());
        String url = "http://47.107.64.157:8088/login";
        String json = "pass="+user.getPasswd()+"&email="+user.getEmail();
        logger.info(json);
        CloseableHttpClient client = HttpClients.createDefault();
        HttpPost httpPost;
        httpPost = new HttpPost(url);
        httpPost.setEntity(new StringEntity(json, "utf-8"));
        httpPost.setHeader("Content-type", "json");
        CloseableHttpResponse rep = null;
        String status = "";
        String content = "";
        try {
            rep = client.execute(httpPost);
            content = EntityUtils.toString(rep.getEntity());
            logger.info("========line========="+content);
            rep.close();
        } catch (IOException e) {
            logger.info("=========login error============"+e);
            return "{\"status\":\"false\"}";
        }
        JSONObject jsonObject = JSON.parseObject(content);
        status = jsonObject.getString("status");
        if (status.equals("ok")) {
            return "{\"status\":\"OK\"}";
        } else {
            return "{\"status\":\"false\"}";
        }
    }

    @RequestMapping(value = "/myserver.html", method = RequestMethod.GET)
    public String myservice(ModelMap map){
        return "myserver";
    }

    @RequestMapping(value = "/myService", method = RequestMethod.POST)
    @ResponseBody
    public String myService(String email){
        logger.info(email);
        List<ServicePojo> servicePojos = serService.findServiceByEmail(email);
        if (servicePojos.size() > 0) {
            JSONArray jsonArray = new JSONArray();
            String result = "";
            for (ServicePojo servicePojo : servicePojos) {
                String servername = servicePojo.getServername();
                String uuid = servicePojo.getUuid();
                Integer clickNumber = earnService.findClickNumByUUID(uuid);
                logger.info("==========clickNumber=============="+clickNumber);
                String arg ="{\"email\":\""+email+"\",\"servername\":\""+servername+"\",\"clicknum\":"+clickNumber+"}";
                JSONObject j = JSON.parseObject(arg);
                jsonArray.add(j);
            }
            result = jsonArray.toJSONString();
            logger.info(result);
            return result;
        } else {
            return "";
        }
    }
}
