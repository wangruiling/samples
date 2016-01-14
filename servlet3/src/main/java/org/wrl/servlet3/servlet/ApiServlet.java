package org.wrl.servlet3.servlet;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.SessionCookieConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * API改进
 * 比如提供HttpOnly支持、HttpServletRequest#getRequestedSessionId直接获取请求时的会话ID、HttpServletResponse#getStatus等直接获取响应状态码、响应头等信息
 比如Servlet3.1的request/response#getContentLengthLong得到long型内容长度、ServletContext#getVirtualServerName得到虚拟主机名
 比如Servlet3.1的通过HttpServletRequest#changeSessionId()直接更改会话ID，并可以通过HttpSessionIdListener监听
 其他的请参考源代码
 * @author: wangrl
 * @Date: 2016-01-14 22:09
 */
@WebServlet(name = "apiServlet", urlPatterns = "/api")
public class ApiServlet extends HttpServlet{
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //以前
        req.getSession().getServletContext();

        //现在
        ServletContext servletContext = req.getServletContext();

        //servlet主要版本
        System.out.println("servlet主要版本:" + servletContext.getEffectiveMajorVersion());
        //servlet次要版本
        System.out.println("servlet次要版本" + servletContext.getEffectiveMinorVersion());



        //默认的session跟踪机制
        System.out.println("默认的session跟踪机制:" + servletContext.getDefaultSessionTrackingModes());

        //有效的session跟踪机制
        System.out.println("有效的session跟踪机制:" + servletContext.getEffectiveSessionTrackingModes());

        //设置session跟踪机制：有COOKIE URL SSL
        //需要在容器初始化时 完成 如ServletContextListener#contextInitialized方法中调用如下代码 具体看javadoc
//        Set<SessionTrackingMode> sessionTrackingModes = new HashSet<SessionTrackingMode>();
//        sessionTrackingModes.add(SessionTrackingMode.COOKIE);
//        servletContext.setSessionTrackingModes(sessionTrackingModes);

        //用于session跟踪的cookie配置，比如默认Name是JSESSIONID，可以修改之
        SessionCookieConfig sessionCookieConfig = servletContext.getSessionCookieConfig();
        System.out.println("sessionCookieConfig.getName():" + sessionCookieConfig.getName());

        //把默认的JSESSIONID--修改为->id   可以观察客户端变成了id
        //sessionCookieConfig.setName("id");

        //得到请求的session id
        String sessionId = req.getRequestedSessionId();
        System.out.println("sessionId:" + sessionId);

        /**得到分派的类型 请参考：{@link javax.servlet.DispatcherType}*/
        System.out.println("得到分派的类型:" + req.getDispatcherType());


        Cookie cookie = new Cookie("key", "value");
        //servlet 3，功能是禁止客户端脚本访问
        cookie.setHttpOnly(true);
        resp.addCookie(cookie);


        //得到响应的状态码
        int status = resp.getStatus();
        System.out.println("status:" + status);
        //得到响应头
//        resp.getHeader();
//        resp.getHeaderNames();
//        resp.getHeaders();

    }
}
