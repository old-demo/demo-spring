package com.heqing.demo.spring.hibernate;

import com.alibaba.fastjson.JSONObject;
import com.heqing.demo.spring.hibernate.config.SpringHibernateConfig;
import com.heqing.demo.spring.hibernate.dao.PeopleDao;
import com.heqing.demo.spring.hibernate.model.People;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(
        classes = SpringHibernateConfig.class
)
public class PeopleTest {

    @Autowired
    PeopleDao peopleDao;

    @Test
    public void testSave() {
        People people = new People();
        people.setName("测试1");
        people.setAge(1);
        people.setGender("M");
        people.setCreateTime(new Date());
        System.out.println("-->"+peopleDao.save(people));
    }

    @Test
    public void testsaveBatch() {
        List<People> peopleList = new ArrayList<>();
        for(int i=0;i<3;i++) {
            People people = new People();
            people.setName("测试"+i);
            people.setAge(i);
            people.setGender("M");
            people.setCreateTime(new Date());
            peopleList.add(people);
        }
        System.out.println("-->"+peopleDao.saveBatch(peopleList));
    }

    @Test
    public void testUpdateByKey() {
        People people = new People();
        people.setId((long)28);
        people.setAge(1);
        people.setName("测试1-1");
        people.setGender("M");
        people.setCreateTime(new Date());

        System.out.println("-->"+peopleDao.updateByKey(people));
    }

    @Test
    public void testGetOneByKey() {
        System.out.println("-->"+peopleDao.getOneByKey(1L));
    }

    @Test
    public void testListByKey() {
        List<Long> ids = new ArrayList<>();
        ids.add(1L);
        ids.add(2L);
        System.out.println("-->"+peopleDao.listByKey(ids));
    }

    @Test
    public void testList() {
        System.out.println("-->"+ JSONObject.toJSONString(peopleDao.list()));

        People people = new People();
        people.setName("1-1");
        people.setGender("M");
        System.out.println("-->"+ JSONObject.toJSONString(peopleDao.list(people)));

        System.out.println("-->"+ JSONObject.toJSONString(peopleDao.listByPage(people, 2, 1)));
    }

    @Test
    public void testDelete() {
        System.out.println("-->"+peopleDao.delete(28L));
    }

    @Test
    public void testDeleteBatch() {
        List<Long> ids = new ArrayList<>();
        ids.add(29L);
        ids.add(30L);
        System.out.println("-->"+peopleDao.deleteBatch(ids));
    }

}
