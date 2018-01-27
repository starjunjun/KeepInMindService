package com.cross.controller;

import com.cross.Bean.ReturnJson;
import com.cross.pojo.GuPiaoBean;
import com.cross.pojo.GuPiaoContent;
import com.cross.pojo.ManageMoneyPassage;
import com.cross.pojo.Test;
import com.cross.service.TestService;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Controller
@RequestMapping("/Test")
public class TestController {

    @Autowired
    private TestService testService;

    //获取所有测试数据
    @RequestMapping("/getAll")
    public @ResponseBody
    ReturnJson getAll() {
        return new ReturnJson().setResult("sfsfds").setResultcode("1");
    }

    //添加测试数据
    @RequestMapping("/add")
    public @ResponseBody
    ReturnJson add() {
        Test test = new Test();
        test.setName("Dawnki");
        testService.addTest(test);
        return new ReturnJson();
    }

    //发送Json参数到springMvc例子
    @RequestMapping(value = "/Json", method = RequestMethod.POST)
    public @ResponseBody
    ReturnJson Test(@RequestBody Map map) {
        return new ReturnJson();
    }


    //获取所有测试数据
    @RequestMapping("/getpassage")
    public @ResponseBody
    ReturnJson getpassage() {

        try {
            List<ManageMoneyPassage> list = testService.getPassage();
            System.out.println(list.toString()+list.get(1).getPassageImg());
            return new ReturnJson<List<ManageMoneyPassage>>().setResult(list).setResultcode("200");
        } catch (Exception e) {
            return new ReturnJson<String>().setResult("出错了").setResultcode("10000");
        }

    }

    @RequestMapping("/getgupiao")
    public @ResponseBody
    ReturnJson getgupiao() {

        try {
           List<GuPiaoBean> result = testService.getGuPiao();
            return new ReturnJson<List<GuPiaoBean>>().setResult(result).setResultcode("200");
        } catch (Exception e) {
            return new ReturnJson<String>().setResult("出错了").setResultcode("10000");
        }

    }

    @RequestMapping("/searchgupiao")
    public @ResponseBody
    ReturnJson getgupiao(String code) {

        try {
            GuPiaoContent result = testService.getGuPiaoContent(code);
            return new ReturnJson<GuPiaoContent>().setResult(result).setResultcode("200");
        } catch (Exception e) {
            return new ReturnJson<String>().setResult("出错了").setResultcode("10000");
        }

    }


}
