package com.github.bluzwong.monkeykingbar_lib;

import android.app.Fragment;
import android.os.Bundle;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by bluzwong on 2016/3/1.
 */
public class KeepStateFragment extends Fragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }


}
