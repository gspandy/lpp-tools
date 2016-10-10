/**
* 文件名：UnsafeUtils.java
* 创建日期： 2016年10月7日
* 作者：     lipanpan
* Copyright (c) 2009-2011 无线开发室
* All rights reserved.
 
* 修改记录：
* 	1.修改时间：2016年10月7日
*   修改人：lipanpan
*   修改内容：
*/
package lpp.tools.comm;

import java.lang.reflect.Field;

import sun.misc.Unsafe;

public final class UnsafeUtils {
    
    private static volatile Unsafe instance = null;
    
    /**
     * 获取Unsafe类
     * @return
     */
    public static Unsafe getUnsafe() {
        if(instance == null){
            synchronized (UnsafeUtils.class)
            {
                if(instance == null){
                    try
                    {
                        Field f = Unsafe.class.getDeclaredField("theUnsafe");
                        f.setAccessible(true);
                        instance = (Unsafe) f.get(null);
                    } catch (Exception e)
                    {}
                }
            }
        }
        return instance;
    }

}
