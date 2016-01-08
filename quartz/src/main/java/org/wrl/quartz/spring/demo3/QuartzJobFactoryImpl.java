package org.wrl.quartz.spring.demo3;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import java.io.Serializable;

/**
 * Job实现类
 * 在这里把它看作工厂类
 * @author: wangrl
 * @Date: 2016-01-08 13:10
 */
public class QuartzJobFactoryImpl implements Job, Serializable {
    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        System.out.println("任务成功运行");
        ScheduleJob scheduleJob = (ScheduleJob)context.getMergedJobDataMap().get("scheduleJob");
        System.out.println("任务名称 = [" + scheduleJob.getJobName() + "]");
    }
}
