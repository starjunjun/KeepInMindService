package com.cross.Timer;

import com.cross.pojo.GuPiaoContent;
import com.cross.pojo.ManageMoneyPassage;
import com.cross.util.PublicUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;


import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class main {

    private static String url1;

    public static void main(String[] args) {


        new Thread(new Runnable() {
            public void run() {
                Document doc = null;
                List<ManageMoneyPassage> list = new ArrayList<ManageMoneyPassage>();
                try {
                    doc = Jsoup.connect("http://stock.laoqianzhuang.com/redianticai/").referrer("http://money.laoqianzhuang.com/licaiguihua/").userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/60.0.3112.101 Safari/537.36").get();
                } catch (IOException e) {
                    e.printStackTrace();
                }
//                Elements es = doc.select("#list_ul").select("span").select("a");
//                for (Element e : es
//                        ) {
//                    System.out.println(e.toString());
//                }
                Elements e1 = doc.select("#list_ul").select("img");
                for (int i = 0; i < 3; i++) {
                    ManageMoneyPassage manageMoneyPassage = new ManageMoneyPassage();
                    manageMoneyPassage.setPassageImg(e1.get(i).attr("src"));
                    manageMoneyPassage.setPassageTitle(e1.get(i).attr("title"));
                    list.add(manageMoneyPassage);
                }
                Elements e2 = doc.select("#list_ul").select("span").select("a");
                for (int i = 0; i < 3; i++) {
                    ManageMoneyPassage mmp=list.get(i);
                    StringBuilder sb = new StringBuilder();
                    try {
                        Document doc1 = Jsoup.connect(e2.get(i).attr("href")).referrer(e2.get(i).attr("href")).userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/60.0.3112.101 Safari/537.36").get();
                        Elements es1 = doc1.select("#atc-content").select("p");
                        for (Element e:es1
                             ) {
                            sb.append(e.text());
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    mmp.setPassageContent(sb.toString());
                }

//                try {
//                    URL url = new URL(url1);
//                    //得到connection对象。
//                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
//                    //设置请求方式
//                    connection.setRequestMethod("GET");
//                    //连接
//                    connection.connect();
//                    //得到响应码
//                    int responseCode = connection.getResponseCode();
//                    if(responseCode == HttpURLConnection.HTTP_OK){
//                        //得到响应流
//                        InputStream inputStream = connection.getInputStream();
//                        //将响应流转换成字符串
//                        String result = convertStreamToString(inputStream);//将流转换为字符串。
//                        System.out.println(result);
//                    }
//
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
            }
        }).start();


    }


}

