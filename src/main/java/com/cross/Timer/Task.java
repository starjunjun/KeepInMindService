package com.cross.Timer;

import com.cross.mapper.TestMapper;
import com.cross.pojo.ManageMoneyPassage;
import com.cross.pojo.Test;
import com.cross.service.TestService;
import com.cross.service.impl.TestServiceImpl;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.TimerTask;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class Task extends TimerTask {

    TestService service;
    private static boolean isRunning = false;
    private List<String> titleList;
    private List<String> imgList;
    private List<String> passageList;

    public void run() {

        if (!isRunning) {
            ExecutorService cachedThreadPool = Executors.newCachedThreadPool();
            cachedThreadPool.execute(new Runnable() {
                public void run() {
                    Document doc = null;
                    titleList = new ArrayList<String>();
                    imgList = new ArrayList<String>();
                    passageList = new ArrayList<String>();
                    try {
                        doc = Jsoup.connect("http://money.laoqianzhuang.com/licaiguihua/").referrer("http://money.laoqianzhuang.com/licaiguihua/").userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/60.0.3112.101 Safari/537.36").timeout(0).get();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    Elements es = doc.select("#list_ul").select("li").select("img");

                    for (Element e : es
                            ) {
                        titleList.add(e.attr("title").toString());
                        imgList.add(e.attr("src").toString());
                    }
                    Elements es1 = doc.select("#list_ul").select("li").select("a[class~=cot-intro]");

                    for (Element e : es1
                            ) {
                        try {
                            doc = Jsoup.connect(e.attr("href").toString()).userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/60.0.3112.101 Safari/537.36").referrer("http://money.laoqianzhuang.com/licaiguihua/").get();
                        } catch (IOException e1) {
                            e1.printStackTrace();
                        }
                        Elements es2 = doc.select("#atc-content").select("p");
                        StringBuilder sb = new StringBuilder();
                        for (int j = 0; j < es2.size(); j++) {
                            sb.append(es2.get(j).text().toString());
                            sb.append("\n");
                        }
                        passageList.add(sb.toString());
                    }

                    for (int i = 0; i < titleList.size(); i++) {
                        ManageMoneyPassage manageMoneyPassage = new ManageMoneyPassage(titleList.get(i), imgList.get(i), passageList.get(i));
                        ApplicationContext context = new ClassPathXmlApplicationContext("classpath:applicationContext.xml");
                        service = (TestService) context.getBean("TestService");
                        service.addManageMoneyPassage(manageMoneyPassage);
                    }
                    service.checkCount();

//
//                    for (int i = 0; i < elements.size(); i += 2) {
//
//                        try {
//                            doc = Jsoup.connect("http://www.livlc.com/" + elements.get(i).attr("href").toString()).referrer("http://www.livlc.com/").userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/60.0.3112.101 Safari/537.36").get();
//                        } catch (IOException e) {
//                            e.printStackTrace();
//                        }
//                        Elements elements1 = doc.select("#content").select("p");
//                        StringBuilder sb = new StringBuilder();
//                        for (int j = 0; j < elements1.size(); j++) {
//                            sb.append(elements1.text().toString());
//                            sb.append("\n");
//                        }
//
//                        ManageMoneyPassage manageMoneyPassage = new ManageMoneyPassage( elements.get(i).select("img").attr("alt").toString(), elements.get(i).select("img").attr("src").toString(),sb.toString());
//                        ApplicationContext context = new ClassPathXmlApplicationContext("classpath:applicationContext.xml");
//                        service = (TestService) context.getBean("TestService");
//                        service.addManageMoneyPassage(manageMoneyPassage);
//                    }
//                    service.checkCount();

                }
            });
        }

    }
}
