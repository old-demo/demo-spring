package com.heqing.demo.spring.mybatis;

import com.alibaba.fastjson.JSONObject;
import com.heqing.demo.spring.mybatis.dao.SchoolClassDao;
import com.heqing.demo.spring.mybatis.dao.TeacherDao;
import com.heqing.demo.spring.mybatis.mapper.SchoolClassMapper;
import com.heqing.demo.spring.mybatis.mapper.TeacherMapper;
import com.heqing.demo.spring.mybatis.model.SchoolClass;
import com.heqing.demo.spring.mybatis.model.Teacher;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(
        locations = {"classpath*:spring_core.xml","classpath:spring_mybatis.xml"}
)
public class TeacherTest {

    @Autowired
    TeacherDao teacherDao;

    @Autowired
    TeacherMapper teacherMapper;

    @Autowired
    SchoolClassDao schoolClassDao;

    @Autowired
    SchoolClassMapper schoolClassMapper;

    @Test
    public void testSchoolClass() {
        SchoolClass schoolClass = schoolClassDao.getSchoolClassById(1L);
        System.out.println("-->"+ JSONObject.toJSONString(schoolClass));

        schoolClass = schoolClassDao.getSchoolClassByHeadTeacherId(2L);
        System.out.println("-->"+ JSONObject.toJSONString(schoolClass));

        Teacher teacher = new Teacher();
        teacher.setId(1L);
        teacher.setName("贺小白");
        List<SchoolClass> schoolClassList = schoolClassDao.listSchoolClassByDirector(teacher);
        System.out.println("-->"+ JSONObject.toJSONString(schoolClassList));

        schoolClassList = schoolClassDao.listSchoolClassByTeacherId(1L);
        System.out.println("-->"+ JSONObject.toJSONString(schoolClassList));
    }

    @Test
    public void testTeacher() {
        Teacher teacher = teacherDao.getTeacherById(1L);
        System.out.println("-->"+ JSONObject.toJSONString(teacher));

        teacher = teacherMapper.getTeacherById(1L);
        System.out.println("-->"+ JSONObject.toJSONString(teacher));
    }

}
