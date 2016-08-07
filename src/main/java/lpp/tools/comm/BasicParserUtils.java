/**
 * 文件名：BasicParserUtils.java
 * 创建日期： 2015年12月14日
 * 作者：     lipanpan
 * Copyright (c) 2009-2011 随手记服务端
 * All rights reserved.
 
 * 修改记录：
 * 	1.修改时间：2015年12月14日
 *   修改人：lipanpan
 *   修改内容：
 */
package lpp.tools.comm;

/**
 * 功能描述：字符串转基本数据类型
 */
public abstract class BasicParserUtils {

    /***
     * 字符串转换为boolean类型
     * @param value
     * @param defaultValue
     * @return
     */
    public static Boolean parseBoolean(String value, Boolean defaultValue) {
        if (StringUtils.isBlank(value)) { return defaultValue; }
        try
        {
            return Boolean.parseBoolean(value);
        } catch (Exception e)
        {}
        return defaultValue;
    }

    /***
     * 字符串转换为字节
     * @param value
     * @param defaultValue
     * @return
     */
    public static Byte parseByte(String value, Byte defaultValue) {
        if (StringUtils.isBlank(value)) { return defaultValue; }
        try
        {
            return Byte.parseByte(value);
        } catch (Exception e)
        {}
        return defaultValue;
    }

    /***
     * 字符串转换为Short型
     * @param value
     * @param defaultValue
     * @return
     */
    public static Short parseShort(String value, Short defaultValue) {
        if (StringUtils.isBlank(value)) { return defaultValue; }
        try
        {
            return Short.parseShort(value);
        } catch (Exception e)
        {}
        return defaultValue;
    }

    /***
     * 字符串转换为整形
     * @param value
     * @param defaultValue
     * @return
     */
    public static Integer parseInt(String value, Integer defaultValue) {
        if (StringUtils.isBlank(value)) { return defaultValue; }
        try
        {
            return Integer.parseInt(value);
        } catch (Exception e)
        {}
        return defaultValue;
    }

    /***
     * 字符串转换为Long型
     * @param value
     * @param defaultValue
     * @return
     */
    public static Long parseLong(String value, Long defaultValue) {
        if (StringUtils.isBlank(value)) { return defaultValue; }
        try
        {
            return Long.parseLong(value);
        } catch (Exception e)
        {}
        return defaultValue;
    }

    /***
     * 字符串转换为Float型
     * @param value
     * @param defaultValue
     * @return
     */
    public static Float parseFloat(String value, Float defaultValue) {
        if (StringUtils.isBlank(value)) { return defaultValue; }
        try
        {
            return Float.parseFloat(value);
        } catch (Exception e)
        {}
        return defaultValue;
    }

    /***
     * 字符串转换为Double型
     * @param value
     * @param defaultValue
     * @return
     */
    public static Double parseDouble(String value, Double defaultValue) {
        if (StringUtils.isBlank(value)) { return defaultValue; }
        try
        {
            return Double.parseDouble(value);
        } catch (Exception e)
        {}
        return defaultValue;
    }

}
