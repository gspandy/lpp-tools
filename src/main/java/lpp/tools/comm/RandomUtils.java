/**
 * 文件名：RandomUtils.java
 * 创建日期： 2015年12月10日
 * 作者：     lipanpan
 * Copyright (c) 2009-2011 随手记服务端
 * All rights reserved.
 
 * 修改记录：
 * 	1.修改时间：2015年12月10日
 *   修改人：lipanpan
 *   修改内容：
 */
package lpp.tools.comm;

import java.util.Random;

/** 功能描述：随机码工具类 */
public abstract class RandomUtils {
    private static final char[] chars = new char[] { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c',
        'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y',
        'z', 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U',
        'V', 'W', 'X', 'Y', 'Z' };

    /** 随机码
     * @param num 验证码位数
     * @return */
    public static String getRandomCode(int num) {
        Random random = new Random();
        StringBuilder buffer = new StringBuilder();
        for (int i = 0; i < num; i++)
        {
            buffer.append(random.nextInt(10)); // 取值范围：[0,10)
        }
        return buffer.toString();
    }

    /** 获取两个数之间的随机值[min,max)
     * @param min
     * @param max
     * @return */
    public static int getRandomInt(int min, int max) {
        Random random = new Random();
        return min + random.nextInt(max - min);
    }

    /** 获取长度[min,max)之间的随机字符串
     * @param min
     * @param max
     * @return */
    public static String getRandomStr(int min, int max) {
        if (min < 0 || min > max) { throw new IllegalArgumentException("参数非法"); }
        int num = 0;
        if (min == max)
        {
            num = max;
        } else
        {
            num = getRandomInt(min, max);
        }
        StringBuilder sb = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < num; i++)
        {
            sb.append(chars[random.nextInt(62)]);// 取值范围：[0,61)
        }
        return sb.toString();
    }

    public static void main(String[] args) {
        String randomStr = null;
        for (int i = 0; i < 1000; i++)
        {
            randomStr = getRandomStr(6, 17);
            System.out.println(randomStr + "---" + randomStr.length());
        }
    }

}
