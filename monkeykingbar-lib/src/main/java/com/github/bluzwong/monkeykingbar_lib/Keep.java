package com.github.bluzwong.monkeykingbar_lib;

import android.app.Activity;
import android.os.Bundle;

/**
 * Created by wangzhijie on 2016/1/22.
 */
public interface Keep {
    void onCreate(Object object, Bundle savedInstanceState);
    void onCreate(Object object, KeepStateFragment keepStateFragment);
    void onSaveInstanceState(Object object, Bundle outState);
    void onSaveInstanceState(Object object, KeepStateFragment keepStateFragment);
}
