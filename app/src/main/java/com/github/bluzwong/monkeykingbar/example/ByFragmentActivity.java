package com.github.bluzwong.monkeykingbar.example;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.widget.TextView;
import com.github.bluzwong.monkeykingbar.R;

/**
 * Created by Bruce-Home on 2016/3/6.
 */
public class ByFragmentActivity extends Activity {

    SwipeRefreshLayout refreshLayout;
    TextView textView;
    ByFragment fragment;
    int count = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_state);

        refreshLayout = (SwipeRefreshLayout) findViewById(R.id.refresh);
        textView = (TextView) findViewById(R.id.tv);

        fragment = (ByFragment) getFragmentManager().findFragmentByTag("ByFragment");
        if (fragment == null) {
            fragment = new ByFragment();
            getFragmentManager().beginTransaction().add(fragment, "ByFragment").commit();
        } else {
            count = fragment.count;
        }
        refreshTv();

        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshLayout.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        count++;
                        fragment.count = count;
                        refreshTv();
                        refreshLayout.setRefreshing(false);
                    }
                }, 1000);
            }
        });
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        fragment.count = count;
    }

    void refreshTv() {
        textView.setText("now count => " + count);
    }
}
