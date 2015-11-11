package org.wrl.spring.aop.aspect;

/**
 * Created with IntelliJ IDEA.
 * 定义切面支持类
 * 切面就是通知和切入点的组合，而切面是通过配置方式定义的，因此这定义切面前，我们需要定义切面支持类，切面支持类提供了通知实现
 * @author: wangrl
 * @Date: 2015-11-11 15:49
 */
public class HelloWorldAspect {
    //前置通知
    public void beforeAdvice() {
        System.out.println("===========before advice");
    }

    //后置最终通知
    public void afterFinallyAdvice() {
        System.out.println("===========after finally advice");
    }
}
