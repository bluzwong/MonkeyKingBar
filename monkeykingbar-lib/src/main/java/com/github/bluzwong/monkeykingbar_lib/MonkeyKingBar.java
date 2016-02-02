package com.github.bluzwong.monkeykingbar_lib;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;

import java.util.List;

/**
 * Created by wangzhijie on 2016/1/22.
 */
public class MonkeyKingBar {
    static Context sContext;
    public static void setContext(Context context) {
        if (sContext != null || context == null) {
            return;
        }
        sContext = context.getApplicationContext();
    }
    public static void init(Context context) {
        setContext(context);
    }

    public static void clearAllCache() {
        MKBUtils.clearAllCache(sContext);
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
     * @param activity
     */
    public static void injectExtras(Activity activity) {
        setContext(activity);
        List<Inject> injects = InjectFactory.create(activity);
        if (injects == null || injects.size() <= 0) {
            logw("@InjectExtra not found, can not create injector !");
            return;
        }
        for (Inject inject : injects) {
            inject.injectExtras(activity);
        }
    }

    public static void injectExtrasAsync(final Activity activity, final ExtraInjectedListener listener) {
        setContext(activity);
        new Thread(new Runnable() {
            @Override
            public void run() {
                injectExtras(activity);
                if (activity == null) {
                    return;
                }
                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        listener.OnExtraInjected();
                    }
                });
            }
        }).start();
    }

    /**
     * inject when savedInstanceState is null
     * @param activity
     * @param savedInstanceState
     */
    public static void injectExtras(Activity activity,  Bundle savedInstanceState) {
        setContext(activity);
        if (savedInstanceState != null) {
            // got savedInstanceState do not need to inject
            return;
        }
        injectExtras(activity);
    }

    public static void injectExtrasAsync(Activity activity,  Bundle savedInstanceState, final ExtraInjectedListener listener) {
        setContext(activity);
        if (savedInstanceState != null) {
            // got savedInstanceState do not need to inject
            return;
        }
        injectExtrasAsync(activity, listener);
    }
    /**
     * restore state when savedInstanceState is not null
     * @param activity
     * @param savedInstanceState
     */
    public static void keepStateOnCreate(Activity activity, Bundle savedInstanceState) {
        setContext(activity);
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

    public static void keepStateOnCrateAsync(final Activity activity, final Bundle savedInstanceState, final StateRestoredListener listener) {
        setContext(activity);
        new Thread(new Runnable() {
            @Override
            public void run() {
                keepStateOnCreate(activity, savedInstanceState);
                if (activity == null) {
                    return;
                }

                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        listener.OnStateRestoredInjected();
                    }
                });
            }
        }).start();
    }

    /**
     * save state
     * @param activity
     * @param outState
     */
    public static void keepStateOnSaveInstanceState(Activity activity, Bundle outState) {
        setContext(activity);
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
