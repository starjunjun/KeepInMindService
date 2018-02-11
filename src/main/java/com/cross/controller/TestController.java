package com.cross.controller;

import com.cross.Bean.ReturnJson;
import com.cross.pojo.*;
import com.cross.service.TestService;
import com.cross.util.PublicUtils;
import org.apache.commons.io.FileUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import java.io.*;
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
            System.out.println(list.toString() + list.get(1).getPassageImg());
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


    @RequestMapping("/getad")
    public @ResponseBody
    ReturnJson geAD() {

        try {
            List<ManageMoneyPassage> result = testService.getAd();
            return new ReturnJson<List<ManageMoneyPassage>>().setResult(result).setResultcode("200");
        } catch (Exception e) {
            return new ReturnJson<String>().setResult("出错了").setResultcode("10000");
        }

    }


    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    @ResponseBody
    public ReturnJson upload(HttpServletRequest username, @RequestParam("file") MultipartFile file) throws IOException {
        String path = username.getSession().getServletContext().getRealPath("upload");
        String fileName = username.getParameter("username");
        File dir = new File(path, fileName);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        //MultipartFile自带的解析方法
        file.transferTo(dir);
        System.out.println("OKOKOKOK");
        return new ReturnJson<String>().setResult("okokok").setResultcode("200");
        //还需要补充失败的返回值
    }


    @RequestMapping(value="/download")
    public ResponseEntity<byte[]>  download(String username){

        WebApplicationContext webApplicationContext = ContextLoader.getCurrentWebApplicationContext();
        ServletContext servletContext = webApplicationContext.getServletContext();
        String path=servletContext.getRealPath("/upload/"+username);
        System.out.println(path);
        File f=new File(path);
        InputStream in;
        ResponseEntity<byte[]> response=null ;
        try {
            in = new FileInputStream(f);
//            String b=PublicUtils.convertStreamToString(in);
            byte[] b=new byte[in.available()];
            in.read(b);
            HttpHeaders headers = new HttpHeaders();
            username = new String(username.getBytes("gbk"),"iso8859-1");
            headers.add("Content-Disposition", "attachment;filename="+username);
            HttpStatus statusCode=HttpStatus.OK;
            response = new ResponseEntity<byte[]>(b, headers, statusCode);
//            System.out.println(response.getBody().length+"  "+response.getHeaders()+"   "+response.toString());
            in.close();
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
//        new ReturnJson<ResponseEntity<byte[]>>().setResult(response).setResultcode("200")
        return response;

    }


    @RequestMapping(method = RequestMethod.POST, value = "/sign")
    public @ResponseBody
    ReturnJson sign(String account, String password) {
        User user = null;
        user = testService.sign(account, password);
        if(user!=null){
            return new ReturnJson().setResult(user).setResultcode("200");
        }else {
            return new ReturnJson().setResult("账号或密码错误").setResultcode("10000");
        }
    }

    @RequestMapping(method = RequestMethod.POST, value = "/register")
    public @ResponseBody
    ReturnJson register(String username, String account, String password) {
        if(username ==null||account ==null||password==null){
            return new ReturnJson().setResult("资料未完善").setResultcode("10000");
        }else{
            testService.register(username, account, password);
            return new ReturnJson().setResult("注册成功").setResultcode("200");
        }



    }


}
