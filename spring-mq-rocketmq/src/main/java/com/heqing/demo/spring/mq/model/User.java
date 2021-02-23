package com.heqing.demo.spring.mq.model;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class User implements Serializable {

    private Long id;
    private String name;
    private Integer age;
    private String address;
    private Date createTime;
}
