package com.bluejean.modules.utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.invoke.MethodHandles;

/**
 * Created by wrl on 2015/7/30.
 */
public class LogParent {
//    protected static Logger logger = LogManager.getLogger(LogManager.ROOT_LOGGER_NAME);
//    protected Logger logger = LogManager.getLogger(MethodHandles.lookup().lookupClass());
    protected Logger logger = LoggerFactory.getLogger(LogParent.class);

    public static void main(String[] args) {
//        logger.trace("trace message");
//        logger.debug("debug message");
//        logger.info("info message");
//        logger.warn("warn message");
//        logger.error("error message");
//        logger.fatal("fatal message");
//        System.out.println("Hello World!");
    }
}
