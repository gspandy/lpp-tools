/*
 * Copyright 1999-2016 feidee.com All right reserved. This software is the
 * confidential and proprietary information of feidee.com ("Confidential
 * Information"). You shall not disclose such Confidential Information and shall
 * use it only in accordance with the terms of the license agreement you entered
 * into with feidee.com.
 */
package lpp.tools.io;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/** 实现描述：资源文件下载器
 * @author lipanpan
 * @time 2016年7月21日 */
public class FileDownloader {

    /** 文件全路径 */
    private String fullPath = null;

    /** 文件下载存放目录 */
    private String storePath = null;

    /** 文件名称(带后缀：header.zip) */
    private String fileName = null;

    public FileDownloader(String storePath, String fileName) {
        if (storePath == null || fileName == null) { throw new IllegalArgumentException(); }
        this.storePath = storePath;
        this.fileName = fileName;
        if (storePath.endsWith(File.separator))
        {
            this.fullPath = this.storePath + this.fileName;
        } else
        {
            this.fullPath = this.storePath + File.separator + this.fileName;
        }

    }

    /** 下载资源文件
     * @param url
     * @throws MalformedURLException */
    public void download(String url) throws Exception {
        HttpURLConnection conn = null;
        InputStream in = null;
        OutputStream out = null;
        try
        {
            URL httpUrl = new URL(url);
            conn = (HttpURLConnection) httpUrl.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Charset", "UTF-8");
            conn.connect();

            File file = FileUtils.create(fullPath);
            in = new BufferedInputStream(conn.getInputStream());
            out = new BufferedOutputStream(new FileOutputStream(file));
            int size = 0;
            byte[] buf = new byte[1024];
            while ((size = in.read(buf)) != -1)
            {
                out.write(buf, 0, size);
            }
            out.flush();
        } finally
        {
            conn.disconnect();
            StreamUtils.close(in);
            StreamUtils.close(out);
        }
    }

    /** 获取文件全路径
     * @return */
    public String getFileFullPath() {
        return this.fullPath;
    }

    /** 将下载的文件转为字节数组
     * @return
     * @throws FileNotFoundException */
    public byte[] toByteArray() throws IOException {
        File file = new File(fullPath);
        BufferedInputStream in = null;
        ByteArrayOutputStream out = null;
        try
        {
            in = new BufferedInputStream(new FileInputStream(file));
            out = new ByteArrayOutputStream();
            int size = 0;
            byte[] buf = new byte[1024];
            while ((size = in.read(buf)) != -1)
            {
                out.write(buf, 0, size);
            }
            return out.toByteArray();
        } finally
        {
            StreamUtils.close(in);
            StreamUtils.close(out);
        }
    }
}
