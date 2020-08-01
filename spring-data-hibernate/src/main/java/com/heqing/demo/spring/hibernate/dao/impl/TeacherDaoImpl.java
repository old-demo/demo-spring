package com.heqing.demo.spring.hibernate.dao.impl;

import com.heqing.demo.spring.hibernate.dao.TeacherDao;
import com.heqing.demo.spring.hibernate.dao.base.AnnotationBaseDaoImpl;
import com.heqing.demo.spring.hibernate.model.Teacher;
import org.springframework.stereotype.Repository;

@Repository
public class TeacherDaoImpl extends AnnotationBaseDaoImpl<Teacher, Long> implements TeacherDao {

}
