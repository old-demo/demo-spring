package com.heqing.demo.spring.convert;

public enum PersonEnum {

    ADULT(0,  "大人"),
    CHILD(1, "小孩");

    private final Integer value;
    private final String desc;

    PersonEnum(Integer value, String desc) {
        this.value = value;
        this.desc = desc;
    }

    public Integer getValue() {
        return value;
    }

    public String getDesc() {
        return desc;
    }

}
