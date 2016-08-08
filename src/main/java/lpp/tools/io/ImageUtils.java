/**
* 文件名：ImageUtils.java
* 创建日期： 2016年8月6日
* 作者：     lipanpan
* Copyright (c) 2009-2011 无线开发室
* All rights reserved.
 
* 修改记录：
* 	1.修改时间：2016年8月6日
*   修改人：lipanpan
*   修改内容：
*/
package lpp.tools.io;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;

import javax.imageio.ImageIO;

import lpp.tools.code.ImgFormat;
import lpp.tools.comm.StringUtils;

/**
 * 功能描述：图片工具类
 */
public abstract class ImageUtils {

    /***
     * 缩放图片资源
     * @param srcPath 源图片地址
     * @param fileName 目标文件名称
     * @param scaleRatio 缩放比例0.0 - n
     * @throws IOException 
     */
    public static void scaleImg(String srcPath, String fileName, float scaleRatio) throws IOException {
        if (StringUtils.isBlank(srcPath) || scaleRatio < 0) { return; }
        File srcFile = new File(srcPath);
        BufferedImage im = scaleBufferImage(ImageIO.read(srcFile), scaleRatio);
        final String imgFormat = ImgFormat.parseImgFormat(srcFile).toString();
        fileName = ImgFormat.parseImgFormat(fileName) != null ? fileName : fileName + "." + imgFormat;
        ImageIO.write(im, imgFormat, new File(srcFile.getParent() + File.separator + fileName));
    }

    /***
     * 缩放图片资源
     * @param srcPath
     * @param scaleRatio
     * @return 
     * @throws IOException 
     */
    public static byte[] scaleImg(String srcPath, float scaleRatio) throws IOException {
        if (StringUtils.isBlank(srcPath) || scaleRatio < 0) { return null; }
        BufferedImage im = scaleBufferImage(ImageIO.read(new File(srcPath)), scaleRatio);
        OutputStream out = new ByteArrayOutputStream();
        ImageIO.write(im, ImgFormat.parseImgFormat(srcPath).toString(), out);
        return ((ByteArrayOutputStream) out).toByteArray();
    }

    /***
     * 图片资源缩放
     * @param original
     * @param scaleRatio
     * @return
     */
    public static BufferedImage scaleBufferImage(BufferedImage original, float scaleRatio) {
        int destWidth = (int) (original.getWidth() * scaleRatio);
        int destHeight = (int) (original.getHeight() * scaleRatio);

        BufferedImage im = new BufferedImage(destWidth, destHeight, original.getType());
        Graphics g = im.getGraphics();
        g.drawImage(original, 0, 0, destWidth, destHeight, null);
        g.dispose();

        return im;
    }
}
