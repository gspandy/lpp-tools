/**
 * 文件名：FileUtils.java
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

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.RandomAccessFile;
import java.nio.channels.FileChannel;

import lpp.tools.comm.StreamUtils;

/** 功能描述： 文件工具类 */
public abstract class FileUtils {

    /** 创建文件，如果存在则删除之前的文件，并新建文件
     * @param filePath
     * @return
     * @throws IOException */
    public static File create(String filePath) throws IOException {
        File file = new File(filePath);
        if (!file.getParentFile().exists())
        {
            file.getParentFile().mkdirs();// 逐级创建父目录
        }
        if (file.exists())
        {
            file.delete();
        }
        file.createNewFile();
        return file;
    }

    /**
     * 如果不存在，则创建，否则直接返回该文件
     * @param filePath
     * @return
     * @throws IOException
     */
    public static File createIfAbsent(String filePath) throws IOException {
        File file = new File(filePath);
        if (file.exists()) { return file; }
        return create(filePath);
    }

    /**
     * 删除目录及文件
     * @param filePath
     */
    public static void delete(String filePath) {
        delete(new File(filePath));
    }

    /**
     * 删除文件或目录
     * @param filePath
     */
    public static void delete(File file) {
        if (file == null || !file.exists()) { return; }
        // 递归删除目录及文件
        if (file.isDirectory())
        {
            File[] filelist = file.listFiles();
            for (File f : filelist)
            {
                delete(f);
            }
            file.delete();
        } else
        {
            file.delete();
        }
    }

    /**
     * 从指定文件读取数据
     * @param filePath
     * @return
     * @throws IOException 
     */
    public static byte[] read(String filePath) throws IOException {
        File file = new File(filePath);
        final boolean flag = file.exists() && file.isFile();
        if (!flag) { return null; }
        InputStream in = null;
        ByteArrayOutputStream out = null;
        try
        {
            in = new FileInputStream(file);
            out = new ByteArrayOutputStream();
            int len = 0;
            byte[] buff = new byte[1024];
            while ((len = in.read(buff)) != -1)
            {
                out.write(buff, 0, len);
            }
            return out.toByteArray();
        } finally
        {
            StreamUtils.close(in);
            StreamUtils.close(out);
        }
    }

    /**
     * 返回指定编码的字符串
     * @param filePath
     * @param charset
     * @return
     * @throws IOException
     */
    public static String read(String filePath, String charset) throws IOException {
        byte[] data = read(filePath);
        if (data == null) { return null; }
        if (charset == null)
        {
            charset = "utf-8";
        }
        return new String(data, charset);
    }

    /**
     * 向指定文件写入数据，如果已存在会覆盖已有数据
     * @param filePath
     * @param data
     * @throws IOException 
     */
    public static void write(String filePath, byte[] data) throws IOException {
        File file = createIfAbsent(filePath);
        final boolean flag = file.exists() && file.isFile();
        if (!flag) { return; }
        OutputStream out = null;
        try
        {
            out = new BufferedOutputStream(new FileOutputStream(file));
            out.write(data);
            out.flush();
        } finally
        {
            StreamUtils.close(out);
        }
    }

    /**
     * 向指定文件追加数据
     * @param filePath
     * @param data
     * @throws IOException 
     */
    public static void append(String filePath, byte[] data) throws IOException {
        RandomAccessFile randomFile = null;
        try
        {
            File file = createIfAbsent(filePath);
            randomFile = new RandomAccessFile(file, "rw");// 以读写的模式打开文件
            long fileLength = randomFile.length();
            randomFile.seek(fileLength); // 将写文件指针移到文件尾
            randomFile.write(data);
        } finally
        {
            StreamUtils.close(randomFile);
        }
    }

    /**
     * 文件及目录复制
     * @param srcPath
     * @param destPath
     * @throws IOException 
     */
    public static void copy(String srcPath, String destPath) throws IOException {
        File srcFile = new File(srcPath);
        File destFile = new File(destPath);
        copy(srcFile, destFile);
    }

    /**
     * 文件及目录复制
     * @param srcFile
     * @param destFile
     * @throws IOException 
     */
    public static void copy(File srcFile, File destFile) throws IOException {
        if (!srcFile.exists()) { return; }
        if (srcFile.isDirectory())
        {
            File dfile = new File(destFile.getPath() + File.separator + srcFile.getName());
            File[] files = srcFile.listFiles();
            for (File f : files)
            {
                copy(f, dfile);
            }
        } else
        {
            File dTempfile = create(destFile.getPath() + File.separator + srcFile.getName());
            FileChannel channelIn = null;
            FileChannel channelOut = null;
            try
            {
                channelIn = new FileInputStream(srcFile).getChannel();
                channelOut = new FileOutputStream(dTempfile).getChannel();
                // java zero-copy vs linux sendfile
                channelIn.transferTo(0, channelIn.size(), channelOut);
            } finally
            {
                StreamUtils.close(channelIn);
                StreamUtils.close(channelOut);
            }
        }
    }

    /**
     * 文件及目录移动
     * @param srcPath
     * @param destPath
     * @throws IOException 
     */
    public static void move(String srcPath, String destPath) throws IOException {
        copy(srcPath, destPath);
        delete(srcPath);
    }

    /**
     * 文件及目录重命名
     * @param filePath C:/Users/lipanpan/Desktop/test.jpg
     * @param newName test123
     */
    public static void rename(String filePath, String newName) {
        File file = new File(filePath);
        if (!file.exists()) { return; }
        if (file.isDirectory() && filePath.endsWith(File.separator))// 文件目录重命名
        {
            filePath = filePath.substring(0, filePath.lastIndexOf(File.separator));
        }
        int index = filePath.lastIndexOf(".");
        if (file.isFile() && index > -1)
        {
            newName = newName + filePath.substring(index);
        }
        final String parentPath = filePath.substring(0, filePath.lastIndexOf(File.separator));
        newName = parentPath + File.separator + newName;
        File dest = new File(newName);
        file.renameTo(dest);
    }

}
