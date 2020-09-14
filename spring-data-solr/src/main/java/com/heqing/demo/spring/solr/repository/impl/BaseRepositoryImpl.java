package com.heqing.demo.spring.solr.repository.impl;

import com.heqing.demo.spring.solr.repository.BaseRepository;
import com.heqing.demo.spring.solr.util.PageInfoUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.solr.core.SolrTemplate;
import org.springframework.data.solr.core.mapping.Indexed;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.List;

public abstract class BaseRepositoryImpl<T, PK extends Serializable> implements BaseRepository<T, PK> {

    @Autowired
    SolrTemplate solrTemplate;

    protected String collectionName;
    protected final Class<T> persistentClass;

    public BaseRepositoryImpl(){
        ParameterizedType pt = (ParameterizedType) this.getClass().getGenericSuperclass();
        this.persistentClass = (Class) pt.getActualTypeArguments()[0];

        // 获取集合名
        Indexed document = persistentClass.getAnnotation(Indexed.class);
        if(document != null) {
            collectionName = document.name();
        }
        if(StringUtils.isEmpty(collectionName)) {
            collectionName = persistentClass.getSimpleName().toLowerCase();
        }
    }

    @Override
    public int save(Object entity) {
        return 0;
    }

    @Override
    public int saveBatch(List entityList) {
        return 0;
    }

    @Override
    public int updateByKey(Object entity) {
        return 0;
    }

    @Override
    public Object getOneByKey(Serializable key) {
        return null;
    }

    @Override
    public List listByKey(List keyList) {
        return null;
    }

    @Override
    public int delete(Serializable serializable) {
        return 0;
    }

    @Override
    public int deleteBatch(List list) {
        return 0;
    }

    @Override
    public List list() {
        return null;
    }

    @Override
    public List list(Object o) {
        return null;
    }

    @Override
    public long count() {
        return 0;
    }

    @Override
    public long count(Object o) {
        return 0;
    }

    @Override
    public PageInfoUtil listByPage(Object o, int pageNum, int pageSize) {
        return null;
    }
}
