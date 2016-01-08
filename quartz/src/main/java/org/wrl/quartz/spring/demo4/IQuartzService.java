package org.wrl.quartz.spring.demo4;

import org.quartz.JobDetail;
import org.quartz.Trigger;
import org.springframework.data.domain.Page;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 抽象Quartz操作接口(实现类 toSee: QuartzServiceImpl)
 * Created by Administrator on 2016/1/8.
 */
public interface IQuartzService {
    /**
     * 获取所有trigger
     * @param page
     * @param orderName
     * @param sortType
     * @return
     */
    List<Map<String, Object>> getQrtzTriggers(Page page, String orderName, String sortType);

    /**
     * 获取所有jobDetail
     * @return
     */
    List<Map<String, Object>> getQrtzJobDetails();

    /**
     * 执行Trigger操作
     * @param name
     * @param group
     * @param action
     * <br/>
     */
    void executeTriggerAction(String name, String group, Integer action);

    /**
     * 执行JobDetail操作
     *
     * @param name
     * @param group
     * @param action
     * <br/>
     */
    void executeJobAction(String name, String group, Integer action);

    /**
     * 动态添加trigger
     *
     * @param jobName
     * @param jobGroup
     * @param triggerVo
     */
    void addTrigger(String jobName, String jobGroup, TriggerVo triggerVo);
    /**
     * 定时执行任务
     *
     * @param jobDetail
     * @param date
     */

    void addTriggerForDate(JobDetail jobDetail, String triggerName , String
            triggerGroup , Date date, Map<String, Object> triggerDataMap) ;
    /**
     * 获取分布式Scheduler列表
     *
     * @return
     */
    List<Map<String, Object>> getSchedulers();
    /**
     * 获取触发器
     * @param name
     * @param group
     * @return
     */
    public Trigger getTrigger(String name, String group);
    /**
     * 获取JobDetail
     * @param name
     * @param group
     * @return
     */
    public JobDetail getJobDetail(String name, String group);
}
