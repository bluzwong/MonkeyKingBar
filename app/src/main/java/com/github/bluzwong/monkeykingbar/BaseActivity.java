package com.github.bluzwong.monkeykingbar;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import com.github.bluzwong.monkeykingbar_lib.*;
import com.github.bluzwong.monkeykingbar_lib.MonkeyKingBar;

/**
 * Created by Bruce-Home on 2016/1/22.
 */
public class BaseActivity extends AppCompatActivity {

    @InjectExtra
    @KeepState
    int foo_base;

    @InjectExtra
    @KeepState
    String bar_base;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MonkeyKingBar.injectExtras(this, savedInstanceState);
        log("注入字段 foo_base => " + foo_base + " bar_base => " + bar_base);
        MonkeyKingBar.keepStateOnCreate(this, savedInstanceState);
        log("恢复字段 foo_base => " + foo_base + " bar_base => " + bar_base);
    }


    @Override
    protected void onSaveInstanceState(Bundle outState) {
        log("保存字段 foo_base => " + foo_base + " bar_base => " + bar_base);
        MonkeyKingBar.keepStateOnSaveInstanceState(this, outState);
        super.onSaveInstanceState(outState);
    }

    private void log(String msg) {
        Log.d("mkb@BaseActivity", msg);
    }
}
