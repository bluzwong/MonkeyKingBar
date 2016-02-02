package com.github.bluzwong.monkeykingbar;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import com.github.bluzwong.monkeykingbar_lib.MonkeyKingBar;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;

import static org.junit.Assert.*;

/**
 * Created by bluzwong on 2016/2/2.
 */
@RunWith(RobolectricGradleTestRunner.class)
@Config(constants = com.github.bluzwong.monkeykingbar_lib.BuildConfig.class)
public class MyActivityTest {

    Context Context;

    @Before
    public void setUp() throws Exception {
        Context = RuntimeEnvironment.application;
        MonkeyKingBar.init(Context);
        //MonkeyKingBar.clearAllCache();
    }

    @After
    public void tearDown() throws Exception {
        //MonkeyKingBar.clearAllCache();
    }

    @Test
    public void testInject() {
        MyClass myClass = new MyClass("content");
        Intent intent = MyActivity_MKB.putExtra(new Intent(), 333, "bar11", myClass);
        //controller.create(intent.getExtras());
        MyActivity myActivity = new MyActivity();
        myActivity.setIntent(intent);
        MonkeyKingBar.injectExtras(myActivity);

        assertEquals(myActivity.foo, 333);
        assertEquals(myActivity.bar, "bar11");
        assertEquals(myActivity.myClass, myClass);
    }

    @Test
    public void testKeep() {
        MyClass myClass = new MyClass("content");
        MyActivity myActivity = new MyActivity();
        myActivity.foo = 444;
        myActivity.bar = "barrr";
        myActivity.myClass = myClass;

        Bundle outState = new Bundle();
        MonkeyKingBar.keepStateOnSaveInstanceState(myActivity, outState);
        MyActivity myActivity2 = new MyActivity();
        MonkeyKingBar.keepStateOnCreate(myActivity2, outState);

        assertEquals(myActivity.foo, 444);
        assertEquals(myActivity.bar, "barrr");
        assertEquals(myActivity.myClass, myClass);
    }
}