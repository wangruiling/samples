package org.wrl.servlet3.servlet;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author: wangrl
 * @Date: 2016-01-14 22:49
 */
@WebServlet(name = "resourceServlet", urlPatterns = "/resource")
public class ResourceServlet extends HttpServlet {

    @Override
    protected void doGet(final HttpServletRequest req, final HttpServletResponse resp) throws ServletException, IOException {
        ServletContext sc = getServletContext();

        //查找顺序
        //1、先相对于web应用的根查找
        //2、找不到再到WEB-INF/lib jar包中的META-INF/resources下找（查找顺序不确定）

        //得到该路径下的所有子路径
        //返回 [/WEB-INF/, /com/, /index.jsp, /t1.txt, /t3.txt]
        System.out.println(sc.getResourcePaths("/"));
        //jar包里的
        System.out.println(sc.getResource("/t1.txt"));
        //webapp下的
        System.out.println(sc.getResource("/t3.txt"));
        //jar包里的
        System.out.println(sc.getResource("/com/sishuok/t2.txt"));
        //webapp下的
        System.out.println(sc.getResource("/com/sishuok/t4.txt"));

        //另外
        //可以直接使用如 http://localhost:9080/chapter4/jsp/hello.jsp 访问jar!/META-INF/resources下的资源
        //http://localhost:9080/chapter4/static/css/style.css

    }
}
