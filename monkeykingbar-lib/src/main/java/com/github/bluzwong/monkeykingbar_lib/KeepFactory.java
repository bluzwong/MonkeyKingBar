package com.github.bluzwong.monkeykingbar_lib;

import java.util.*;

/**
 * Created by wangzhijie@wind-mobi.com on 2015/9/25.
 */
public class KeepFactory {

    private static final Map<String, List<Keep>> injectionMap = new HashMap<>();
    static List<Keep> create(Object target) {

        Class<?> targetClass = target.getClass();
        String clzName = targetClass.getCanonicalName();
        if (injectionMap.containsKey(clzName)) {
            List<Keep> iKeep = injectionMap.get(clzName);
            if (iKeep != null) {
                return iKeep;
            }
        }
        List<Keep> findOutKeep = findKeepClass(targetClass);
        injectionMap.put(clzName, findOutKeep);
        return findOutKeep;
    }

    private static List<Keep> findKeepClass(Class<?> clz) {
        if (clz == null) {
            return null;
        }
        ArrayList<Keep> keeps = new ArrayList<>();
        Class<?> p_clz = clz;

        while (p_clz != null) {
            try {
                Class<?> injectClz = Class.forName(p_clz.getCanonicalName() + "_MKB");
                Keep iKeep = (Keep) injectClz.newInstance();
                if (!keeps.contains(iKeep)) {
                    keeps.add(iKeep);
                }
            } catch (ClassNotFoundException e) {
                //iKeep = findKeepClass(clz.getSuperclass());
                //e.printStackTrace();
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
            p_clz = p_clz.getSuperclass();
        }
        Collections.reverse(keeps);
        return keeps;
    }
}
