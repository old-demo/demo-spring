package com.heqing.demo.spring.hibernate.dao.base;

import com.heqing.demo.spring.hibernate.util.PageInfoUtil;
import org.hibernate.query.NativeQuery;
import org.springframework.util.StringUtils;

import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.Table;
import java.beans.PropertyDescriptor;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Date;
import java.util.List;

public class AnnotationBaseDaoImpl<T, PK extends Serializable> extends BaseDaoImpl<T, PK> implements AnnotationBaseDao<T, PK> {

    protected String field, tableName;

    public AnnotationBaseDaoImpl(){
        super();
        // 获取 查询字段
        StringBuilder fieldStr = new StringBuilder();
        Field[] fields = persistentClass.getDeclaredFields();
        for (Field field : fields) {
            String fieldName = "";
            field.setAccessible(true);
            if(field.isAnnotationPresent(Column.class)) {
                Column column = field.getDeclaredAnnotation(Column.class);
                if(column != null && !StringUtils.isEmpty(column.name())) {
                    fieldName = column.name();
                } else {
                    fieldName = field.getName();
                }
            } else if(field.isAnnotationPresent(JoinColumn.class)) {
                JoinColumn column = field.getDeclaredAnnotation(JoinColumn.class);
                if(column != null && !StringUtils.isEmpty(column.name())) {
                    fieldName = column.name();
                } else {
                    fieldName = field.getName();
                }
            }
            fieldStr.append(" ").append(fieldName).append(",");
        }
        field = fieldStr.toString();
        while (field.endsWith(",") || field.endsWith(" ")) {
            field = field.substring(0, field.length()-1);
        }

        // 获取表名
        Table annotation = persistentClass.getAnnotation(Table.class);
        if(annotation != null) {
            tableName = annotation.name();
        }
        if(StringUtils.isEmpty(tableName)) {
            tableName = persistentClass.getSimpleName().toLowerCase();
        }
    }

    @Override
    public List<T> list() {
        return list(null);
    }

    @Override
    public List list(T t) {
        String hql = getWhereHql(t);
        NativeQuery query = getSession().createSQLQuery(hql).addEntity(persistentClass);
        return query.list();
    }

    @Override
    public PageInfoUtil listByPage(T t, int pageNum, int pageSize) {
        int total = list(t).size();
        String hql = getWhereHql(t);
        NativeQuery query = getSession().createSQLQuery(hql).addEntity(persistentClass);
        int index = 1, size = 100, max = 1000;
        if(pageNum > 0) {
            index = (pageNum - 1) * pageSize;
        }
        if(pageSize > 0 && pageSize < max) {
            size = pageSize;
        }
        query.setFirstResult(index);
        query.setMaxResults(size);
        return new PageInfoUtil(query.list(), pageNum, pageSize, total);
    }


    protected String getWhereHql(T t) {
        StringBuilder hql = new StringBuilder();
        try {
            hql.append(" SELECT "+ field +" FROM " + tableName);
            if(t != null) {
                hql.append(" WHERE 1=1");
                //获得属性集合
                Field[] fields = t.getClass().getDeclaredFields();
                //获得Object对象中的所有方法
                for (Field field : fields) {
                    field.setAccessible(true);
                    // 获取属性名
                    String fieldName = "";
                    if(field.isAnnotationPresent(Column.class)) {
                        Column column = field.getDeclaredAnnotation(Column.class);
                        fieldName = column.name();
                        if(StringUtils.isEmpty(fieldName)) {
                            fieldName = field.getName();
                        }
                    }

                    PropertyDescriptor pd = new PropertyDescriptor(field.getName(), t.getClass());
                    // 获取get方法
                    Method getMethod = pd.getReadMethod();
                    Object object = getMethod.invoke(t);
                    if(object != null) {
                        if (object instanceof Byte || object instanceof Short || object instanceof Integer || object instanceof Long
                                || object instanceof Character || object instanceof Float || object instanceof Double) {
                            hql.append(" AND ").append(fieldName).append("=").append(object);
                        } else if (object instanceof Boolean) {

                        } else if (object instanceof Date) {

                        } else if (object instanceof String && !StringUtils.isEmpty(object)) {
                            hql.append(" AND ").append(fieldName).append("='").append(object).append("'");
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return hql.toString();
    }
}
