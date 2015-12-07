package org.wrl.spring.noxml.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * Created by wrl on 2015/12/7.
 */
@Configuration("ctxConfig2")
@Import({ApplicationContextConfig.class})
public class ApplicationContextConfig2 {
    @Bean(name = {"message2"})
    public String message() {
        return "hello";
    }
}
