package com.github.bluzwong.monkeykingbar_lib;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import java.util.List;

/**
 * Created by wangzhijie on 2016/1/22.
 */
public class MonkeyKingBar {
    static Context sContext;
    public static final String FRAGMENT_TAG = "$MKB$KeepStateFragment";

    private static void setContext(Context context) {
        if (sContext != null || context == null) {
            return;
        }
        sContext = context.getApplicationContext();
    }
    public static void init(Context context) {
        setContext(context);
    }

    /**
     * inject any way
     */
    public static void injectExtras(Object target, Intent intent) {
        if (target == null || intent == null) {
            //throw new IllegalArgumentException("target is null ");
            return;
        }
        List<Inject> injects = InjectFactory.create(target);
        if (injects == null || injects.size() <= 0) {
            logw("@InjectExtra not found, can not create injector !");
            return;
        }
        for (Inject inject : injects) {
            inject.injectExtras(target, intent);
        }
    }

    /**
     * inject when savedInstanceState is null
     * @param savedInstanceState
     */
    public static void injectExtras(Object target, Intent intent,  Bundle savedInstanceState) {
        if (savedInstanceState != null || target == null || intent == null) {
            // got savedInstanceState do not need to inject
            return;
        }
        injectExtras(target, intent);
    }


    /**
     * restore state when savedInstanceState is not null
     * @param savedInstanceState
     */
    public static void keepStateOnCreate(Object target, Bundle savedInstanceState) {
        if (savedInstanceState == null || target == null) {
            // no state to keep
            return;
        }
        List<Keep> keeps = KeepFactory.create(target);
        if (keeps == null || keeps.size() <= 0) {
            logw("@KeepState not found, can not create injector !");
            return;
        }
        for (Keep keep : keeps) {
            keep.onCreate(target, savedInstanceState);
        }
    }

    public static void keepStateOnCreate(Activity target) {
        if (target == null) {
            // no target to keep
            return;
        }
        FragmentManager fragmentManager = target.getFragmentManager();
        Fragment fragment = fragmentManager.findFragmentByTag(FRAGMENT_TAG);
        if (fragment == null || !(fragment instanceof KeepStateFragment)) {
            return;
        }
        KeepStateFragment keepStateFragment = (KeepStateFragment) fragment;

        List<Keep> keeps = KeepFactory.create(target);
        if (keeps == null || keeps.size() <= 0) {
            logw("@KeepState not found, can not create injector !");
            return;
        }
        for (Keep keep : keeps) {
            keep.onCreate(target, keepStateFragment);
        }
    }

    /**
     * save state
     * @param outState
     */
    public static void keepStateOnSaveInstanceState(Object target, Bundle outState) {
        if (outState == null || target == null) {
            // no outstate kannot save state
            return;
        }
        List<Keep> keeps = KeepFactory.create(target);
        if (keeps == null || keeps.size() <= 0) {
            logw("@KeepState not found, can not create injector !");
            return;
        }
        for (Keep keep : keeps) {
            keep.onSaveInstanceState(target, outState);
        }
    }

    public static void keepStateOnSaveInstanceState(Activity target) {
        if (target == null) {
            return;
        }
        FragmentManager fragmentManager = target.getFragmentManager();
        Fragment fragment = fragmentManager.findFragmentByTag(FRAGMENT_TAG);
        if (fragment == null || !(fragment instanceof KeepStateFragment)) {
            fragment = new KeepStateFragment();
            fragmentManager.beginTransaction().add(fragment, FRAGMENT_TAG).commit();
        }
        KeepStateFragment keepStateFragment = (KeepStateFragment) fragment;
        List<Keep> keeps = KeepFactory.create(target);
        if (keeps == null || keeps.size() <= 0) {
            logw("@KeepState not found, can not create injector !");
            return;
        }
        for (Keep keep : keeps) {
            keep.onSaveInstanceState(target, keepStateFragment);
        }
    }


    private static void logw(String msg) {
        Log.w("MonkeyKingBar", msg);
    }
}
