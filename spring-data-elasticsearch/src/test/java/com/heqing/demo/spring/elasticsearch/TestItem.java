package com.heqing.demo.spring.elasticsearch;

import com.alibaba.fastjson.JSONObject;
import com.heqing.demo.spring.elasticsearch.config.SpringEsConfig;
import com.heqing.demo.spring.elasticsearch.model.Item;
import com.heqing.demo.spring.elasticsearch.repository.ItemRepository;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.mapping.IndexCoordinates;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;;import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(
        classes = SpringEsConfig.class
)
public class TestItem {

    @Autowired
    RestHighLevelClient highLevelClient;

    @Autowired
    ElasticsearchRestTemplate elasticsearchRestTemplate;

    /**
     * https://my.oschina.net/matol/blog/3023088
     * index(T t) ：添加数据
     * save(T t)：添加数据
     * count()： 获取数据总数
     * findAll()：获取所有的数据，返回的是一个java.lang.Iterable
     * Iterable<T> findAllById(Iterable<ID> ids)：根据Id批量返回数据
     * saveAll(Iterable entity) ：批量保存数据，可以传入List
     * delete(T t) ： 删除指定的实体类，只需要指定实体类中的Id即可
     * deleteAll()：删除所有的数据
     * deleteById(ID Id)：根据Id删除数据
     * existsById(ID Id)： 判断指定Id的数据是否存在
     */
    @Autowired
    ItemRepository itemRepository;

    @Test
    public void testESClient() throws IOException {
        GetRequest getRequest= new GetRequest("demo_item", "fa055066404a");
        GetResponse getResponse = highLevelClient.get(getRequest, RequestOptions.DEFAULT);
        System.out.println(getResponse.getIndex());
        System.out.println(getResponse.toString());
    }

    @Test
    public void deleteAll() {
        itemRepository.deleteAll();
    }

    @Test
    public void testSave() {
        Item item1 = new Item();
        item1.setId(System.currentTimeMillis()+"");
        item1.setName("苹果12");
        item1.setBrand("苹果");
        item1.setDesc("苹果最新款手机");
        item1.setPrice(8888.0D);
        item1.setIsdelete(0);
        item1.setCreateTime(LocalDate.now());
        item1 = itemRepository.save(item1);
        System.out.println("1 --> " + item1);

        Item item2 = new Item();
        item2.setId(System.currentTimeMillis()+"");
        item2.setName("苹果11");
        item2.setBrand("苹果");
        item2.setDesc("苹果去年旧款");
        item2.setPrice(6666.0D);
        item2.setIsdelete(0);
        item2.setCreateTime(LocalDate.now());
        item2 = itemRepository.save(item2);
        System.out.println("2 --> " + item2);

        Item item3 = new Item();
        item3.setId(System.currentTimeMillis()+"");
        item3.setName("华为V30");
        item3.setBrand("华为");
        item3.setDesc("华为5G标杆");
        item3.setPrice(3500.0D);
        item3.setIsdelete(0);
        item3.setCreateTime(LocalDate.now());
        item3 = itemRepository.save(item3);
        System.out.println("3 --> " + item3);
    }

    @Test
    public void testQuery() {
        // 排序
         Sort sort = Sort.by(Sort.Direction.DESC, "price");
        // 分页
        PageRequest pageRequest = PageRequest.of(0, 2, sort);
        Iterable<Item> itemList = itemRepository.findAll(pageRequest);
        itemList.forEach(System.out::println);
    }


    @Test
    public void testFindByName() {
        List<Item> data = itemRepository.findByNameLike("苹果");
        System.out.println("-->"+ JSONObject.toJSONString(data));
    }

    @Test
    public void testFindByKeywords() {
        List<Item> data = itemRepository.selectByKeywords("华为");
        System.out.println("-->"+ JSONObject.toJSONString(data));
    }

    @Test
    public void testHighlight() {
        List<Item> itemList = new ArrayList<>();
        List<SearchHit<Item>> dataList = itemRepository.findByName("华");
        System.out.println("-->"+ JSONObject.toJSONString(dataList));
        for(SearchHit<Item> data : dataList) {
            Item item = data.getContent();
            Map<String, List<String>> highlightFields = data.getHighlightFields();
            List<String> stringList = highlightFields.get("name");
            if(item != null && stringList.size() > 0) {
                item.setName(stringList.get(0));
            }
            itemList.add(item);
        }
        System.out.println("-->"+ JSONObject.toJSONString(itemList));
    }

    //使用原生的查询，查询内容不需要完全匹配
    //类似于QueryString
    @Test
    public void NativeSearchQuery() throws Exception{
        Sort sort = Sort.by(Sort.Direction.ASC, "price");

        //创建一个查询对象
        NativeSearchQuery query = new NativeSearchQueryBuilder()
                .withQuery(QueryBuilders.queryStringQuery("苹果").defaultField("name"))
                .withPageable(PageRequest.of(0,5, sort))
                .withHighlightFields(new HighlightBuilder.Field("name").preTags("<font style='color:red'>").postTags("</font>"))
                .build();

        List<Item> itemlList = elasticsearchRestTemplate.queryForList(query, Item.class, IndexCoordinates.of("demo_item"));
        itemlList.stream().forEach(article -> System.out.println(itemlList));

        System.out.println("-----------------------");

        SearchHits<Item> itemlHitList = elasticsearchRestTemplate.search(query, Item.class);
        itemlHitList.stream().forEach(article -> System.out.println(JSONObject.toJSONString(itemlHitList)));
    }

}