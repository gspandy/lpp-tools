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

/** 功能描述：断言工具类 */
public abstract class AssertUtils {

    public void isNull(Object obj, String throwMsg) {
        if (obj == null) { throw new IllegalArgumentException(throwMsg); }
    }

    public void isNull(Object obj) {
        isNull(obj, "param is null");
    }

}
