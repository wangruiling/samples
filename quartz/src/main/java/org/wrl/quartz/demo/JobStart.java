package org.wrl.quartz.demo;

import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

import static org.quartz.SimpleScheduleBuilder.simpleSchedule;
import static org.quartz.TriggerBuilder.newTrigger;

/**
 * 当调用StdSchedulerFactory.getDefaultScheduler()获取scheduler实例对象后，在调用scheduler.shutdown()之前，scheduler不会终止，因为还有活跃的线程在执行
 * @author: wangrl
 * @Date: 2016-01-06 16:55
 */
public class JobStart {
    public static void main(String[] args) throws SchedulerException {
        try {
            //获取Scheduler
            // Grab the Scheduler instance from the Factory
            Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();

            //添加job
            addJob(scheduler);

            //启动job
            scheduler.start();

            //停止scheduler
            //scheduler.shutdown();

        } catch (SchedulerException se) {
            se.printStackTrace();
        }
    }

    private static void addJob(Scheduler scheduler) throws SchedulerException {
        //1、首先，创建JobDetail
        // define the job and tie it to our HelloJob class
        JobDetail job = JobBuilder.newJob(HelloJob.class)
                .withIdentity("job1", "group1")
                .build();

        //2、然后，创建Trigger：
        // Trigger the job to run now, and then repeat every 40 seconds
        Trigger trigger = newTrigger()
                .withIdentity("trigger1", "group1")
                .startNow()
                .withSchedule(simpleSchedule()
                        .withIntervalInSeconds(40)
                        .repeatForever())
                .build();

        //3、添加job，以及其关联的trigger
        // Tell quartz to schedule the job using our trigger
        scheduler.scheduleJob(job, trigger);
    }
}
