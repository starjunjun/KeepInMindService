package com.cross.mapper;

import com.cross.pojo.ManageMoneyPassage;
import com.cross.pojo.Test;
import com.cross.pojo.User;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 *  dao
 */
@Repository("TestMapper")
public interface TestMapper {
    public int add(Test test);

    public void delete(int id);

    public Test get(int id);

    public int update(Test test);

    public List<Test> getAll();

    public List<ManageMoneyPassage> getPassage();

    public void addManageMoneyPassage(ManageMoneyPassage manageMoneyPassage);

    public  void checkCount();

    public User sign(String account );

    public void register(User user);

}
