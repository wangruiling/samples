package org.wrl.servlet3.springmvc.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.stereotype.Controller;

/**
 * 根上下文
 * @author: wangrl
 * @Date: 2016-01-13 0:11
 */
@Configuration
@ComponentScan(
        value = {"org.wrl.servlet3.springmvc.**.service", "org.wrl.servlet3.springmvc.**.repository"},
        excludeFilters = {
                @ComponentScan.Filter(type = FilterType.ANNOTATION, value = Controller.class)
        })
public class RootConfiguration {
}
