/**
 * 文件名：ByteUtil.java
 * 创建日期： 2014-4-22
 * 作者：    lipanpan
 * All rights reserved.
 * 修改记录：
 * 	1.修改时间：2014-4-22
 *   修改人：lipanpan
 *   修改内容：
 */
package lpp.tools.io;

/**
 * 功能描述：字节操作工具类
 * @author lipanpan
 */
public class ByteUtil {

    /**
     * 将整数按网络字节序写入字节数组某个位置。
     * @param dest 保存16进制字符的字节数组。
     * @param position 最高字节的位置（在地址低端）。
     * @param value 待写入的整数值。
     */
    public static void writeShort(byte[] dest, int position, short value) {
        dest[position] = (byte) (value >>> 8 & 0xff);
        dest[position + 1] = (byte) (value & 0xff);
    }

    /**
     * 将整数按网络字节序写入字节数组某个位置。
     * @param dest 保存16进制字符的字节数组。
     * @param position 最高字节的位置（在地址低端）。
     * @param value 待写入的整数值。
     */
    public static void writeInt(byte[] dest, int position, int value) {
        dest[position] = (byte) (value >>> 24 & 0xff);
        dest[position + 1] = (byte) (value >>> 16 & 0xff);
        dest[position + 2] = (byte) (value >>> 8 & 0xff);
        dest[position + 3] = (byte) (value & 0xff);
    }

    /**
     * 将长整数按网络字节序写入字节数组某个位置。
     * @param dest 保存16进制字符的字节数组。
     * @param position 最高字节的位置（在地址低端）。
     * @param value 待写入的整数值。
     */
    public static void writeLong(byte[] dest, int position, long value) {
        dest[position] = (byte) (value >>> 56 & 0xff);
        dest[position + 1] = (byte) (value >>> 48 & 0xff);
        dest[position + 2] = (byte) (value >>> 40 & 0xff);
        dest[position + 3] = (byte) (value >>> 32 & 0xff);
        dest[position + 4] = (byte) (value >>> 24 & 0xff);
        dest[position + 5] = (byte) (value >>> 16 & 0xff);
        dest[position + 6] = (byte) (value >>> 8 & 0xff);
        dest[position + 7] = (byte) (value & 0xff);
    }

    /**
     * 将指定的字节数组写入目标字节数组中。
     * @param dest 保存16进制字符的字节数组。
     * @param destPos 最高字节的位置（在地址低端）。
     * @param src 待写入的字节数组。
     */
    public static void writeBytes(byte[] dest, int destPos, byte[] src) {
        System.arraycopy(src, 0, dest, destPos, src.length);
    }

    /**
     * 字节数组转换成short类型.
     * @param b 字节数组.
     * @param offset 数组位置.
     * @return short.
     */
    public static short bytes2short(byte[] b, int offset) {
        return (short) (((b[offset + 1] & 0xFF) << 0) + ((b[offset]) << 8));
    }

    /**
     * 字节数组转换成int类型.
     * @param b 字节数组.
     * @param offset 位置信息.
     * @return int.
     */
    public static int bytes2int(byte[] b, int offset) {
        return ((b[offset + 3] & 0xFF) << 0) + ((b[offset + 2] & 0xFF) << 8) + ((b[offset + 1] & 0xFF) << 16)
            + ((b[offset]) << 24);
    }

    /**
     * 将字节数组转换成long类型
     * @param b 字节数组.
     * @param offset 位置信息.
     * @return long.
     */
    public static long bytes2long(byte[] b, int offset) {
        return ((b[offset + 7] & 0xFFL) << 0) + ((b[offset + 6] & 0xFFL) << 8) + ((b[offset + 5] & 0xFFL) << 16)
            + ((b[offset + 4] & 0xFFL) << 24) + ((b[offset + 3] & 0xFFL) << 32) + ((b[offset + 2] & 0xFFL) << 40)
            + ((b[offset + 1] & 0xFFL) << 48) + (((long) b[offset]) << 56);
    }

}
