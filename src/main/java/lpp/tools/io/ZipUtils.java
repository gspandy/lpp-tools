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
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import lpp.tools.comm.StreamUtils;
import lpp.tools.comm.StringUtils;

/**
 * 功能描述：zip解压缩
 */
public abstract class ZipUtils {

    /***
     * 压缩文件或文件夹
     * @param filePath
     * @throws IOException 
     */
    public static void zip(String filePath) throws IOException {
        if (StringUtils.isBlank(filePath)) { return; }
        File file = new File(filePath);
        if (!file.exists()) { return; }

        ZipOutputStream out = null;
        BufferedOutputStream bo = null;
        try
        {
            String zipFilePath = file.getParent() + File.separator;
            if (file.isDirectory())
            {
                zipFilePath = zipFilePath + file.getName();
            } else
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
        } finally
        {
            StreamUtils.close(bo);
            StreamUtils.close(out);
        }
    }

    private static void _zip(ZipOutputStream out, File f, String base, BufferedOutputStream bo) throws IOException {
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
        } else
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
            } finally
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
    public static void unzip(String filePath) throws IOException {
        if (StringUtils.isBlank(filePath)) { return; }

    }

    /***
     * 安全压缩
     * @param filePath
     * @throws IOException
     */
    public static void safeZip(String filePath) throws IOException {

    }

    /***
     * 安全解压缩
     * @param filePath
     * @throws IOException
     */
    public static void safeUnzip(String filePath) throws IOException {

    }

}
