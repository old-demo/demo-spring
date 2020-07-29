package com.heqing.demo.spring.mybatis.dao;

import com.heqing.demo.spring.mybatis.model.SchoolClass;
import com.heqing.demo.spring.mybatis.model.Teacher;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SchoolClassDao {

    void save(SchoolClass schoolClass);

    SchoolClass getSchoolClassById(long id);

    SchoolClass getSchoolClassByHeadTeacherId(long teacherId);

    List<SchoolClass> listSchoolClassByDirector(@Param("teacher")Teacher teacher);

    List<SchoolClass> listSchoolClassByTeacherId(long teacherId);
}
