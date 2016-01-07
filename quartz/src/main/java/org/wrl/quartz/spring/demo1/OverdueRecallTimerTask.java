package org.wrl.quartz.spring.demo1;

import java.time.LocalDateTime;

/**
 * 短信催还提醒任务调度
 * @author: wangrl
 * @Date: 2016-01-07 10:26
 */
public class OverdueRecallTimerTask {
    public void overdueRecall() {
        System.out.println("hello, now:" + LocalDateTime.now());
    }
}
