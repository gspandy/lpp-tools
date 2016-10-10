package lpp.tools.file.filter;

import java.io.File;
import java.io.FileFilter;


/**
 * Author:lipanpan</br>
 * Date:2016年10月10日</br>
 * Description:目录搭配其他过滤器</br>
 * Copyright (c) 2016 随手记</br> 
 */
public class DirectoryWithOtherFilter implements FileFilter {

    /**其他过滤器*/
    private FileFilter otherFilter = null;

    public DirectoryWithOtherFilter(FileFilter otherFilter) {
        this.otherFilter = otherFilter;
    }

    @Override
    public boolean accept(File pathname) {
        if (pathname.isDirectory()) {
            return true;
        }
        return otherFilter.accept(pathname);
    }

}
