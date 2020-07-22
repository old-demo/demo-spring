<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>

<html >
    <head>
        <meta charset="UTF-8">
        <title>Title</title>

        <script src="../js/jquery-3.1.1.min.js"></script>
        <script src="../js/ajaxfileupload.js"></script>
    </head>

    <body>
        后台传递过来的消息：${message}  <br/>
        国际化：默认：<spring:message code="message"/> &nbsp;&nbsp;
                标签：<spring:message code="title"/> &nbsp;&nbsp;
                后台: ${title} <br/>
        <br/>
        <button id="initBinder"     type="button">测试  InitBinder</button><br/>
        <button id="pathVariable"   type="button">测试  PathVariable</button><br/>
        <button id="requestParam"   type="button">测试  RequestParam</button><br/>
        <button id="requestBody"    type="button">测试  RequestBody</button><br/>
        <button id="request"        type="button">测试  Request</button><br/>
        <button id="response"       type="button">测试  Response</button><br/>
        <br/>
        <input type="file" id="uFile" name="uFile" multiple="multiple"/>
        <button id="uploadAll" type="button">上传文件</button>
    </body>

    <script>
        $("#initBinder").click(function(){
            $.ajax({
                type: "POST",
                url: "/demo/date",
                data: {
                    "date" : "2020-06-15 11:11:11"
                }
            });
        });

        $("#pathVariable").click(function(){
            $.ajax({
                type: "POST",
                url: "/demo/pathVariable/246512",
            });
        });

        $("#requestParam").click(function(){
            // $.ajax({
            //     type: "POST",
            //     url: "/demo/requestParam?name=heqing&age=30"
            // });

            $.ajax({
                type: "POST",
                url: "/demo/requestParam",
                data: {
                    "name" : "heqing",
                    "age" : 30
                }
            });
        });

        $("#requestBody").click(function(){
            var userAry=[];
            var user1 = {"name":"heqing", "age":30, "address":"安庆市宿松县"};
            var user2 = {"name":"hequan", "age":18, "createTime" : "2020-06-15 11:11:11"};
            userAry.push(user1);
            userAry.push(user2);

            $.ajax({
                type: "POST",
                url: "/demo/requestBody",
                data:JSON.stringify(userAry),
                dataType: "json",
                contentType: "application/json;charset=utf-8;"
            });
        });

        $("#request").click(function(){
            var user = {"name":"heqing", "age":30, "address":"安庆市宿松县"};

            $.ajax({
                type: "POST",
                url: "/demo/request?id=1",
                data:JSON.stringify(user),
                dataType: "json",
                contentType: "application/json;charset=utf-8;"
            });
        });

        $("#response").click(function(){
            $.ajax({
                type: "POST",
                url: "/demo/response",
                success: function(data){
                    console.info(data);
                }
            });
        });

        $("#uploadAll").click(function(){
            $.ajaxFileUpload({
                url:"/demo/upload",
                fileElementId:"uFile",
                data: {
                    "filePath" : "D:/test/png/"
                }
            });
        });
    </script>
</html>