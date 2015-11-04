package com.bluejean.modules.log;

import com.bluejean.modules.utils.LogParent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by wrl on 2015/7/30.
 */
public class LogChild extends LogParent{
    protected Logger logger = LoggerFactory.getLogger(LogChild.class);
    public static void main(String[] args) {
        LogChild logChild = new LogChild();
        logChild.maind();
    }
    public void maind() {
        logger.trace("trace message");
        logger.debug("debug message");
        logger.info("info message");
        logger.warn("warn message");
        logger.error("error message");
//        logger.fatal("fatal message");
        System.out.println("Hello World!");
    }
}
