/**
* 文件名：QrCodePaddingBuilder.java
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
import java.io.ByteArrayOutputStream;
import java.util.Hashtable;
import java.util.Map;

import javax.imageio.ImageIO;

import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import com.google.zxing.qrcode.encoder.ByteMatrix;
import com.google.zxing.qrcode.encoder.Encoder;
import com.google.zxing.qrcode.encoder.QRCode;

/**
 * 功能描述：可控制padding但二维码大小不可控的二维码构建器
 * 建议通过css控制二维码大小
 */
public class QrCodePaddingBuilder extends QrCodeBuilder {

    /**边距默认为2px*/
    protected Integer padding = 2;

    public Integer getPadding() {
        return padding;
    }

    public QrCodePaddingBuilder setPadding(Integer padding) {
        this.padding = padding;
        return this;
    }

    @Override
    public byte[] build() {
        try
        {
            QRCodeWriter qrcodeWrite = new QRCodeWriter();
            Hashtable<EncodeHintType, String> hints = new Hashtable<EncodeHintType, String>();
            hints.put(EncodeHintType.CHARACTER_SET, charset);
            BitMatrix bitMatrix = qrcodeWrite.encode(content, width, height, hints);
            BufferedImage im = toBufferedImage(bitMatrix);
            ByteArrayOutputStream output = new ByteArrayOutputStream();
            ImageIO.write(im, format.toString(), output);
            return output.toByteArray();
        } catch (Exception e)
        {
            log.error("QrCodePaddingBuilder build error", e);
        }
        return null;
    }

    /**
     * This object renders a QR Code as a BitMatrix 2D array of greyscale values.
     * @description 因为zxing实现存在很大的白边（padding），因此需要将白边去掉
     * 可以解决padding大小可控，但无法控制生成二维码的大小；
     * @author lipanpan
     */
    private final class QRCodeWriter {

        private static final int QUIET_ZONE_SIZE = 4;

        public BitMatrix encode(String contents, int width, int height, Map<EncodeHintType, ?> hints)
            throws WriterException {

            if (contents.isEmpty()) { throw new IllegalArgumentException("Found empty contents"); }

            if (width < 0 || height < 0) { throw new IllegalArgumentException("Requested dimensions are too small: "
                + width + 'x' + height); }

            ErrorCorrectionLevel errorCorrectionLevel = ErrorCorrectionLevel.L;
            int quietZone = QUIET_ZONE_SIZE;
            if (hints != null)
            {
                ErrorCorrectionLevel requestedECLevel = (ErrorCorrectionLevel) hints
                    .get(EncodeHintType.ERROR_CORRECTION);
                if (requestedECLevel != null)
                {
                    errorCorrectionLevel = requestedECLevel;
                }
                Integer quietZoneInt = (Integer) hints.get(EncodeHintType.MARGIN);
                if (quietZoneInt != null)
                {
                    quietZone = quietZoneInt;
                }
            }

            QRCode code = Encoder.encode(contents, errorCorrectionLevel, hints);
            return renderResult(code, width, height, quietZone);
        }

        private BitMatrix renderResult(QRCode code, int width, int height, int quietZone) {
            ByteMatrix input = code.getMatrix();
            if (input == null) { throw new IllegalStateException(); }
            int inputWidth = input.getWidth();
            int inputHeight = input.getHeight();
            int qrWidth = inputWidth;
            int qrHeight = inputHeight;
            int outputWidth = Math.max(width, qrWidth);
            int outputHeight = Math.max(height, qrHeight);

            int multiple = Math.min(outputWidth / qrWidth, outputHeight / qrHeight);

            int leftPadding = (outputWidth - (inputWidth * multiple)) / 2;
            int topPadding = (outputHeight - (inputHeight * multiple)) / 2;

            // 控制二维码padding
            outputWidth = outputWidth - 2 * (leftPadding - padding);
            leftPadding = padding;
            outputHeight = outputHeight - 2 * (topPadding - padding);
            topPadding = padding;

            BitMatrix output = new BitMatrix(outputWidth, outputHeight);

            for (int inputY = 0, outputY = topPadding; inputY < inputHeight; inputY++, outputY += multiple)
            {
                for (int inputX = 0, outputX = leftPadding; inputX < inputWidth; inputX++, outputX += multiple)
                {
                    if (input.get(inputX, inputY) == 1)
                    {
                        output.setRegion(outputX, outputY, multiple, multiple);
                    }
                }
            }
            return output;
        }
    }
}
