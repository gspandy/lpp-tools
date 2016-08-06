/**
* 文件名：LogoQrCodeBuilder.java
* 创建日期： 2016年8月6日
* 作者：     lipanpan
* Copyright (c) 2009-2011 无线开发室
* All rights reserved.
 
* 修改记录：
* 	1.修改时间：2016年8月6日
*   修改人：lipanpan
*   修改内容：
*/
package lpp.tools.code;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import lpp.tools.comm.ImageUtils;

import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

/**
 * 功能描述：二维码logo构建器
 */
public class LogoQrCodeBuilder extends QrCodePaddingBuilder {

    /**logo图标路径*/
    protected String logoPath = null;

    public LogoQrCodeBuilder() {
        // 设置二维码高容错级别
        this.level = ErrorCorrectionLevel.H;
    }

    public String getLogoPath() {
        return logoPath;
    }

    public LogoQrCodeBuilder setLogoPath(String logoPath) {
        this.logoPath = logoPath;
        return this;
    }

    /***
     * logo的宽度和高度在二维码的要小于1/5，防止识别不出来
     */
    @Override
    protected BufferedImage toBufferedImage(BitMatrix matrix) {
        BufferedImage matrixImage = super.toBufferedImage(matrix);
        try
        {
            BufferedImage logoImage = ImageIO.read(new File(this.logoPath));
            Graphics2D g = matrixImage.createGraphics();
            int matrixWidth = matrixImage.getWidth();
            int matrixHeight = matrixImage.getHeight();
            int logoWidth = logoImage.getWidth();
            int logoHeight = logoImage.getHeight();
            int destLogoWidth = Math.min(logoWidth, matrixWidth / 5);
            int destLogoHeight = Math.min(logoHeight, matrixHeight / 5);
            float scaleWidthRatio = (float) ((destLogoWidth * 1.0) / logoWidth);
            float scaleHeightRatio = (float) ((destLogoHeight * 1.0) / logoHeight);
            float scaleRatio = Math.min(scaleWidthRatio, scaleHeightRatio);
            logoImage = ImageUtils.scaleBufferImage(logoImage, scaleRatio);
            int x = (matrixWidth - destLogoWidth) / 2;
            int y = (matrixHeight - destLogoHeight) / 2;
            // 绘制logo
            g.drawImage(logoImage, x, y, destLogoWidth, destLogoHeight, null);
        } catch (IOException e)
        {}
        return matrixImage;
    }
}
