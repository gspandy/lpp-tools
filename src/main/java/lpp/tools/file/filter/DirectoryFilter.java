package lpp.tools.file.filter;

import java.io.File;
import java.io.FileFilter;


/**
 * 设计模式：访问者模式
 * Author:lipanpan</br>
 * Date:2016年10月10日</br>
 * Description:目录过滤器</br>
 * Copyright (c) 2016 随手记</br> 
 */
public class DirectoryFilter implements FileFilter {

    @Override
    public boolean accept(File pathname) {
        if (pathname.isDirectory()) {
            return true;
        }
        return false;
    }

}
