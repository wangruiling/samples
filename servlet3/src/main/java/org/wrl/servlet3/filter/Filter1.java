package org.wrl.servlet3.filter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * @author: wangrl
 * @Date: 2016-01-11 22:47
 */
@WebFilter(filterName = "filter1", urlPatterns = "/*", asyncSupported = true
        , dispatcherTypes = {DispatcherType.REQUEST, DispatcherType.FORWARD})
public class Filter1 implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        System.out.println("init Filter1");
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        System.out.println("filter1===" + httpServletRequest.getRequestURI());
        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {
        System.out.println("filter1 destroy");
    }
}
