package com.heqing.demo.spring.elasticsearch.repository;

import com.heqing.demo.spring.elasticsearch.model.Item;
import org.springframework.data.elasticsearch.annotations.Highlight;
import org.springframework.data.elasticsearch.annotations.HighlightField;
import org.springframework.data.elasticsearch.annotations.HighlightParameters;
import org.springframework.data.elasticsearch.annotations.Query;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository public interface ItemRepository extends ElasticsearchRepository<Item, String> {

    List<Item> findByNameLike(String name);

    @Query("{\"bool\" : {\"must\" : {\"match\" : {\"key-words\" : \"?0\"}}}}")
    List<Item> selectByKeywords(String keywords);



    @Highlight(
            fields = @HighlightField(name = "name"),
            parameters = @HighlightParameters(
                    preTags = "<strong>",
                    postTags = "</strong>"
            )
    )
    List<SearchHit<Item>> findByName(String name);
}