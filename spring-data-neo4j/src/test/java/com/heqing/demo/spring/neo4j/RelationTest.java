package com.heqing.demo.spring.neo4j;

import com.alibaba.fastjson.JSONObject;
import com.heqing.demo.spring.neo4j.config.SpringNeo4jDBConfig;
import com.heqing.demo.spring.neo4j.model.node.Person;
import com.heqing.demo.spring.neo4j.model.relation.LoveData;
import com.heqing.demo.spring.neo4j.model.relation.Lover;
import com.heqing.demo.spring.neo4j.repository.LoverRepository;
import com.heqing.demo.spring.neo4j.repository.PersonRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(
        classes = SpringNeo4jDBConfig.class
)
public class RelationTest {

    @Autowired
    PersonRepository personRepository;

    @Autowired
    LoverRepository loverRepository;

    @Test
    public void testAdd() {
        Person hq = personRepository.findPersonByName("贺小白");
        System.out.println("hq -->" + JSONObject.toJSONString(hq));

        Person sy = personRepository.findPersonByName("石小燕");
        System.out.println("sy -->" + JSONObject.toJSONString(sy));

        Lover lover = new Lover();
        lover.setStartNode(hq);
        lover.setEndNode(sy);
        loverRepository.save(lover);
    }

    @Test
    public void testGetLoveData() {
        LoveData loveData = loverRepository.getLoveData("贺小白");
        System.out.println("--> "+JSONObject.toJSONString(loveData));
    }
}
