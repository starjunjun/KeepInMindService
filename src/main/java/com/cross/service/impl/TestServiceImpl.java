package com.cross.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.cross.mapper.TestMapper;
import com.cross.pojo.*;
import com.cross.service.TestService;
import com.cross.util.PublicUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

@Service("TestService")
public class TestServiceImpl implements TestService {
    @Autowired
    private TestMapper testMapper;
    private String url1 = "";
    private String result = "";


    public void addTest(Test test) {
        testMapper.add(test);
        System.out.println(test.getId());
    }

    public void updateTest(Test test) {

    }

    public void deleteTest(int id) {

    }

    public List<Test> getAll() {
        return testMapper.getAll();
    }

    public Test get(int id) {
        return testMapper.get(id);
    }

    public void addManageMoneyPassage(ManageMoneyPassage manageMoneyPassage) {
        testMapper.addManageMoneyPassage(manageMoneyPassage);
    }

    public void checkCount() {
        testMapper.checkCount();
    }

    public List<ManageMoneyPassage> getPassage() {
        return testMapper.getPassage();
    }

    public List<GuPiaoBean> getGuPiao() {
        Thread childrenThread = new Thread(new Runnable() {
            public void run() {
                Document doc = null;
                try {
                    doc = Jsoup.connect("http://www.laoqianzhuang.com/quotes/").referrer("http://www.laoqianzhuang.com/quotes/").userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/60.0.3112.101 Safari/537.36").get();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                Elements es = doc.select("#hqshow_1").select("table").select("#msg");
                for (Element e : es
                        ) {
                    System.out.println(e.toString().substring(e.toString().indexOf("url: \"") + 6, e.toString().indexOf("\"", e.toString().indexOf("url: \"") + 6)));
                    url1 = e.toString().substring(e.toString().indexOf("url: \"") + 6, e.toString().indexOf("\"", e.toString().indexOf("url: \"") + 6));
                }
                try {
                    URL url = new URL(url1);
                    //得到connection对象。
                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                    //设置请求方式
                    connection.setRequestMethod("GET");
                    //连接
                    connection.connect();
                    //得到响应码
                    int responseCode = connection.getResponseCode();
                    if (responseCode == HttpURLConnection.HTTP_OK) {
                        //得到响应流
                        InputStream inputStream = connection.getInputStream();
                        //将响应流转换成字符串
                        result = PublicUtils.convertStreamToString(inputStream);//将流转换为字符串。

                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        childrenThread.start();
        try {
            childrenThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        JSONArray jsonArray = JSON.parseArray(result);
        int size = jsonArray.size();
        List<GuPiaoBean> list1 = new ArrayList<GuPiaoBean>();
        for (int i = 0; i < size; i++) {
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            System.out.println(jsonObject.getString("code"));
            GuPiaoBean gb = new GuPiaoBean(jsonObject.getString("code"), jsonObject.getString("name"), jsonObject.getString("trade"), jsonObject.getString("pricechange"), jsonObject.getString("changepercent"), jsonObject.getString("buy"), jsonObject.getString("sell"), jsonObject.getString("high"), jsonObject.getString("low"), jsonObject.getLong("volume"), jsonObject.getLong("amount"), jsonObject.getString("open"), jsonObject.getString("settlement"));
            list1.add(gb);
        }
        return list1;


    }

    public GuPiaoContent getGuPiaoContent(final String code) {
        final GuPiaoContent cpc = new GuPiaoContent();
        String type = "sh";
        if (code.substring(0, 3).equals("000") || code.substring(0, 3).equals("002") || code.substring(0, 3).equals("300"))
            type = "sz";
        else if (code.substring(0, 3).equals("600") || code.substring(0, 3).equals("601")) type = "sh";
        final String finalType = type;
        Thread t = new Thread(new Runnable() {
            public void run() {
                Document doc = null;
                try {
                    doc = Jsoup.connect("https://gupiao.baidu.com/stock/" + finalType + code + ".html").referrer("https://gupiao.baidu.com/stock/sz000001.html").userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/60.0.3112.101 Safari/537.36").timeout(0).get();
                } catch (IOException e) {
                    e.printStackTrace();
                }
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
                    cpc.setLiangbi(e3.get(17).text());
                    cpc.setTotal(e3.get(18).text());
                    cpc.setShijinglv(e3.get(19).text());
                    cpc.setAssetValuePerShare(e3.get(20).text());
                    cpc.setFlowOfEquity(e3.get(21).text());
                }
                Elements es2 = doc.select("#app-wrap > div.stock-info > div>div");
                cpc.setTrade(es2.get(0).select("strong").text());
                cpc.setPricechange(es2.get(0).select("span:nth-child(2)").text());
                cpc.setChangepercent(es2.get(0).select("span:nth-child(3)").text());
                Elements es4 = doc.select("#app-wrap > div.right.stock-right-box > div:nth-child(3) > div.industry > p:nth-child(4)");
                for (Element e : es4) {
                    cpc.setContent(e.text());
                }
            }
        });
        t.start();
        try {
            t.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return cpc;
    }


    public List<ManageMoneyPassage> getAd() {
        final List<ManageMoneyPassage> list = new ArrayList<ManageMoneyPassage>();
        Thread t = new Thread(new Runnable() {
            public void run() {
                Document doc = null;

                try {
                    doc = Jsoup.connect("http://stock.laoqianzhuang.com/redianticai/").referrer("http://money.laoqianzhuang.com/licaiguihua/").userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/60.0.3112.101 Safari/537.36").get();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                Elements e1 = doc.select("#list_ul").select("img");
                for (int i = 0; i < 3; i++) {
                    ManageMoneyPassage manageMoneyPassage = new ManageMoneyPassage();
                    manageMoneyPassage.setPassageImg(e1.get(i).attr("src"));
                    manageMoneyPassage.setPassageTitle(e1.get(i).attr("title"));
                    list.add(manageMoneyPassage);
                }
                Elements e2 = doc.select("#list_ul").select("span").select("a");
                for (int i = 0; i < 3; i++) {
                    ManageMoneyPassage mmp = list.get(i);
                    StringBuilder sb = new StringBuilder();
                    try {
                        Document doc1 = Jsoup.connect(e2.get(i).attr("href")).referrer(e2.get(i).attr("href")).userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/60.0.3112.101 Safari/537.36").get();
                        Elements es1 = doc1.select("#atc-content").select("p");
                        for (Element e : es1
                                ) {
                            sb.append(e.text());
                            sb.append("\n");
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    mmp.setPassageContent(sb.toString());
                }

            }
        });
        t.start();
        try {
            t.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return list;
    }

    public User sign(String account, String password) {
        User user = null;
        user = testMapper.sign(account);
        return user;
    }

    public String register(String username, String account, String password) {
        User user = new User(username, account, password);
        testMapper.register(user);
        return "11231";
    }


}
