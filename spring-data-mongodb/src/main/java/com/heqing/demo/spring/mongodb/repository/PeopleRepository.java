package com.heqing.demo.spring.mongodb.repository;

import com.heqing.demo.spring.mongodb.model.People;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.query.Query;

import java.util.List;

public interface PeopleRepository extends BaseRepository<People, Long> {

    List<People> list(Query query);

    long count(Query query);

    List<People> listGroupBy(Aggregation aggregation);
}
