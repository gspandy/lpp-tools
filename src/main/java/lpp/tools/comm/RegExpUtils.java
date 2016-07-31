/**
* 文件名：RegExpUtils.java
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

import java.util.regex.Pattern;

/**
 * 功能描述：正则表达式工具类
 */
public abstract class RegExpUtils {

    /***
     * 功能描述：正则表达式集
     */
    public static interface RegExp {
        /**手机号正则表达式*/
        final String REGEX_MOBILE = "^((13[0-9])|(14[5,7])|(15[^4,\\D])|(17[6-8])|(18[0-9]))\\d{8}$";
        /**身份证ID正则表达式*/
        final String REGEX_ID_CARD = "(^\\d{18}$)|(^\\d{15}$)";
        /**邮箱正则表达式*/
        final String REGEX_EMAIL = "^\\s*\\w+(?:\\.{0,1}[\\w-]+)*@[a-zA-Z0-9]+(?:[-.][a-zA-Z0-9]+)*\\.[a-zA-Z]+\\s*$";

    }

    /***
     * 正则校验数字是否合法
     * @param value
     * @return
     */
    public static boolean checkNumber(String value) {
        return Boolean.FALSE;
    }

    /***
     * 正则校验手机号是否合法
     * @param value
     * @return
     */
    public static boolean checkPhoneNo(String value) {
        if (StringUtils.isBlank(value)) { return false; }
        return Pattern.matches(RegExp.REGEX_MOBILE, value);
    }

    /***
     * 正则校验email是否合法
     * @param value
     * @return
     */
    public static boolean checkEmail(String value) {
        if (StringUtils.isBlank(value)) { return false; }
        return Pattern.matches(RegExp.REGEX_EMAIL, value);
    }

    /***
     * 正则校验身份证是否合法
     * @param value
     * @return
     */
    public static boolean checkCardId(String value) {
        if (StringUtils.isBlank(value)) { return false; }
        return Pattern.matches(RegExp.REGEX_ID_CARD, value);
    }

}
