package com.heqing.demo.spring.solr.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.solr.client.solrj.beans.Field;
import org.springframework.data.annotation.Id;
import org.springframework.data.solr.core.mapping.Dynamic;
import org.springframework.data.solr.core.mapping.Indexed;
import org.springframework.data.solr.core.mapping.SolrDocument;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@NoArgsConstructor
@Data
@SolrDocument(collection = "demo_solr")
public class Item {

    @Id
    @Indexed("item_id")
    private Long id;

    @Indexed(type="text_ik")
    @Field("item_name")
    private String name;

    @Field("item_desc")
    private String desc;

    @Indexed
    @Field("item_brand")
    private String brand;

    @Field("item_price")
    private Double price;

    @Indexed
    @Field("item_isdelete")
    private Integer isdelete;

    @Field("item_create_ime")
    private Date createTime;

    @Field("item_keywords")
    private String keywords;

    @Dynamic
    @Field("item_spec_*")
    private Map<String, String> specMap = new HashMap<String, String>();
}
