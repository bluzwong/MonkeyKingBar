package com.github.bluzwong.monkeykingbar_lib;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import io.paperdb.Book;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;

import java.util.UUID;

import static com.github.bluzwong.monkeykingbar_lib.MKBUtils.book;
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
        MonkeyKingBar.setContext(context);
        initBookIfNeed();
        book.destroy();
    }

    @Test
    public void testInitBookIfNeed() throws Exception {
        book = null;
        initBookIfNeed();
        Book testBook = book;
        assertNotNull(testBook);
        initBookIfNeed();
        assertTrue(testBook == book);
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
        assertNull(getExtra(intent, "notpublicclass"));

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
        assertNull(getExtra(bundle, "notpublicclass"));

        putExtra(bundle, "notpublic const", new NotPublicConstructor());
        assertNull(getExtra(bundle, "notpublic const"));
    }

    @Test
    public void testLoadObjectOrOrigin() throws Exception {
        String value = OBJECT_PREFIX + UUID.randomUUID();
        saveToBook(value, "wsdObject");

        Object ccf = loadObjectOrOrigin("ccf");
        assertEquals(ccf, "ccf");

        ccf = loadObjectOrOrigin("");
        assertEquals(ccf, "");

        ccf = loadObjectOrOrigin(OBJECT_PREFIX);
        assertEquals(ccf, OBJECT_PREFIX);

        Object wsd = loadObjectOrOrigin(value);
        assertEquals(wsd, "wsdObject");
    }

    @Test
    public void testSaveToBook() throws Exception {
        assertNull(book.read("ccf"));

        saveToBook("ccf", "value-object");
        Object ccf = book.read("ccf");
        assertEquals(ccf, "value-object");

        saveToBook("ccf", "value-object");
        ccf = book.read("ccf");
        assertEquals(ccf, "value-object");

        saveToBook("ccf", "value-object2");
        ccf = book.read("ccf");
        assertEquals(ccf, "value-object2");

    }

    @Test
    public void testLoadFromBook() throws Exception {
        Object wsd = loadFromBook("wsd");
        assertEquals(wsd, null);

        book.write("wsd", "wsd-object");
        wsd = loadFromBook("wsd");
        assertEquals(wsd, "wsd-object");
    }

    @Test
    public void testRemoveBookKey() throws Exception {
        testSaveToBook();
        testLoadFromBook();

        assertTrue(book.exist("wsd"));
        assertTrue(book.exist("ccf"));
        removeBookKey(context, "wsd");
        assertFalse(book.exist("wsd"));
        assertTrue(book.exist("ccf"));

        removeBookKey(context, "ccf");
        assertFalse(book.exist("wsd"));
        assertFalse(book.exist("ccf"));
    }

    @Test
    public void testClearAllCache() throws Exception {
        testSaveToBook();
        testLoadFromBook();
        assertTrue(book.exist("wsd"));
        assertTrue(book.exist("ccf"));
        clearAllCache(context);
        assertFalse(book.exist("wsd"));
        assertFalse(book.exist("ccf"));
        int size = book.getAllKeys().size();
        assertEquals(size, 0);
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

    static class NotPublicClass {

    }

    public class NotPublicConstructor {
        NotPublicConstructor() {

        }
    }
}