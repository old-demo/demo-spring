package com.heqing.demo.spring.elasticsearch.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.elasticsearch.common.Nullable;
import org.springframework.data.annotation.Id;
import org.springframework.data.domain.Persistable;
import org.springframework.data.elasticsearch.annotations.*;

import java.time.LocalDate;

/**
 indexName： 索引名称
type：索引类型
shards：分片的数量
replicas：副本的数量
refreshInterval： 刷新间隔
indexStoreType：索引文件存储类型
 */
@Document(indexName = "demo_item", shards = 5,replicas = 0)
@NoArgsConstructor
@Data
@Setting(settingPath = "/settings/demo_item.json")
@Mapping(mappingPath = "/mappings/demo_item.json")
public class Item implements Persistable<String> {

    /** 主键注解，标识一个属性为主键 */
    @Id
    @Nullable
    private String id;

    /**
     * 分词不起作用,bug??
     * @Field 标注在属性上，用来指定属性的类型
     * analyzer：指定分词器，es中默认使用的标准分词器，比如我们需要指定中文IK分词器，可以指定值为ik_max_word
     * type： 指定该属性在es中的类型，其中的值是FileType类型的值，比如FileType.Text类型对应es中的text类型
     * index：指定该词是否需要索引，默认为true
     * store：指定该属性内容是否需要存储，默认为
     * fielddata ：指定该属性能否进行排序，因为es中的text类型是不能进行排序（已经分词了）
     * searchAnalyzer ： 指定搜索使用的分词器
     */
    @Field(type = FieldType.Text, analyzer="ik_max_word_pinyin")
    private String name;

    @Field(type = FieldType.Text)
    private String desc;

    @Field(type = FieldType.Text)
    private String brand;

    @Field(type = FieldType.Double)
    private Double price;

    @Field(name = "is-delete",type = FieldType.Integer)
    private Integer isdelete;

    @Nullable
    @Field(name = "create-date", type = FieldType.Date, format = DateFormat.basic_date_time, pattern ="yyyy-MM-dd HH:mm:ss")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern ="yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDate createTime;

    @Override
    public boolean isNew() {
        return id == null || (createTime == null);
    }
}
