package com.heqing.demo.spring.hibernate.model;

import lombok.*;

import java.io.Serializable;
import java.util.List;
import java.util.Set;

@NoArgsConstructor
@Setter
@Getter
@ToString
public class SchoolClassXml implements Serializable {

	private long id;
	private String name;
	private TeacherXml headTeacher;
	private TeacherXml classDirector;
	private String classDirectorName;
	private List<TeacherXml> teacherList;
}
