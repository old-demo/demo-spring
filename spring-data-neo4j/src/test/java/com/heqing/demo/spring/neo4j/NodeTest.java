package com.heqing.demo.spring.neo4j;

import com.alibaba.fastjson.JSONObject;
import com.heqing.demo.spring.neo4j.config.SpringNeo4jDBConfig;
import com.heqing.demo.spring.neo4j.model.node.Person;
import com.heqing.demo.spring.neo4j.repository.PersonRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.neo4j.ogm.session.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.time.format.DateTimeFormatter;
import java.util.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(
        classes = SpringNeo4jDBConfig.class
)
public class NodeTest {

    @Autowired
    Session neo4jSession;

    @Autowired
    PersonRepository personRepository;

    @Test
    public void testSession() {
        String cql = "MATCH(p:Person) WHERE p.name = {name} OR p.age IN {age} RETURN p ORDER BY p.birthday SKIP 1 LIMIT 1 ";
        Map<String,Object> params = new HashMap<>();
        params.put("name", "贺小白");
        params.put("age", Arrays.asList(27));
        Person person = neo4jSession.queryForObject(Person.class, cql, params);
        System.out.println("--> "+JSONObject.toJSONString(person));

        cql = "MATCH(p:Person) WHERE p.person_id = {personId} SET p.name = {name} ,p.age = {age} RETURN p";
        params = new HashMap<>();
        params.put("personId", 4);
        params.put("name", "贺小黑");
        params.put("age", 31);
        person = neo4jSession.queryForObject(Person.class, cql, params);
        System.out.println("--> "+JSONObject.toJSONString(person));

        cql = "MATCH ( person:Person ) WHERE person.person_id={personId} RETURN person";
        params.put("personId", 4);
        person = neo4jSession.queryForObject(Person.class, cql, params);
        System.out.println("--> "+JSONObject.toJSONString(person));

        cql = "CREATE ( person:Person { person_id:{person_id}, name:{name}, age:{age}, gender:{gender}, birthday:{birthday}} ) RETURN person";
        params = new HashMap<>();
        params.put("person_id", 14);
        params.put("name", "贺小黑");
        params.put("age", 31);
        params.put("gender", "M");
        DateTimeFormatter format = DateTimeFormatter.ISO_INSTANT;
        params.put("birthday", format.format(new Date().toInstant()));
        System.out.println("--> "+JSONObject.toJSONString(params));

//        CREATE ( person:Person { person_id:14, name:"机器人", age:100, gender:"M", birthday:2020-09-02T19:46:50.030Z} ) RETURN person
//        person = neo4jSession.queryForObject(Person.class, cql, params);
//        System.out.println("--> "+JSONObject.toJSONString(person));
    }

    @Test
    public void testAddNode() {
        Person personNode = new Person();
        personNode.setPersonId(4L);
        personNode.setName("贺小白");
        personNode.setGender("M");
        personNode.setAge(19);
        personNode.setBirthday(new Date());
        Person save = personRepository.save(personNode);
        System.out.println("-->" + save);
    }

    @Test
    public void testAddNodeList() {
        Person hqNode = new Person();
        hqNode.setPersonId(1L);
        hqNode.setName("贺小白");
        hqNode.setGender("M");
        hqNode.setAge(30);
        hqNode.setBirthday(new Date());

        Person syNode = new Person();
        syNode.setPersonId(2L);
        syNode.setName("石小燕");
        syNode.setGender("F");
        syNode.setAge(27);
        syNode.setBirthday(new Date());

        List<Person> nodeList = new ArrayList<>();
        nodeList.add(hqNode);
        nodeList.add(syNode);
        personRepository.saveAll(nodeList);
    }

    @Test
    public void testFindAll() {
        Iterable<Person> nodeAll = personRepository.findAll();
        System.out.println("-->" + JSONObject.toJSONString(nodeAll));
    }

    @Test
    public void testFindByName() {
        List<Person> personList = personRepository.findByName("贺小白");
        System.out.println("personList -->" + JSONObject.toJSONString(personList));
    }

    @Test
    public void testCountPersonByAge() {
        List<Person> personList = personRepository.findAllByAgeAfter(20);
        System.out.println("-->" + JSONObject.toJSONString(personList));

        Long num = personRepository.countPersonByName("贺小白");
        Person person = personRepository.findPersonByName("贺小白");
        System.out.println(num + "-->" + JSONObject.toJSONString(person));
    }

    @Test
    public void testDeleteNode() {
         personRepository.deleteAll();
    }

}
