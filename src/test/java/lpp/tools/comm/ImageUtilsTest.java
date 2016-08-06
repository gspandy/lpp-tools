/**
* 文件名：ImageUtilsTest.java
* 创建日期： 2016年8月6日
* 作者：     lipanpan
* Copyright (c) 2009-2011 无线开发室
* All rights reserved.
 
* 修改记录：
* 	1.修改时间：2016年8月6日
*   修改人：lipanpan
*   修改内容：
*/
package lpp.tools.comm;

import java.io.IOException;

import org.junit.Test;

/**
 * 功能描述：
 *
 */
public class ImageUtilsTest {

    @Test
    public void scalelittle() throws IOException {
        System.out.println("scalelittle start......");
        ImageUtils.scaleImg("C:\\Users\\lipanpan\\Desktop\\qrlogo.png", "scalelittle.png", 0.5f);
        System.out.println("scalelittle end......");
    }

    @Test
    public void scalebig() throws IOException {
        System.out.println("scalebig start......");
        ImageUtils.scaleImg("C:\\Users\\lipanpan\\Desktop\\qrlogo.png", "scalebig", 2.0f);
        System.out.println("scalebig end......");
    }

}
