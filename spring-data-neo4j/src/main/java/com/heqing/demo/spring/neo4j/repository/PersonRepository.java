package com.heqing.demo.spring.neo4j.repository;

import com.heqing.demo.spring.neo4j.model.node.Person;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

public interface PersonRepository extends Neo4jRepository<Person, Long> {

    List<Person> findByName(String name);

    List<Person> findAllByAgeAfter(int age);

    @Query("MATCH(p:Person) WHERE p.name = {name} RETURN p ORDER BY p.birthday LIMIT 1")
    Person findPersonByName(@Param("name") String name);

    @Query("MATCH(p:Person) WHERE p.name = {name} RETURN count(p.name)")
    long countPersonByName(@Param("name") String name);
}
