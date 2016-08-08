/**
* 文件名：StreamUtils.java
* 创建日期： 2016年7月22日
* 作者：     lipanpan
* Copyright (c) 2009-2011 无线开发室
* All rights reserved.
 
* 修改记录：
* 	1.修改时间：2016年7月22日
*   修改人：lipanpan
*   修改内容：
*/
package lpp.tools.io;

import java.io.Closeable;
import java.io.IOException;

/**
 * 功能描述：流关闭工具类
 */
public abstract class StreamUtils {

    /***
     * 流关闭
     * @param c
     */
    public static void close(Closeable c) {
        if (c == null) { return; }
        try
        {
            c.close();
            c = null;
        } catch (IOException e)
        {}
    }

}
