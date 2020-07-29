package com.heqing.demo.spring.mybatis.mapper;

import com.heqing.demo.spring.mybatis.model.People;
import org.apache.ibatis.annotations.*;

import java.util.List;

@CacheNamespace
@Mapper
public interface PeopleMapper {

    @Insert("INSERT INTO people(name,age,gender,create_time) VALUES(#{name},#{age},#{gender},#{createTime})")
    @Options(useGeneratedKeys = true, keyColumn = "id", keyProperty = "id")
    int savePeople(People people);

    @InsertProvider(type=SqlProvider.class, method="saveBatchPeople")
    int saveBatchPeople(List<People> peopleList);

    @Update("update people set name = #{name}, age = #{age}, gender= #{gender}, create_time = #{createTime} where id = #{id}")
    int updatePeopleByKey(People people);

    @Results(
        id="peopleResultMap",
        value={
            @Result(id=true,column="id",property="id"),
            @Result(column="name",property="name"),
            @Result(column="age",property="age"),
            @Result(column="gender",property="gender"),
            @Result(column="create_time",property="createTime")
        }
    )
    @Select("SELECT id, name, age, gender, create_time  FROM people WHERE id = #{id}")
    People getPeopleByKey(Long id);

    @Select("SELECT id, name, age, gender, create_time FROM people ORDER BY id DESC")
    @ResultMap("peopleResultMap")
    List<People> listPeople();

    @Select("<script>" +
            "SELECT " +
                "id, name, age, gender, create_time" +
            "FROM people" +
            "<where> " +
                "<if test=\"id != null and id != ''\"> AND id=#{id} </if>" +
                "<if test=\"name != null and name != ''\"> AND name=#{name} </if>" +
                "<if test=\"age != null and age != ''\"> AND age=#{age} </if>" +
                "<if test=\"gender != null and gender != ''\"> AND gender=#{gender} </if>" +
                "<if test=\"createTime != null\"> AND date(create_time)=date(#{createTime,jdbcType=TIMESTAMP}) </if>" +
            "</where>" +
            "ORDER BY id DESC" +
            "</script>")
    @ResultMap("peopleResultMap")
    List<People> listPeopleByParam(People people);

    @SelectProvider(type=SqlProvider.class, method="listPeopleByKey")
    @ResultMap("peopleResultMap")
    List<People> listPeopleByKey(List<Long> idList);

    @Delete("DELETE FROM people WHERE id = #{id}")
    int deletePeopleByKey(Long id);

    @DeleteProvider(type=SqlProvider.class, method="deleteBatchPeopleByKey")
    int deleteBatchPeopleByKey(List<Long> idList);

    @Delete("<script>" +
            "DELETE FROM people" +
            "<where>" +
                "<if test=\"id != null and id != ''\"> AND id=#{id} </if>" +
                "<if test=\"name != null and name != ''\"> AND name=#{name} </if>" +
                "<if test=\"age != null and age != ''\"> AND age=#{age} </if>" +
                "<if test=\"gender != null and gender != ''\"> AND gender=#{gender} </if>" +
                "<if test=\"createTime != null\"> AND date(create_time)=date(#{createTime,jdbcType=TIMESTAMP}) </if>" +
            "</where>" +
            "</script>")
    int deletePeopleByParam(People people);
}
