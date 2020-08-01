package com.heqing.demo.spring.hibernate.dao.impl;

import com.heqing.demo.spring.hibernate.dao.PeopleXmlDao;
import com.heqing.demo.spring.hibernate.dao.base.BaseDaoImpl;
import com.heqing.demo.spring.hibernate.model.PeopleXml;
import org.springframework.stereotype.Repository;

@Repository
public class PeopleXmlDaoImpl extends BaseDaoImpl<PeopleXml, Long> implements PeopleXmlDao {
}
