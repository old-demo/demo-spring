package com.heqing.demo.spring.mvc.controller;

import com.heqing.demo.spring.mvc.aop.DemoException;
import com.heqing.demo.spring.mvc.model.User;
import com.heqing.demo.spring.mvc.util.ResultUtil;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSONObject;
import org.springframework.web.servlet.support.RequestContext;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

@RestController
@RequestMapping("/demo")
public class DemoController {

    @ModelAttribute
    public void modelAttribute(HttpServletRequest req, HttpServletResponse resp) {
        System.out.println("===Controller.before===");
    }

    @InitBinder
    public void initBinder(ServletRequestDataBinder binder){
        binder.registerCustomEditor(Date.class, new CustomDateEditor(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"),true));
    }

    @RequestMapping("/index")
    public ResultUtil<String> index() {
        return ResultUtil.buildSuccess("链接测试成功了");
    }

    @RequestMapping("/view")
    public ModelAndView view(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        //1、收集参数、验证参数
        //2、绑定参数到命令对象
        //3、将命令对象传入业务对象进行业务处理
        //4、选择下一个页面
        ModelAndView mv = new ModelAndView();

        //从后台代码获取国际化信息
        RequestContext requestContext = new RequestContext(req);
        mv.addObject("title", requestContext.getMessage("title"));

        //添加模型数据 可以是任意的POJO对象
        mv.addObject("message", "Hello World!");
        //设置逻辑视图名，视图解析器会根据该名字解析到具体的视图页面
        mv.setViewName("springMVC");
        return mv;
    }

    @RequestMapping("/date")
    public void date(String date) {
        System.out.println("---------注意：使用切面后，参数未Date类型会报：org.springframework.web.method.annotation.MethodArgumentTypeMismatchExceptio--------");
        System.out.println("===Controller.data===");
        System.out.println("date="+date);
//        return ResultUtil.buildSuccess();
    }

    @RequestMapping("/pathVariable/{id}")
    public ResultUtil pathVariable(@PathVariable("id") String id) {
        System.out.println("=== DemoController.pathVariable ===");
        System.out.println("id="+id);
        return ResultUtil.buildSuccess();
    }

    @RequestMapping("/requestParam")
    public ResultUtil requestParam(@RequestParam(value="name",required=false,defaultValue="heqing") String name, @RequestParam("age") Integer age){
        System.out.println("=== DemoController.requestParam ===");
        System.out.println("name="+name+", age="+age);
        return ResultUtil.buildSuccess();
    }

    @RequestMapping("/requestBody")
    public ResultUtil requestBody(@RequestBody List<User> userList){
        System.out.println("=== DemoController.requestBody ===");
        System.out.println("user="+JSONObject.toJSONString(userList));
        return ResultUtil.buildSuccess();
    }

    @RequestMapping("/request")
    public ResultUtil request(@RequestParam("id") Long id,@RequestBody User user){
        System.out.println("=== DemoController.request ===");
        System.out.println("id="+id+", user="+JSONObject.toJSONString(user));
        return ResultUtil.buildSuccess();
    }

    @RequestMapping("/response")
    public ResultUtil<User> response(){
        System.out.println("=== DemoController.request ===");

        User user = new User();
        user.setName("heqing");
        user.setAge(30);
        user.setAddress("安庆市");
        user.setCreateTime(new Date());

        return  ResultUtil.buildSuccess(user);
    }

    @RequestMapping("/exception")
    public ResultUtil exception(){
        System.out.println("=== DemoController.exception ===");
        throw new DemoException(-1, "系统错误");
    }

    @RequestMapping("/upload")
    public void upload(HttpServletRequest request, @RequestParam(required=false) String filePath) throws IllegalStateException, IOException {
        System.out.println("===upload===");
        if(StringUtils.isEmpty(filePath)) {
            filePath = "D:/test/";
        }

        //创建一个通用的多部分解析器
        CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(request.getSession().getServletContext());
        //判断 request 是否有文件上传,即多部分请求
        if(multipartResolver.isMultipart(request)){
            //转换成多部分request
            MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest)request;
            //取得request中的所有文件名
            Iterator<String> iter = multiRequest.getFileNames();
            while(iter.hasNext()){
                //记录上传过程起始时的时间，用来计算上传时间
                int pre = (int) System.currentTimeMillis();
                //取得上传文件
                MultipartFile file = multiRequest.getFile(iter.next());
                if(file != null){
                    //取得当前上传文件的文件名称
                    String myFileName = file.getOriginalFilename();
                    //如果名称不为“”,说明该文件存在，否则说明该文件不存在
                    if(myFileName.trim() !=""){
                        //重命名上传后的文件名
                        String fileName = file.getOriginalFilename();
                        //定义上传路径
                        String path = filePath + fileName;
                        File localFile = new File(path);
                        file.transferTo(localFile);
                    }
                }
                //记录上传该文件后的时间
                int finaltime = (int) System.currentTimeMillis();
                System.out.println("上传用时："+(finaltime - pre)+"秒");
            }
        }
    }
}
