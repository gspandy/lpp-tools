/**
* 文件名：ImgCodeBuilderTest.java
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

import java.io.IOException;

import org.junit.Test;
import lpp.tools.comm.RandomUtils;
import lpp.tools.io.FileUtils;

/**
 * 功能描述：各种码单元测试
 */
public class CodeBuilderTest {

    @Test
    public void testImgCodeBuilder() throws IOException {
        System.out.println("testImgCodeBuilder start......");
        ImgCodeBuilder builder = new ImgCodeBuilder();
        FileUtils.write("C:\\Users\\lipanpan\\Desktop\\img.jpeg", builder.build(RandomUtils.getRandomCode(4)));
        System.out.println("testImgCodeBuilder end......");
    }

    @Test
    public void testQrCodeBuilder() throws IOException {
        System.out.println("testQrCodeBuilder start......");
        QrCodeBuilder builder = new QrCodeBuilder();
        final String content = "https://www.pandan.ren/";
        builder.setWidth(50).setHeight(50).setFormat(ImgFormat.PNG).setContent(content).setCharset("utf-8")
            .build("C:\\Users\\lipanpan\\Desktop\\qr.png");
        System.out.println("testQrCodeBuilder end......");
    }

    @Test
    public void testQrCodePaddingBuilder() throws IOException {
        System.out.println("testQrCodePaddingBuilder start......");
        QrCodePaddingBuilder builder = new QrCodePaddingBuilder();
        final String content = "https://www.pandan.ren/";
        builder.setPadding(5).setWidth(50).setHeight(50).setFormat(ImgFormat.PNG).setContent(content)
            .setCharset("utf-8").build("C:\\Users\\lipanpan\\Desktop\\qrpadding.png");
        System.out.println("testQrCodePaddingBuilder end......");
    }

    @Test
    public void testLogoQrCodeBuilder() throws IOException {
        System.out.println("testLogoQrCodeBuilder start......");
        LogoQrCodeBuilder builder = new LogoQrCodeBuilder();
        final String content = "https://www.pandan.ren/";
        builder.setLogoPath("C:\\Users\\lipanpan\\Desktop\\logo.png").setPadding(5).setWidth(100).setHeight(100)
            .setFormat(ImgFormat.PNG).setContent(content).setCharset("utf-8")
            .build("C:\\Users\\lipanpan\\Desktop\\qrlogo.png");
        System.out.println("testLogoQrCodeBuilder end......");
    }
}
