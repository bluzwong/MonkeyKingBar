package com.github.bluzwong.monkeykingbar_lib;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

import java.util.List;

/**
 * Created by wangzhijie on 2016/1/22.
 */
public class MonkeyKingBar {

    /**
     * inject any way
     * @param activity
     */
    public static void injectExtras(Activity activity) {
        List<Inject> injects = InjectFactory.create(activity);
        if (injects == null || injects.size() <= 0) {
            logw("@InjectExtra not found, can not create injector !");
            return;
        }
        for (Inject inject : injects) {
            inject.injectExtras(activity);
        }
    }

    /**
     * inject when savedInstanceState is null
     * @param activity
     * @param savedInstanceState
     */
    public static void injectExtras(Activity activity,  Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            // got savedInstanceState do not need to inject
            return;
        }
        List<Inject> injects = InjectFactory.create(activity);
        if (injects == null || injects.size() <= 0) {
            logw("@InjectExtra not found, can not create injector !");
            return;
        }
        for (Inject inject : injects) {
            inject.injectExtras(activity);
        }
    }

    /**
     * restore state when savedInstanceState is not null
     * @param activity
     * @param savedInstanceState
     */
    public static void keepStateOnCreate(Activity activity, Bundle savedInstanceState) {
        if (savedInstanceState == null) {
            // no state to keep
            return;
        }
        List<Keep> keeps = KeepFactory.create(activity);
        if (keeps == null || keeps.size() <= 0) {
            logw("@KeepState not found, can not create injector !");
            return;
        }
        for (Keep keep : keeps) {
            keep.onCreate(activity, savedInstanceState);
        }
    }

    /**
     * save state
     * @param activity
     * @param outState
     */
    public static void keepStateOnSaveInstanceState(Activity activity, Bundle outState) {
        if (outState == null) {
            // no outstate kannot save state
            return;
        }
        List<Keep> keeps = KeepFactory.create(activity);
        if (keeps == null || keeps.size() <= 0) {
            logw("@KeepState not found, can not create injector !");
            return;
        }
        for (Keep keep : keeps) {
            keep.onSaveInstanceState(activity, outState);
        }
    }

    private static void logw(String msg) {
        Log.w("MonkeyKingBar", msg);
    }
}
