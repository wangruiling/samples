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
<form method="post" action="servlet/upload" enctype="multipart/form-data">
    <input type="file" name="upload"/>
    <input type="submit" value="upload"/>
</form>
</body>
</html>
