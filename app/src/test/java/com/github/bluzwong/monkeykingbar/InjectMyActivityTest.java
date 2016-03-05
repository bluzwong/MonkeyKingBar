package com.github.bluzwong.monkeykingbar;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import mkb.MKB;
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
public class InjectMyActivityTest {

    Context Context;

    @Before
    public void setUp() throws Exception {
        Context = RuntimeEnvironment.application;
    }

    @After
    public void tearDown() throws Exception {
        //MonkeyKingBar.clearAllCache();
    }


    @Test
    public void testKeep() {
        MyClass myClass = new MyClass("content");
        MyActivity myActivity = new MyActivity();
        myActivity.foo = 444;
        myActivity.bar = "barrr";
        myActivity.myClass = myClass;

        Bundle outState = new Bundle();
        MKB.saveState(myActivity, outState);

        MyActivity myActivity2 = new MyActivity();
        MKB.loadState(myActivity2, outState);

        assertEquals(myActivity2.foo, 444);
        assertEquals(myActivity2.bar, "barrr");
        assertEquals(myActivity2.myClass, myClass);
/*
        MonkeyKingBar.onDestroy(myActivity2);
        myActivity2 = new MyActivity();
        MonkeyKingBar.keepStateOnCreate(myActivity2, outState);
        assertEquals(myActivity2.foo, 444);
        assertEquals(myActivity2.bar, "barrr");
        assertEquals(myActivity2.myClass, null);*/
    }

    @Test
    public void testInject() {
        MyClass myClass = new MyClass("content");
        Intent intent = MyActivity_MKB.putExtra(new Intent(), 333, "bar11", myClass);
        //controller.create(intent.getExtras());
        MyActivity myActivity = new MyActivity();
        MKB.inject(myActivity, intent);

        assertEquals(myActivity.foo, 333);
        assertEquals(myActivity.bar, "bar11");
        assertEquals(myActivity.myClass, myClass);
        /*MonkeyKingBar.onDestroy(myActivity);
        myActivity = new MyActivity();
        myActivity.setIntent(intent);
        MonkeyKingBar.injectExtras(myActivity);
        assertEquals(myActivity.foo, 333);
        assertEquals(myActivity.bar, "bar11");
        assertEquals(myActivity.myClass, null);*/
    }


}