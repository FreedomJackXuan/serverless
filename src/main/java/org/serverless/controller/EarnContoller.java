package org.serverless.controller;

import com.alibaba.fastjson.JSON;
import org.apache.log4j.Logger;
import org.serverless.pojo.EarnPojo;
import org.serverless.service.impl.EarnServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

@Controller
public class EarnContoller {

    private static Logger logger = Logger.getLogger(EarnContoller.class);

    @Autowired
    EarnServiceImpl earnService;

    @RequestMapping(value = "/earn",method = RequestMethod.GET)
    public String getMess(){
        return "index2";
    }

    @RequestMapping(value = "/index2",method = RequestMethod.GET)
    public String index2(){
        return "index2";
    }

    @RequestMapping(value = "/earn/hall",method = RequestMethod.GET)
    public String getHall(){
        return "myshare";
    }

    @RequestMapping(value = "/myshare.html",method = RequestMethod.GET)
    public String share(){
        return "myshare";
    }

    @RequestMapping(value = "/earn/hall1",method = RequestMethod.POST)
    @ResponseBody
    public String getHall1(@RequestParam(value = "email")String email){
        System.out.println("==================");
        List<EarnPojo> earnPojoList ;
        try {

            earnPojoList = earnService.findByEmail(email);
        }catch (Exception e){
            logger.error("getHall1",e);
            return "{\"data\":\"null\"}";
        }
        if(earnPojoList == null){
            return "{\"data\":\"null\"}" ;
        }
        String earnJson = JSON.toJSONString(earnPojoList);
        System.out.println(earnJson);
//        return "share ("+earnJson+")";
        return earnJson;
    }

    @RequestMapping(value = "/earn/search",method = RequestMethod.POST)
    @ResponseBody
    public String getByEmail(@RequestParam(value = "email")String email){
        System.out.println("_+_+_+_+_+_");
        List<EarnPojo> earnPojos ;
        try{
            earnPojos = earnService.findByEmail(email);
        }catch (Exception e){
            logger.error("getByEmail: ",e);
            return "{\"data\":\"null\"}";
        }

        System.out.println(earnPojos.size());
        if (earnPojos.size() == 0){
            return  "{\"data\":\"null\"}" ;
        }
        String earnJson = JSON.toJSONString(earnPojos);
        System.out.println("share ( "+earnJson +" )");
        return "share( "+earnJson +" )";
    }

    @RequestMapping(value = "/earn/nextpage",method = RequestMethod.POST)
    @ResponseBody
    public String getNextPage(@RequestParam(name = "email")String email,@RequestParam(value = "page")Integer page){
        System.out.println("-=-=-=-=-=-=-=-");
        HashMap hashMap = new HashMap();
        int start = (page-1)*3;//算出数据库第几条
        hashMap.put("email",email);
        hashMap.put("start",start);
        hashMap.put("size",3);//取3条数据
        System.out.println(hashMap);
        List<EarnPojo> earnPojoList ;
        try {
            earnPojoList  = earnService.getNextPage(hashMap);
        }catch (Exception e){
            return "{\"data\":\"null\"}";
        }
        if(earnPojoList == null){
            return "{\"data\":\"null\"}" ;
        }
        String earnJson = JSON.toJSONString(earnPojoList);
//        return "page( "+earnJson +" )";
        return earnJson;
    }

    @RequestMapping(value = "/earn/getClickNum",method = RequestMethod.POST)
    @ResponseBody
    public String getClickNumByEmail(@RequestParam(value = "email")String email){
        List<EarnPojo> earnPojos;
        try{

            earnPojos   = earnService.findClickNumByEmail(email);
        }catch (Exception e){
            logger.error("getClickNumByEmail: ",e);
            return "{\"data\":\"null\"}";
        }
        if(earnPojos == null){
            return "{\"data\":\"null\"}";
        }
        int sum = 0;
        System.out.println(earnPojos);
        for (EarnPojo e:earnPojos) {
            sum+=e.getClickNum();
        }
        return "{\"clickNum\":\""+sum+"\"}";
    }

    @RequestMapping(value = "/earn/click",method = RequestMethod.POST)
    @ResponseBody
    public String setClickNum(@RequestParam(value = "uuid")String uuid){
        try {
            earnService.upDateClickNum(uuid);
        }catch (Exception e){
            return "{\"data\":\"null\"}";
        }
        return "{\"clickNum\":\"ok\"}";
    }

    @RequestMapping(value = "/earn/addserver",method = RequestMethod.POST)
    @ResponseBody
    public String addServer(@RequestParam(value = "email") String email,@RequestParam(value = "servername")String servername,@RequestParam(value = "uuid")String uuid){
        EarnPojo earnPojo = new EarnPojo();
        earnPojo.setEmail(email);
        earnPojo.setUuid(uuid);
        earnPojo.setServer(servername);
        try {
            earnService.addServer(earnPojo);
        }catch (Exception e){
            return "{\"data\":\"null\"}";
        }
        return "{\"status\":\"ok\"}";
    }
}
