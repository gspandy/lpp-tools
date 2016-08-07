/**
* 文件名：ImgCodeUtils.java
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

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;

import lpp.tools.comm.AssertUtils;
import lpp.tools.io.FileUtils;

/**
 * 功能描述：图片验证码生成器
 * 设计模式：建造者模式
 */
public class ImgCodeBuilder {
    private static final Integer DEFAULT_WIDTH = 170;
    private static final Integer DEFAULT_HEIGHT = 60;
    private static final ImgFormat DEFAULT_FORMAT = ImgFormat.JPEG;
    private static final Color[] DEFAULT_BG_COLOR = new Color[] { new Color(255, 230, 176), new Color(155, 247, 175),
        new Color(223, 249, 190), new Color(199, 223, 247), new Color(223, 225, 255), new Color(229, 213, 239) };
    private static final Color[] DEFAULT_FONT_COLOR = new Color[] { new Color(188, 9, 9), new Color(175, 128, 21),
        new Color(29, 78, 53), new Color(11, 42, 88), new Color(64, 29, 111), new Color(72, 10, 57) };
    private static final Color[] DEFAULT_LINE_COLOR = new Color[] { new Color(211, 211, 211), new Color(194, 194, 194) };
    /**图片宽度*/
    protected Integer width = DEFAULT_WIDTH;
    /**图片高度*/
    protected Integer height = DEFAULT_HEIGHT;
    /**待写入内容*/
    protected String content = null;
    /**图片验证码格式*/
    protected ImgFormat format = DEFAULT_FORMAT;
    /**背景颜色集*/
    protected Color[] bckColor = DEFAULT_BG_COLOR;
    /**字体颜色集*/
    protected Color[] fontColor = DEFAULT_FONT_COLOR;
    /**干扰线颜色集*/
    protected Color[] lineColor = DEFAULT_LINE_COLOR;

    public Integer getWidth() {
        return width;
    }

    public ImgCodeBuilder setWidth(Integer width) {
        this.width = width;
        return this;
    }

    public Integer getHeight() {
        return height;
    }

    public ImgCodeBuilder setHeight(Integer height) {
        this.height = height;
        return this;
    }

    public String getContent() {
        return content;
    }

    public ImgCodeBuilder setContent(String content) {
        this.content = content;
        return this;
    }

    public ImgFormat getFormat() {
        return format;
    }

    public ImgCodeBuilder setFormat(ImgFormat format) {
        this.format = format;
        return this;
    }

    public Color[] getBckColor() {
        return bckColor;
    }

    public ImgCodeBuilder setBckColor(Color[] bckColor) {
        this.bckColor = bckColor;
        return this;
    }

    public Color[] getFontColor() {
        return fontColor;
    }

    public ImgCodeBuilder setFontColor(Color[] fontColor) {
        this.fontColor = fontColor;
        return this;
    }

    public Color[] getLineColor() {
        return lineColor;
    }

    public ImgCodeBuilder setLineColor(Color[] lineColor) {
        this.lineColor = lineColor;
        return this;
    }

    /***
     * 写入背景色
     * @return
     */
    protected BufferedImage buildBgImg() throws IOException {
        Random random = new Random();
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics2D g = image.createGraphics();

        // 填充背景颜色
        g.setColor(bckColor[random.nextInt(bckColor.length)]);
        g.fillRect(0, 0, width, height);

        // 画干扰线(随机参数干扰线条数)
        int lineNum = 2 + random.nextInt(2);
        for (int n = 0; n < lineNum; n++)
        {
            g.setColor(lineColor[random.nextInt(lineColor.length)]);
            int h = random.nextInt(height);
            int w = random.nextInt((int) (width / 4));
            int x = random.nextBoolean() ? 1 : -1;
            for (int i = 0; i < width; i = i + 3)
            {
                int j = random.nextInt(4) * x;
                g.setStroke(new BasicStroke(2f));
                g.drawLine(w, h, w + i, h + j);
                w = w + i;
                h = h + j;
            }
        }
        return image;
    }

    /**
     * 生成验证码
     * @param value
     * @return
     * @throws IOException 
     */
    public byte[] build() throws IOException {
        AssertUtils.isBlank(content, "content can`t blank.");
        // 构建图片验证码背景色
        BufferedImage image = buildBgImg();
        Random random = new Random();
        Graphics2D g = image.createGraphics();
        // 写入验证码值
        int fontSize = height - 18;
        Font font = new Font("Arial", Font.PLAIN, fontSize);
        g.setFont(font);
        int verifySize = content.length();
        char[] chars = content.toCharArray();
        for (int i = 0; i < verifySize; i++)
        {
            AffineTransform affine = new AffineTransform();
            affine.setToRotation(Math.PI / 16 * random.nextDouble() * (random.nextBoolean() ? 1 : -1),
                (width / verifySize) * i + fontSize / 2, height / 2);
            g.setColor(fontColor[random.nextInt(fontColor.length)]);
            g.setTransform(affine);
            g.drawChars(chars, i, 1, ((width - 70) / verifySize) * i + 35, height / 2 + fontSize / 2 - 8);
        }
        g.dispose();

        // 先将图片输出到缓存数组，然后一起写出（避免ImageIO.write分多次输出，客户端不好解析的问题）
        ByteArrayOutputStream byteOut = new ByteArrayOutputStream();
        ImageIO.write(image, format.toString(), byteOut);
        return byteOut.toByteArray();
    }

    /***
     * 保存到指定地址文件
     * @param filePath
     * @throws IOException
     */
    public void build(String filePath) throws IOException {
        FileUtils.write(filePath, build());
    }
}
