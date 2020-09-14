package com.heqing.demo.spring.mongodb.repository.impl;

import com.heqing.demo.spring.mongodb.repository.PersonRepository;
import com.heqing.demo.spring.mongodb.model.Person;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class PersonRepositoryImpl extends BaseRepositoryImpl<Person, Long> implements PersonRepository {

    @Override
    public List<Person> list(Query query) {
        return mongoTemplate.find(query, persistentClass);
    }

    @Override
    public long count(Query query) {
        return mongoTemplate.count(query, persistentClass);
    }

    @Override
    public List<Person> listGroupBy(Aggregation aggregation) {
        AggregationResults<Person> results = mongoTemplate.aggregate(aggregation, collectionName, persistentClass);
        return results.getMappedResults();
    }
}
