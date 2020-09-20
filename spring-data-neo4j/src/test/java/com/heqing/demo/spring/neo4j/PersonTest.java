package com.heqing.demo.spring.neo4j;

import com.alibaba.fastjson.JSONObject;
import com.heqing.demo.spring.neo4j.config.SpringNeo4jDBConfig;
import com.heqing.demo.spring.neo4j.model.node.Person;
import com.heqing.demo.spring.neo4j.repository.PersonExtRepository;
import com.heqing.demo.spring.neo4j.util.PageInfoUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Date;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(
        classes = SpringNeo4jDBConfig.class
)
public class PersonTest {

    @Autowired
    PersonExtRepository personExtRepository;

    @Test
    public void testSave() {
        Person person = new Person();
        person.setPersonId(14L);
        person.setName("机器人");
        person.setGender("M");
        person.setAge(100);
        person.setBirthday(new Date());
        Person result = personExtRepository.save(person);
        System.out.println("-->"+ JSONObject.toJSONString(result));
    }

    @Test
    public void testUpdate() {
        Person person = new Person();
        person.setPersonId(14L);
        person.setName("机器人");
        person.setGender("M");
        person.setAge(101);
        person.setBirthday(new Date());
        Person result = personExtRepository.updateByKey(person);
        System.out.println("-->"+ JSONObject.toJSONString(result));
    }

    @Test
    public void testGetOneByKey() {
        Person result = personExtRepository.getOneByKey(14L);
        System.out.println("-->"+ JSONObject.toJSONString(result));
    }

    @Test
    public void testList() {
        List<Person> result = personExtRepository.list();
        System.out.println("-->"+ JSONObject.toJSONString(result));

        Person person = new Person();
        person.setPersonId(15L);
        person.setName("机器人");
        result = personExtRepository.list(person);
        System.out.println("-->"+ JSONObject.toJSONString(result));
    }

    @Test
    public void testCount() {
        Long result = personExtRepository.count();
        System.out.println("-->"+ result);
    }

    @Test
    public void testListByPage() {
        Person person = new Person();
        person.setPersonId(14L);
        person.setName("机器人");
        PageInfoUtil<Person> result = personExtRepository.listByPage(person, 1, 5);
        System.out.println("-->"+ JSONObject.toJSONString(result));
    }

    @Test
    public void testDelete() {
        personExtRepository.delete(14L);
    }
}
