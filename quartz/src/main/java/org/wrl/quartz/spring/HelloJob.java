package org.wrl.quartz.spring;

import java.time.LocalDateTime;

/**
 * 创建一个简单的任务调度
 * @author: wangrl
 * @Date: 2016-01-06 16:49
 */
public class HelloJob {
    private String name;

    /**
     * 这个方法中是我们需要任务调度执行的具体内容
     */
    public void execute() {
        //JobDataMap jobDataMap = jobExecutionContext.getMergedJobDataMap();
        //String name = jobDataMap.getString("name");
        System.out.println("hello " + name + ", " + LocalDateTime.now());
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
