package com.heqing.demo.spring.convert;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Map;

@NoArgsConstructor
@Data
@ToString
public class PersonBO implements Serializable {

    private Long id;
    private String name;
    private String remarkJson;

    /**
     * 测试类型不一样 （Date -- gender）
     */
    private Date date;

    /**
     * 测试属性名不一样 （sex -- gender）
     */
    private Integer sex;

    /**
     * 测试list
     */
    private List<String> nameParts;

    /**
     * 测试map
     */
    private Map<String, Object> remarkMap;

    /**
     * 测试对象
     */
    private Address address;

    /**
     * 测试枚举
     */
    private PersonEnum type;
}
