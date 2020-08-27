package com.heqing.demo.spring.neo4j.model.base;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.neo4j.ogm.annotation.Id;
import org.neo4j.ogm.annotation.GeneratedValue;
import org.neo4j.ogm.annotation.Labels;
import org.neo4j.ogm.annotation.Version;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * 节点基础属性  （这里所有继承BaseNode的节点会自动带上 BaseNode 标签）
 */
@NoArgsConstructor
@Data
public class BaseNode implements Serializable {

    @Id
    @GeneratedValue
    private Long id;

    /**
     * 节点的标签(代表什么类型的节点) 可以自定义添加多个
     */
    @Labels
    private Set<String> labels = new HashSet<>();

    @Version
    private Long version;
}
