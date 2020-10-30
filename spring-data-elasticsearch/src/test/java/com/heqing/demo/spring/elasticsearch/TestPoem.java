package com.heqing.demo.spring.elasticsearch;

import com.alibaba.fastjson.JSON;
import com.heqing.demo.spring.elasticsearch.config.SpringEsConfig;
import com.heqing.demo.spring.elasticsearch.model.Poem;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.support.master.AcknowledgedResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.client.indices.CreateIndexResponse;
import org.elasticsearch.client.indices.GetIndexRequest;
import org.elasticsearch.common.text.Text;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.MatchQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightField;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(
        classes = SpringEsConfig.class
)
public class TestPoem {

    @Autowired
    RestHighLevelClient highLevelClient;

    private static final String INDEX = "demo_poem";

    /**
     * 判断索引是否存在
     * @throws IOException
     */
    @Test
    public void getIndex() throws IOException {
        GetIndexRequest request = new GetIndexRequest(INDEX);
        boolean exists = highLevelClient.indices().exists(request, RequestOptions.DEFAULT);
        System.out.println(exists);
    }

    /**
     * 创建index
     * @throws IOException
     */
    @Test
    public void testCreateIndex() throws IOException {
        CreateIndexRequest request = new CreateIndexRequest(INDEX);
        CreateIndexResponse indexResponse = highLevelClient.indices().create(request, RequestOptions.DEFAULT);
        System.out.println(indexResponse);
    }

    /**
     * 删除索引
     * @throws IOException
     */
    @Test
    public void delIndex() throws IOException {
        DeleteIndexRequest request = new DeleteIndexRequest("demo_item");
        AcknowledgedResponse response = highLevelClient.indices().delete(request, RequestOptions.DEFAULT);
        System.out.println(response.isAcknowledged());
    }

    /**
     * 创建文档
     * @throws IOException
     */
    @Test
    public void addDoc() throws IOException {
        IndexRequest request = new IndexRequest(INDEX);
        request.id("1");
        request.timeout(TimeValue.timeValueSeconds(1));
        request.source(JSON.toJSONString(new Poem("行宫", "元稹", "寥落古行宫，宫花寂寞红。白头宫女在，闲坐说玄宗。")), XContentType.JSON);
        IndexResponse indexResponse = highLevelClient.index(request, RequestOptions.DEFAULT);
        System.out.println(indexResponse);
        System.out.println(indexResponse.status());
    }

    /**
     * 批量创建文档
     * @throws IOException
     */
    @Test
    public void addBatchDoc() throws IOException {
        BulkRequest request = new BulkRequest(INDEX);
        request.timeout(TimeValue.timeValueSeconds(10));
        List<Poem> list = new ArrayList<>();
        list.add(new Poem("新嫁娘词", "王建", "三日入厨下，洗手作羹汤。未谙姑食性，先遣小姑尝。"));
        list.add(new Poem("相思", "王维", "红豆生南国，春来发几枝。愿君多采撷，此物最相思。"));
        list.add(new Poem("杂诗三首·其二", "王维", "君自故乡来，应知故乡事。来日绮窗前，寒梅著花未？"));
        list.add(new Poem("鹿柴", "王维", "空山不见人，但闻人语响。返景入深林，复照青苔上。"));
        list.add(new Poem("芙蓉楼送辛渐", "王昌龄", "寒雨连江夜入吴，平明送客楚山孤。洛阳亲友如相问，一片冰心在玉壶。"));
        list.add(new Poem("江雪", "柳宗元", "千山鸟飞绝，万径人踪灭。孤舟蓑笠翁，独钓寒江雪。"));
        for (int i = 0; i < list.size(); i++) {
            request.add(new IndexRequest(INDEX)
                    .id((i+2)+"")
                    .source(JSON.toJSONString(list.get(i)), XContentType.JSON)
            );
        }
        BulkResponse bulk = highLevelClient.bulk(request, RequestOptions.DEFAULT);
        System.out.println(bulk.status());
        System.out.println(bulk.hasFailures());
    }

    /**
     * 判断文档是否存在
     * @throws IOException
     */
    @Test
    public void chkDocExist() throws IOException {
        GetRequest request = new GetRequest(INDEX);
        request.id("1");
        boolean exists = highLevelClient.exists(request, RequestOptions.DEFAULT);
        System.out.println(exists);
    }

    /**
     * 获取文档
     * @throws IOException
     */
    @Test
    public void getDoc() throws IOException {
        GetRequest request = new GetRequest(INDEX);
        request.id("1");
        GetResponse documentFields = highLevelClient.get(request, RequestOptions.DEFAULT);
        System.out.println(JSON.toJSONString(documentFields.getSource()));
    }

    /**
     * 更新文档
     * @throws IOException
     */
    @Test
    public void updateDoc() throws IOException {
        UpdateRequest request = new UpdateRequest(INDEX, "1");
        request.timeout(TimeValue.timeValueSeconds(1));
        Poem poem = new Poem("登鹳雀楼", "王之涣", "白日依山尽，黄河入海流。欲穷千里目，更上一层楼。");
        request.doc(JSON.toJSONString(poem), XContentType.JSON);
        UpdateResponse updateResponse = highLevelClient.update(request, RequestOptions.DEFAULT);
        System.out.println(JSON.toJSONString(updateResponse.status()));
        System.out.println(updateResponse.getGetResult());
    }

    /**
     * 删除文档
     * @throws IOException
     */
    @Test
    public void delDoc() throws IOException {
        DeleteRequest request = new DeleteRequest(INDEX, "1");
        DeleteResponse deleteResponse = highLevelClient.delete(request, RequestOptions.DEFAULT);
        System.out.println(deleteResponse.status());
    }

    /**
     * 搜索
     * @throws IOException
     */
    @Test
    public void search() throws IOException {
        SearchRequest request = new SearchRequest(INDEX);
        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
        MatchQueryBuilder matchQueryBuilder = QueryBuilders.matchQuery("content", "故乡");
        SearchSourceBuilder query = sourceBuilder.query(matchQueryBuilder);
        request.source(query);
        SearchResponse search = highLevelClient.search(request, RequestOptions.DEFAULT);
        System.out.println(search.status());
        System.out.println(JSON.toJSONString(search));
    }

    /**
     * 搜索-关键字高亮显示
     * @throws IOException
     */
    @Test
    public void test1()  throws IOException {
        SearchRequest searchRequest = new SearchRequest(INDEX);
        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
        MatchQueryBuilder termQueryBuilder = QueryBuilders.matchQuery("content", "故乡");
        SearchSourceBuilder query = sourceBuilder.query(termQueryBuilder);
        searchRequest.source(query);
        // 分页
        sourceBuilder.from(0);
        sourceBuilder.size(10);

        HighlightBuilder highlightBuilder = new HighlightBuilder()
                .requireFieldMatch(false)
                .field("content")
                .preTags("<span style='color: red'>")
                .postTags("</span>");
        sourceBuilder.highlighter(highlightBuilder);
        sourceBuilder.timeout(new TimeValue(60, TimeUnit.SECONDS));

        SearchResponse searchResponse = highLevelClient.search(searchRequest, RequestOptions.DEFAULT);

        List<Map<String, Object>> list = new ArrayList<>();
        for (SearchHit hit : searchResponse.getHits().getHits()) {
            Map<String, Object> sourceAsMap = hit.getSourceAsMap();
            Map<String, HighlightField> highlightFields = hit.getHighlightFields();
            HighlightField content = highlightFields.get("content");
            if (content != null) {
                Text[] fragments = content.getFragments();
                String newCon = "";
                for (Text text : fragments) {
                    newCon += text;
                }
                sourceAsMap.put("content", newCon);
            }
            list.add(sourceAsMap);
        }

        System.out.println(JSON.toJSONString(list));
    }
}
