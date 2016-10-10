package lpp.tools.file;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import lpp.tools.file.filter.DirectoryFilter;

/**
 * Author:lipanpan</br>
 * Date:2016年10月10日</br>
 * Description:</br>
 * Copyright (c) 2016 随手记</br>
 */
public class DirectoryScanner {

    /**
     * 扫描所有目录,根据正则表达式去匹配
     * @param rootPath
     * @param matchRegex 匹配的文件夹名
     * @param notMatchRegex 不需要匹配的
     */
    public static List<String> scan(String rootPath, String matchRegex, String notMatchRegex) {
        File root = new File(rootPath);
        if (!root.exists()) {
            return null;
        }
        Pattern pMatch = Pattern.compile(matchRegex);
        Pattern pNotMatch = null;
        if (notMatchRegex != null)
            pNotMatch = Pattern.compile(notMatchRegex);

        DirectoryFilter filter = new DirectoryFilter();
        // 扫描所有文件
        List<String> lst = new ArrayList<String>();
        scanRecu(root, filter, pMatch, pNotMatch, lst);

        return lst;
    }

    /**
     * 递归扫描
     */
    private static void scanRecu(File parentFile, DirectoryFilter filter, Pattern pMatch, Pattern pNotMatch,
            List<String> lst) {
        File[] files = parentFile.listFiles(filter);
        for (File f : files) {
            if (pMatch.matcher(f.getAbsolutePath()).matches()) {
                lst.add(f.getAbsolutePath());
            }
            if (pNotMatch != null && pNotMatch.matcher(f.getAbsolutePath()).matches()) {
                continue;
            }
            scanRecu(f, filter, pMatch, pNotMatch, lst);
        }
    }

}
