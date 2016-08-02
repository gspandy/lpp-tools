/**
 * 文件名：CollectionUtils.java
 * 创建日期： 2015年11月28日
 * 作者：     User
 * Copyright (c) 2009-2011 无线开发室
 * All rights reserved.
 
 * 修改记录：
 * 	1.修改时间：2015年11月28日
 *   修改人：User
 *   修改内容：
 */
package lpp.tools.comm;

import java.util.Collection;

/**
 * 功能描述：集合工具类
 */
public abstract class CollectionUtils {

    public static boolean isNull(Collection<?> c) {
        return null == c;
    }

    public static boolean isEmpty(Collection<?> c) {
        if (null == c || c.isEmpty()) { return true; }
        return false;
    }

    public static boolean isNotEmpty(Collection<?> c) {
        return !isEmpty(c);
    }
}