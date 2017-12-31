package com.Marissa.FAQ.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LogUtil {
    private static Logger logger = LoggerFactory.getLogger(LogUtil.class);

    public static void info(String str){
        logger.info(str);
    }

    public static void debug(String str){
        logger.debug(str);
    }

    public static void warn(String str){
        logger.warn(str);
    }

    public static void trace(String str){
        logger.trace(str);
    }

    public static void error(String str){
        logger.error(str);
    }

}
