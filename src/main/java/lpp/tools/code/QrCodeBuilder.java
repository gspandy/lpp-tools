/*
 * Copyright 1999-2016 feidee.com All right reserved. This software is the
 * confidential and proprietary information of feidee.com ("Confidential
 * Information"). You shall not disclose such Confidential Information and shall
 * use it only in accordance with the terms of the license agreement you entered
 * into with feidee.com.
 */
package lpp.tools.code;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Hashtable;

import javax.imageio.ImageIO;

import lpp.tools.comm.StreamUtils;
import lpp.tools.io.FileUtils;

import org.apache.log4j.Logger;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.common.BitMatrix;

/**
 * 实现描述：二维码构建器
 * @author lipanpan
 * @time 2016年8月3日
 */
public class QrCodeBuilder
{

    protected final Logger log = Logger.getLogger(getClass());

    /** 二维码宽度 */
    protected Integer width = 400;

    /** 二维码高度 */
    protected Integer height = 400;

    /** 二维码背景颜色：默认白色 */
    protected Integer bgColor = 0xFFFFFFFF;

    /** 二维码点颜色：默认黑色 */
    protected Integer pointColor = 0xFF000000;

    /** 二维码生成格式 */
    protected ImgFormat format = ImgFormat.JPG;

    /** 内容编解码格式，默认：utf-8 */
    protected String charset = "utf-8";

    /** 二维码写入内容 */
    protected String content = null;

    public Integer getWidth()
    {
        return width;
    }

    public QrCodeBuilder setWidth(Integer width)
    {
        this.width = width;
        return this;
    }

    public Integer getHeight()
    {
        return height;
    }

    public QrCodeBuilder setHeight(Integer height)
    {
        this.height = height;
        return this;
    }

    public Integer getBgColor()
    {
        return bgColor;
    }

    public QrCodeBuilder setBgColor(Integer bgColor)
    {
        this.bgColor = bgColor;
        return this;
    }

    public Integer getPointColor()
    {
        return pointColor;
    }

    public QrCodeBuilder setPointColor(Integer pointColor)
    {
        this.pointColor = pointColor;
        return this;
    }

    public ImgFormat getFormat()
    {
        return format;
    }

    public QrCodeBuilder setFormat(ImgFormat format)
    {
        this.format = format;
        return this;
    }

    public String getContent()
    {
        return content;

    }

    public QrCodeBuilder setContent(String content)
    {
        this.content = content;
        return this;
    }

    public String getCharset()
    {
        return charset;
    }

    public QrCodeBuilder setCharset(String charset)
    {
        this.charset = charset;
        return this;
    }

    /***
     * 转换image,后续扩展要加logo可复写此方法
     * @param matrix
     * @return
     */
    protected BufferedImage toBufferedImage(BitMatrix matrix)
    {
        int width = matrix.getWidth();
        int height = matrix.getHeight();
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        for (int x = 0; x < width; x++)
        {
            for (int y = 0; y < height; y++)
            {
                image.setRGB(x, y, matrix.get(x, y) ? pointColor : bgColor);
            }
        }
        return image;
    }

    /***
     * 生成二维码并返回字节数组
     * @param config
     * @return
     */
    public byte[] build()
    {
        try
        {
            MultiFormatWriter multiFormatWriter = new MultiFormatWriter();
            Hashtable<EncodeHintType, String> hints = new Hashtable<EncodeHintType, String>();
            hints.put(EncodeHintType.CHARACTER_SET, charset);
            BitMatrix bitMatrix = multiFormatWriter.encode(content, BarcodeFormat.QR_CODE, width, height, hints);
            BufferedImage im = toBufferedImage(bitMatrix);
            ByteArrayOutputStream output = new ByteArrayOutputStream();
            ImageIO.write(im, format.toString(), output);
            return output.toByteArray();
        }
        catch (Exception e)
        {
            log.error("QrCodeBuilder build error", e);
        }
        return null;
    }

    /***
     * 生成二维码并写入输出流
     * @param out
     * @param isClose 是否关闭out
     */
    public void build(OutputStream out, boolean isClose)
    {
        byte[] data = build();
        if (data == null)
        {
            return;
        }
        ByteArrayInputStream in = null;
        try
        {
            in = new ByteArrayInputStream(data);
            int len = -1;
            byte[] buf = new byte[1024];
            while ((len = in.read(buf)) != -1)
            {
                out.write(buf, 0, len);
            }
        }
        catch (Exception e)
        {
            log.error("QrCodeBuilder build error", e);
        }
        finally
        {
            StreamUtils.close(in);
            if (isClose)
            {
                StreamUtils.close(out);
            }
        }
    }

    /***
     * 生成二维码并写入文件
     * @param file
     */
    public void build(String filePath)
    {
        byte[] data = build();
        if (filePath == null || data == null)
        {
            return;
        }
        try
        {
            FileUtils.write(filePath, data);
        }
        catch (IOException e)
        {
            log.error("QrCodeBuilder build error", e);
        }
    }

}
