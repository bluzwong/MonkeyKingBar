package com.github.bluzwong.monkeykingbar_lib;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;

import java.io.*;
import java.lang.reflect.Method;
import java.util.ArrayList;

/**
 * Created by wangzhijie on 2016/1/22.
 */
public class MKBUtils {

    public static Object getExtra(Intent intent, String name) {
        if (intent == null) {
            return null;
        }
        return getExtra(intent.getExtras(), name);
    }

    public static Object getExtra(Bundle bundle, String name) {
        if (bundle == null) {
            return null;
        }
        return bundle.get(name);
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
