package com.heqing.demo.spring.hibernate.model;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

@NoArgsConstructor
@Data
public class PeopleXml implements Serializable {

	private Long id;
	private String name;
	private Integer age;
	private String gender;
	// 注意
	@JSONField(name="create_time", format="yyyy-MM-dd HH:mm:ss")
	private Date createTime;

}
