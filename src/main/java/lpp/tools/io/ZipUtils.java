/**
 * 文件名：ZipUtils.java
 * 创建日期： 2016年7月31日
 * 作者：     lipanpan
 * Copyright (c) 2009-2011 无线开发室
 * All rights reserved.
 
 * 修改记录：
 * 	1.修改时间：2016年7月31日
 *   修改人：lipanpan
 *   修改内容：
 */
package lpp.tools.io;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import lpp.tools.comm.StringUtils;

/**
 * 功能描述：zip解压缩
 */
public abstract class ZipUtils
{

    /** zip文件标准文件头 */
    private final static byte[] ZIP_HEADER_FORMAT = { 0x50, 0x4B, 0x03, 0x04 };

    /***
     * 压缩文件或文件夹
     * @param filePath
     * @throws IOException
     */
    public static void zip(String filePath) throws IOException
    {
        if (StringUtils.isBlank(filePath))
        {
            return;
        }
        File file = new File(filePath);
        if (!file.exists())
        {
            return;
        }

        ZipOutputStream out = null;
        BufferedOutputStream bo = null;
        try
        {
            String zipFilePath = file.getParent() + File.separator;
            if (file.isDirectory())
            {
                zipFilePath = zipFilePath + file.getName();
            }
            else
            {
                String name = file.getName();
                int suffixIndex = name.lastIndexOf(".");
                if (suffixIndex > -1)
                {
                    name = name.substring(0, suffixIndex);
                }
                zipFilePath = zipFilePath + name;
            }
            zipFilePath = zipFilePath + ".zip";
            out = new ZipOutputStream(new FileOutputStream(zipFilePath));
            bo = new BufferedOutputStream(out);
            _zip(out, file, file.getName(), bo);
        }
        finally
        {
            StreamUtils.close(bo);
            StreamUtils.close(out);
        }
    }

    private static void _zip(ZipOutputStream out, File f, String base, BufferedOutputStream bo) throws IOException
    {
        if (f.isDirectory())
        {
            File[] fl = f.listFiles();
            if (fl.length == 0)
            {
                out.putNextEntry(new ZipEntry(base + File.separator)); // 创建zip压缩进入点base
            }
            for (int i = 0; i < fl.length; i++)
            {
                _zip(out, fl[i], base + File.separator + fl[i].getName(), bo); // 递归遍历子文件夹
            }
        }
        else
        {
            out.putNextEntry(new ZipEntry(base)); // 创建zip压缩进入点base
            FileInputStream in = null;
            BufferedInputStream bi = null;
            try
            {
                in = new FileInputStream(f);
                bi = new BufferedInputStream(in);
                int b;
                while ((b = bi.read()) != -1)
                {
                    bo.write(b);
                }
            }
            finally
            {
                StreamUtils.close(in);
                StreamUtils.close(bi);
            }
        }
    }

    /***
     * 解压zip文件
     * @param filePath
     */
    public static void unzip(String filePath) throws IOException
    {
        if (StringUtils.isBlank(filePath))
        {
            return;
        }

    }

    /***
     * 加密zip文件
     * @param filePath
     * @throws IOException
     */
    public static void encryptZipFile(String filePath) throws IOException
    {
        // 去除文件头（也可以自定义字节，此处为zip文件标准文件头）
        File srcFile = new File(filePath);
        File tempFile = new File(filePath + ".temp");
        InputStream fis = null;
        OutputStream fos = null;
        try
        {
            fis = new BufferedInputStream(new FileInputStream(srcFile));
            fos = new FileOutputStream(tempFile);
            int len = 0;
            byte[] b = new byte[1024];
            fis.read(new byte[ZIP_HEADER_FORMAT.length]);// 去掉文件头加密
            while ((len = fis.read(b)) > 0)
            {
                fos.write(b, 0, len);
            }
            srcFile.delete();
            tempFile.renameTo(srcFile);
        }
        finally
        {
            StreamUtils.close(fis);
            StreamUtils.close(fos);
        }
    }

    /***
     * 解密Zip文件
     * @param filePath
     * @throws IOException
     */
    public static void decryptZipFile(String filePath) throws IOException
    {
        // 补上文件头（也可以自定义字节，此处为zip文件标准文件头）
        File srcFile = new File(filePath);
        File tempFile = new File(filePath + ".temp");
        InputStream fis = null;
        OutputStream fos = null;
        try
        {
            fis = new BufferedInputStream(new FileInputStream(srcFile));
            fos = new FileOutputStream(tempFile);
            int len = 0;
            byte[] b = new byte[1024];
            fos.write(ZIP_HEADER_FORMAT);// 补上文件头
            while ((len = fis.read(b)) > 0)
            {
                fos.write(b, 0, len);
            }
            srcFile.delete();
            tempFile.renameTo(srcFile);
        }
        finally
        {
            StreamUtils.close(fis);
            StreamUtils.close(fos);
        }
    }

}
