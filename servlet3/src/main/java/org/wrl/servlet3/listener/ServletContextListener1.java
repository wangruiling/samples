package org.wrl.servlet3.listener;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

/**
 * @author: wangrl
 * @Date: 2016-01-11 22:52
 */
@WebListener
public class ServletContextListener1 implements ServletContextListener {
    @Override
    public void contextInitialized(final ServletContextEvent sce) {
        System.out.println("init servlet context");
    }

    @Override
    public void contextDestroyed(final ServletContextEvent sce) {
        System.out.println("destroy servlet container");
    }
}
