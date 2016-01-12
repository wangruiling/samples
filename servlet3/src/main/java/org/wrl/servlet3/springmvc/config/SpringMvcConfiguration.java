package org.wrl.servlet3.springmvc.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.stereotype.Controller;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.resource.DefaultServletHttpRequestHandler;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;

/**
 * springmvc上下文
 *
 * @author: wangrl
 * @Date: 2016-01-13 0:14
 */
@Configuration
@EnableWebMvc
@ComponentScan(
        value = "org.wrl.servlet3.springmvc.**.web.controller",
        includeFilters = {
                @ComponentScan.Filter(type = FilterType.ANNOTATION, value = Controller.class)
        })
public class SpringMvcConfiguration {

    @Bean
    public ViewResolver createInternalResourceViewResolver() {
        InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
        viewResolver.setViewClass(JstlView.class);
        viewResolver.setContentType("text/html");
        viewResolver.setPrefix("/WEB-INF/jsp/");
        viewResolver.setSuffix(".jsp");

        return viewResolver;
    }

    @Bean
    public DefaultServletHttpRequestHandler createDefaultServletHttpRequestHandler() {
        return new DefaultServletHttpRequestHandler();
    }
}
