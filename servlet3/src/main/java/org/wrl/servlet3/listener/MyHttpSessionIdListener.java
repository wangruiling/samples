package org.wrl.servlet3.listener;

import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionIdListener;

/**
 * 通过HttpServletRequest#changeSessionId()直接更改会话ID，并可以通过HttpSessionIdListener监听
 * @author: wangrl
 * @Date: 2016-01-15 0:27
 */
@WebListener
public class MyHttpSessionIdListener implements HttpSessionIdListener {

    @Override
    public void sessionIdChanged(final HttpSessionEvent event, final String oldSessionId) {
        System.out.println("===========session id变更了，老的是：" + oldSessionId + "，新的是：" + event.getSession().getId());
    }
}
