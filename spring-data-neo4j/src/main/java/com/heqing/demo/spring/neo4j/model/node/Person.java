package com.heqing.demo.spring.neo4j.model.node;

import com.alibaba.fastjson.annotation.JSONField;
import com.heqing.demo.spring.neo4j.model.base.BaseNode;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.neo4j.ogm.annotation.Index;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Property;

import java.util.Date;

@NoArgsConstructor
@Data
@NodeEntity(label = "Person")
@EqualsAndHashCode(callSuper=false)
public class Person extends BaseNode {

    @Property(name = "person_id")
    @Index(unique = true)
    private Long personId;

    @Property
    private String name;

    @Property
    private Integer age;

    @Property
    private String gender;

    @Property
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date birthday;
}
