package com.github.bluzwong.mycache;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import com.github.bluzwong.monkeykingbar_lib.Inject;
import com.github.bluzwong.monkeykingbar_lib.Keep;
import com.github.bluzwong.monkeykingbar_lib.MKBUtils;

import java.util.ArrayList;

/**
 * Created by wangzhijie on 2016/1/22.
 */
public class MainActivity_MKB implements Inject, Keep {

    // inject start
    @Override
    public void injectExtras(Activity activity) {
        if (activity == null) {
            throw new IllegalStateException();
        }
        MainActivity target = (MainActivity) activity;
        // 多个
        Object ccf = MKBUtils.getExtra(activity.getIntent(), "ccf");
        if (ccf != null) {
            target.ccf = (Integer) ccf;
            Log.i("bruce", "oncreate got ccf => " + target.ccf);
        }
        // 多个
    }

    public static Intent getStartIntent(Activity context, int ccf) {
        Intent intent = new Intent(context, MainActivity.class);
        intent.putExtra("ccf", ccf);
        return intent;
    }

    public static void startActivity(Activity activity, int ccf) {
        activity.startActivity(getStartIntent(activity, ccf));
    }

    // inject end

    // keep start
    @Override
    public void onCreate(Activity activity, Bundle savedInstanceState) {
        if (activity == null) {
            throw new IllegalStateException();
        }
        if (savedInstanceState == null) {
            Log.i("bruce", "没有保存的数据");
            return;
        }

        MainActivity target = (MainActivity) activity;

        Object datas = MKBUtils.getExtra(savedInstanceState, "datas");
        if (datas != null) {
            Log.i("bruce", "恢复datas");
            target.datas = (ArrayList<String>) datas;
        }

    }

    @Override
    public void onSaveInstanceState(Activity activity, Bundle outState) {
        if (activity == null) {
            throw new IllegalStateException();
        }
        MainActivity target = (MainActivity) activity;


        Log.i("bruce", "保存datas");
        MKBUtils.putExtra(outState, "datas", target.datas);
    }
    // keep end
}
