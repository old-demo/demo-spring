package com.heqing.demo.spring.mybatis.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@NoArgsConstructor
@Data
public class Teacher implements Serializable {

	private long id;
	//名字
	private String name;
	//管理班级/班主任(一对一)
	private SchoolClass superviseSchoolClass;
	//管理年级/年级主任（一对多）
	private List<SchoolClass> schoolClassDirector;
	//授课班级（多对多）
	private List<SchoolClass> teachSchoolClasses;

}
