package com.heqing.demo.spring.struts2.controller;

import com.alibaba.fastjson.JSONObject;
import com.heqing.demo.spring.struts2.model.User;
import com.heqing.demo.spring.struts2.util.ResultUtil;
import com.opensymphony.xwork2.ActionSupport;
import org.apache.struts2.convention.annotation.*;
import org.apache.struts2.json.annotations.JSON;

import java.util.Date;

@ParentPackage("demo-default")
@Namespace("/demo")
@InterceptorRefs({
        @InterceptorRef("annotatedStack")
})
public class DemoAction extends ActionSupport {

    @Action(value = "demoIndex",results={
            @Result(name = "success",location = "/index.jsp"),
            @Result(name = "error",location = "/index.jsp")
    })
    public String index() {
        System.out.println("---index---");
        this.addActionError(this.getText("welcome",new String[]{"贺小白"}));
        return SUCCESS;
    }

    private ResultUtil resultUtil;
    @JSON
    public ResultUtil getResultUtil() {
        return resultUtil;
    }

    @Action(value = "demoResponse",results={@Result(name = "response",type = "json")})
    public String response() {
        System.out.println("--------- demo_response --------");

        User user = new User();
        user.setName("贺小白");
        user.setAge(30);
        user.setAddress("安庆");
        user.setCreateTime(new Date());
        resultUtil = ResultUtil.buildSuccess(user);

        // 返回结果
        return "response";
    }

    private User user;
    public User getUser() {
        return user;
    }
    public void setUser(User user) {
        this.user = user;
    }

    @Action(value = "demoRequest",results={@Result(name = "request",type = "json")})
    public String request() {
        System.out.println("--------- demo_request --------");

        System.out.println("-->"+ JSONObject.toJSONString(user));
        resultUtil = ResultUtil.buildSuccess();

        // 返回结果
        return "request";
    }

    private String name;
    private Integer age;
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public Integer getAge() {
        return age;
    }
    public void setAge(Integer age) {
        this.age = age;
    }

    @Action(value = "demoRequestParam",results={@Result(name = "requestParam",type = "json")})
    public String requestParam() {
        System.out.println("--------- demo_request --------");

        System.out.println("--> name=" + name + ", age=" + age);
        resultUtil = ResultUtil.buildSuccess();

        // 返回结果
        return "requestParam";
    }
}
