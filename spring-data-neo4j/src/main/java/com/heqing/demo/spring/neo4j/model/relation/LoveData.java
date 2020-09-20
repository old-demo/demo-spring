package com.heqing.demo.spring.neo4j.model.relation;

import com.heqing.demo.spring.neo4j.model.node.Person;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.neo4j.annotation.QueryResult;

@NoArgsConstructor
@Data
@QueryResult
public class LoveData {

    Person person1;
    Lover relation;
    Person person2;
}
