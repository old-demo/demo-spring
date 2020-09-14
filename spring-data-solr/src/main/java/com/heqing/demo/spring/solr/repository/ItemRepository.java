package com.heqing.demo.spring.solr.repository;

import com.heqing.demo.spring.solr.model.Item;
import org.springframework.data.domain.Pageable;
import org.springframework.data.solr.core.query.result.HighlightPage;
import org.springframework.data.solr.core.query.result.HighlightQueryResult;
import org.springframework.data.solr.repository.Highlight;
import org.springframework.data.solr.repository.SolrCrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ItemRepository extends SolrCrudRepository<Item,String> {

    List<Item> findByName(String name);

    //    @Highlight(prefix = "<strong>",postfix = "</strong>")
//    HighlightPage<Item> findByKeywordsContaining(String keywords, Pageable page) ;

    @Highlight(prefix = "<strong>",postfix = "</strong>")
    HighlightPage<Item> findByNameContaining(String keywords, Pageable page) ;
}
