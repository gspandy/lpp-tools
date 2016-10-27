package lpp.tools.cache;

import java.io.Serializable;


/**
 * Author:lipanpan</br>
 * Date:2016年10月27日</br>
 * Description:</br>
 * Copyright (c) 2016 code</br> 
 */
public final class URL implements Serializable {

    private static final long serialVersionUID = 1L;

    public String getParameter(String key) {
        return null;
    }

    public String getParameter(String key, String defaultValue) {
        return defaultValue;
    }

    public int getParameter(String key, int defaultVaule) {
        return defaultVaule;
    }

    public long getParameter(String key, long defaultVaule) {
        return defaultVaule;
    }

    /**
     * 返回URL 生成的唯一Key
     */
    @Override
    public String toString() {
        return null;
    }

}
