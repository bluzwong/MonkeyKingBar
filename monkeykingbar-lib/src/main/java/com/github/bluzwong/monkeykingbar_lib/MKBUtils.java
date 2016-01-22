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

    private static Object byteArrayToObject(byte[] bytes) {
        java.lang.Object obj = null;
        try {
            ByteArrayInputStream bi = new ByteArrayInputStream(bytes);
            ObjectInputStream oi = new ObjectInputStream(bi);
            obj = oi.readObject();
            bi.close();
            oi.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return obj;
    }

    private static byte[] objectToByteArray(Object obj) {
        byte[] bytes = null;
        try {
            ByteArrayOutputStream bo = new ByteArrayOutputStream();
            ObjectOutputStream oo = new ObjectOutputStream(bo);
            oo.writeObject(obj);
            bytes = bo.toByteArray();
            bo.close();
            oo.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bytes;
    }

    private static final Class[] defaultTypes = new Class[] {
            Boolean.class, Byte.class,
            Short.class, Character.class,
            Integer.class, Long.class,
            Float.class, Double.class,
            String.class, CharSequence.class,
            Parcelable.class, Serializable.class
    };

    public static boolean isDefaultType(Object obj) {
        for (Class type : defaultTypes) {
            if (type.isInstance(obj)) {
                return true;
            }
        }

        if (obj instanceof Boolean[]
                || obj instanceof Byte[]
                || obj instanceof Short[]
                || obj instanceof Character[]
                || obj instanceof Integer[]
                || obj instanceof Long[]
                || obj instanceof Float[]
                || obj instanceof Double[]
                || obj instanceof String[]
                || obj instanceof CharSequence[]
                || obj instanceof Parcelable[]
                || obj instanceof Serializable[]) {

            return true;
        }


            if (obj instanceof ArrayList) {
                try {
                    Method method = obj.getClass().getMethod("get", null);
                    Class returnType = method.getReturnType();
                    if (Parcelable.class == returnType) {
                        return true;
                    }
                } catch (NoSuchMethodException e) {
                    e.printStackTrace();
                }
            }

        return false;
    }

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

    public static Object getExtraViaByteArray(Intent intent, String name) {
        if (intent == null) {
            return null;
        }
        return getExtraViaByteArray(intent.getExtras(), name);
    }

    public static Object getExtraViaByteArray(Bundle bundle, String name) {
        if (bundle == null) {
            return null;
        }
        return byteArrayToObject(bundle.getByteArray(name));
    }

    public static Bundle putExtraViaByteArray(Bundle bundle, String name, Object value) {
        if (bundle == null) {
            return null;
        }
        putExtra(bundle, name, objectToByteArray(value));
        return bundle;
    }

    public static Intent putExtraViaByteArray(Intent intent, String name, Object value) {
        if (intent == null) {
            return null;
        }
        putExtraViaByteArray(intent.getExtras(), name, value);
        return intent;
    }

    @Deprecated
    public static Bundle putExtra(Bundle bundle, String name, Object value) {
        // add for compile
        return bundle;
    }

    @Deprecated
    public static Intent putExtra(Intent intent, String name, Object value) {
        // add for compile
        return intent;
    }

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

   /* public static Bundle putExtra(Bundle bundle, String name, ArrayList<Integer> value) {
        bundle.putIntegerArrayList(name, value);
        return bundle;
    }

    public static Bundle putExtra(Bundle bundle, String name, ArrayList<String> value) {
        bundle.putStringArrayList(name, value);
        return bundle;
    }

    public static Bundle putExtra(Bundle bundle, String name, ArrayList<CharSequence> value) {
        bundle.putCharSequenceArrayList(name, value);
        return bundle;
    }*/

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
}
