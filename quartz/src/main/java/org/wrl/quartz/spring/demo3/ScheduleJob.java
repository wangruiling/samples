package org.wrl.quartz.spring.demo3;

import java.io.Serializable;

/**
 * 任务对应实体类
 * @author: wangrl
 * @Date: 2016-01-08 13:12
 */
public class ScheduleJob implements Serializable {
    /** 任务id **/
    private String jobId;

    /** 任务名称 **/
    private String jobName;

    /** 任务分组 **/
    private String jobGroup;

    /** 触发器名 */
    //@Column(name="TRIGGER_NAME",nullable=false)
    private String triggerName;

    /** 触发器组名 */
    //@Column(name="TRIGGER_GROUP_NAME",nullable=false)
    private String triggerGroupName;

    /** 任务 */
    //@Column(name="JOB",nullable=false)
    private String job;

    /** 任务运行时间表达式 **/
    private String cronExpression;

    /** 任务状态 0-未启动 1-启动 2-暂停 3-停止 4-删除 **/
    private String jobStatus;

    /** 备注 */
    //@Column(name="ISDELETE",nullable=false)
    private Integer isdelete = 0;

    /** 任务描述 **/
    private String desc;

    public String getJobId() {
        return jobId;
    }

    public void setJobId(String jobId) {
        this.jobId = jobId;
    }

    public String getJobName() {
        return jobName;
    }

    public void setJobName(String jobName) {
        this.jobName = jobName;
    }

    public String getJobGroup() {
        return jobGroup;
    }

    public void setJobGroup(String jobGroup) {
        this.jobGroup = jobGroup;
    }

    public String getJobStatus() {
        return jobStatus;
    }

    public void setJobStatus(String jobStatus) {
        this.jobStatus = jobStatus;
    }

    public String getCronExpression() {
        return cronExpression;
    }

    public void setCronExpression(String cronExpression) {
        this.cronExpression = cronExpression;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
