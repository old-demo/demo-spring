package com.heqing.demo.spring.struts2.controller;

import com.alibaba.fastjson.JSONObject;
import com.heqing.demo.spring.struts2.model.User;
import com.heqing.demo.spring.struts2.util.ResultUtil;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

import java.util.Date;

public class JsonAction extends ActionSupport implements ModelDriven<User> {

    private ResultUtil resultUtil;

    public ResultUtil getResultUtil() {
        return resultUtil;
    }

    public String response() {
        System.out.println("---------response--------");

        User user = new User();
        user.setName("贺小白");
        user.setAge(30);
        user.setAddress("安庆");
        user.setCreateTime(new Date());
        resultUtil = ResultUtil.buildSuccess(user);

        // 返回结果
        return "myJson";
    }

    /** 领域模型User对象 **/
    private User user = new User();

    @Override
    public User getModel() {
        return user;
    }

    public String request() {
        System.out.println("---------request--------");

        System.out.println("-->"+ JSONObject.toJSONString(user));
        resultUtil = ResultUtil.buildSuccess();

        // 返回结果
        return "myJson";
    }

}
