package org.wrl.spring.aop.service.impl;

import org.wrl.spring.aop.service.IHelloWorldService;

/**
 * Created with IntelliJ IDEA.
 * 定义目标接口实现
 * @author: wangrl
 * @Date: 2015-11-11 15:47
 */
public class HelloworldService implements IHelloWorldService {
    @Override
    public void sayHello() {
        System.out.println("============Hello World!");
    }
}
