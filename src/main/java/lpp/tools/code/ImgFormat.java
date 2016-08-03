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

/**
 * 功能描述：图片格式
 */
public enum ImgFormat {

    PNG("png"), JPEG("jpeg"), JPG("jpg"), GIF("gif");

    private String value = null;

    private ImgFormat(String v)
    {
        this.value = v;
    }

    @Override
    public String toString()
    {
        return this.value;
    }
}
