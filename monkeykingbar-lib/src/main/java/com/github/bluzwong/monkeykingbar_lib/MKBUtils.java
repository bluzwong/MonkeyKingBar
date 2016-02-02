package com.github.bluzwong.monkeykingbar_lib;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import io.paperdb.Book;
import io.paperdb.Paper;

import java.io.*;
import java.util.*;

/**
 * Created by wangzhijie on 2016/1/22.
 */
public class MKBUtils {
    private static final String OBJECT_PREFIX = "$$MKB_Object$$";
    private static final String OBJECT_SPLIT_REGEX = "\\$\\$";

    static Book book;

    static void initBookIfNeed() {
        if (book != null || MonkeyKingBar.sContext == null) {
            return;
        }
        synchronized (MKBUtils.class) {
            Paper.init(MonkeyKingBar.sContext);
            book = Paper.book("MKB_CACHE_BOOK");
        }
    }


    public static Object getExtra(Intent intent, String name) {
        initBookIfNeed();
        if (intent == null) {
            return null;
        }
        return getExtra(intent.getExtras(), name);
    }

    public static Object getExtra(Bundle bundle, String name) {
        initBookIfNeed();
        if (bundle == null) {
            return null;
        }
        Object value = bundle.get(name);
        return loadObjectOrOrigin(value);
    }

    static Object loadObjectOrOrigin(Object value) {
        if (value == null) {
            return null;
        }
        if (value instanceof String) {
            String maybeKey = (String) value;
            if (maybeKey.equals("") || !maybeKey.startsWith(OBJECT_PREFIX)) {
                return value;
            }
            String[] keys = maybeKey.split(OBJECT_SPLIT_REGEX);
            if (keys.length != 3) {
                return value;
            }
            return loadFromBook(maybeKey);
        }
        return value;
    }

    static void saveToBook(String key, Object value) {
        if (book.exist(key)) {
            book.delete(key);
        }
        book.write(key, value);
    }

    static Object loadFromBook(String key) {
        if (!book.exist(key)) {
            return null;
        }
        return book.read(key);
    }

    static void removeBookKey(String key) {
        if (!book.exist(key)) {
            return;
        }
        book.delete(key);
    }

    static void clearAllCache() {
        if (book.getAllKeys().size() <= 0) {
            return;
        }
        book.destroy();
    }

    public static Intent putExtra(Intent intent, String name, Object value) {
        initBookIfNeed();
        String uuid = UUID.randomUUID().toString();
        String key = OBJECT_PREFIX + uuid;
        saveToBook(key, value);
        putExtra(intent, name, key);
        return intent;
    }


    public static Bundle putExtra(Bundle bundle, String name, Object value) {
        initBookIfNeed();
        String uuid = UUID.randomUUID().toString();
        String key = OBJECT_PREFIX + uuid;
        saveToBook(key, value);
        putExtra(bundle, name, key);
        return bundle;
    }

    /**
     * Bundle start
     */

    public static Bundle putExtra(Bundle bundle, String name, boolean value) {
        bundle.putBoolean(name, value);
        return bundle;
    }

    public static Bundle putExtra(Bundle bundle, String name, byte value) {
        bundle.putByte(name, value);
        return bundle;
    }

    public static Bundle putExtra(Bundle bundle, String name, char value) {
        bundle.putChar(name, value);
        return bundle;
    }

    public static Bundle putExtra(Bundle bundle, String name, short value) {
        bundle.putShort(name, value);
        return bundle;
    }

    public static Bundle putExtra(Bundle bundle, String name, int value) {
        bundle.putInt(name, value);
        return bundle;
    }

    public static Bundle putExtra(Bundle bundle, String name, long value) {
        bundle.putLong(name, value);
        return bundle;
    }

    public static Bundle putExtra(Bundle bundle, String name, float value) {
        bundle.putFloat(name, value);
        return bundle;
    }

    public static Bundle putExtra(Bundle bundle, String name, double value) {
        bundle.putDouble(name, value);
        return bundle;
    }

    public static Bundle putExtra(Bundle bundle, String name, String value) {
        bundle.putString(name, value);
        return bundle;
    }

    public static Bundle putExtra(Bundle bundle, String name, CharSequence value) {
        bundle.putCharSequence(name, value);
        return bundle;
    }

    public static Bundle putExtra(Bundle bundle, String name, Parcelable value) {
        bundle.putParcelable(name, value);
        return bundle;
    }

    public static Bundle putExtra(Bundle bundle, String name, Parcelable[] value) {
        bundle.putParcelableArray(name, value);
        return bundle;
    }

    public static Bundle putExtra(Bundle bundle, String name, ArrayList<? extends Parcelable> value) {
        bundle.putParcelableArrayList(name, value);
        return bundle;
    }

    public static Bundle putExtra(Bundle bundle, String name, Serializable value) {
        bundle.putSerializable(name, value);
        return bundle;
    }

    public static Bundle putExtra(Bundle bundle, String name, boolean[] value) {
        bundle.putBooleanArray(name, value);
        return bundle;
    }

    public static Bundle putExtra(Bundle bundle, String name, byte[] value) {
        bundle.putByteArray(name, value);
        return bundle;
    }

    public static Bundle putExtra(Bundle bundle, String name, short[] value) {
        bundle.putShortArray(name, value);
        return bundle;
    }

    public static Bundle putExtra(Bundle bundle, String name, char[] value) {
        bundle.putCharArray(name, value);
        return bundle;
    }

    public static Bundle putExtra(Bundle bundle, String name, int[] value) {
        bundle.putIntArray(name, value);
        return bundle;
    }

    public static Bundle putExtra(Bundle bundle, String name, long[] value) {
        bundle.putLongArray(name, value);
        return bundle;
    }

    public static Bundle putExtra(Bundle bundle, String name, float[] value) {
        bundle.putFloatArray(name, value);
        return bundle;
    }

    public static Bundle putExtra(Bundle bundle, String name, double[] value) {
        bundle.putDoubleArray(name, value);
        return bundle;
    }

    public static Bundle putExtra(Bundle bundle, String name, String[] value) {
        bundle.putStringArray(name, value);
        return bundle;
    }

    public static Bundle putExtra(Bundle bundle, String name, CharSequence[] value) {
        bundle.putCharSequenceArray(name, value);
        return bundle;
    }

    public static Bundle putExtra(Bundle bundle, String name, Bundle value) {
        bundle.putBundle(name, value);
        return bundle;
    }


    /** Bundle start */


    /**
     * Intent start
     */

    public static Intent putExtra(Intent intent, String name, boolean value) {
        return intent.putExtra(name, value);
    }

    public static Intent putExtra(Intent intent, String name, byte value) {
        return intent.putExtra(name, value);
    }

    public static Intent putExtra(Intent intent, String name, char value) {
        return intent.putExtra(name, value);
    }

    public static Intent putExtra(Intent intent, String name, short value) {
        return intent.putExtra(name, value);
    }

    public static Intent putExtra(Intent intent, String name, int value) {
        return intent.putExtra(name, value);
    }

    public static Intent putExtra(Intent intent, String name, long value) {
        return intent.putExtra(name, value);
    }

    public static Intent putExtra(Intent bundle, String name, float value) {
        return bundle.putExtra(name, value);
    }

    public static Intent putExtra(Intent bundle, String name, double value) {
        return bundle.putExtra(name, value);
    }

    public static Intent putExtra(Intent bundle, String name, String value) {
        return bundle.putExtra(name, value);
    }

    public static Intent putExtra(Intent bundle, String name, CharSequence value) {
        return bundle.putExtra(name, value);
    }

    public static Intent putExtra(Intent bundle, String name, Parcelable value) {
        return bundle.putExtra(name, value);
    }

    public static Intent putExtra(Intent bundle, String name, Parcelable[] value) {
        return bundle.putExtra(name, value);
    }

    public static Intent putExtra(Intent bundle, String name, ArrayList<? extends Parcelable> value) {
        return bundle.putExtra(name, value);
    }

    public static Intent putExtra(Intent bundle, String name, Serializable value) {
        return bundle.putExtra(name, value);
    }

    public static Intent putExtra(Intent bundle, String name, boolean[] value) {
        return bundle.putExtra(name, value);
    }

    public static Intent putExtra(Intent bundle, String name, byte[] value) {
        return bundle.putExtra(name, value);
    }

    public static Intent putExtra(Intent bundle, String name, short[] value) {
        return bundle.putExtra(name, value);
    }

    public static Intent putExtra(Intent bundle, String name, char[] value) {
        return bundle.putExtra(name, value);
    }

    public static Intent putExtra(Intent bundle, String name, int[] value) {
        return bundle.putExtra(name, value);
    }

    public static Intent putExtra(Intent bundle, String name, long[] value) {
        return bundle.putExtra(name, value);
    }

    public static Intent putExtra(Intent bundle, String name, float[] value) {
        return bundle.putExtra(name, value);
    }

    public static Intent putExtra(Intent bundle, String name, double[] value) {
        return bundle.putExtra(name, value);
    }

    public static Intent putExtra(Intent bundle, String name, String[] value) {
        return bundle.putExtra(name, value);
    }

    public static Intent putExtra(Intent bundle, String name, CharSequence[] value) {
        return bundle.putExtra(name, value);
    }

    public static Intent putExtra(Intent bundle, String name, Bundle value) {
        return bundle.putExtra(name, value);
    }


    /** Bundle start */

}
