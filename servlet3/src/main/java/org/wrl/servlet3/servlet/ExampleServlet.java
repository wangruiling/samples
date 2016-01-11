package org.wrl.servlet3.servlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Servlet3.0支持使用注解配置Servlet。我们只需在Servlet对应的类上使用@WebServlet进行标注，
 * 我们就可以访问到该Servlet了，而不需要再在web.xml文件中进行配置。@WebServlet的urlPatterns
 * 和value属性都可以用来表示Servlet的部署路径，它们都是对应的一个数组。
 * @author: wangrl
 * @Date: 2016-01-11 22:24
 */
@WebServlet(name = "exampleServlet", urlPatterns = "/servlet/example", loadOnStartup = 1
        , initParams = {@WebInitParam(name = "msg", value = "hello world")})
public class ExampleServlet extends HttpServlet{
    private String msg;

    public ExampleServlet() {
        System.out.println("load on startup");
    }

    @Override
    public void init() throws ServletException {
        super.init();
        msg = this.getInitParameter("msg");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("msg:" + msg);
        resp.getWriter().write(msg);
    }
}
