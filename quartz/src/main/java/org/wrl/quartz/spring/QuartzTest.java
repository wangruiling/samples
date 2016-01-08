package org.wrl.quartz.spring;

import org.apache.commons.lang3.StringUtils;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;
import org.quartz.impl.triggers.CronTriggerImpl;
import org.springframework.expression.ParseException;

/**
 * 动态生成任务类
 *
 * @author: wangrl
 * @Date: 2016-01-07 13:52
 */
public class QuartzTest {
    private static SchedulerFactory sf = new StdSchedulerFactory();
    private static String JOB_GROUP_NAME = "ddlib";
    private static String TRIGGER_NAME = "myTrigger";
    private static String TRIGGER_GROUP_NAME = "myTriggerGroup";


    /**
     * 添加一个定时任务，使用默认的任务组名，触发器名，触发器组名
     *
     * @param jobName        任务名
     * @param jobClass       任务
     * @param cronExpression 时间设置，参考quartz说明文档
     * @throws SchedulerException
     * @throws ParseException
     */
    public static void addJob(String jobName, Class<? extends Job> jobClass, String cronExpression)
            throws SchedulerException, ParseException {
        addJob(jobName, JOB_GROUP_NAME, null, null, jobClass, cronExpression);
    }

    /**
     * 添加一个定时任务
     *
     * @param jobName          任务名
     * @param jobGroupName     任务组名
     * @param triggerName      触发器名
     * @param triggerGroupName 触发器组名
     * @param jobClass         任务
     * @param cronExpression   时间设置，参考quartz说明文档
     */
    public static void addJob(String jobName, String jobGroupName, String triggerName, String triggerGroupName, Class<? extends Job> jobClass
            , String cronExpression) throws SchedulerException, ParseException {

        if (StringUtils.isBlank(triggerName)) {
            triggerName = TRIGGER_NAME;
        }
        if (StringUtils.isBlank(triggerGroupName)) {
            triggerGroupName = TRIGGER_GROUP_NAME;
        }
        Scheduler sched = sf.getScheduler();

        //任务名，任务组，任务执行类
        JobDetail jobDetail = JobBuilder.newJob(jobClass).withIdentity(jobName, jobGroupName).build();

        //CronTrigger  trigger = new CronTrigger(jobName,triggerGroupName,cronExpression);//触发器名,触发器组,cron表达式
        //触发器名,触发器组,cron表达式
        Trigger trigger = TriggerBuilder.newTrigger()
                .withIdentity(triggerName, triggerGroupName)
                .withSchedule(CronScheduleBuilder.cronSchedule(cronExpression))
                .build();

        sched.scheduleJob(jobDetail, trigger);
        //启动
        if (!sched.isShutdown()) {
            sched.start();
        }
    }

    /**
     * 暂停任务
     * @param jobName
     * @param jobGroupName
     * @param triggerName
     * @param triggerGroupName
     */
    public static void pauseJob(String jobName, String jobGroupName, String triggerName, String triggerGroupName) {
        try {
            Scheduler sched = sf.getScheduler();
            sched.pauseTrigger(new TriggerKey(jobName, jobGroupName));// 停止触发器
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    /**
     * 重新启动任务
     * @param jobName
     * @param jobGroupName
     * @param triggerName
     * @param triggerGroupName
     */
    public static void resumeJob(String jobName, String jobGroupName, String triggerName, String triggerGroupName) {
        try {
            Scheduler sched = sf.getScheduler();
            sched.resumeTrigger(new TriggerKey(jobName, jobGroupName)); //重新启动任务
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    /**
     * @Description:启动所有定时任务
     */
    public static void startJobs() {
        try {
            Scheduler sched = sf.getScheduler();
            sched.start();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    /**
     * @Description:关闭所有定时任务
     */
    public static void shutdownJobs() {
        try {
            Scheduler sched = sf.getScheduler();
            if (!sched.isShutdown()) {
                sched.shutdown();
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 修改一个任务的触发时间(使用默认的任务组名，触发器名，触发器组名)
     *
     * @param jobName
     * @param cronExpression
     * @throws SchedulerException
     * @throws ParseException
     */
    public static void modifyJobTime(String jobName, String cronExpression)
            throws SchedulerException, ParseException {
        Trigger trigger = getTrigger(null, null);
        if (trigger != null) {
            CronTrigger ct = (CronTrigger) trigger;

            String oldCron = ct.getCronExpression();
            if (!StringUtils.equalsIgnoreCase(oldCron, cronExpression)) {
                JobDetail jobDetail = sf.getScheduler().getJobDetail(JobKey.jobKey(jobName));
                Class<? extends Job> jobClass = jobDetail.getJobClass();
                sf.getScheduler().resumeJob(JobKey.jobKey(jobName));
                addJob(jobName, jobClass, cronExpression);
            }
        }
    }

    private static Trigger getTrigger(String triggerName, String triggerGroupName) throws SchedulerException {
        Scheduler sched = sf.getScheduler();

        if (StringUtils.isBlank(triggerName)) {
            triggerName = TRIGGER_NAME;
        }
        if (StringUtils.isBlank(triggerGroupName)) {
            triggerGroupName = TRIGGER_GROUP_NAME;
        }
        Trigger trigger = sched.getTrigger(TriggerKey.triggerKey(triggerName, triggerGroupName));


        return trigger;
    }

    /**
     * 修改一个任务的触发时间
     */
    public static void modifyJobTime(String jobName, String triggerName, String triggerGroupName,
                                     String cronExpression) throws SchedulerException, ParseException, java.text.ParseException {

        if (StringUtils.isBlank(triggerName)) {
            triggerName = TRIGGER_NAME;
        }
        if (StringUtils.isBlank(triggerGroupName)) {
            triggerGroupName = TRIGGER_GROUP_NAME;
        }
        Trigger trigger = getTrigger(triggerName, triggerGroupName);

        if (trigger != null) {
            CronTriggerImpl ct = (CronTriggerImpl) trigger;

            String oldCron = ct.getCronExpression();
            if (!StringUtils.equalsIgnoreCase(oldCron, cronExpression)) {
                //修改时间
                ct.setCronExpression(cronExpression);
                //重启触发器
                sf.getScheduler().resumeTrigger(TriggerKey.triggerKey(triggerName, triggerGroupName));
            }
        }
    }

    /**
     * 移除一个任务和触发器(使用默认的任务组名，触发器名，触发器组名)
     */
    public static void removeJob(String jobName, String triggerName)
            throws SchedulerException {
        removeJob(jobName, null, triggerName, null);
    }

    /**
     * 移除一个任务和触发器
     */
    public static void removeJob(String jobName, String jobGroupName,
                                 String triggerName, String triggerGroupName)
            throws SchedulerException {
        if (StringUtils.isBlank(jobGroupName)) {
            jobGroupName = JOB_GROUP_NAME;
        }
        if (StringUtils.isBlank(triggerGroupName)) {
            triggerGroupName = TRIGGER_GROUP_NAME;
        }
        Scheduler sched = sf.getScheduler();

        //停止触发器
        sched.pauseTrigger(TriggerKey.triggerKey(triggerName, triggerGroupName));

        //移除触发器
        sched.unscheduleJob(TriggerKey.triggerKey(triggerName, triggerGroupName));

        //删除任务
        sched.deleteJob(JobKey.jobKey(jobName, jobGroupName));
    }


    public static void main(String[] args) throws SchedulerException, ParseException {
//      addJob("test", new TestJob(), "*/5 * * * * ?");
//      addJob("zht", new TestJob(), "*/10 * * * * ?");
//      removeJob("myJob","myJobGroup", "myTrigger","myTriggerGroup");
        removeJob("test", "test");
        removeJob("zht", "zht");

    }
}
