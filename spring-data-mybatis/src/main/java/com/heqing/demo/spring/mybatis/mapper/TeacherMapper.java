package com.heqing.demo.spring.mybatis.mapper;

import com.heqing.demo.spring.mybatis.model.SchoolClass;
import com.heqing.demo.spring.mybatis.model.Teacher;
import org.apache.ibatis.annotations.*;

@Mapper
public interface TeacherMapper {

    @Results(
        id="teacherResult",
        value={
            @Result(id=true,column="id",property="id"),
            @Result(column="name",property="name"),
            @Result(column="id",property="superviseSchoolClass",javaType = SchoolClass.class,
                    one=@One(select="com.heqing.demo.spring.mybatis.mapper.SchoolClassMapper.getSchoolClassByHeadTeacherId")),
            @Result(column="{id=id,name=name}",property="schoolClassDirector",
                    many=@Many(select="com.heqing.demo.spring.mybatis.mapper.SchoolClassMapper.listSchoolClassByDirector")),
            @Result(column="id",property="teachSchoolClasses",
                    many=@Many(select="com.heqing.demo.spring.mybatis.mapper.SchoolClassMapper.listSchoolClassByTeacherId"))
        }
    )
    @Select("SELECT id, name FROM teacher WHERE id = #{teacherId}")
    Teacher getTeacherById(@Param("teacherId") long teacherId);
}
