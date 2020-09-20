package com.heqing.demo.spring.solr;

import com.alibaba.fastjson.JSONObject;
import com.heqing.demo.spring.solr.config.SpringSolrConfig;
import com.heqing.demo.spring.solr.model.Item;
import com.heqing.demo.spring.solr.repository.ItemRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.solr.core.SolrTemplate;
import org.springframework.data.solr.core.query.*;
import org.springframework.data.solr.core.query.result.*;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(
        classes = SpringSolrConfig.class
)
public class TestSolr {

    @Autowired
    ItemRepository itemRepository;

    @Autowired
    SolrTemplate solrTemplate;

    @Test
    public void test() {
        System.out.println("---");
    }

    @Test
    public void testSave() {
        // 增加或者修改
        for(Long i =0L; i<100; i++) {
            Item item = new Item();
            item.setId(i);
            item.setName("测试商品"+i);
            item.setBrand("测试商标"+i);
            item.setDesc("测试描述"+i);
            item.setPrice(1.1D+i);
            item.setIsdelete(0);
            item.setCreateTime(new Date());
            item.setKeywords("测试"+i);
            itemRepository.save(item);
        }
    }

    @Test
    public void testFindByName() {
        List<Item> itemList = itemRepository.findByName("30");
        itemList.forEach(System.out::println);
    }

    @Test
    public void testFindAll() {
        itemRepository.deleteAll();

        Iterable<Item> all = itemRepository.findAll();
        all.forEach(System.out::println); // 直接输出最终结果
    }

    @Test
    public void testQuery() {
        Query query = new SimpleQuery("*:*");
        // 分页
        query.setOffset(0L); //开始索引（默认0）
        query.setRows(20); //每页记录数（默认10）
        // 排序
        Sort sort = Sort.by(Sort.Direction.ASC, "item_id");
        query.addSort(sort);
        // 查询条件
        Criteria criteria =  Criteria.where("item_id").between(11L, 90L).and("item_isdelete").is(0)
                .and("item_name").contains("商").and("item_keywords").in("10", "20", "30", "40","50", "60");
        query.addCriteria(criteria);

        GroupPage<Item> page = solrTemplate.queryForGroupPage("demo_solr", query, Item.class);
        System.out.println("总记录数：" + page.getTotalElements());
        System.out.println("总页数："+ page.getTotalPages());
        List<Item> list = page.getContent();
        list.forEach(System.out::println);
    }

    @Test
    public void testQueryForGroupPage() {

        Query query = new SimpleQuery("*:*");
        Criteria criteria = Criteria.where("item_id").between(51L, 100L);
        query.addCriteria(criteria);
        //设置分组选项
//        GroupOptions groupOptions = new GroupOptions().addGroupByField("item_isdelete");
//        query.setGroupOptions(groupOptions);

//        GroupPage<Item> page = solrTemplate.queryForGroupPage("demo_solr", query, Item.class);
//        System.out.println("总记录数：" + page.getTotalElements());

        //得到分组页
        GroupPage<Item> page = solrTemplate.queryForGroupPage("demo_solr", query, Item.class);
        System.out.println("总记录数：" + page.getTotalElements());
        System.out.println("-->" + JSONObject.toJSONString(page));

    }

    @Test
    public void testHighlightQuery (){
        HighlightQuery query=new SimpleHighlightQuery();
        HighlightOptions highlightOptions=new HighlightOptions().addField("item_name");//设置高亮的域
        highlightOptions.setSimplePrefix("<em style='color:red'>");//高亮前缀
        highlightOptions.setSimplePostfix("</em>");//高亮后缀
        query.setHighlightOptions(highlightOptions);//设置高亮选项
        //按照关键字查询
        Criteria criteria=new Criteria("item_keywords").is("测");
        query.addCriteria(criteria);
        HighlightPage<Item> page = solrTemplate.queryForHighlightPage("demo_solr", query, Item.class);
        for(HighlightEntry<Item> h: page.getHighlighted()){//循环高亮入口集合
            Item item = h.getEntity();//获取原实体类
            if(h.getHighlights().size()>0 && h.getHighlights().get(0).getSnipplets().size()>0){
                System.out.println("---------------");
                item.setName(h.getHighlights().get(0).getSnipplets().get(0));//设置高亮的结果
            }
        }
        System.out.println("--"+JSONObject.toJSONString(page.getContent()));
    }


}
