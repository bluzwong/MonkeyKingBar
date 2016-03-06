package com.github.bluzwong.monkeykingbar.example.atfragment;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.widget.TextView;
import com.github.bluzwong.monkeykingbar.R;

/**
 * Created by Bruce-Home on 2016/3/6.
 */
public class FragmentActivity extends Activity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_for_fragment);

        Fragment fragment = getFragmentManager().findFragmentById(R.id.container);
        if (fragment == null) {
            fragment = new MyFragment();
            getFragmentManager().beginTransaction().add(R.id.container, fragment).commit();
        }
    }
}
