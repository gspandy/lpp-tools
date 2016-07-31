/**
 * 文件名：CguidUtil.java
 * 创建日期： 2015年3月10日
 * 作者：     lipanpan
 * Copyright (c) 2009-2011 无线开发室
 * All rights reserved.
 
 * 修改记录：
 * 	1.修改时间：2015年3月10日
 *   修改人：lipanpan
 *   修改内容：
 */
package lpp.tools.comm;

import java.util.UUID;

/** 功能描述：全局唯一编号生成工具(跟mac关联+当前时间) */
public abstract class UuidUtils {

    public static String uuid() {
        String uuid = UUID.randomUUID().toString();
        return uuid.replaceAll("-", "");
    }

    public static void main(String[] args) {
        System.out.println(uuid().length());
        System.out.println(uuid());
    }

}
