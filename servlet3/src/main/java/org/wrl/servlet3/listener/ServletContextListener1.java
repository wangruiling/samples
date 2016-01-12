package org.wrl.servlet3.listener;

import javax.servlet.*;
import javax.servlet.annotation.WebListener;

/**
 * 动态配置与按需加载
 * 以编程方式添加Servlet、监听器和过滤器
 * 只能在下列各项中执行配置
 * 1.ServletContetlistener.contextInitialized()
 * 2.ServletContainerInitializer.onStartup()
 * @author: wangrl
 * @Date: 2016-01-11 22:52
 */
@WebListener
public class ServletContextListener1 implements ServletContextListener {

    @Override
    public void contextInitialized(final ServletContextEvent sce) {
        System.out.println("init servlet context");

        //动态配置
        //dynamicRegistrate(sce);
    }

    /**
     * 动态配置
     * @param sce
     */
    private void dynamicRegistrate(ServletContextEvent sce) {
        ServletContext ctx = sce.getServletContext();
        //注册Servlet
        ServletRegistration.Dynamic servReg = ctx.addServlet("action", "org.apache.struts.action.ActionServlet");

        //设置初始化参数
        servReg.setInitParameter("config", "/WEB-INF/struts-config.xml");
        servReg.setLoadOnStartup(2);

        //Add mappings
        servReg.addMapping("*.do");

        //Annotate with @MultipartConfig
        servReg.setMultipartConfig(new MultipartConfigElement("/tmp"));
    }

    @Override
    public void contextDestroyed(final ServletContextEvent sce) {
        System.out.println("destroy servlet container");
    }
}
