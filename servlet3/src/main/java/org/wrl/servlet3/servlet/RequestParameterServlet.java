package org.wrl.servlet3.servlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;

/**
 * @author: wangrl
 * @Date: 2016-01-14 22:44
 */
@WebServlet(name = "requestParameterServlet", urlPatterns = "/requestParameter")
public class RequestParameterServlet extends HttpServlet {

    @Override
    protected void doPost(final HttpServletRequest req, final HttpServletResponse resp) throws ServletException, IOException {
        //非新特性

        //访问 parameter.jsp 然后提交 看结果
        System.out.println("name===" + Arrays.toString(req.getParameterValues("name")));

        //如果只想获取 get的 或 post的呢？
//        req.getInputStream();
//        得到post内容区数据
//        req.getReader();

        //req.getQueryString();  get请求的url部分的 ? 后边的 自己再根据实际情况提取
        System.out.println(req.getQueryString());
    }
}
