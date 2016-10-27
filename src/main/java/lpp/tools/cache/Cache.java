package lpp.tools.cache;

/**
 * Author:lipanpan</br>
 * Date:2016年10月27日</br>
 * Description:</br>
 * Copyright (c) 2016 code</br> 
 */
public interface Cache {

    void put(Object key, Object value);

    Object get(Object key);

}
