package com.heqing.demo.spring.mybatis.dao;

import com.heqing.demo.spring.mybatis.model.People;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PeopleDao {

    /**
     * 增加实例
     *
     * @param people 实例
     * @return int 成功数量
     */
    int savePeople(People people);

    /**
     * 增加多条
     *
     * @param peopleList 多条列
     * @return int 成功数量
     */
    int saveBatchPeople(List<People> peopleList);

    /**
     * 根据主键修改
     *
     * @param people 列信息
     * @return int 成功数量
     */
    int updatePeopleByKey(People people);

    /**
     * 根据主键获取
     *
     * @param id
     * @return People 信息
     */
    People getPeopleByKey(@Param("id") Long id);

    /**
     * 获取所有
     *
     * @return List<People> 主键集合
     */
    List<People> listPeople();

    /**
     * 根据条件获取多条
     *
     * @param people 列:做条件查询使用
     * @return List<People> 列集合
     */
    List<People> listPeopleByParam(People people);

    /**
     * 根据多个主键获取
     *
     * @param idList 列集合
     * @return List<T> 列实体信息
     */
    List<People> listPeopleByKey(List<Long> idList);

    /**
     * 根据主键删除
     *
    * @param id
     * @return int 成功数量
     */
    int deletePeopleByKey(@Param("id") Long id);

    /**
     * 根据多个主键删除多条
     *
     * @param idList  列集合
     * @return int 成功数量
     */
    int deleteBatchPeopleByKey(List<Long> idList);

    /**
     * 根据条件删除多条实例
     *
     * @param people 实例:做条件删除使用
     * @return int 成功数量
     */
    int deletePeopleByParam(People people);

}
