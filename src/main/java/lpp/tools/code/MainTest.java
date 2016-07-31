/**
* 文件名：MainTest.java
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

import java.io.IOException;

import lpp.tools.comm.RandomUtils;
import lpp.tools.io.FileUtils;

/**
 * 功能描述：
 */
public class MainTest {

    /**
     * @param args
     * @throws IOException 
     */
    public static void main(String[] args) throws IOException {
        ImgCodeBuilder builder = new ImgCodeBuilder();
        FileUtils.write("C:\\Users\\lipanpan\\Desktop\\code.jpeg", builder.build(RandomUtils.getRandomCode(4)));
    }
}
