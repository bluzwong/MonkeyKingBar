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
    int ccf;

    @InjectExtra
    @KeepState
    String wsd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        initBtn();
        MonkeyKingBar.injectExtras(this);
        MonkeyKingBar.keepStateOnCreate(this, savedInstanceState);
        Log.i("bruce ", "on create ccf => " + ccf + " wsd => " + wsd);
        ccf = 250;
        wsd = "oncreate change wsd";
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
                Log.i("bruce", "start main activity ccf => " + 123);
                MainActivity_MKB.startActivity(MainActivity.this, 123, "wsd haha");
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
