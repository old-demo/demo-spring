package com.heqing.demo.spring.hibernate.dao.impl;

import com.heqing.demo.spring.hibernate.dao.PeopleDao;
import com.heqing.demo.spring.hibernate.dao.base.AnnotationBaseDaoImpl;
import com.heqing.demo.spring.hibernate.model.People;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

@Repository
public class PeopleDaoImpl extends AnnotationBaseDaoImpl<People, Long> implements PeopleDao {

    @Override
    protected String getWhereHql(People people) {
        StringBuilder hql = new StringBuilder();
        hql.append(" SELECT "+ field +" FROM people");
        if(people != null) {
            hql.append(" WHERE 1=1");
            if(people.getId() != null) {
                hql.append("AND id=").append(people.getId());
            }
            if(!StringUtils.isEmpty(people.getName())) {
                hql.append(" AND name Like '%").append(people.getName()).append("%'");
            }
            if(people.getAge() != null) {
                hql.append(" AND age=").append(people.getAge());
            }
            if(!StringUtils.isEmpty(people.getGender())) {
                hql.append(" AND gender = '").append(people.getGender()).append("'");
            }
        }
        return hql.toString();
    }
}
