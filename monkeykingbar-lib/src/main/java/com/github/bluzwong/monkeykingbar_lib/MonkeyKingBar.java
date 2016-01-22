package com.github.bluzwong.monkeykingbar_lib;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

/**
 * Created by wangzhijie on 2016/1/22.
 */
public class MonkeyKingBar {

    // 注入外部参数
    public static void injectExtras(Activity activity) {
        Inject inject = InjectFactory.create(activity);
        if (inject == null) {
            logw("@InjectExtra not found, can not create injector !");
            return;
        }
        inject.injectExtras(activity);
    }

    public static void keepStateOnCreate(Activity activity, Bundle savedInstanceState) {
        Keep keep = KeepFactory.create(activity);
        if (keep == null) {
            logw("@KeepState not found, can not create injector !");
            return;
        }
        keep.onCreate(activity, savedInstanceState);
    }

    public static void keepStateOnSaveInstanceState(Activity activity, Bundle outState) {
        Keep keep = KeepFactory.create(activity);
        if (keep == null) {
            logw("@KeepState not found, can not create injector !");
            return;
        }
        keep.onSaveInstanceState(activity, outState);
    }

    private static void logw(String msg) {
        Log.w("MonkeyKingBar", msg);
    }
}
