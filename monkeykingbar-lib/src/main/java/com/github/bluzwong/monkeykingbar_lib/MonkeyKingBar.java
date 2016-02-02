package com.github.bluzwong.monkeykingbar_lib;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import io.paperdb.Paper;

import java.util.List;

/**
 * Created by wangzhijie on 2016/1/22.
 */
public class MonkeyKingBar {
    static Context sContext;
    private static void setContext(Context context) {
        if (sContext != null || context == null) {
            return;
        }
        sContext = context.getApplicationContext();
    }
    public static void init(Context context) {
        setContext(context);
        Paper.init(sContext);
        MKBUtils.book = Paper.book("MKB_CACHE_BOOK");
    }

    public static void clearAllCache() {
        MKBUtils.clearAllCache();
    }

   /* public static void onDestroy(Activity activity) {
        List<Inject> injects = InjectFactory.create(activity);
        if (injects != null && injects.size() > 0) {
            for (Inject inject : injects) {
                inject.onDestroy();
            }
            return;
        }
        List<Keep> keeps = KeepFactory.create(activity);
        if (keeps != null && keeps.size() > 0) {
            for (Keep keep : keeps) {
                keep.onDestroy();
            }
        }
    }*/

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

    public static void injectExtrasAsync(final Object target, final Intent intent, final ExtraInjectedListener listener) {
        if (target == null || intent == null) {
            return;
        }
        new Thread(new Runnable() {
            @Override
            public void run() {
                injectExtras(target, intent);
                if (listener != null) {
                    listener.OnExtraInjected();
                }
            }
        }).start();
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

    public static void injectExtrasAsync(Object target,  Intent intent,Bundle savedInstanceState, final ExtraInjectedListener listener) {
        if (savedInstanceState != null || target == null || intent == null) {
            // got savedInstanceState do not need to inject
            return;
        }
        injectExtrasAsync(target, intent, listener);
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

    public static void keepStateOnCrateAsync(final Object target, final Bundle savedInstanceState, final StateRestoredListener listener) {
        if (target == null || savedInstanceState == null) {
            return;
        }
        new Thread(new Runnable() {
            @Override
            public void run() {
                keepStateOnCreate(target, savedInstanceState);
                if (listener!= null) {
                    listener.OnStateRestoredInjected();
                }

            }
        }).start();
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

    public interface ExtraInjectedListener {
        void OnExtraInjected();
    }

    public interface StateRestoredListener {
        void OnStateRestoredInjected();
    }

    private static void logw(String msg) {
        Log.w("MonkeyKingBar", msg);
    }
}
