package com.shiliangxu.network.util;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/*******************************************************************************************************
 * Copyright © 2018 ShiliangXu. HIT .College of Computer Science and Technology. All rights reserved.
 * @Package: com.xushiliang.network.util
 * @author: ShiliangXu
 * @date: 2018/11/2 10:14
 * @Description: 日志工具
 *******************************************************************************************************/
public class LoggerUtil {

    private final static Logger logger = LoggerFactory.getLogger(LoggerUtil.class);

    //输出日志的方法1：详细输出
    public static void logx(String className,String message){
//        logger.info("\n-----------------------------------------------------------------@Shiliang Xu-----------------------------------------------------------------\n\n" +
//                "【"+className+"】:"+message+
//                "\n\n-----------------------------------------------------------------@Shiliang Xu-----------------------------------------------------------------\n");

    }

    //输出日志的方法
    public static void logx(String className,String methodName,String message){
//        logger.info("\n-----------------------------------------------------------------@Shiliang Xu-----------------------------------------------------------------\n\n" +
//                "【"+className+"/"+methodName+"】:"+message+
//                "\n\n-----------------------------------------------------------------@Shiliang Xu-----------------------------------------------------------------\n");
    }

    //普通输出的方法
    public static void Systemx(String message){
//        System.out.println(message);
    }

    //普通输出的方法
    public static void Systemy(String message){
//        System.out.println(message);
    }
}

