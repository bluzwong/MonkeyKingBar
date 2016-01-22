package com.github.bluzwong.monkeykingbar_lib;

/**
 * Created by wangzhijie on 2016/1/20.
 */
public interface ICacheManager {
    void put(String key, long timeout);
    boolean get(String key);
}
