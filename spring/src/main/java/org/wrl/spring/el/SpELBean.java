package org.wrl.spring.el;

import org.springframework.beans.factory.annotation.Value;

/**
 * Created with IntelliJ IDEA.
 * 注解风格的配置
 * 基于注解风格的 SpEL 配置也非常简单，使用@Value 注解来指定 SpEL 表达式，该注解可以放到字段、方法及方法参数上。
 * @author: wangrl
 * @Date: 2015-11-10 17:52
 */
public class SpELBean {
    @Value("#{'Hello' + world}")
    private String value;

    public void setValue(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
