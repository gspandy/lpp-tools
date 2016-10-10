package lpp.tools.file.filter;

import java.io.File;
import java.io.FileFilter;


/**
 * 访问者模式
 * Author:lipanpan</br>
 * Date:2016年10月10日</br>
 * Description:文件后缀过滤器</br>
 * Copyright (c) 2016 随手记</br> 
 */
public class FileSuffixFilter implements FileFilter {

    /**[".png",".jpg",".jpeg",".gif"]*/
    private String[] rules = null;

    public FileSuffixFilter(String[] rules) {
        this.rules = new String[rules.length];
        String rule = null;
        for (int i = 0; i < rules.length; i++) {
            rule = rules[i];
            if (!rule.startsWith(".")) {
                rule = "." + rule;
            }
            this.rules[i] = rule;
        }
    }

    @Override
    public boolean accept(File pathname) {
        if (pathname.isDirectory()) {
            return false;
        } else {
            for (String s : rules) {
                if (pathname.getName().endsWith(s))
                    return true;
            }
            return false;
        }
    }

}
