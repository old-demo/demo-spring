package com.heqing.demo.spring.mybatis.mapper;

import com.heqing.demo.spring.mybatis.model.People;
import com.heqing.demo.spring.mybatis.model.Teacher;
import org.springframework.util.StringUtils;

import java.text.MessageFormat;
import java.util.List;
import java.util.Map;

public class SqlProvider {

    public String saveBatchPeople(Map<String, Object> map) {
        List<People> peopleList =  (List<People>) map.get("list");
        int length = peopleList.size();

        StringBuilder sb = new StringBuilder();
        sb.append("INSERT INTO ");
        sb.append(" people(name,age,gender,create_time) ");
        sb.append("VALUES ");
        MessageFormat mf = new MessageFormat("(#'{'list[{0}].name},#'{'list[{0}].age},#'{'list[{0}].gender},#'{'list[{0}].createTime})");
        for(int i=0; i<length; i++){
            sb.append(mf.format(new Object[]{i}));
            if (i < length-1) {
                sb.append(",");
            }
        }
        return sb.toString();
    }

    public String listPeopleByKey(Map<String, Object> map) {
        List<Long> idList =  (List<Long>) map.get("list");
        int length = idList.size();

        StringBuilder sb = new StringBuilder();
        sb.append("SELECT id, name, age, gender, create_time ");
        sb.append("FROM people ");
        sb.append("WHERE id IN (");
        for(int i=0; i<length; i++){
            sb.append(idList.get(i));
            if (i < length-1) {
                sb.append(",");
            }
        }
        sb.append(")");
        return sb.toString();
    }

    public String deleteBatchPeopleByKey(Map<String, Object> map) {
        List<Long> idList =  (List<Long>) map.get("list");
        int length = idList.size();

        StringBuilder sb = new StringBuilder();
        sb.append("DELETE FROM people ");
        sb.append("WHERE id IN (");
        for(int i=0; i<length; i++){
            sb.append(idList.get(i));
            if (i < length-1) {
                sb.append(",");
            }
        }
        sb.append(")");
        return sb.toString();
    }

    public String listSchoolClassByDirector(Teacher teacher) {
        StringBuilder sb = new StringBuilder();
        if(teacher != null) {
            sb.append("SELECT ");
            sb.append(" id, name, head_teacher_id, class_director_id, class_director_name ");
            sb.append("FROM school_class ");
            sb.append("WHERE 1=1 ");
            if(teacher.getId() != 0) {
                sb.append(" AND class_director_id = "+ teacher.getId());
            }
            if(!StringUtils.isEmpty(teacher.getName())) {
                sb.append(" AND class_director_name = '"+ teacher.getName()).append("'");
            }
        }
        return sb.toString();
    }

}
