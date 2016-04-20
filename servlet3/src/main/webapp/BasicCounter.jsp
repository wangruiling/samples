<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="org.wrl.servlet3.jsp.Counter" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
The page count is:
<%
    out.println(org.wrl.servlet3.jsp.Counter.getCount());
%>
<br>
scriptlet:
<%
    out.println(Counter.getCount());
%>
<br>
表达式代码:
<%= Counter.getCount()%>
<br>
jsp声明:
<%!
    int count = 0;
%>
<%= ++count %>
</body>
</html>
