package com.heqing.demo.spring.mybatis;

import com.alibaba.fastjson.JSONObject;
import com.heqing.demo.spring.mybatis.config.SpringMybatisConfig;
import com.heqing.demo.spring.mybatis.dao.PeopleDao;
import com.heqing.demo.spring.mybatis.mapper.PeopleMapper;
import com.heqing.demo.spring.mybatis.model.People;
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
        classes = SpringMybatisConfig.class
)
public class PeopleMapperTest {

    @Autowired
    PeopleDao peopleDao;

    @Autowired
    PeopleMapper peopleMapper;

    @Test
    public void testSavePeople() {
        People people = new People();
        people.setName("测试1");
        people.setAge(1);
        people.setGender("M");
        people.setCreateTime(new Date());
        System.out.println("dao-->"+peopleDao.savePeople(people));
        System.out.println("mapper-->"+peopleMapper.savePeople(people));
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
        System.out.println("dao-->"+peopleDao.saveBatchPeople(peopleList));
        System.out.println("mapper-->"+peopleMapper.saveBatchPeople(peopleList));
    }

    @Test
    public void testListPeople() {
        System.out.println("dao-->"+ JSONObject.toJSONString(peopleDao.listPeople()));
        System.out.println("mapper-->"+ JSONObject.toJSONString(peopleMapper.listPeople()));
    }

    @Test
    public void testListPeopleByParam() {
        People people = new People();
        people.setName("测试1");
        System.out.println("dao-->"+JSONObject.toJSONString(peopleDao.listPeopleByParam(people)));
        System.out.println("mapper-->"+ JSONObject.toJSONString(peopleMapper.listPeopleByParam(people)));
    }

    @Test
    public void testGetPeopleByKey() {
        long peoplePK = 1L;
        System.out.println("dao-->"+JSONObject.toJSONString(peopleDao.getPeopleByKey(peoplePK)));
        System.out.println("mapper-->"+JSONObject.toJSONString(peopleMapper.getPeopleByKey(peoplePK)));
    }

    @Test
    public void testListPeopleByKey() {
        List<Long> keyList = new ArrayList<>();
        keyList.add(1L);
        keyList.add(2L);
        System.out.println("dao-->"+JSONObject.toJSONString(peopleDao.listPeopleByKey(keyList)));
        System.out.println("mapper-->"+JSONObject.toJSONString(peopleMapper.listPeopleByKey(keyList)));
    }

    @Test
    public void testUpdatePeopleByKey() {
        People people = new People();
        people.setId((long)1);
        people.setName("测试1-1");
        people.setCreateTime(new Date());
        System.out.println("-->"+people);

        System.out.println("dao-->"+peopleDao.updatePeopleByKey(people));

        people = new People();
        people.setId((long)2);
        people.setName("测试1-2");
        people.setCreateTime(new Date());
        System.out.println("mapper-->"+peopleMapper.updatePeopleByKey(people));
    }

    @Test
    public void testDeletePeopleByKey() {
        long peoplePK = 14L;
        System.out.println("dao-->"+peopleDao.deletePeopleByKey(peoplePK));

        peoplePK = 15L;
        System.out.println("mapper-->"+peopleMapper.deletePeopleByKey(peoplePK));
    }

    @Test
    public void testDeleteBatchPeopleByKey() {
        List<Long> keyList = new ArrayList<>();
        keyList.add(16L);
        keyList.add(17L);
        System.out.println("dao-->"+peopleDao.deleteBatchPeopleByKey(keyList));

        keyList = new ArrayList<>();
        keyList.add(18L);
        keyList.add(19L);
        System.out.println("mapper-->"+peopleMapper.deleteBatchPeopleByKey(keyList));
    }

    @Test
    public void testDeletePeopleByParam() {
        People people = new People();
        people.setName("测试2");
        System.out.println("dao-->"+peopleDao.deletePeopleByParam(people));

        people = new People();
        people.setName("测试3");
        System.out.println("mapper-->"+peopleMapper.deletePeopleByParam(people));
    }

    @Test
    public void testCache() {
        for(int i=0; i<3; i++) {
            System.out.println("dao-->" + JSONObject.toJSONString(peopleDao.getPeopleByKey(1L)));
            System.out.println("mapper-->" + JSONObject.toJSONString(peopleMapper.getPeopleByKey(2L)));
        }
    }
}
