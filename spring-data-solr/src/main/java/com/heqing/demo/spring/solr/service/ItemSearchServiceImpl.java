package com.heqing.demo.spring.solr.service;

import com.heqing.demo.spring.solr.model.Item;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.data.solr.core.SolrTemplate;
import org.springframework.data.solr.core.query.*;
import org.springframework.data.solr.core.query.result.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ItemSearchServiceImpl {

    @Autowired
    private SolrTemplate solrTemplate;


    /**
     * 查询分类列表 category  根据搜索关键字分组查询
     */
    private List searchCategoryList(Map searchMap) {
        List<String> list = new ArrayList();
        Query query = new SimpleQuery();
        //按照关键字查询
        Criteria criteria = new Criteria("item_keywords").is(searchMap.get("keywords"));
        query.addCriteria(criteria);
        //设置分组选项
        GroupOptions groupOptions = new GroupOptions().addGroupByField("item_category");
        query.setGroupOptions(groupOptions);
        //得到分组页
        GroupPage<Item> page = solrTemplate.queryForGroupPage("demo_solr", query, Item.class);
        //根据列得到分组结果集
        GroupResult<Item> groupResult = page.getGroupResult("item_category");
        //得到分组结果入口页
        Page<GroupEntry<Item>> groupEntries = groupResult.getGroupEntries();
        //得到分组入口集合
        List<GroupEntry<Item>> content = groupEntries.getContent();
        for (GroupEntry<Item> entry : content) {
            list.add(entry.getGroupValue());//将分组结果的名称封装到返回值中
        }
        return list;
    }
}
