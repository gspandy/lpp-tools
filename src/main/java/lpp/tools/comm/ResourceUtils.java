/*
 * Copyright 1999-2016 feidee.com All right reserved. This software is the
 * confidential and proprietary information of feidee.com ("Confidential
 * Information"). You shall not disclose such Confidential Information and shall
 * use it only in accordance with the terms of the license agreement you entered
 * into with feidee.com.
 */
package lpp.tools.comm;

import java.io.File;
import java.net.JarURLConnection;
import java.net.URL;
import java.util.HashSet;
import java.util.Set;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import lpp.tools.file.FileScanner;
import lpp.tools.file.FileScanner.ScanCallback;

/**
 * 实现描述：资源加载
 * @author lipanpan
 * @time 2016年9月7日
 */
public abstract class ResourceUtils {

    /** 当前资源文件 */
    private static final String SINGLE_CLASSPATH = "classpath:";

    /** 扫描所有jar classpath下的资源文件 */
    private static final String ALL_CLASSPATH = "classpath*:";

    /** 路径分隔符 */
    private static final String PATH_SEPARATOR = "/";

    /***
     * 加载classpath下资源文件，classpath:加载当前应用下的资源文件，classpath*:加载所有jar下的资源文件
     * @param path，示例："classpath:plugins","classpath*:plugins",
     *            "classpath:/plugins/resource/text.txt"等
     * @see FrameworkCore
     * @return
     * @throws Exception
     */
    public static Set<URL> loadResource(String path) throws Exception {
        if (StringUtils.isEmpty(path)) {
            throw new IllegalArgumentException("loadResource param error. path can`t null");
        }
        boolean single = path.startsWith(SINGLE_CLASSPATH);
        boolean all = path.startsWith(ALL_CLASSPATH);
        if (!single && !all) {
            throw new IllegalArgumentException(
                    "loadResource param error. path must startsWith 'classpath:' or 'classpath*:'");
        }

        final Set<URL> urlSet = new HashSet<URL>();
        if (single) {
            String realPath = path.substring(SINGLE_CLASSPATH.length()).trim();
            if (!realPath.startsWith(PATH_SEPARATOR)) {
                realPath = PATH_SEPARATOR + realPath;
            }
            urlSet.add(ResourceUtils.class.getResource(realPath));
        } else {
            String classpath = ResourceUtils.class.getResource("/").getPath();
            String subPath = classpath.substring(0, (classpath.length() - "classes".length() - 1));
            String libPath = subPath + "lib";
            final String relativePath = path.substring(ALL_CLASSPATH.length()).trim();
            FileScanner.scan(libPath, new String[] {"jar"}, new ScanCallback() {

                public void invoke(File file) {
                    URL url = loadResourceByJar(file, relativePath);
                    if (url != null) {
                        urlSet.add(url);
                    }
                }
            });

            // 当前应用是否包含资源文件
            String current = classpath + relativePath;
            File file = new File(current);
            if (file.exists() && file.isFile()) {
                urlSet.add(new URL("file:" + current));
            }
        }
        return urlSet;
    }

    /***
     * 加载jar中的资源文件
     * @param file
     * @param reourcePath
     * @return
     */
    private static URL loadResourceByJar(File file, String reourcePath) {
        String path = file.getPath();
        String jarPath = "jar:file:" + path + "!/";
        try {
            URL FileSysUrl = new URL(jarPath);
            JarURLConnection jarURLConnection = (JarURLConnection) FileSysUrl.openConnection();
            JarFile jarFile = jarURLConnection.getJarFile();
            JarEntry entry = jarFile.getJarEntry(reourcePath);
            if (entry != null && !entry.isDirectory()) {
                return new URL(jarPath + entry.getName());
            }
        } catch (Exception e) {
        }
        return null;
    }

}
