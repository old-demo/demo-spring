package com.heqing.demo.spring.mongodb.dao;

import com.heqing.demo.spring.mongodb.model.People;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.query.Query;

import java.util.List;

public interface PeopleDao extends BaseDao<People, Long> {

    List<People> list(Query query);

    long count(Query query);

    List<People> listGroupBy(Aggregation aggregation);
}
