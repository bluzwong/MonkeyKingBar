package com.github.bluzwong.monkeykingbar;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ScrollView;
import android.widget.TextView;
import com.github.bluzwong.monkeykingbar_lib.*;
import mkb.MKB;

import java.util.Random;

public class MainActivity extends BaseActivity {

    @InjectExtra
    @KeepState
    int foo;

    @InjectExtra
    @KeepState
    String bar;

    @KeepState
    String msgs = "";


    @KeepState
    int count = 0;

    @InjectExtra
    @KeepState
    MyClass myClass;

    TextView tvMsg;
    ScrollView scroll;
    TextView tv;
    //private KeepFragment fragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_main);
        initView();

        MKB.inject(this, getIntent(), savedInstanceState);
        log("注入字段完成");

        MKB.loadState(this, savedInstanceState);

        log("恢复字段完成");

        /*fragment = (KeepFragment) getFragmentManager().findFragmentByTag("KeepFragment");
        if (fragment == null) {
            fragment = new KeepFragment();
            getFragmentManager().beginTransaction().add(fragment, "KeepFragment").commit();
        } else {
            myClass = (MyClass) fragment.getDataMap().get("MyClass");
        }*/
        refreshTv();
        log("onCreate finish");
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        //fragment.getDataMap().put("MyClass", myClass);
        // 自动保存 @KeepState字段 数据

        MKB.saveState(this, outState);

        log("保存字段完成");
        super.onSaveInstanceState(outState);
    }

    private void initView() {
//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
        tv = (TextView) findViewById(R.id.tv);
        tvMsg = (TextView) findViewById(R.id.tv_msg);
        scroll = (ScrollView) findViewById(R.id.scroll);
        initBtn();
    }

    private void initBtn() {
        findViewById(R.id.btn_rx).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                log("将要发送字段");
                int foo = new Random().nextInt(100);
                String bar = new Random().nextInt(100)+"";
                if (new Random().nextBoolean()) {
                    // 简单启动activity 参数为@InjectExtra 所注解字段
                    MainActivity_MKB.startActivity(MainActivity.this, foo, bar, myClass);
                } else {
                    // 复杂启动activity
                    int foo_base = new Random().nextInt(100);
                    String bar_base = new Random().nextInt(100)+"";

                    // 获取要启动的activity 添加数据
                    Intent intent = MainActivity_MKB.getStartIntent(MainActivity.this, foo, bar, myClass);
                    // 添加父类activity的数据
                    BaseActivity_MKB.putExtra(intent, foo_base, bar_base);

                    startActivity(intent);
                }
            }
        });

        findViewById(R.id.btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myClass = new MyClass();
                refreshTv();
            }
        });

    }

    private void refreshTv() {
        tv.setText("foo => " + bar + " myClass => " + myClass);
    }

    private void log(String msg) {
        msg = count++ + ". " + msg;
        Log.d("mkb@MainActivity", msg);
        msgs += "\n";
        msgs += msg;
        msgs += "\n";
        msgs += "foo_base => " + foo_base + " bar_base => " + bar_base;
        msgs += "  foo => " + foo + " bar => " + bar;

        if (tvMsg != null) {
            tvMsg.setText(msgs);
            scroll.fullScroll(ScrollView.FOCUS_DOWN);
        }
    }

}
