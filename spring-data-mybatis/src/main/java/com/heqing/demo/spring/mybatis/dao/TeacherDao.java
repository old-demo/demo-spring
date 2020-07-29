package com.heqing.demo.spring.mybatis.dao;

import com.heqing.demo.spring.mybatis.model.Teacher;

public interface TeacherDao {

    void save(Teacher teacher);

    /**
     * 根据班主任ID查找管理的班级
     * @param teacherId 实体类ID
     * @return List<T>  实体列表
     */
    Teacher getTeacherById(long teacherId);
}
