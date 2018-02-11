package com.cross.service;

import com.cross.Bean.ReturnJson;
import com.cross.pojo.*;
import org.springframework.stereotype.Service;

import java.util.List;


public interface TestService {
    public void addTest(Test test);

    public void updateTest(Test test);

    public void deleteTest(int id);

    public List<Test> getAll();

    public Test get(int id);

    public void addManageMoneyPassage(ManageMoneyPassage manageMoneyPassage);

    public void checkCount();

    public List<ManageMoneyPassage> getPassage();

    public List<GuPiaoBean> getGuPiao();

    public GuPiaoContent getGuPiaoContent(String code);

    public List<ManageMoneyPassage> getAd();

    public User sign(String account, String password);

    public String register(String username,String account, String password);

}
