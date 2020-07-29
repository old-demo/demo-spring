package com.heqing.demo.spring.mybatis.service;

import com.heqing.demo.spring.mybatis.model.People;
import com.heqing.demo.spring.mybatis.util.PageInfoUtil;

import java.util.List;

/**
 * 业务逻辑层接口
 */
public interface PeopleService {

    /**
     * 增加实例
     *
     * @param people 实例
     * @return int 成功数量
     */
    int savePeople(People people);

    /**
     * 增加多条实例
     *
     * @param peopleList 多条实例
     * @return int 成功数量
     */
    int savePeople(List<People> peopleList);

    /**
     * 批量修改实例
     *
     * @param peopleList 多条实例信息
     * @return int 是否成功
     */
    int updatePeopleByKey(List<People> peopleList);

    /**
     * 根据主键获取实例信息
     *
     * @param id  实例
     * @return List<T> 实例信息
     */
    People getPeopleByKey(Long id);

    /**
     * 根据多个主键获取实例信息
     *
     * @param idList  实例集合
     * @return List<T> 实例信息
     */
    List<People> listPeopleByKey(List<Long> idList);

    /**
     * 获取所有实例信息
     *
     * @return List<People> 主键集合
     */
    List<People> listPeople();

    /**
     * 根据条件获取多条实例
     *
     * @param people 实例:做条件查询使用
     * @return List<People> 实例集合
     */
    List<People> listPeopleByParam(People people);

    /**
     * 根据分页获取多条实例
     *
     * @param pageNum 第几页
     * @param pageSize 每页数量
     * @return Page<People> 实例集合
     */
    PageInfoUtil<People> listPeopleByPage(int pageNum, int pageSize);

    /**
     * 根据分页及条件获取多条实例
     *
     * @param people 实例:做条件查询使用
     * @param pageNum 第几页
     * @param pageSize 每页数量
     * @return Page<People> 实例集合
     */
    PageInfoUtil<People> listPeopleByParamAndPage(People people, int pageNum, int pageSize);

    /**
     * 根据条件删除多条实例
     *
     * @param people 实例:做条件删除使用
     * @return int 成功数量
     */
    int deletePeopleByParam(People people);

    /**
     * 修改实例
     *
     * @param people 实例信息
     * @return int 成功数量
     */
    int updatePeopleByKey(People people);

    /**
     * 根据主键删除多条实例
     *
     * @param id 主键
     * @return int 成功数量
     */
    int deletePeopleByKey(Long id);

    /**
     * 根据多个主键删除多条实例
     *
     * @param idList 主键集合
     * @return int 成功数量
     */
    int deletePeopleByKey(List<Long> idList);

}
