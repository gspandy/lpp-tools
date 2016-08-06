/**
 * 文件名：ImgFormat.java
 * 创建日期： 2016年7月31日
 * 作者：     lipanpan
 * Copyright (c) 2009-2011 无线开发室
 * All rights reserved.
 
 * 修改记录：
 * 	1.修改时间：2016年7月31日
 *   修改人：lipanpan
 *   修改内容：
 */
package lpp.tools.code;

import java.io.File;

import lpp.tools.comm.AssertUtils;

/**
 * 功能描述：图片格式
 */
public enum ImgFormat {

    PNG("png"), JPEG("jpeg"), JPG("jpg"), GIF("gif");

    private String value = null;

    private ImgFormat(String v) {
        this.value = v;
    }

    /***
     * 解析图片类型
     * @param file
     * @return
     */
    public static ImgFormat parseImgFormat(String filePath) {
        AssertUtils.isBlank(filePath, "filePath param is invalid.");
        int suffixIndex = filePath.lastIndexOf(".");
        if (suffixIndex < -1) { return null; }
        String suffix = filePath.substring(suffixIndex + 1);
        if (PNG.toString().equalsIgnoreCase(suffix))
        {
            return ImgFormat.PNG;
        } else if (JPEG.toString().equalsIgnoreCase(suffix))
        {
            return ImgFormat.JPEG;
        } else if (JPG.toString().equalsIgnoreCase(suffix))
        {
            return ImgFormat.JPG;
        } else if (GIF.toString().equalsIgnoreCase(suffix))
        {
            return ImgFormat.GIF;
        } else
        {
            return null;
        }
    }

    /***
     * 解析图片类型
     * @param file
     * @return
     */
    public static ImgFormat parseImgFormat(File file) {
        AssertUtils.isNull(file, "file param is invalid.");
        return parseImgFormat(file.getPath());
    }

    @Override
    public String toString() {
        return this.value;
    }

}
