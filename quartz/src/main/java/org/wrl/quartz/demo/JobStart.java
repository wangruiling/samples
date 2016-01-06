package org.wrl.quartz.demo;

import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

/**
 * @author: wangrl
 * @Date: 2016-01-06 16:55
 */
public class JobStart {
    public static void main(String[] args) throws SchedulerException {
        //1、首先，创建JobDetail
        JobDetail jobDetail = JobBuilder.newJob(HelloJob.class)
                .withIdentity("helloJob", "group1")
                .usingJobData("name", "wangrl")
                .build();

        //2、然后，创建Trigger：
        Trigger trigger = TriggerBuilder.newTrigger()
                .withIdentity("myTrigger","group1")
                .startNow()
                .withSchedule(
                        SimpleScheduleBuilder.simpleSchedule()
                                //每5s运行一次
                                .withIntervalInSeconds(5)
                                //重复运行3次
                                .withRepeatCount(3)
                ).build();

        //3、最后，获取Scheduler，并启动任务
        SchedulerFactory schedulerFactory = new StdSchedulerFactory();
        Scheduler scheduler = schedulerFactory.getScheduler();
        //添加job，以及其关联的trigger
        scheduler.scheduleJob(jobDetail, trigger);

        //启动job
        scheduler.start();
    }
}
