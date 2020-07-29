package com.heqing.demo.spring.mybatis.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.heqing.demo.spring.mybatis.dao.PeopleDao;
import com.heqing.demo.spring.mybatis.model.People;
import com.heqing.demo.spring.mybatis.service.PeopleService;
import com.heqing.demo.spring.mybatis.util.PageInfoUtil;
import com.heqing.demo.spring.mybatis.util.ValidateUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * 业务逻辑实现类
 */
@Service("peopleService")
public class PeopleServiceImpl implements PeopleService {

    private static final Logger LOGGER = LoggerFactory.getLogger(PeopleServiceImpl.class);

	@Autowired
	private PeopleDao peopleDao;

    @Override
    public int savePeople(People people) {
        boolean param = ValidateUtil.validateParameter(people, "name", "age", "gender", "createTime");
        if(!param) {
            return 0;
        }
        return peopleDao.savePeople(people);
    }

    @Override
    public int savePeople(List<People> peopleList) {
        List<People> temppeopleList = new ArrayList<>();
        for(People people : peopleList) {
            boolean param = ValidateUtil.validateParameter(people, "name", "age", "gender", "createTime");
            if(param) {
                temppeopleList.add(people);
            }
        }
        return peopleDao.saveBatchPeople(temppeopleList);
    }

    @Override
    public int updatePeopleByKey(People people) {
        boolean param = ValidateUtil.validateParameter(people, "id");
        if(!param) {
            return 0;
        }
        return peopleDao.updatePeopleByKey(people);
    }

    @Override
    public int updatePeopleByKey(List<People> peopleList) {
        int successNum = 0;
        for(People people : peopleList) {
            successNum += updatePeopleByKey(people);
        }
        return successNum;
    }

    @Override
    public People getPeopleByKey(Long id) {
        return peopleDao.getPeopleByKey(id);
    }

    @Override
    public List<People> listPeopleByKey(List<Long> idList) {
        return peopleDao.listPeopleByKey(idList);
    }

    @Override
    public List<People> listPeople() {
        return peopleDao.listPeople();
    }

    @Override
    public List<People> listPeopleByParam(People people) {
        return peopleDao.listPeopleByParam(people);
    }

    @Override
    public PageInfoUtil<People> listPeopleByPage(int pageNum, int pageSize) {
        pageNum = pageNum < 1 ? 1 : pageNum;
        pageSize = pageSize < 1 ? 1 : pageSize > 100 ? 100 : pageSize;
        PageHelper.startPage(pageNum, pageSize);
        PageInfo<People> peoplePage = new PageInfo<People>(peopleDao.listPeople());
        return new PageInfoUtil(peoplePage.getList(), pageNum, pageSize, peoplePage.getTotal());
    }

    @Override
    public PageInfoUtil<People> listPeopleByParamAndPage(People people, int pageNum, int pageSize) {
        pageNum = pageNum < 1 ? 1 : pageNum;
        pageSize = pageSize < 1 ? 1 : pageSize > 100 ? 100 : pageSize;
        PageHelper.startPage(pageNum, pageSize);
        PageInfo<People> peoplePage = new PageInfo<People>(peopleDao.listPeopleByParam(people));
        return new PageInfoUtil(peoplePage.getList(), pageNum, pageSize, peoplePage.getTotal());
    }

    @Override
    public int deletePeopleByKey(Long id) {
        return peopleDao.deletePeopleByKey(id);
    }

    @Override
    public int deletePeopleByKey(List<Long> idList) {
        return  peopleDao.deleteBatchPeopleByKey(idList);
    }

    @Override
    public int deletePeopleByParam(People people) {
        return peopleDao.deletePeopleByParam(people);
    }
}
