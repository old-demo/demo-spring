package com.heqing.demo.spring.mongodb.repository;

import com.heqing.demo.spring.mongodb.model.Person;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PersonExtRepository extends MongoRepository<Person, Long>  {

    List<Person> findByName(String name);

    @Query("{ 'name' : ?#{[0]} }")
    List<Person> findPersonByName(@Param("name") String name);
}
