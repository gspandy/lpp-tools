/**
* 文件名：ZipUtilsTest.java
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

import java.io.IOException;

import org.junit.Test;

/**
 * 功能描述：Zip解压缩工具单元测试
 */
public class ZipUtilsTest {

    /***
     * 压缩目录
     * @throws IOException
     */
    @Test
    public void zipDir() throws IOException {
        ZipUtils.zip("C:/Users/lipanpan/Desktop/JFinal学习");
    }
    
    /***
     * 加密zip文件
     * @throws IOException
     */
    @Test
    public void encryptZipDir() throws IOException {
        ZipUtils.encryptZipFile("C:/Users/lipanpan/Desktop/JFinal学习.zip");
    }
    
    /***
     * 解密zip文件
     * @throws IOException
     */
    @Test
    public void decryptZipDir() throws IOException {
        ZipUtils.decryptZipFile("C:/Users/lipanpan/Desktop/JFinal学习.zip");
    }

    /***
     * 压缩文件
     * @throws IOException
     */
    @Test
    public void zipFile() throws IOException {
        ZipUtils.zip("C:/Users/lipanpan/Desktop/simple-bean-1.0.jar");
    }

}
