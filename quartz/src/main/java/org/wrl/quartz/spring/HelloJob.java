package org.wrl.quartz.spring;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;

/**
 * 创建一个简单的任务调度
 * @author: wangrl
 * @Date: 2016-01-06 16:49
 */
public class HelloJob extends QuartzJobBean{
    private String name;

    @Override
    protected void executeInternal(JobExecutionContext jobExecutionContext) throws JobExecutionException {

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
