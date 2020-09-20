package com.heqing.demo.spring.neo4j.repository;

import com.heqing.demo.spring.neo4j.model.relation.LoveData;
import com.heqing.demo.spring.neo4j.model.relation.Lover;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface LoverRepository extends Neo4jRepository<Lover, Long> {

    @Query("MATCH (person1:Person)-[l:Lover]->(person2:Person)" +
            "WHERE person1.name = {name} " +
            "RETURN person1, l, person2")
     LoveData getLoveData(@Param("name") String name);
}
