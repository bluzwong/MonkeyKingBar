package com.github.bluzwong.monkeykingbar.example.atfragment.viewpager;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.support.v13.app.FragmentPagerAdapter;
import android.support.v13.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import com.github.bluzwong.monkeykingbar.R;
import com.github.bluzwong.monkeykingbar.example.atfragment.*;

/**
 * Created by Bruce-Home on 2016/3/6.
 */
public class ViewPagerWithFragmentActivity extends Activity {


    ViewPager viewPager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_for_view_pager);

        viewPager = (ViewPager) findViewById(R.id.vp);

        viewPager.setAdapter(new FragmentStatePagerAdapter(getFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return new MyFragment();
            }

            @Override
            public int getCount() {
                return 5;
            }
        });
    }
}
