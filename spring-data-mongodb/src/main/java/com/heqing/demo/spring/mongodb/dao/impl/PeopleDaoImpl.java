package com.heqing.demo.spring.mongodb.dao.impl;

import com.heqing.demo.spring.mongodb.dao.PeopleDao;
import com.heqing.demo.spring.mongodb.model.People;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class PeopleDaoImpl extends BaseDaoImpl<People, Long> implements PeopleDao {

    @Override
    public List<People> list(Query query) {
        return mongoTemplate.find(query, persistentClass);
    }

    @Override
    public long count(Query query) {
        return mongoTemplate.count(query, persistentClass);
    }

    @Override
    public List<People> listGroupBy(Aggregation aggregation) {
        AggregationResults<People> results = mongoTemplate.aggregate(aggregation, collectionName, persistentClass);
        return results.getMappedResults();
    }
}
