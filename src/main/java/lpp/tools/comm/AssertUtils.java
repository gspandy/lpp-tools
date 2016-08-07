/**
 * 文件名：AssertUtils.java
 * 创建日期： 2016年4月4日
 * 作者：     lipanpan
 * Copyright (c) 2009-2011 无线开发室
 * All rights reserved.
 
 * 修改记录：
 * 	1.修改时间：2016年4月4日
 *   修改人：lipanpan
 *   修改内容：
 */
package lpp.tools.comm;

import java.io.File;

/** 功能描述：断言工具类 */
public abstract class AssertUtils {

    public static void isNull(Object obj, String throwMsg) {
        if (obj == null) { throw new IllegalArgumentException(throwMsg); }
    }

    public static void isNull(Object obj) {
        isNull(obj, "param is null");
    }

    public static void isBlank(String value) {
        isBlank(value, "param is null");
    }

    public static void isBlank(String value, String throwMsg) {
        if (StringUtils.isBlank(value)) { throw new IllegalArgumentException(throwMsg); }
    }

    public static File isFile(String value) {
        return isFile(value, "param is invalid file path.");
    }

    public static File isFile(String value, String throwMsg) {
        isBlank(value, throwMsg);
        File file = new File(value);
        if (!file.exists() || file.isDirectory()) { throw new IllegalArgumentException(throwMsg); }
        return file;
    }

}
