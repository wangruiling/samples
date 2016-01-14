<%--
  Created by IntelliJ IDEA.
  User: wrl
  Date: 2016/1/11
  Time: 23:52
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>文件上传</title>
</head>
<body>
<form method="post" action="${pageContext.request.contextPath}/upload" enctype="multipart/form-data">
    name:<input type="text" name="name"><br/>

    file1: <input type="file" name="file1"><br/>

    file2: <input type="file" name="file2"><br/>
    file2: <input type="file" name="file2"><br/>

    <input type="submit" value="上传">
</form>
</body>
</html>
