package com.heqing.demo.spring.mongodb;

import com.alibaba.fastjson.JSONObject;
import com.heqing.demo.spring.mongodb.repository.PeopleRepository;
import com.heqing.demo.spring.mongodb.model.People;
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
public class PeopleTest {

    @Autowired
    PeopleRepository peopleDao;

    @Test
    public void testSave() {
        People people = new People();
        people.setId(6L);
        people.setName("贺小黑");
        people.setAge(28);
        people.setGender("M");
        people.setCreateTime(new Date());
        System.out.println("-->"+peopleDao.save(people));
    }

    @Test
    public void testBatchSave() {
        List<People> peopleList = new ArrayList<>();
        for(int i=2;i<5;i++) {
            People people = new People();
            people.setId((long) i);
            people.setName("测试"+i);
            people.setAge(25+i);
            people.setGender("G");
            people.setCreateTime(new Date());
            peopleList.add(people);
        }
        System.out.println("-->"+peopleDao.saveBatch(peopleList));
    }

    @Test
    public void testUpdate() {
        People people = new People();
        people.setId((long) 4);
        people.setName("测试1");
        people.setAge(30);
        people.setGender("M");
        people.setCreateTime(new Date());
        peopleDao.updateByKey(people);
    }

    @Test
    public void testGetOneByKey() {
        System.out.println("-->"+ JSONObject.toJSONString(peopleDao.getOneByKey(1L)));
    }

    @Test
    public void testListByKey() {
        List<Long> ids = new ArrayList<>();
        ids.add(1L);
        ids.add(2L);
        System.out.println("-->"+ JSONObject.toJSONString(peopleDao.listByKey(ids)));
    }

    @Test
    public void testList() {
        System.out.println("-->"+ JSONObject.toJSONString(peopleDao.list()));

        People people = new People();
        people.setId(1L);
        people.setAge(30);
        people.setGender("M");
        System.out.println("-->"+ JSONObject.toJSONString(peopleDao.list(people)));

        people = new People();
        people.setAge(30);
        System.out.println("-->"+ JSONObject.toJSONString(peopleDao.listByPage(people, 1, 1)));
    }

    @Test
    public void testDelete() {
        System.out.println("-->"+ JSONObject.toJSONString(peopleDao.delete(1L)));
    }

    @Test
    public void testDeleteBatch() {
        List<Long> ids = new ArrayList<>();
        ids.add(2L);
        ids.add(3L);
        System.out.println("-->"+ JSONObject.toJSONString(peopleDao.deleteBatch(ids)));
    }

    @Test
    public void testQuery() {
        // 查询一个条件
        Query query = new Query(Criteria.where("name").is("贺小白"));
        List<People> list = peopleDao.list(query);
        System.out.println("is -->"+ JSONObject.toJSONString(list));

        // 查询总数
        long cnt = peopleDao.count(query);
        System.out.println("数量 -->"+ cnt);

        // and多条件查询
        query = new Query(Criteria.where("gender").is("G").and("age").is(28));
        list = peopleDao.list(query);
        System.out.println("and -->"+ JSONObject.toJSONString(list));

        // or或查询
        query = new Query(new Criteria().orOperator(Criteria.where("name").is("贺小白"), Criteria.where("age").is(28)));
        list = peopleDao.list(query);
        System.out.println("or -->"+ JSONObject.toJSONString(list));

        // in查询
        query = new Query(Criteria.where("age").in(Arrays.asList(18, 20, 30)));
        list = peopleDao.list(query);
        System.out.println("in -->"+ JSONObject.toJSONString(list));

        // 数值比较  gt:>  gte:>=  lt:<  lte:<=
        query = new Query(Criteria.where("age").gt(29));
        list = peopleDao.list(query);
        System.out.println("大于 -->"+ JSONObject.toJSONString(list));
        query = new Query(Criteria.where("age").gte(29));
        list = peopleDao.list(query);
        System.out.println("大于/等于 -->"+ JSONObject.toJSONString(list));
        query = new Query(Criteria.where("age").lt(29));
        list = peopleDao.list(query);
        System.out.println("小于 -->"+ JSONObject.toJSONString(list));
        query = new Query(Criteria.where("age").lte(29));
        list = peopleDao.list(query);
        System.out.println("小于/等于 -->"+ JSONObject.toJSONString(list));

        // 正则查询  已某某开始（^某某.*$）  包含（^.*某某.*$）  已某某结束（^.*某某$）
        query = new Query(Criteria.where("name").regex("^.*白$"));
        list = peopleDao.list(query);
        System.out.println("正则 -->"+ JSONObject.toJSONString(list));

        // 排序
        query = Query.query(Criteria.where("gender").is("G")).with(Sort.by(Sort.Order.asc("age")));
        list = peopleDao.list(query);
        System.out.println("正序 -->"+ JSONObject.toJSONString(list));
        query = Query.query(Criteria.where("gender").is("G")).with(Sort.by(Sort.Order.desc("age")));
        list = peopleDao.list(query);
        System.out.println("倒序 -->"+ JSONObject.toJSONString(list));

        // 分页
        query = Query.query(Criteria.where("gender").is("G")).with(Sort.by(Sort.Order.asc("age"))).skip(2).limit(2);
        list = peopleDao.list(query);
        System.out.println("分页 -->"+ JSONObject.toJSONString(list));

        // 分组查询
        Aggregation aggregation = Aggregation.newAggregation(
                Aggregation.match(Criteria.where("gender").is("M")),
                Aggregation.group( "age").count().as("name"),
                Aggregation.sort(Sort.by("name"))
        );
        list = peopleDao.listGroupBy(aggregation);
        System.out.println("分组 -->"+ JSONObject.toJSONString(list));

    }

}
