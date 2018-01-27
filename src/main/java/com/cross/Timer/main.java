package com.cross.Timer;

import com.cross.pojo.GuPiaoContent;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;


import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class main {

    private static String url1;

    public static void main(String[] args) {
        new Thread(new Runnable() {
            public void run() {
                Document doc = null;
                try {
                    doc = Jsoup.connect("https://gupiao.baidu.com/stock/sh600000.html").referrer("https://gupiao.baidu.com/").userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/60.0.3112.101 Safari/537.36").get();
                } catch (IOException e) {
                    e.printStackTrace();
                }



                GuPiaoContent cpc = new GuPiaoContent();
                Elements es = doc.select("#app-wrap > div.stock-info > div ");
                for (Element e : es
                        ) {
                    Elements e1 = e.select("h1");
                    for (Element i : e1
                            ) {
                        cpc.setName_code(i.text().substring(0, i.text().indexOf(")") + 1).trim());
                        cpc.setState(i.text().substring(i.text().indexOf(")") + 1).trim());
                    }
                    Elements e3 = e.select("dl").select("dd");
                    cpc.setOpen(e3.get(0).text());
                    cpc.setVolume(e3.get(1).text());
                    cpc.setHigh(e3.get(2).text());
                    cpc.setLimitUp(e3.get(3).text());
                    cpc.setIn(e3.get(4).text());
                    cpc.setAmount(e3.get(5).text());
                    cpc.setCommitte(e3.get(6).text());
                    cpc.setCurrency(e3.get(7).text());
                    cpc.setEarnings(e3.get(8).text());
                    cpc.setEarningsPerShare(e3.get(9).text());
                    cpc.setTotalCapitalStock(e3.get(10).text());
                    cpc.setSettlement(e3.get(11).text());
                    cpc.setUrnoverRate(e3.get(12).text());
                    cpc.setLow(e3.get(13).text());
                    cpc.setLimitDown(e3.get(14).text());
                    cpc.setOut(e3.get(15).text());
                    cpc.setLiangbi(e3.get(16).text());
                    cpc.setTotal(e3.get(17).text());
                    cpc.setShijinglv(e3.get(18).text());
                    cpc.setAssetValuePerShare(e3.get(19).text());
                    cpc.setFlowOfEquity(e3.get(20).text());
                }
                Elements es2 = doc.select("#app-wrap > div.stock-info > div>div");
                cpc.setTrade(es2.get(0).select("strong").text());
                cpc.setPricechange(es2.get(0).select("span:nth-child(2)").text());
                cpc.setChangepercent(es2.get(0).select("span:nth-child(3)").text());
                Elements es4 = doc.select("#app-wrap > div.right.stock-right-box > div:nth-child(3) > div.industry > p:nth-child(4)");
                for (Element e : es4){
                    System.out.println(e.text());
                }
                System.out.println(cpc.getAssetValuePerShare());
                System.out.println(cpc.getContent());


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

