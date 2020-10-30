package com.heqing.demo.spring.hibernate.model;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

@Data
@Entity
@Table(name="school_class")
public class SchoolClass implements Serializable {

	@Id
	@GeneratedValue(strategy= GenerationType.IDENTITY)
	@Column
	private long id;

	@Column
	private String name;

	@OneToOne(cascade = CascadeType.ALL,fetch=FetchType.EAGER)
	@JoinColumn(name = "head_teacher_id")
	private Teacher headTeacher;

	@ManyToOne( targetEntity = Teacher.class,cascade = CascadeType.ALL,fetch=FetchType.EAGER)
	@JoinColumn(name = "class_director_id",referencedColumnName="id")
	private Teacher classDirector;

	@Column(name="class_director_name")
	private String classDirectorName;

	@ManyToMany(mappedBy="teachSchoolClassesList",fetch=FetchType.EAGER)
	private Set<Teacher> teacherList;
}
