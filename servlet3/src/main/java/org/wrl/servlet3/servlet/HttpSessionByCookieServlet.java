package org.wrl.servlet3.servlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * HttpSession的默认Cookie实现案例
 *
 * @author: wangrl
 * @Date: 2016-01-19 13:50
 */
@WebServlet(urlPatterns = "/sessionByCookie")
public class HttpSessionByCookieServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        if (session.isNew()) {
            // 设置session属性值
            session.setAttribute("name", "Jeff");
        }
        // 获取SessionId
        String sessionId = session.getId();
        System.out.println("sessionId:" + sessionId);

        PrintWriter out = resp.getWriter();
        // 如果HttpSeesion是新建的话
        if (session.isNew()) {
            out.println("Hello,HttpSession! <br>The first response - SeesionId="
                    + sessionId + " <br>");
        } else {
            out.println("Hello,HttpSession! <br>The second response - SeesionId="
                    + sessionId + " <br>");
            // 从Session获取属性值
            out.println("The second-response - name: "
                    + session.getAttribute("name"));
        }
    }
}
