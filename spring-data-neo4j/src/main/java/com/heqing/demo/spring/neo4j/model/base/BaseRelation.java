package com.heqing.demo.spring.neo4j.model.base;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.neo4j.ogm.annotation.GeneratedValue;
import org.neo4j.ogm.annotation.Id;

import java.io.Serializable;

/**
 * 所有Neo4j中的节点、(关系)边 都需要有一个默认的id属性，保存的是Neo4j中自己的数据主键，与业务无关
 */
@NoArgsConstructor
@Data
public class BaseRelation implements Serializable {

    @Id
    @GeneratedValue
    private Long id;
}
