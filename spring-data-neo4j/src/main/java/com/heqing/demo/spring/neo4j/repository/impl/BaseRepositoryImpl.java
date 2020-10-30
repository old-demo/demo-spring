package com.heqing.demo.spring.neo4j.repository.impl;

import com.alibaba.fastjson.JSONObject;
import com.heqing.demo.spring.neo4j.repository.BaseRepository;
import com.heqing.demo.spring.neo4j.util.PageInfoUtil;
import org.neo4j.ogm.annotation.Index;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Property;
import org.neo4j.ogm.session.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

import java.beans.PropertyDescriptor;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public abstract class BaseRepositoryImpl<T, PK extends Serializable> implements BaseRepository<T, PK> {

    private static final DateTimeFormatter FORMAT = DateTimeFormatter.ISO_INSTANT;

    @Autowired
    Session neo4jSession;

    protected String nodeName, labelName, indexName;
    protected final Class<T> persistentClass;

    public BaseRepositoryImpl(){

        ParameterizedType pt = (ParameterizedType) this.getClass().getGenericSuperclass();
        this.persistentClass = (Class) pt.getActualTypeArguments()[0];

        // 获取集合名
        NodeEntity nodeEntity = persistentClass.getAnnotation(NodeEntity.class);
        if(nodeEntity != null) {
            nodeName = nodeEntity.value();
            labelName = nodeEntity.label();
        }
        if(StringUtils.isEmpty(nodeName)) {
            nodeName = persistentClass.getSimpleName().toLowerCase();
        }

        Field[] fields = persistentClass.getDeclaredFields();
        for (Field field : fields) {
            field.setAccessible(true);
            if (field.isAnnotationPresent(Index.class) && field.isAnnotationPresent(Property.class)) {
                Property property = field.getDeclaredAnnotation(Property.class);
                indexName = property.name();
                if (StringUtils.isEmpty(indexName)) {
                    indexName = field.getName();
                }
            }
        }
    }

    // type 0:主键  1:创建  2:修改  3:查询
    public String getCql(T entity, int type) {
        StringBuilder cql = new StringBuilder();
        try {
            if (entity != null) {
                //获得Object对象中的所有方法
                Field[] fields = entity.getClass().getDeclaredFields();
                for (Field field : fields) {
                    field.setAccessible(true);
                    PropertyDescriptor pd = new PropertyDescriptor(field.getName(), entity.getClass());
                    // 获取get方法
                    Method getMethod = pd.getReadMethod();
                    Object object = getMethod.invoke(entity);
                    if(object != null) {
                        // 获取属性名
                        String fieldName = "";
                        if (field.isAnnotationPresent(Property.class)) {
                            Property property = field.getDeclaredAnnotation(Property.class);
                            fieldName = property.name();
                            if (StringUtils.isEmpty(fieldName)) {
                                fieldName = field.getName();
                            }
                        }
                        // 获取属性值
                        Object value = null;
                        if (object instanceof Byte || object instanceof Short || object instanceof Integer || object instanceof Long
                                || object instanceof Character || object instanceof Float || object instanceof Double || object instanceof Boolean) {
                            value = object;
                        } else if (object instanceof Date) {
                            value = "'" + FORMAT.format(((Date) object).toInstant()) + "'";
                        } else if (object instanceof String && !StringUtils.isEmpty(object)) {
                            value = JSONObject.toJSONString(object);
                        }

                        // 拼装cql语句
                        if (type == 0) {
                            if(field.isAnnotationPresent(Index.class) && field.isAnnotationPresent(Property.class)) {
                                cql.append(", ").append(nodeName).append(".").append(fieldName).append("=").append(value);
                            }
                        } else if(type == 1) {
                            cql.append(", ").append(fieldName).append(":").append(value);
                        } else if(type == 2) {
                            if(field.isAnnotationPresent(Index.class)) {
                                continue;
                            }
                            cql.append(", ").append(nodeName).append(".").append(fieldName).append("=").append(value);
                        } else if(type == 3) {
                            cql.append(nodeName).append(".").append(fieldName).append("=").append(value).append(" AND ");
                        }
                    }
                }
            }
        } catch(Exception e){
            e.printStackTrace();
        }
        // 去掉无效字段
        String cqlStr = cql.toString();
        if(cqlStr.startsWith(",")) {
            cqlStr = cqlStr.substring(1, cqlStr.length());
        }
        if(cqlStr.endsWith("AND ")) {
            cqlStr = cqlStr.substring(0, cqlStr.length()-5);
        }
        return cqlStr;
    }

    @Override
    public T save(T t) {
        StringBuilder cql = new StringBuilder();
        cql.append("CREATE ( ")
                .append(nodeName).append(":").append(labelName)
                .append(" {").append(getCql(t, 1)).append("} ")
            .append(")").append(" RETURN ").append(nodeName);
        System.out.println("-->" + cql.toString());
        return  neo4jSession.queryForObject(persistentClass, cql.toString(), new HashMap<>(0));
    }

    @Override
    public List<T> saveBatch(List<T> tList) {
        List<T> tResultList = new ArrayList<>();
        for(T t : tList) {
            tResultList.add(save(t));
        }
        return tResultList;
    }

    @Override
    public T updateByKey(T t) {
        StringBuilder cql = new StringBuilder();
        cql.append("MATCH ( ").append(nodeName).append(":").append(labelName).append(" )")
                .append(" WHERE ").append(getCql(t, 0)).append(" SET").append(getCql(t, 2))
                .append(" RETURN ").append(nodeName);
        System.out.println("-->" + cql.toString());
        return neo4jSession.queryForObject(persistentClass, cql.toString(), new HashMap<>(0));
    }

    @Override
    public T getOneByKey(PK key) {
        StringBuilder cql = new StringBuilder();
        cql.append("MATCH ( ").append(nodeName).append(":").append(labelName).append(" )")
           .append(" WHERE ").append(nodeName).append(".").append(indexName).append("=").append(key)
           .append(" RETURN ").append(nodeName);
        System.out.println("-->" + cql.toString());
        return neo4jSession.queryForObject(persistentClass, cql.toString(), new HashMap<>(0));
    }

    @Override
    public List<T> listByKey(List<PK> keyList) {
        List<T> tList = new ArrayList<>();
        for(PK key : keyList) {
            tList.add(getOneByKey(key));
        }
        return tList;
    }

    @Override
    public List<T> list() {
        List<T> target = new ArrayList<>();
        StringBuilder cql = new StringBuilder();
        cql.append("MATCH ( ").append(nodeName).append(":").append(labelName).append(" )")
                .append(" RETURN ").append(nodeName);
        System.out.println("-->" + cql.toString());
        Iterable<T> tIterable = neo4jSession.query(persistentClass, cql.toString(), new HashMap<>(0));
        tIterable.forEach(target::add);
        return target;
    }

    @Override
    public long count() {
        return list().size();
    }

    @Override
    public List<T> list(T t) {
        StringBuilder cql = new StringBuilder();
        cql.append("MATCH ( ").append(nodeName).append(":").append(labelName).append(" )")
           .append(" WHERE ").append(getCql(t, 3))
           .append(" RETURN ").append(nodeName);
        System.out.println("-->" + cql.toString());
        Iterable<T> tIterable = neo4jSession.query(persistentClass, cql.toString(), new HashMap<>(0));
        List<T> target = new ArrayList<>();
        tIterable.forEach(target::add);
        return target;
    }

    @Override
    public long count(T t) {
        return list(t).size();
    }

    @Override
    public PageInfoUtil<T> listByPage(T t, int pageNum, int pageSize) {
        int index = 1, size = 100, max = 1000;
        if(pageNum > 0) {
            index = (pageNum - 1) * pageSize;
        }
        if(pageSize > 0 && pageSize < max) {
            size = pageSize;
        }
        StringBuilder cql = new StringBuilder();
        cql.append("MATCH ( ").append(nodeName).append(":").append(labelName).append(" )")
                .append(" WHERE ").append(getCql(t, 3))
                .append(" RETURN ").append(nodeName)
                .append(" SKIP ").append(index)
                .append(" LIMIT ").append(size);
        System.out.println("-->" + cql.toString());
        Iterable<T> tIterable = neo4jSession.query(persistentClass, cql.toString(), new HashMap<>(0));
        List<T> target = new ArrayList<>();
        tIterable.forEach(target::add);
        return new PageInfoUtil(target, pageNum, pageSize, count(t));
    }

    @Override
    public int delete(PK key) {
        StringBuilder cql = new StringBuilder();
        cql.append("MATCH ( ").append(nodeName).append(":").append(labelName).append(" )")
                .append(" WHERE ").append(nodeName).append(".").append(indexName).append("=").append(key)
                .append(" DELETE ").append(nodeName);
        System.out.println("-->" + cql.toString());
        neo4jSession.queryForObject(persistentClass, cql.toString(), new HashMap<>(0));
        return 1;
    }

    @Override
    public int deleteBatch(List<PK> pkList) {
        int successNum = 0;
        for(PK pk : pkList) {
            successNum += delete(pk);
        }
        return successNum;
    }

}
