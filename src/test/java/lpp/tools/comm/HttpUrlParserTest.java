/**
 * 文件名：HttpUrlParserTest.java
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

import org.junit.Test;

/**
 * 功能描述：httpUrl解析器
 */
public class HttpUrlParserTest
{

    @Test
    public void parserUrl()
    {
        String url = "http://127.0.0.1/appapi/book/book_template.do?opt=init&ikey=ACVJbjbcania&sid=chauicbhiuagicghahihh";
        HttpUrlParser parser = new HttpUrlParser(url);
        System.out.println(parser.getBaseUrl());
        System.out.println(parser.getQueryUrl());
        System.out.println(parser.getParameterMap());
        System.out.println(parser.getParameter("sid"));
    }

}
