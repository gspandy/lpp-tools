/**
* 文件名：GzipUtils.java
* 创建日期： 2016年7月26日
* 作者：     lipanpan
* Copyright (c) 2009-2011 无线开发室
* All rights reserved.
 
* 修改记录：
* 	1.修改时间：2016年7月26日
*   修改人：lipanpan
*   修改内容：
*/
package lpp.tools.io;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

/**
 * 功能描述：gzip解压缩
 */
public abstract class GzipUtils {

    /**
     * gzip压缩
     * @param src
     * @return
     */
    public static byte[] compress(byte[] src) throws IOException {
        if (src == null) { return null; }
        ByteArrayInputStream srcIn = new ByteArrayInputStream(src);
        ByteArrayOutputStream destOut = new ByteArrayOutputStream();
        compress(srcIn, destOut);
        return destOut.toByteArray();
    }

    /**
     * gzip压缩输出流
     * @param src
     * @param out
     * @throws IOException 
     */
    public static void compress(byte[] src, OutputStream out) throws IOException {
        if (src == null || out == null) { return; }
        ByteArrayInputStream in = new ByteArrayInputStream(src);
        compress(in, out);
    }

    /**
     * gizp压缩并关闭输入输出流
     * @param in
     * @param out
     */
    public static void compress(InputStream in, OutputStream out) throws IOException {
        if (in == null || out == null) { return; }
        GZIPOutputStream gout = new GZIPOutputStream(out);
        try
        {
            int len = -1;
            byte[] buff = new byte[1024];
            while ((len = in.read(buff)) != -1)
            {
                gout.write(buff, 0, len);
            }
            gout.finish();
            gout.flush();
        } finally
        {
            StreamUtils.close(in);
            StreamUtils.close(gout);
        }
    }

    /***
     * gzip解压缩
     * @param src
     * @return
     */
    public static byte[] decompress(byte[] src) throws IOException {
        if (src == null) { return null; }
        GZIPInputStream gIn = new GZIPInputStream(new ByteArrayInputStream(src));
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        try
        {
            int len = -1;
            byte[] buff = new byte[1024];
            while ((len = gIn.read(buff)) != -1)
            {
                out.write(buff, 0, len);
            }
            out.flush();
            return out.toByteArray();
        } finally
        {
            StreamUtils.close(gIn);
            StreamUtils.close(out);
        }
    }

}
