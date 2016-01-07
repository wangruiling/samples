package org.wrl.quartz.spring.demo2;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.quartz.QuartzJobBean;

/**
 * 因为Job需要持久化到数据库中，SimpleService必须实现Serializable接口，在这里只是简单打印一下日志
 * @author: wangrl
 * @Date: 2016-01-07 15:44
 */
public class SimpleService extends QuartzJobBean {
    private static final Logger logger = LoggerFactory.getLogger(SimpleService.class);

    public void testMethod1(){
        //这里执行定时调度业务
        System.out.println("testMethod1.......1");
    }

    public void testMethod2(){
        System.out.println("testMethod2.......2");
    }

    @Override
    protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
        testMethod1();
    }
}
