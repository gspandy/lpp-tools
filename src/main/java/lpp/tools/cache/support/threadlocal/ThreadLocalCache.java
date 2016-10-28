package lpp.tools.cache.support.threadlocal;

import java.util.HashMap;
import java.util.Map;

import lpp.tools.cache.Cache;
import lpp.tools.cache.URL;


/**
 * Author:lipanpan</br>
 * Date:2016年10月27日</br>
 * Description:本地线程缓存</br>
 * Copyright (c) 2016 code</br> 
 */
public class ThreadLocalCache implements Cache {

    private final ThreadLocal<Map<Object, Object>> store;

    public ThreadLocalCache(URL url) {
        this.store = new ThreadLocal<Map<Object, Object>>() {
            @Override
            protected Map<Object, Object> initialValue() {
                return new HashMap<Object, Object>();
            }
        };
    }

    public void put(Object key, Object value) {
        store.get().put(key, value);
    }

    public Object get(Object key) {
        return store.get().get(key);
    }


}
