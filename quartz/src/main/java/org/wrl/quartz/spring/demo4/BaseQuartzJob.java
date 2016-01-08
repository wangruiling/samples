package org.wrl.quartz.spring.demo4;

import org.quartz.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.quartz.QuartzJobBean;

import java.io.Serializable;
import java.lang.invoke.MethodHandles;

/**
 * 定义所有job的父类，并负责异常发送邮件任务和日志任务
 * @author: wangrl
 * @Date: 2016-01-08 14:33
 */
public abstract class BaseQuartzJob extends QuartzJobBean implements Serializable {
    protected static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    //定义抽象方法，供子类实现
    public abstract void action(JobExecutionContext context);

    @Override
    public void executeInternal(JobExecutionContext context) throws JobExecutionException {
        try {
            long start = System.currentTimeMillis();
            this.action(context);
            long end = System.currentTimeMillis();
            JobDetail jobDetail = context.getJobDetail();
            Trigger trigger = context.getTrigger();
            StringBuilder buffer = new StringBuilder();
            buffer.append("jobName = ").append(jobDetail.getKey().getName()).append(" triggerName = ")
                    .append(trigger.getKey().getName()).append(" 执行完成 , 耗时: ").append((end - start)).append(" ms");
            logger.info(buffer.toString());
        } catch (Exception e) {
            doResolveException(context != null ? context.getMergedJobDataMap() : null, e);
        }
    }

    @SuppressWarnings("unchecked")
    private void doResolveException(JobDataMap dataMap, Exception ex) {
        //发送邮件实现此处省略
        //...
    }
}
