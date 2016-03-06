package com.github.bluzwong.monkeykingbar.example;

import android.app.Fragment;
import android.os.Bundle;

/**
 * Created by Bruce-Home on 2016/3/6.
 */
public class ByFragment extends Fragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    int count = 0;
}
