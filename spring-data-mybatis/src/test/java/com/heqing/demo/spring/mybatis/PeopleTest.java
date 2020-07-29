package com.heqing.demo.spring.mybatis;

import com.alibaba.fastjson.JSONObject;
import com.heqing.demo.spring.mybatis.model.People;
import com.heqing.demo.spring.mybatis.service.PeopleService;
import com.heqing.demo.spring.mybatis.util.PageInfoUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(
        locations = {"classpath*:spring_core.xml","classpath:spring_mybatis.xml"}
)
public class PeopleTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(PeopleTest.class);

    @Autowired
    PeopleService peopleService;

    @Test
    public void testSavePeople() {
        People people = new People();
        people.setName("测试1");
        people.setAge(1);
        people.setGender("M");
        people.setCreateTime(new Date());
        System.out.println("-->"+peopleService.savePeople(people));
    }

    @Test
    public void testBatchSavePeople() {
        List<People> peopleList = new ArrayList<>();
        for(int i=2;i<5;i++) {
            People people = new People();
            people.setName("测试"+i);
            people.setAge(i);
            people.setGender("M");
            people.setCreateTime(new Date());
            peopleList.add(people);
        }
        System.out.println("-->"+peopleService.savePeople(peopleList));
    }

    @Test
    public void testListPeople() {
        System.out.println("-->"+JSONObject.toJSONString(peopleService.listPeople()));
    }

    @Test
    public void testListPeopleByParam() {
        People people = new People();
        people.setName("测试1");
        System.out.println("-->"+JSONObject.toJSONString(peopleService.listPeopleByParam(people)));
    }

    @Test
    public void testListPeopleByPage() {
        PageInfoUtil<People> peopleList = peopleService.listPeopleByPage(1, 2);
        System.out.println("-->"+ JSONObject.toJSONString(peopleList));
    }

    @Test
    public void testListPeopleByParamAndPage() {
        People people = new People();
        people.setGender("M");
        PageInfoUtil<People> peopleList = peopleService.listPeopleByParamAndPage(people, 1, 2);
        System.out.println("-->"+JSONObject.toJSONString(peopleList));
    }

    @Test
    public void testGetPeopleByKey() {
        long peoplePK = 10L;
        System.out.println("-->"+JSONObject.toJSONString(peopleService.getPeopleByKey(peoplePK)));
    }

    @Test
    public void testListPeopleByKey() {
        List<Long> keyList = new ArrayList<>();
        keyList.add(11L);
        keyList.add(12L);
        System.out.println("-->"+JSONObject.toJSONString(peopleService.listPeopleByKey(keyList)));
    }

    @Test
    public void testUpdatePeopleByKey() {
        People people = new People();
        people.setId((long)12);
        people.setName("测试1-1");
        people.setCreateTime(new Date());
        System.out.println("-->"+peopleService.updatePeopleByKey(people));
    }

    @Test
    public void testUpdateBatchPeopleByKey() {
        List<People> peopleList = new ArrayList<>();
        for(long i=2l;i<5l;i++) {
            People people = new People();
            people.setId(i);
            people.setName("测试"+i+"_"+i);
            peopleList.add(people);
        }
        System.out.println("-->"+peopleService.updatePeopleByKey(peopleList));
    }

    @Test
    public void testDeletePeopleByKey() {
        long peoplePK = 2L;
        System.out.println("-->"+peopleService.deletePeopleByKey(peoplePK));
    }

    @Test
    public void testDeleteBatchPeopleByKey() {
        List<Long> keyList = new ArrayList<>();
        keyList.add(3L);
        keyList.add(4L);
        System.out.println("-->"+peopleService.deletePeopleByKey(keyList));
    }

    @Test
    public void testDeletePeopleByParam() {
        People people = new People();
        people.setName("测试4");
        System.out.println("-->"+peopleService.deletePeopleByParam(people));
    }

}
