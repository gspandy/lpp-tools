package lpp.tools.file;

import java.io.File;
import java.io.FileFilter;

import lpp.tools.file.filter.DirectoryWithOtherFilter;
import lpp.tools.file.filter.FileSuffixFilter;

/**
 * Author:lipanpan</br>
 * Date:2016年10月10日</br>
 * Description:文件扫描器</br>
 * Copyright (c) 2016 随手记</br>
 */
public class FileScanner {

    public interface ScanCallback {

        /**
         * 回调函数
         * @param file 对应文件
         * @param className 不包含根目录的包名
         */
        public void invoke(File file);
    }

    /**
     * 扫描某个目录下的所有文件
     * @param rootPath
     */
    public static void scan(String rootPath, String[] suffix, ScanCallback callback) {
        File root = new File(rootPath);
        if (!root.exists()) {
            return;
        }
        FileFilter filter = new DirectoryWithOtherFilter(new FileSuffixFilter(suffix));
        // 扫描所有文件
        scanRecu(root, filter, callback);
    }

    /**
     * 递归扫描
     * @param parentFile
     * @param filter
     * @param callback
     */
    private static void scanRecu(File parentFile, FileFilter filter, ScanCallback callback) {
        File[] files = parentFile.listFiles(filter);
        for (File f : files) {
            if (f.isDirectory()) {
                scanRecu(f, filter, callback);
            } else {
                callback.invoke(f);
            }
        }
    }

}
