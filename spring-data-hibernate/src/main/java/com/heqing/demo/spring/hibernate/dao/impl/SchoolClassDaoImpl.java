package com.heqing.demo.spring.hibernate.dao.impl;

import com.heqing.demo.spring.hibernate.dao.SchoolClassDao;
import com.heqing.demo.spring.hibernate.dao.base.AnnotationBaseDaoImpl;
import com.heqing.demo.spring.hibernate.model.SchoolClass;
import org.springframework.stereotype.Repository;

@Repository
public class SchoolClassDaoImpl extends AnnotationBaseDaoImpl<SchoolClass, Long> implements SchoolClassDao {
}
