/**
* 文件名：XmlParserUtils.java
* 创建日期： 2016年8月8日
* 作者：     lipanpan
* Copyright (c) 2009-2011 无线开发室
* All rights reserved.
 
* 修改记录：
* 	1.修改时间：2016年8月8日
*   修改人：lipanpan
*   修改内容：
*/
package lpp.tools.io;

import java.io.InputStream;
import java.io.StringReader;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import lpp.tools.comm.StringUtils;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.Node;
import org.dom4j.io.SAXReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

/**
 * 功能描述：xml文件解析器,主要负责将xml文本字符转换成dom对象，将inputstream转换成dom对象
 */
public abstract class XmlParserUtils {

    private static Logger log = LoggerFactory.getLogger(XmlParserUtils.class);

    /**
     * 将xml文本字符串转化为Document对象,转换失败将返回null.
     * @param xml xml文本字符串
     * @return dom对象
     * @exception xml格式非法时将抛出运行时异常
     */
    public static Document xml2Doc(String xml) {
        Document doc = null;
        if (!StringUtils.isEmpty(xml))
        {
            String encoding = getXmlEncoding(xml);
            InputSource source = new InputSource(new StringReader(xml));
            source.setEncoding(encoding);
            try
            {
                SAXReader reader = createSAXReader();
                if (reader != null)
                {
                    doc = reader.read(source);
                }
            } catch (DocumentException e)
            {
                log.error("Fail to parse xml. \n{}", xml, e);
                doc = null;
            }
        }
        return doc;
    }

    private static SAXReader createSAXReader() {
        SAXReader reader = new SAXReader();
        // 防止entity 攻击
        reader.setValidation(false);
        try
        {
            reader.setFeature("http://xml.org/sax/features/external-general-entities", false);
            reader.setFeature("http://xml.org/sax/features/external-parameter-entities", false);
            reader.setFeature("http://apache.org/xml/features/disallow-doctype-decl", true);
            reader.setFeature("http://apache.org/xml/features/nonvalidating/load-external-dtd", false);
        } catch (SAXException e)
        {
            log.error("Fail to create SAXReader. ", e);
            return null;
        }
        return reader;
    }

    private static String getXmlEncoding(String text) {
        String result = null;
        String xml = text.trim();
        if (xml.startsWith("<?xml"))
        {
            int end = xml.indexOf("?>");
            String sub = xml.substring(0, end);
            StringTokenizer tokens = new StringTokenizer(sub, " =\"\'");
            while (tokens.hasMoreTokens())
            {
                String token = tokens.nextToken();
                if ("encoding".equals(token))
                {
                    if (tokens.hasMoreTokens())
                    {
                        result = tokens.nextToken();
                    }
                    break;
                }
            }
        }
        return result;
    }

    /**
     * 将输入流中的数据转成dom. 转换失败将返回null.
     * @param in 输入流
     * @return dom对象
     */
    public static Document readDatabyStream(InputStream in) {
        Document doc = null;
        try
        {
            SAXReader reader = createSAXReader();
            if (reader != null)
            {
                doc = reader.read(in);
            }
        } catch (DocumentException e)
        {
            log.error("Fail to parse xml inputstream.", e);
            doc = null;
        }
        return doc;
    }

    /**
     * xml数据解析,节点名称作为key,值作为value放入到map对象中 解析失败返回空的map对象.
     * 注意：该方法只解析root目录下的一级子节点，即只解析一层，不会递归解析所有节点.
     * @param xml xml格式文本串
     * @return map 该doc对象的所有节点值
     */
    @SuppressWarnings("rawtypes")
    public static Map<String, String> parseData(String xml) {
        Map<String, String> result = new HashMap<String, String>();
        if (xml != null)
        {
            try
            {
                Document doc = xml2Doc(xml);
                if (doc != null)
                {
                    Element root = doc.getRootElement();
                    if (root != null)
                    {
                        List nodes = root.elements();
                        if (nodes != null)
                        {
                            for (int i = 0; i < nodes.size(); i++)
                            {
                                Node node = (Node) nodes.get(i);
                                if (nodes != null)
                                {
                                    result.put(node.getName(), node.getStringValue());
                                }
                            }
                        }
                    }
                }
            } catch (Exception e)
            {
                log.error("Fail to parse xml as Data. \n{}", xml, e);
            }
        }
        return result;
    }
}
