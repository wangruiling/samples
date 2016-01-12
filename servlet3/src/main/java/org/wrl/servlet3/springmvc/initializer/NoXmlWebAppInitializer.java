package org.wrl.servlet3.springmvc.initializer;

import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;
import org.wrl.servlet3.springmvc.config.RootConfiguration;
import org.wrl.servlet3.springmvc.config.SpringMvcConfiguration;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;

/**
 * @author: wangrl
 * @Date: 2016-01-13 0:19
 */
public class NoXmlWebAppInitializer implements WebApplicationInitializer {

    @Override
    public void onStartup(final ServletContext sc) throws ServletException {

        //1、根上下文
        AnnotationConfigWebApplicationContext  rootContext = new AnnotationConfigWebApplicationContext();
        rootContext.register(RootConfiguration.class);
        sc.addListener(new ContextLoaderListener(rootContext));

        //2、springmvc上下文
        AnnotationConfigWebApplicationContext springMvcContext = new AnnotationConfigWebApplicationContext();
        springMvcContext.register(SpringMvcConfiguration.class);

        //3、DispatcherServlet
        DispatcherServlet dispatcherServlet = new DispatcherServlet(springMvcContext);

        ServletRegistration.Dynamic dynamic = sc.addServlet("dispatcherServlet", dispatcherServlet);
        dynamic.setLoadOnStartup(1);
        dynamic.addMapping("/");
    }
}
