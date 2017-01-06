package lpp.tools.comm;

import java.util.regex.Pattern;

/**
 * Author:lipanpan</br>
 * Date:2017年1月5日</br>
 * Description:Emoji表情符</br>
 * Copyright (c) 2016 code</br> 
 */
public abstract class EmojiUtils {

    /**emoji表情符正则表达式*/
    private static final String EMOJI_REG_PATTERN = "[\\ud800\\udc00-\\udbff\\udfff\\ud800-\\udfff]";


    /**
     * 是否包含emoji表情符
     * @param value
     * @return
     */
    public static boolean contain(String value) {
        if (StringUtils.isEmpty(value)) {
            return false;
        }
        char[] chars = value.toCharArray();
        for (Character c : chars) {
            if (Pattern.matches(EMOJI_REG_PATTERN, c.toString())) {
                return true;
            }
        }
        return false;
    }

    /**
     * 过滤emoji表情符
     * @param value
     * @return
     */
    public static String filter(String value) {
        if (StringUtils.isBlank(value)) {
            return value;
        }
        return value.replaceAll(EMOJI_REG_PATTERN, "");
    }

}
