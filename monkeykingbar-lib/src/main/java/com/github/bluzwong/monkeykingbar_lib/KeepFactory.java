package com.github.bluzwong.monkeykingbar_lib;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by wangzhijie@wind-mobi.com on 2015/9/25.
 */
public class KeepFactory {

    private static final Map<String, Keep> keepMap = new HashMap<>();
    static Keep create(Object target) {

        Class<?> targetClass = target.getClass();
        String clzName = targetClass.getCanonicalName();
        if (keepMap.containsKey(clzName)) {
            Keep iKeep = keepMap.get(clzName);
            if (iKeep != null) {
                return iKeep;
            }
        }
        Keep findOutKeep = findKeepClass(targetClass);
        keepMap.put(clzName, findOutKeep);
        return findOutKeep;
    }

    private static Keep findKeepClass(Class<?> clz) {
        if (clz == null) {
            return null;
        }
        Keep iKeep = null;
        try {
            Class<?> keepClz = Class.forName(clz.getCanonicalName() + "_MKB");
            iKeep = (Keep) keepClz.newInstance();
        } catch (ClassNotFoundException e) {
            iKeep = findKeepClass(clz.getSuperclass());
            //e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return iKeep;
    }
}
