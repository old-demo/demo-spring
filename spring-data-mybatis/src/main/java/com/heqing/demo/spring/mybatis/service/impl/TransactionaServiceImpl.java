package com.heqing.demo.spring.mybatis.service.impl;

import com.heqing.demo.spring.mybatis.dao.SchoolClassDao;
import com.heqing.demo.spring.mybatis.dao.TeacherDao;
import com.heqing.demo.spring.mybatis.model.SchoolClass;
import com.heqing.demo.spring.mybatis.model.Teacher;
import com.heqing.demo.spring.mybatis.service.TransactionaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TransactionaServiceImpl implements TransactionaService {

    @Autowired
    TeacherDao teacherDao;

    @Autowired
    SchoolClassDao schoolClassDao;

    /**
     * @Transactional 在以下情况会失效
     *  1. @Transactional 注解只能应用到 public 可见度的方法上。 如果应用在protected、private或者 package可见度的方法上，也不会报错，不过事务设置不会起作用。
     *  2. 对unchecked异常进行事务回滚；如果是checked异常则不回滚。
     *      java里面将派生于Error或者RuntimeException（比如空指针，1/0）的异常称为unchecked异常，其他继承自java.lang.Exception得异常统称为Checked Exception，如IOException、TimeoutException等
     */
    @Override
    @Transactional
    public void save(boolean isbug) throws RuntimeException {
        Teacher teacher = new Teacher();
        teacher.setName("测试1");
        teacherDao.save(teacher);

        if(isbug) {
            throw new RuntimeException("-------------- 出现bug了，数据库插入不成功！ --------------");
        }

        SchoolClass schoolClass = new SchoolClass();
        schoolClass.setName("测试1");
        schoolClass.setHeadTeacherId(teacher.getId());
        schoolClass.setClassDirectorId(teacher.getId());
        schoolClassDao.save(schoolClass);
    }
}
