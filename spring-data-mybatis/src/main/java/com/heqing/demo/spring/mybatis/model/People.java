package com.heqing.demo.spring.mybatis.model;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

@NoArgsConstructor
@Data
public class People implements Serializable {

    private static final long serialVersionUID = 1L;

	private Long id;
	private String name;
	private Integer age;
	private String gender;
	@JSONField(name="create_time", format="yyyy-MM-dd HH:mm:ss")
	private Date createTime;

}
