/**
* 文件名：CodeParserTest.java
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

/**
 * 功能描述：二维码解析
 */
public class CodeParserTest {

    @Test
    public void testQrCodeParser() throws IOException {
        System.out.println("testQrCodeParser start......");
        QrCodeParser parser = new QrCodeParser("C:\\Users\\lipanpan\\Desktop\\qr.png");
        System.out.println("testQrCodeParser result = " + parser.setCharset("utf-8").parse());
    }
}
