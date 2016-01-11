package org.wrl.servlet3.servlet;

import javax.servlet.AsyncContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;

/**
 * 支持异步返回的Servlet
 * 对于Servlet的异步返回，首先我们必须指定@WebServlet的asyncSupported属性为true（默认是false），同时在它之前的Filter
 * 的asyncSupported属性也必须是true，否则传递过来的request就是不支持异步调用的。
 * @author: wangrl
 * @Date: 2016-01-11 22:59
 */
@WebServlet(value = "/servlet/async", asyncSupported = true)
public class AsyncServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/plain;charset=UTF-8");
        final PrintWriter writer = resp.getWriter();
        writer.println("进入Servlet的时间:" + LocalDateTime.now());
        writer.flush();

        //开始异步调用，获取对应的AsyncContext。 在子线程中执行业务调用，并由其负责输出响应，主线程退出
        final AsyncContext asyncContext = req.startAsync();
        new Thread(new MyExecutor(asyncContext)).start();


        writer.println("结束Servlet的时间:" + LocalDateTime.now());
        writer.flush();
    }
}

class MyExecutor implements Runnable {
    private AsyncContext ctx = null;
    public MyExecutor(AsyncContext ctx){
        this.ctx = ctx;
    }

    public void run(){
        try {
            //等待十秒钟，以模拟业务方法的执行
            Thread.sleep(10000);
            PrintWriter out = ctx.getResponse().getWriter();
            out.println("业务处理完毕的时间：" + LocalDateTime.now() + ".");
            out.flush();
            ctx.complete();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}