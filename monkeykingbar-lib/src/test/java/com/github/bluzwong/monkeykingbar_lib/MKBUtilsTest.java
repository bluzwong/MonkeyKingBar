package com.github.bluzwong.monkeykingbar_lib;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;


import static org.junit.Assert.*;
import static com.github.bluzwong.monkeykingbar_lib.MKBUtils.*;
/**
 * Created by bluzwong on 2016/2/2.
 */
@RunWith(RobolectricGradleTestRunner.class)
@Config(constants = BuildConfig.class)
public class MKBUtilsTest {

    Context context;

    @Before
    public void setUp() throws Exception {
        context = RuntimeEnvironment.application;
        MonkeyKingBar.init(context);
    }


    @Test
    public void testGetExtra() throws Exception {
        Intent intent = new Intent();
        String wsdValue = "wsd-";
        putExtra(intent, "wsd", wsdValue);
        MyClass ccfValue = new MyClass("ccf-");
        putExtra(intent, "ccf", ccfValue);

        Object wsdGet = getExtra(intent, "wsd");
        Object ccfGet = getExtra(intent, "ccf");
        assertNotNull(wsdGet);
        assertNotNull(ccfGet);
        assertEquals(wsdGet, wsdValue);
        assertEquals(ccfGet, ccfValue);

        putExtra(intent, "notpublicclass", new NotPublicClass());
        assertNotNull(getExtra(intent, "notpublicclass"));

        putExtra(intent, "notpublic const", new NotPublicConstructor());
        assertNull(getExtra(intent, "notpublic const"));
    }

    @Test
    public void testGetExtra1() throws Exception {
        Bundle bundle = new Bundle();
        String wsdValue = "wsd-";
        putExtra(bundle, "wsd", wsdValue);
        MyClass ccfValue = new MyClass("ccf-");
        putExtra(bundle, "ccf", ccfValue);

        Object wsdGet = getExtra(bundle, "wsd");
        Object ccfGet = getExtra(bundle, "ccf");
        assertNotNull(wsdGet);
        assertNotNull(ccfGet);
        assertEquals(wsdGet, wsdValue);
        assertEquals(ccfGet, ccfValue);

        putExtra(bundle, "notpublicclass", new NotPublicClass());
        assertNotNull(getExtra(bundle, "notpublicclass"));

        putExtra(bundle, "notpublic const", new NotPublicConstructor());
        assertNull(getExtra(bundle, "notpublic const"));
    }


    public static class MyClass {
        String ccf;
        public MyClass(String ccf) {
            this.ccf = ccf;
        }

        public MyClass() {
        }

        @Override
        public boolean equals(Object o) {
            return o instanceof MyClass && ccf.equals(((MyClass) o).ccf);
        }
    }

    private static class NotPublicClass {

    }

    public class NotPublicConstructor {
        // non-static inner class is disallowed
         NotPublicConstructor() {

        }
    }
}