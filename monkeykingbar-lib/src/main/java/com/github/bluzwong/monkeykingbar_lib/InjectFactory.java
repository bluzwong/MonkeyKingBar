package com.github.bluzwong.monkeykingbar_lib;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by wangzhijie@wind-mobi.com on 2015/9/25.
 */
public class InjectFactory {

    private static final Map<String, Inject> injectionMap = new HashMap<>();
    static Inject create(Object target) {

        Class<?> targetClass = target.getClass();
        String clzName = targetClass.getCanonicalName();
        if (injectionMap.containsKey(clzName)) {
            Inject iInject = injectionMap.get(clzName);
            if (iInject != null) {
                return iInject;
            }
        }
        Inject findOutInject = findInjectClass(targetClass);
        injectionMap.put(clzName, findOutInject);
        return findOutInject;
    }

    private static Inject findInjectClass(Class<?> clz) {
        if (clz == null) {
            return null;
        }
        Inject iInject = null;
        try {
            Class<?> injectClz = Class.forName(clz.getCanonicalName() + "_MKB");
            iInject = (Inject) injectClz.newInstance();
        } catch (ClassNotFoundException e) {
            iInject = findInjectClass(clz.getSuperclass());
            //e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return iInject;
    }
}
