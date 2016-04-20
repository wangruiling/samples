<%--
  Created by IntelliJ IDEA.
  User: wangrl
  Date: 2016/4/20
  Time: 23:12
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>初始化Jsp:为jsp配置初始化参数</title>

</head>
<body>
<%!
    public void jspInit(){
        ServletConfig servletConfig = getServletConfig();
        String emailAddr = servletConfig.getInitParameter("email");
        ServletContext ctx = servletConfig.getServletContext();
        ctx.setAttribute("mail", emailAddr);
    }
%>
Email is:
<%= pageContext.findAttribute("mail")%>
</body>
</html>
