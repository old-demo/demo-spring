package com.heqing.demo.spring.hibernate;

import com.alibaba.fastjson.JSONObject;
import com.heqing.demo.spring.hibernate.config.SpringHibernateConfig;
import com.heqing.demo.spring.hibernate.dao.SchoolClassDao;
import com.heqing.demo.spring.hibernate.dao.TeacherDao;
import com.heqing.demo.spring.hibernate.model.SchoolClass;
import com.heqing.demo.spring.hibernate.model.Teacher;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(
        classes = SpringHibernateConfig.class
)
public class TeacherTest {

    @Autowired
    TeacherDao teacherDao;

    @Autowired
    SchoolClassDao schoolClassDao;

    @Test
    public void testList() {
        Teacher teacher = new Teacher();
        teacher.setId(2);
        System.out.println("-->"+ JSONObject.toJSONString(teacherDao.list(teacher)));

        SchoolClass schoolClass = new SchoolClass();
        schoolClass.setId(1);
        System.out.println("-->" + JSONObject.toJSONString(schoolClassDao.list(schoolClass)));
    }


    @Test
    public void testGetOneByKey() {
        // 对象建议使用Set而不是list, 否则可能出现相同数据
        Teacher teacher = teacherDao.getOneByKey(1L);
        System.out.println("-->" + JSONObject.toJSONString(teacher));
        System.out.println("-----------管理班级----------");
        System.out.println("-->" + JSONObject.toJSONString(teacher.getSuperviseSchoolClass()));
        System.out.println("-----------授课年级中含有的班级----------");
        for(SchoolClass schoolClass : teacher.getSchoolClassDirectorList()) {
            System.out.println("-->" + JSONObject.toJSONString(schoolClass));
        }
        System.out.println("-----------授课班级----------");
        for(SchoolClass schoolClass : teacher.getTeachSchoolClassesList()) {
            System.out.println("-->" + JSONObject.toJSONString(schoolClass));
        }

        SchoolClass schoolClass = schoolClassDao.getOneByKey(1L);
        System.out.println("-->" + JSONObject.toJSONString(schoolClass));
        System.out.println("-----------班主任----------");
        System.out.println("-->" + JSONObject.toJSONString(schoolClass.getHeadTeacher()));
        System.out.println("-----------年级主任----------");
        System.out.println("-->" + JSONObject.toJSONString(schoolClass.getClassDirector()));
        System.out.println("-----------授课教师----------");
        for(Teacher t : schoolClass.getTeacherList()) {
            System.out.println("-->" + JSONObject.toJSONString(t));
        }
    }

}
