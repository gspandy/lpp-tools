package lpp.tools.io;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.lang.reflect.Method;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.FileChannel.MapMode;
import java.security.AccessController;
import java.security.PrivilegedAction;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Author:lipanpan</br>
 * Date:2016年12月6日</br>
 * Description:内存文件映射工具类</br>
 * Copyright (c) 2016 code</br> 
 */
public abstract class MappedByteBufferUtils {

    private static Logger LOGGER = LoggerFactory.getLogger(MappedByteBufferUtils.class);

    /***
     * 创建内存文件映射对象
     * @param file
     * @param size
     * @return
     * @throws IOException
     */
    public static MappedByteBuffer mmapFile(File file, long size) throws IOException {
        try (RandomAccessFile raf = new RandomAccessFile(file, "rw")) {
            raf.setLength(size);
            FileChannel fc = raf.getChannel();

            long position = 0;
            MappedByteBuffer mmap = fc.map(MapMode.READ_WRITE, position, size);
            return mmap;
        }
    }

    /***
     * 回收MappedByteBuffer对象
     * @param buffer
     */
    public static void clean(final Object buffer) {
        AccessController.doPrivileged(new PrivilegedAction<Object>() {
            public Object run() {
                try {
                    Method cleanerMethod = buffer.getClass().getMethod("cleaner", new Class[0]);
                    cleanerMethod.setAccessible(true);
                    sun.misc.Cleaner cleaner = (sun.misc.Cleaner) cleanerMethod.invoke(buffer, new Object[0]);
                    cleaner.clean();
                } catch (Exception e) {
                    LOGGER.error("cannot clean Buffer", e);
                }
                return null;
            }
        });
    }

}
