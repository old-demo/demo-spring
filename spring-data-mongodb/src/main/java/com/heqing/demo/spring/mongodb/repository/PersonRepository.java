package com.heqing.demo.spring.mongodb.repository;

import com.heqing.demo.spring.mongodb.model.Person;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.query.Query;

import java.util.List;

public interface PersonRepository extends BaseRepository<Person, Long> {

    List<Person> list(Query query);

    long count(Query query);

    List<Person> listGroupBy(Aggregation aggregation);
}
