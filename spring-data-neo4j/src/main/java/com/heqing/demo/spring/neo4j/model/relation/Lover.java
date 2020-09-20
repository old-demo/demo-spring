package com.heqing.demo.spring.neo4j.model.relation;

import com.heqing.demo.spring.neo4j.model.base.BaseRelation;
import com.heqing.demo.spring.neo4j.model.node.Person;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.neo4j.ogm.annotation.EndNode;
import org.neo4j.ogm.annotation.RelationshipEntity;
import org.neo4j.ogm.annotation.StartNode;

@NoArgsConstructor
@Data
@RelationshipEntity(type = "Lover")
@EqualsAndHashCode(callSuper=false)
public class Lover extends BaseRelation {

    @StartNode
    private Person startNode;

    @EndNode
    private Person endNode;
}
