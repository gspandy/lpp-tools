/**
* 文件名：StringUtils.java
* 创建日期： 2016年7月31日
* 作者：     lipanpan
* Copyright (c) 2009-2011 无线开发室
* All rights reserved.
 
* 修改记录：
* 	1.修改时间：2016年7月31日
*   修改人：lipanpan
*   修改内容：
*/
package lpp.tools.comm;

/**
 * 功能描述：
 */
public abstract class StringUtils {

    /**
     * 判断字符串是否为空(null, "")
     * @param value
     * @return
     */
    public static boolean isEmpty(String value) {
        if (value == null || value.isEmpty()) {
            return true;
        }
        return false;
    }

    /**
     * 判断字符串是否不为空(null, "")
     * @param value
     * @return
     */
    public static boolean isNotEmpty(String value) {
        if (value != null && !value.isEmpty()) {
            return true;
        }
        return false;
    }

    /**
     * 判断给定的字符串列表是否存在空字符串
     * @param args
     * @return
     */
    public static boolean isExistEmpty(String... args) {
        for (String arg : args) {
            if (isEmpty(arg)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 判断字符串是否为空(null, "" , "  ")
     * @param value
     * @return
     */
    public static boolean isBlank(String value) {
        if (value == null || value.trim().isEmpty()) {
            return true;
        }
        return false;
    }

    /**
     * 判断字符串是否不为空(null, "" , "  ")
     * @param value
     * @return
     */
    public static boolean isNotBlank(String value) {
        if (value != null && !value.trim().isEmpty()) {
            return true;
        }
        return false;
    }

    /**
     * 判断给定的字符串列表是否存在blank字符串
     * @param args
     * @return
     */
    public static boolean isExistBlank(String... args) {
        for (String arg : args) {
            if (isBlank(arg)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 连接字符集
     * @param connector
     * @param args
     * @return
     */
    public static String contactString(String connector, String... args) {
        StringBuilder sb = new StringBuilder();
        if (connector == null) {
            connector = "";
        }
        for (String arg : args) {
            sb.append(arg).append(connector);
        }
        String result = sb.toString();
        if (!connector.equals("")) {
            return result.substring(0, result.length() - connector.length());
        }
        return result;
    }

    /**
     * 拆分字符串
     * @param src
     * @param regex
     * @return
     */
    public static String[] splitString(String src, String regex) {
        if (isEmpty(src)) {
            return null;
        }
        if (isEmpty(regex)) {
            return new String[] {src};
        }
        return src.split(regex);
    }

}
