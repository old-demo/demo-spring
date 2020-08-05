package com.heqing.demo.spring.mongodb.dao.impl;

import com.heqing.demo.spring.mongodb.dao.BaseDao;
import com.heqing.demo.spring.mongodb.util.PageInfoUtil;
import com.mongodb.client.result.UpdateResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.util.StringUtils;

import java.beans.PropertyDescriptor;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.List;

import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;

public abstract class BaseDaoImpl<T, PK extends Serializable> implements BaseDao<T, PK> {

    @Autowired
    protected MongoTemplate mongoTemplate;

    protected String collectionName;
    protected final Class<T> persistentClass;

    public BaseDaoImpl(){
        ParameterizedType pt = (ParameterizedType) this.getClass().getGenericSuperclass();
        this.persistentClass = (Class) pt.getActualTypeArguments()[0];

        // 获取集合名
        Document document = persistentClass.getAnnotation(Document.class);
        if(document != null) {
            collectionName = document.collection();
        }
        if(StringUtils.isEmpty(collectionName)) {
            collectionName = persistentClass.getSimpleName().toLowerCase();
        }
    }

    @Override
    public int save(T entity) {
        mongoTemplate.save(entity);
        return 1;
    }

    @Override
    public int saveBatch(List<T> entityList) {
        int successNum = 0;
        for(T entity : entityList) {
            mongoTemplate.save(entity);
            successNum++;
        }
        return successNum;
    }

    @Override
    public int updateByKey(T t) {
        Criteria where = null;
        Update update = new Update();

        try {
            Field[] fields = persistentClass.getDeclaredFields();
            for (Field field : fields) {
                field.setAccessible(true);

                PropertyDescriptor pd = new PropertyDescriptor(field.getName(), t.getClass());
                Method getMethod = pd.getReadMethod();
                Object object = getMethod.invoke(t);
                if(field.isAnnotationPresent(Id.class)) {
                    where = where(field.getName()).is(object);
                } else {
                    update.set(field.getName(), object);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        UpdateResult updateResult = mongoTemplate.updateMulti(query(where), update, persistentClass);
        int num = 0;
        if (updateResult != null) {
            num = (int) updateResult.getModifiedCount();
        }
        return num;
    }

    @Override
    public T getOneByKey(PK key) {
        return mongoTemplate.findById(key, persistentClass);
    }

    @Override
    public List<T> listByKey(List<PK> keyList) {
        List<T> entityList = new ArrayList<>();
        for(PK key : keyList) {
            T t = mongoTemplate.findById(key, persistentClass);
            if(t != null) {
                entityList.add(t);
            }
        }
        return entityList;
    }

    @Override
    public List<T> list() {
        return mongoTemplate.findAll(persistentClass);
    }

    @Override
    public List<T> list(T t) {
        return mongoTemplate.find(getWhere(t), persistentClass);
    }

    @Override
    public long count() {
        return mongoTemplate.count(new Query(), persistentClass);
    }

    @Override
    public long count(T t) {
        return mongoTemplate.count(getWhere(t), persistentClass);
    }

    @Override
    public PageInfoUtil<T> listByPage(T t, int pageNum, int pageSize) {
        int total = list(t).size();

        Query query = getWhere(t);
        int index = 1, size = 100, max = 1000;
        if(pageNum > 0) {
            index = (pageNum - 1) * pageSize;
        }
        if(pageSize > 0 && pageSize < max) {
            size = pageSize;
        }
        query.skip(index);
        query.limit(size);
        return new PageInfoUtil(mongoTemplate.find(query, persistentClass), pageNum, pageSize, total);
    }

    @Override
    public int delete(PK key) {
        T t =  mongoTemplate.findById(key, persistentClass);
        if(t != null) {
            mongoTemplate.remove(t);
        }
        return 1;
    }

    @Override
    public int deleteBatch(List<PK> pkList) {
        int successNum = 0;
        for(PK key : pkList) {
            delete(key);
            successNum++;
        }
        return successNum;
    }

    protected Query getWhere(T t) {
        Criteria where = null;
        try {
            Field[] fields = persistentClass.getDeclaredFields();
            for (Field field : fields) {
                field.setAccessible(true);

                PropertyDescriptor pd = new PropertyDescriptor(field.getName(), t.getClass());
                Method getMethod = pd.getReadMethod();
                Object object = getMethod.invoke(t);
                if(object != null) {
                    if (where == null) {
                        where = where(field.getName()).is(object);
                    } else {
                        where.and(field.getName()).is(object);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return query(where);
    }

}
