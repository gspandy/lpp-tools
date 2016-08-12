/**
 * 文件名：HttpUrlParser.java
 * 创建日期： 2016年8月12日
 * 作者：     lipanpan
 * Copyright (c) 2009-2011 服务端
 * All rights reserved.
 
 * 修改记录：
 * 	1.修改时间：2016年8月12日
 *   修改人：lipanpan
 *   修改内容：
 */
package lpp.tools.comm;

import java.util.HashMap;
import java.util.Map;

/**
 * 功能描述：http url解析器
 */
public class HttpUrlParser
{
    private String baseUrl = null;

    private String queryUrl = null;

    private Map<String, String> paramMap = new HashMap<String, String>();

    public HttpUrlParser(String url)
    {
        parseUrl(url);
    }

    /***
     * 解析http url
     */
    private void parseUrl(String url)
    {
        try
        {
            int index = url.indexOf("?");
            baseUrl = url.substring(0, index);
            queryUrl = url.substring(index + 1);
            String[] params = queryUrl.split("&");
            String[] keyValue = null;
            for (String param : params)
            {
                keyValue = param.split("=");
                if (keyValue.length == 2)
                {
                    paramMap.put(keyValue[0], keyValue[1]);
                }
            }
        }
        catch (Exception e)
        {
            throw new IllegalArgumentException("url is invalid.");
        }
    }

    public String getBaseUrl()
    {
        return this.baseUrl;
    }

    public String getQueryUrl()
    {
        return this.queryUrl;
    }

    public String getParameter(String key)
    {
        return paramMap.get(key);
    }

    public String getParameter(String key, String defaultValue)
    {
        String value = paramMap.get(key);
        return StringUtils.isBlank(value) ? defaultValue : value;
    }

    public Map<String, String> getParameterMap()
    {
        return this.paramMap;
    }
}
