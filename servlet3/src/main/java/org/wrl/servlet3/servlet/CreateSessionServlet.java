package org.wrl.servlet3.servlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * @author: wangrl
 * @Date: 2016-01-15 0:48
 */
@WebServlet(name = "createSessionServlet", urlPatterns = "/createSession")
public class CreateSessionServlet extends HttpServlet {

    @Override
    protected void doGet(final HttpServletRequest req, final HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("requestedSessionId:" + req.getRequestedSessionId());
        HttpSession session = req.getSession();
        String sessionId = session.getId();
        System.out.println("create session, id : " + sessionId);
    }
}