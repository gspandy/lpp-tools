/**
* 文件名：ConcurrentHashSet.java
* 创建日期： 2016年10月30日
* 作者：     lipanpan
* Copyright (c) 2009-2011 无线开发室
* All rights reserved.
 
* 修改记录：
* 	1.修改时间：2016年10月30日
*   修改人：lipanpan
*   修改内容：
*/
package lpp.tools.comm;

import java.io.Serializable;
import java.util.AbstractSet;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 功能描述：基于ConcurrentHashMap实现的线程安全的ConcurrentHashSet
 */
public class ConcurrentHashSet<E> extends AbstractSet<E> implements Set<E>, Serializable {
    private static final long serialVersionUID = 6286489351850959602L;
    private static final Object PRESENT = new Object();
    private final ConcurrentHashMap<E, Object> map;
    
    public ConcurrentHashSet(){
        map = new ConcurrentHashMap<E, Object>();
    }

    public ConcurrentHashSet(int initialCapacity){
        map = new ConcurrentHashMap<E, Object>(initialCapacity);
    }

    public Iterator<E> iterator() {
        return map.keySet().iterator();
    }

    public int size() {
        return map.size();
    }

    public boolean isEmpty() {
        return map.isEmpty();
    }

    public boolean contains(Object o) {
        return map.containsKey(o);
    }

    public boolean add(E e) {
        return map.put(e, PRESENT) == null;
    }

    public boolean remove(Object o) {
        return map.remove(o) == PRESENT;
    }

    public void clear() {
        map.clear();
    }

}
