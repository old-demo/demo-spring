package com.heqing.demo.spring.hibernate.model;

import lombok.*;

import java.io.Serializable;
import java.util.List;
import java.util.Set;

@NoArgsConstructor
@Setter
@Getter
@ToString
public class TeacherXml implements Serializable {

	private long id;
	private String name;
	private SchoolClassXml superviseSchoolClass;
	private Set<SchoolClassXml> schoolClassDirectorList;
	private Set<SchoolClassXml> teachSchoolClassesList;

}
