package mkb;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import com.github.bluzwong.monkeykingbar_lib.Inject;
import com.github.bluzwong.monkeykingbar_lib.InjectFactory;
import com.github.bluzwong.monkeykingbar_lib.Keep;
import com.github.bluzwong.monkeykingbar_lib.KeepFactory;

import java.util.List;

/**
 * Created by wangzhijie on 2016/1/22.
 */
public class MKB {
    public static final String FRAGMENT_TAG = "$MKB$KeepStateFragment";
    public static final String FLAG_LOAD_CALLED = "$FLAG_LOAD_CALLED$";
    /**
     * avoid fragment has not a bundle
     *
     * @param fragment
     */
    public static void initFragment(Fragment fragment) {
        if (fragment.getArguments() == null) {
            fragment.setArguments(new Bundle());
        }
    }

    /**
     * inject any way
     */
    public static void inject(Object target, Intent intent) {
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
     *
     * @param savedInstanceState
     */
    public static void inject(Object target, Intent intent, Bundle savedInstanceState) {
        if (savedInstanceState != null || target == null || intent == null) {
            // got savedInstanceState do not need to inject
            return;
        }
        inject(target, intent);
    }


    /**
     * save state
     *
     * @param outState
     */
    public static void saveState(Object target, Bundle outState) {
        if (outState == null || target == null) {
            // no outstate kannot save state
            return;
        }
        Keep keep = KeepFactory.create(target);
        if (keep == null) {
            logw("@KeepState not found, can not create injector !");
            return;
        }
        //for (Keep keep : keeps) {
        keep.onSaveInstanceState(target, outState);
        //}
    }

    public static void saveState(Activity target) {
        if (target == null) {
            return;
        }
        FragmentManager fragmentManager = target.getFragmentManager();
        Fragment fragment = fragmentManager.findFragmentByTag(FRAGMENT_TAG);
        Keep keep = KeepFactory.create(target);
        if (keep == null) {
            logw("@KeepState not found, can not create injector !");
            return;
        }
        if (fragment == null || !(fragment instanceof Keep)) {
            fragment = (Fragment) keep;
            fragmentManager.beginTransaction().add(fragment, FRAGMENT_TAG).commit();
        }
        keep.onSaveInstanceState(target);
    }


    /**
     * do save work if need, better to call it when get the data for the situation that fragment's detaching will not call onSaveInstanceState().
     * or save at onSaveInstanceState()
     *
     * @param fragment
     */
    public static void saveState(Fragment fragment) {
        Bundle bundle = fragment.getArguments();
        if (bundle.getBoolean(FLAG_LOAD_CALLED, false)) {
            saveState(fragment, bundle);
            bundle.putBoolean(FLAG_LOAD_CALLED, false);
        }
    }


    /**
     * restore state when savedInstanceState is not null
     * load state for all object
     *
     * @param savedInstanceState
     */
    public static boolean loadState(Object target, Bundle savedInstanceState) {
        if (target == null) {
            return false;
        }
        if (savedInstanceState == null ) {
            // no state to keep
            if (target instanceof ILoadStateListener) {
                ((ILoadStateListener) target).stateNotLoad();
            }
            return false;
        }
        Keep keep = KeepFactory.create(target);
        if (keep == null) {
            logw("@KeepState not found, can not create injector !");
            if (target instanceof ILoadStateListener) {
                ((ILoadStateListener) target).stateNotLoad();
            }
            return false;
        }
        //for (Keep keep : keeps) {
        boolean loadOK = keep.onCreate(target, savedInstanceState);
        if (target instanceof ILoadStateListener) {
            if (loadOK) {
                ((ILoadStateListener) target).loadStateOK();
            } else {
                ((ILoadStateListener) target).stateNotLoad();
            }
        }
        return loadOK;
        //}
    }

    /**
     * load state for activity
     *
     * @param target
     */
    public static boolean loadState(Activity target) {
        if (target == null) {
            // no target to keep
            return false;
        }
        FragmentManager fragmentManager = target.getFragmentManager();
        Fragment fragment = fragmentManager.findFragmentByTag(FRAGMENT_TAG);
        if (fragment == null || !(fragment instanceof Keep)) {
            if (target instanceof ILoadStateListener) {
                ((ILoadStateListener) target).stateNotLoad();
            }
            return false;
        }
        Keep keep = (Keep) fragment;
        boolean loadOK = keep.onCreate(target);
        if (target instanceof ILoadStateListener) {
            if (loadOK) {
                ((ILoadStateListener) target).loadStateOK();
            } else {
                ((ILoadStateListener) target).stateNotLoad();
            }
        }
        return loadOK;
    }

    /**
     * restore saved state from saveState or saveState
     *
     * @param fragment
     */
    public static boolean loadState(Fragment fragment) {
        Bundle arguments = fragment.getArguments();
         if (arguments != null) {
            arguments.putBoolean(FLAG_LOAD_CALLED, true);
            return loadState((Object) fragment, arguments);
        }
        if (fragment instanceof ILoadStateListener) {
            ((ILoadStateListener) fragment).stateNotLoad();
        }
        return false;
    }

    public interface ILoadStateListener {
        void loadStateOK();

        void stateNotLoad();
    }

    private static void logw(String msg) {
        Log.w("MonkeyKingBar", msg);
    }
}
