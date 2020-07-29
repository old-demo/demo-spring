package com.heqing.demo.spring.mybatis.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@NoArgsConstructor
@Data
public class SchoolClass implements Serializable {

	private long id;
	private String name;
	private long headTeacherId;
	private long classDirectorId;
	private String classDirectorName;

}
