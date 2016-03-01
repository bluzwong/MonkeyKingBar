package com.github.bluzwong.monkeykingbar_lib;

import android.app.Fragment;
import android.os.Bundle;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by bluzwong on 2016/3/1.
 */
public class KeepStateFragment extends Fragment {

    private Map<String, Object> dataMap = new ConcurrentHashMap<String, Object>();
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    public void put(String key, Object object) {
        if (key == null || object == null) {
            return;
        }
        dataMap.put(key, object);
    }

    public Object get(String key) {
        if (key == null) {
            return null;
        }
        return dataMap.get(key);
    }
}
