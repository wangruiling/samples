package org.wrl.spring.spring3;

/**
 * Created by wrl on 2015/11/4.
 */
public class HelloImpl implements HelloApi {
    @Override
    public void sayHello() {
        System.out.println("Hello World!");
    }
}
