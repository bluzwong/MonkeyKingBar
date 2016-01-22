package com.github.bluzwong.mycache;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import com.github.bluzwong.monkeykingbar_lib.*;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    @InjectExtra
    @KeepState
    int ccf;

    @InjectExtra
    @KeepState
    String string;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        initBtn();

        MonkeyKingBar.injectExtras(this);
        MonkeyKingBar.keepStateOnCreate(this, savedInstanceState);
        Log.i("bruce ", "on create ccf => " + ccf);
    }


    @Override
    protected void onSaveInstanceState(Bundle outState) {
        MonkeyKingBar.keepStateOnSaveInstanceState(this, outState);
        super.onSaveInstanceState(outState);
    }


    private void initBtn() {
        findViewById(R.id.btn_rx).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {

                MainActivity_MKB.startActivity(MainActivity.this, 123, "hhh");
            }
        });

    }


    static class Timer {
        private long startTime;

        void setStartTime() {
            startTime = System.currentTimeMillis();
        }

        long getUsingTime() {
            return System.currentTimeMillis() - startTime;
        }

        void printUsingTime(String owner) {
            Log.i("httprequest", owner + " using time => " + getUsingTime() + " ms");
        }
    }

}
