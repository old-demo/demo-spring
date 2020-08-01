package com.heqing.demo.spring.hibernate;

import com.heqing.demo.spring.hibernate.dao.PeopleXmlDao;
import com.heqing.demo.spring.hibernate.model.PeopleXml;
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
        locations = {"classpath*:spring_core.xml","classpath:spring_hibernate.xml"}
)
public class PeopleXmlTest {

    @Autowired
    PeopleXmlDao peopleXmlDao;

    @Test
    public void testSave() {
        PeopleXml people = new PeopleXml();
        people.setName("测试1");
        people.setAge(1);
        people.setGender("M");
        people.setCreateTime(new Date());
        System.out.println("-->"+peopleXmlDao.save(people));
    }

    @Test
    public void testsaveBatch() {
        List<PeopleXml> peopleList = new ArrayList<>();
        for(int i=0;i<3;i++) {
            PeopleXml people = new PeopleXml();
            people.setName("测试"+i);
            people.setAge(i);
            people.setGender("M");
            people.setCreateTime(new Date());
            peopleList.add(people);
        }
        System.out.println("-->"+peopleXmlDao.saveBatch(peopleList));
    }

    @Test
    public void testUpdateByKey() {
        PeopleXml people = new PeopleXml();
        people.setId((long)35);
        people.setAge(1);
        people.setName("测试1-1");
        people.setGender("M");
        people.setCreateTime(new Date());

        System.out.println("-->"+peopleXmlDao.updateByKey(people));
    }

    @Test
    public void testGetOneByKey() {
        System.out.println("-->"+peopleXmlDao.getOneByKey(1L));
    }

    @Test
    public void testListByKey() {
        List<Long> ids = new ArrayList<>();
        ids.add(1L);
        ids.add(2L);
        System.out.println("-->"+peopleXmlDao.listByKey(ids));
    }

    @Test
    public void testDelete() {
        System.out.println("-->"+peopleXmlDao.delete(35L));
    }

    @Test
    public void testDeleteBatch() {
        List<Long> ids = new ArrayList<>();
        ids.add(36L);
        ids.add(37L);
        System.out.println("-->"+peopleXmlDao.deleteBatch(ids));
    }

}
