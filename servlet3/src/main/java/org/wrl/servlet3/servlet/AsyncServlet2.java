package org.wrl.servlet3.servlet;

import javax.servlet.AsyncContext;
import javax.servlet.AsyncEvent;
import javax.servlet.AsyncListener;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * 支持异步返回的Servlet
 * 对于Servlet的异步返回，首先我们必须指定@WebServlet的asyncSupported属性为true（默认是false），同时在它之前的Filter
 * 的asyncSupported属性也必须是true，否则传递过来的request就是不支持异步调用的。
 * @author: wangrl
 * @Date: 2016-01-11 22:59
 */
@WebServlet(value = "/servlet/async2", asyncSupported = true)
public class AsyncServlet2 extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/plain;charset=UTF-8");
        final PrintWriter writer = resp.getWriter();
        writer.println("异步之前输出的内容。");
        writer.flush();
        //开始异步调用，获取对应的AsyncContext。
        final AsyncContext asyncContext = req.startAsync();
        //设置当前异步调用对应的监听器
        asyncContext.addListener(new MyAsyncListener());
        //设置超时时间，当超时之后程序会尝试重新执行异步任务，即我们新起的线程。
        asyncContext.setTimeout(10*1000L);
        //新起线程开始异步调用，start方法不是阻塞式的，它会新起一个线程来启动Runnable接口，之后主程序会继续执行
        asyncContext.start(new Runnable() {

            @Override
            public void run() {
                try {
                    Thread.sleep(5*1000L);
                    writer.println("异步调用之后输出的内容。");
                    writer.flush();
                    //异步调用完成
                    asyncContext.complete();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

        });
        writer.println("可能在异步调用前输出，也可能在异步调用之后输出，因为异步调用会新起一个线程。");
        writer.flush();
    }

    /**
     * Servlet 3.0 为异步处理提供了一个监听器，使用 AsyncListener 接口表示。它可以监控如下四种事件
     * @author Yeelim
     * @date 2014-2-8
     * @mail yeelim-zhang@todaytech.com.cn
     */
    private class MyAsyncListener implements AsyncListener {

        @Override
        public void onComplete(AsyncEvent event) throws IOException {
            System.out.println("异步执行完毕时……");
            event.getSuppliedResponse().getWriter().println("异步执行完毕时……");
        }

        @Override
        public void onError(AsyncEvent event) throws IOException {
            System.out.println("异步线程出错时……");
            event.getSuppliedResponse().getWriter().println("异步线程出错时……");
        }

        @Override
        public void onStartAsync(AsyncEvent event) throws IOException {
            System.out.println("异步线程开始时……");
            event.getSuppliedResponse().getWriter().println("异步线程开始时……");
        }

        @Override
        public void onTimeout(AsyncEvent event) throws IOException {
            System.out.println("异步线程执行超时……");
            event.getSuppliedResponse().getWriter().println("异步线程执行超时……");
        }

    }
}
