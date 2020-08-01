package com.heqing.demo.spring.hibernate.dao.impl;

import com.heqing.demo.spring.hibernate.dao.SchoolClassXmlDao;
import com.heqing.demo.spring.hibernate.dao.base.BaseDaoImpl;
import com.heqing.demo.spring.hibernate.model.SchoolClassXml;
import org.springframework.stereotype.Repository;

@Repository
public class SchoolClassXmlDaoImpl extends BaseDaoImpl<SchoolClassXml, Long> implements SchoolClassXmlDao {
}
