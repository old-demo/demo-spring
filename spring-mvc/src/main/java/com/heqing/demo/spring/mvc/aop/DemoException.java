package com.heqing.demo.spring.mvc.aop;

public class DemoException extends RuntimeException {

    private Integer code;
    private String msg;
    private Object data;

    public DemoException(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public DemoException(Integer code, String msg, Object obj) {
        this.code = code;
        this.msg = msg;
        this.data = obj;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
