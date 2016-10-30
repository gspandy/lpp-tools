/**
* 文件名：NamedThreadFactory.java
* 创建日期： 2016年10月30日
* 作者：     lipanpan
* Copyright (c) 2009-2011 无线开发室
* All rights reserved.
 
* 修改记录：
* 	1.修改时间：2016年10月30日
*   修改人：lipanpan
*   修改内容：
*/
package lpp.tools.comm;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 功能描述：自定义线程工厂
 */
public class NamedThreadFactory implements ThreadFactory {

    /**全局线程工厂编号*/
    private static final AtomicInteger POOL_SEQ = new AtomicInteger(1);

    /**线程编号*/
    private final AtomicInteger mThreadNum = new AtomicInteger(1);

    /**线程名称前缀*/
    private final String mPrefix;

    /**是否已守护线程启动线程*/
    private final boolean mDaemo;

    /**所属线程组*/
    private final ThreadGroup mGroup;

    public NamedThreadFactory() {
        this("pool-" + POOL_SEQ.getAndIncrement(), false);
    }

    public NamedThreadFactory(String prefix) {
        this(prefix, false);
    }

    public NamedThreadFactory(String prefix, boolean daemo) {
        mPrefix = prefix + "-thread-";
        mDaemo = daemo;
        SecurityManager s = System.getSecurityManager();
        mGroup = (s == null) ? Thread.currentThread().getThreadGroup() : s.getThreadGroup();
    }

    public Thread newThread(Runnable runnable) {
        String name = mPrefix + mThreadNum.getAndIncrement();
        Thread ret = new Thread(mGroup, runnable, name, 0);
        ret.setDaemon(mDaemo);
        return ret;
    }

    public ThreadGroup getThreadGroup() {
        return mGroup;
    }

}
