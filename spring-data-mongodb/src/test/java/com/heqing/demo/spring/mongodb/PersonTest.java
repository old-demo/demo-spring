package com.heqing.demo.spring.mongodb;

import com.alibaba.fastjson.JSONObject;
import com.heqing.demo.spring.mongodb.repository.PersonRepository;
import com.heqing.demo.spring.mongodb.model.Person;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;

import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(
//        classes = SpringMongoDBConfig.class
        locations = {"classpath*:spring_core.xml","classpath:spring_mongodb.xml"}
)
public class PersonTest {

    @Autowired
    PersonRepository personDao;

    @Test
    public void testSave() {
        Person person = new Person();
        person.setId(6L);
        person.setName("贺小黑");
        person.setAge(28);
        person.setGender("M");
        person.setCreateTime(new Date());
        System.out.println("-->"+ personDao.save(person));
    }

    @Test
    public void testBatchSave() {
        List<Person> personList = new ArrayList<>();
        for(int i=2;i<5;i++) {
            Person person = new Person();
            person.setId((long) i);
            person.setName("测试"+i);
            person.setAge(25+i);
            person.setGender("G");
            person.setCreateTime(new Date());
            personList.add(person);
        }
        System.out.println("-->"+ personDao.saveBatch(personList));
    }

    @Test
    public void testUpdate() {
        Person person = new Person();
        person.setId((long) 4);
        person.setName("测试1");
        person.setAge(30);
        person.setGender("M");
        person.setCreateTime(new Date());
        personDao.updateByKey(person);
    }

    @Test
    public void testGetOneByKey() {
        System.out.println("-->"+ JSONObject.toJSONString(personDao.getOneByKey(1L)));
    }

    @Test
    public void testListByKey() {
        List<Long> ids = new ArrayList<>();
        ids.add(1L);
        ids.add(2L);
        System.out.println("-->"+ JSONObject.toJSONString(personDao.listByKey(ids)));
    }

    @Test
    public void testList() {
        System.out.println("-->"+ JSONObject.toJSONString(personDao.list()));

        Person person = new Person();
        person.setId(1L);
        person.setAge(30);
        person.setGender("M");
        System.out.println("-->"+ JSONObject.toJSONString(personDao.list(person)));

        person = new Person();
        person.setAge(30);
        System.out.println("-->"+ JSONObject.toJSONString(personDao.listByPage(person, 1, 1)));
    }

    @Test
    public void testDelete() {
        System.out.println("-->"+ JSONObject.toJSONString(personDao.delete(1L)));
    }

    @Test
    public void testDeleteBatch() {
        List<Long> ids = new ArrayList<>();
        ids.add(2L);
        ids.add(3L);
        System.out.println("-->"+ JSONObject.toJSONString(personDao.deleteBatch(ids)));
    }

    @Test
    public void testQuery() {
        // 查询一个条件
        Query query = new Query(Criteria.where("name").is("贺小白"));
        List<Person> list = personDao.list(query);
        System.out.println("is -->"+ JSONObject.toJSONString(list));

        // 查询总数
        long cnt = personDao.count(query);
        System.out.println("数量 -->"+ cnt);

        // and多条件查询
        query = new Query(Criteria.where("gender").is("G").and("age").is(28));
        list = personDao.list(query);
        System.out.println("and -->"+ JSONObject.toJSONString(list));

        // or或查询
        query = new Query(new Criteria().orOperator(Criteria.where("name").is("贺小白"), Criteria.where("age").is(28)));
        list = personDao.list(query);
        System.out.println("or -->"+ JSONObject.toJSONString(list));

        // in查询
        query = new Query(Criteria.where("age").in(Arrays.asList(18, 20, 30)));
        list = personDao.list(query);
        System.out.println("in -->"+ JSONObject.toJSONString(list));

        // 数值比较  gt:>  gte:>=  lt:<  lte:<=
        query = new Query(Criteria.where("age").gt(29));
        list = personDao.list(query);
        System.out.println("大于 -->"+ JSONObject.toJSONString(list));
        query = new Query(Criteria.where("age").gte(29));
        list = personDao.list(query);
        System.out.println("大于/等于 -->"+ JSONObject.toJSONString(list));
        query = new Query(Criteria.where("age").lt(29));
        list = personDao.list(query);
        System.out.println("小于 -->"+ JSONObject.toJSONString(list));
        query = new Query(Criteria.where("age").lte(29));
        list = personDao.list(query);
        System.out.println("小于/等于 -->"+ JSONObject.toJSONString(list));

        // 正则查询  已某某开始（^某某.*$）  包含（^.*某某.*$）  已某某结束（^.*某某$）
        query = new Query(Criteria.where("name").regex("^.*白$"));
        list = personDao.list(query);
        System.out.println("正则 -->"+ JSONObject.toJSONString(list));

        // 排序
        query = Query.query(Criteria.where("gender").is("G")).with(Sort.by(Sort.Order.asc("age")));
        list = personDao.list(query);
        System.out.println("正序 -->"+ JSONObject.toJSONString(list));
        query = Query.query(Criteria.where("gender").is("G")).with(Sort.by(Sort.Order.desc("age")));
        list = personDao.list(query);
        System.out.println("倒序 -->"+ JSONObject.toJSONString(list));

        // 分页
        query = Query.query(Criteria.where("gender").is("G")).with(Sort.by(Sort.Order.asc("age"))).skip(2).limit(2);
        list = personDao.list(query);
        System.out.println("分页 -->"+ JSONObject.toJSONString(list));

        // 分组查询
        Aggregation aggregation = Aggregation.newAggregation(
                Aggregation.match(Criteria.where("gender").is("M")),
                Aggregation.group( "age").count().as("name"),
                Aggregation.sort(Sort.by("name"))
        );
        list = personDao.listGroupBy(aggregation);
        System.out.println("分组 -->"+ JSONObject.toJSONString(list));

    }

}
