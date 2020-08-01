package com.heqing.demo.spring.hibernate.dao.impl;

import com.heqing.demo.spring.hibernate.dao.TeacherXmlDao;
import com.heqing.demo.spring.hibernate.dao.base.BaseDaoImpl;
import com.heqing.demo.spring.hibernate.model.TeacherXml;
import org.springframework.stereotype.Repository;

@Repository
public class TeacherXmlDaoImpl extends BaseDaoImpl<TeacherXml, Long> implements TeacherXmlDao {
}
