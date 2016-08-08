/**
* 文件名：QrCodeParser.java
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

import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;

import com.google.zxing.Binarizer;
import com.google.zxing.BinaryBitmap;
import com.google.zxing.DecodeHintType;
import com.google.zxing.LuminanceSource;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.NotFoundException;
import com.google.zxing.Result;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.common.HybridBinarizer;

import lpp.tools.io.StreamUtils;

/**
 * 功能描述：二维码解析器
 */
public class QrCodeParser {

    /**内容编解码*/
    protected String charset = "utf-8";

    /**二维码输入流*/
    private InputStream in = null;

    public String getCharset() {
        return charset;
    }

    public QrCodeParser setCharset(String charset) {
        this.charset = charset;
        return this;
    }

    public QrCodeParser(String path) {
        try
        {
            in = new BufferedInputStream(new FileInputStream(new File(path)));
        } catch (FileNotFoundException e)
        {}
    }

    public QrCodeParser(byte[] data) {
        in = new ByteArrayInputStream(data);
    }

    /***
     * 解析二维码
     * @return
     * @throws IOException 
     */
    public String parse() throws IOException {
        if (in == null) { return null; }
        MultiFormatReader formatReader = new MultiFormatReader();
        BufferedImage image = ImageIO.read(in);
        LuminanceSource source = new BufferedImageLuminanceSource(image);
        Binarizer binarizer = new HybridBinarizer(source);
        BinaryBitmap binaryBitmap = new BinaryBitmap(binarizer);
        Map<DecodeHintType, Object> hints = new HashMap<DecodeHintType, Object>();
        hints.put(DecodeHintType.CHARACTER_SET, this.charset);
        Result result = null;
        try
        {
            result = formatReader.decode(binaryBitmap, hints);
            return result.getText();
        } catch (NotFoundException e)
        {
            result = null;
            StreamUtils.close(in);
        }
        return null;
    }
}
