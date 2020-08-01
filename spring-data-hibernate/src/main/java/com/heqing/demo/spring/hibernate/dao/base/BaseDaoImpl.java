package com.heqing.demo.spring.hibernate.dao.base;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.List;

/**
 * 数据访问基础类
 */
@Transactional
@SuppressWarnings("unchecked")
public abstract class BaseDaoImpl<T, PK extends Serializable> implements BaseDao<T, PK> {

    protected final Class<T> persistentClass;

    @Autowired
    private SessionFactory sessionFactory;

    public BaseDaoImpl(){
        ParameterizedType pt = (ParameterizedType) this.getClass().getGenericSuperclass();
        this.persistentClass = (Class) pt.getActualTypeArguments()[0];
    }

    protected Session getSession(){
        return sessionFactory.getCurrentSession();
    }

    @Override
    public int save(T entity) {
        getSession().save(entity);
        return 1;
    }

    @Override
    public int saveBatch(List<T> entityList) {
        int successNum = 0;
        for(T entity : entityList) {
            getSession().save(entity);
            successNum++;
        }
        return successNum;
    }

    @Override
    public int updateByKey(T entity) {
        getSession().update(entity);
        return 1;
    }

    @Override
    public T getOneByKey(PK key) {
        return (T) getSession().get(persistentClass, key);
    }

    @Override
    public List<T> listByKey(List<PK> keyList) {
        List<T> entityList = new ArrayList<>();
        for(PK key : keyList) {
            entityList.add((T) getSession().get(persistentClass, key));
        }
        return entityList;
    }

    @Override
    public int delete(PK pk) {
        Object obj = getSession().get(persistentClass, pk);
        getSession().delete(obj);
        return 1;
    }

    @Override
    public int deleteBatch(List<PK> pkList) {
        int successNum = 0;
        for(PK pk : pkList) {
            Object obj = getSession().get(persistentClass, pk);
            getSession().delete(obj);
            successNum++;
        }
        return successNum;
    }

}
