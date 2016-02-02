package com.github.bluzwong.monkeykingbar_lib;


import android.content.Intent;

/**
 * Created by wangzhijie@wind-mobi.com on 2015/9/25.
 */
public interface Inject {
     void injectExtras(Object target, Intent intent);
}
