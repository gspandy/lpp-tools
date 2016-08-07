/**
* 文件名：ImgCodeBgBuilder.java
* 创建日期： 2016年8月7日
* 作者：     lipanpan
* Copyright (c) 2009-2011 无线开发室
* All rights reserved.
 
* 修改记录：
* 	1.修改时间：2016年8月7日
*   修改人：lipanpan
*   修改内容：
*/
package lpp.tools.code;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import lpp.tools.comm.AssertUtils;
import lpp.tools.comm.ImageUtils;

/**
 * 功能描述：含背景图片验证码
 */
public class ImgCodeBgBuilder extends ImgCodeBuilder {

    /**背景图片地址*/
    protected String bgImgPath = null;

    public String getBgImgPath() {
        return bgImgPath;
    }

    public ImgCodeBgBuilder setBgImgPath(String bgImgPath) {
        this.bgImgPath = bgImgPath;
        return this;
    }

    /***
     * 重写背景色构建方法
     */
    @Override
    protected BufferedImage buildBgImg() throws IOException {
        File bgFile = AssertUtils.isFile(bgImgPath, "bgImgPath is invalid file.");
        BufferedImage bgImg = ImageIO.read(bgFile);
        float widthRatio = (float) ((this.width * 1.0) / bgImg.getWidth());
        float heightRatio = (float) ((this.height * 1.0) / bgImg.getHeight());
        float scaleRatio = Math.min(widthRatio, heightRatio);
        return ImageUtils.scaleBufferImage(bgImg, scaleRatio);
    }
}
