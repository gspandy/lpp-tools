package lpp.tools.cache.support.lru;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;

import lpp.tools.cache.Cache;
import lpp.tools.cache.URL;


/**
 * Author:lipanpan</br>
 * Date:2016年10月27日</br>
 * Description:LinkedHashMap 采用双向循环链表，记录元素的插入顺序和访问顺序（默认记录访问顺序，通过accessOrder属性控制）</br>
 * Copyright (c) 2016 code</br> 
 */
public class LruCache implements Cache {

    private final Map<Object, Object> store;

    public LruCache(URL url) {
        final int max = url.getParameter("cache.size", 1000);
        this.store = new LinkedHashMap<Object, Object>() {
            private static final long serialVersionUID = -3834209229668463829L;

            @Override
            protected boolean removeEldestEntry(Entry<Object, Object> eldest) {
                // 当元素个数大于max时，清除最久没有使用的元素，在每次put或putAll时调用此方法校验
                return size() > max;
            }
        };
    }

    public void put(Object key, Object value) {
        synchronized (store) {
            store.put(key, value);
        }
    }

    public Object get(Object key) {
        synchronized (store) {
            return store.get(key);
        }
    }

}
