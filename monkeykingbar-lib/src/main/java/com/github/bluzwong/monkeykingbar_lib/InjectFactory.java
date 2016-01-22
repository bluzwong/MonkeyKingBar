package com.github.bluzwong.monkeykingbar_lib;

import java.util.*;

/**
 * Created by wangzhijie@wind-mobi.com on 2015/9/25.
 */
public class InjectFactory {

    private static final Map<String, List<Inject>> injectionMap = new HashMap<>();
    static List<Inject> create(Object target) {

        Class<?> targetClass = target.getClass();
        String clzName = targetClass.getCanonicalName();
        if (injectionMap.containsKey(clzName)) {
            List<Inject> iInject = injectionMap.get(clzName);
            if (iInject != null) {
                return iInject;
            }
        }
        List<Inject> findOutInject = findInjectClass(targetClass);
        injectionMap.put(clzName, findOutInject);
        return findOutInject;
    }

    private static List<Inject> findInjectClass(Class<?> clz) {
        if (clz == null) {
            return null;
        }
        ArrayList<Inject> injects = new ArrayList<>();
        Class<?> p_clz = clz;

        while (p_clz != null) {
            try {
                Class<?> injectClz = Class.forName(p_clz.getCanonicalName() + "_MKB");
                Inject iInject = (Inject) injectClz.newInstance();
                if (!injects.contains(iInject)) {
                    injects.add(iInject);
                }
            } catch (ClassNotFoundException e) {
                //iInject = findInjectClass(clz.getSuperclass());
                //e.printStackTrace();
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
            p_clz = p_clz.getSuperclass();
        }
        Collections.reverse(injects);
        return injects;
    }
}
