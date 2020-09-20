<%@ taglib prefix="s" uri="/struts-tags" %>
<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2020/8/22
  Time: 23:53
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
  <head>
    <title>标题</title>
  </head>
  <body>
  测试struts2连接成功！

  <form action="file/upload" method="post" enctype="multipart/form-data">
    <input type="file" name="upload">
    <input type="submit" name="btnUpload" value="上传">
  </form>

  <a href="file/download?fileName=test.jpg">下载</a>

  <a href="demo/demoIndex?request_locale=zh_CN">中文</a>
  <a href="demo/demoIndex?request_locale=en_US">English</a>

  <s:text name="title"/>
  <s:text name="welcome" />

  </body>
</html>
