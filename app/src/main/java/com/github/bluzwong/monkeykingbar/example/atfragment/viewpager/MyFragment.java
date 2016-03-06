package com.github.bluzwong.monkeykingbar.example.atfragment.viewpager;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.github.bluzwong.monkeykingbar.R;

/**
 * Created by Bruce-Home on 2016/3/6.
 */
public class MyFragment extends Fragment {
    SwipeRefreshLayout refreshLayout;
    TextView textView;
    int count = 0;

    public MyFragment() {
        setArguments(new Bundle());
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_state, container, false);

        count = getArguments().getInt("KEY_COUNT", 0);

        getArguments().putBoolean("loaded", true);

        refreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.refresh);
        textView = (TextView) view.findViewById(R.id.tv);

        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshLayout.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        count++;
                        refreshTv();
                        refreshLayout.setRefreshing(false);
                        if (getArguments().getBoolean("loaded", false)) {
                            getArguments().putInt("KEY_COUNT", count);
                            getArguments().putBoolean("loaded", false);
                        }
                    }
                }, 1000);
            }
        });
        refreshTv();
        return view;
    }


    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

    }

    void refreshTv() {
        textView.setText("now count => " + count);
    }
}
