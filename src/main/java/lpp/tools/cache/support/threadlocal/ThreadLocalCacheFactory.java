package lpp.tools.cache.support.threadlocal;

import lpp.tools.cache.Cache;
import lpp.tools.cache.URL;
import lpp.tools.cache.support.AbstractCacheFactory;


/**
 * Author:lipanpan</br>
 * Date:2016年10月27日</br>
 * Description:抽象工厂模式运用</br>
 * Copyright (c) 2016 code</br> 
 */
public class ThreadLocalCacheFactory extends AbstractCacheFactory {

    @Override
    protected Cache createCache(URL url) {
        return new ThreadLocalCache(url);
    }

}
