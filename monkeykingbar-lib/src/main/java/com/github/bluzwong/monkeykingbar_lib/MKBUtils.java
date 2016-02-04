package com.github.bluzwong.monkeykingbar_lib;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.KryoException;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;
import com.esotericsoftware.kryo.serializers.CompatibleFieldSerializer;
import de.javakaffee.kryoserializers.ArraysAsListSerializer;
import de.javakaffee.kryoserializers.SynchronizedCollectionsSerializer;
import de.javakaffee.kryoserializers.UnmodifiableCollectionsSerializer;

import java.io.*;
import java.lang.reflect.Constructor;
import java.lang.reflect.Modifier;
import java.util.*;

/**
 * Created by wangzhijie on 2016/1/22.
 */
public class MKBUtils {
    static final String OBJECT_PREFIX = "$$MKB$$";
    static final String OBJECT_SPLIT_REGEX = "\\$\\$";

    static Kryo kryo = new Kryo();

    static {
        kryo.register(ContentContainer.class);
        kryo.setDefaultSerializer(CompatibleFieldSerializer.class);
        kryo.setReferences(false);
        kryo.register(Arrays.asList("").getClass(), new ArraysAsListSerializer());
        UnmodifiableCollectionsSerializer.registerSerializers(kryo);
        SynchronizedCollectionsSerializer.registerSerializers(kryo);
        // Serialize inner AbstractList$SubAbstractListRandomAccess
        kryo.addDefaultSerializer(new ArrayList<>().subList(0, 0).getClass(),
                new NoArgCollectionSerializer());
        // Serialize AbstractList$SubAbstractList
        kryo.addDefaultSerializer(new LinkedList<>().subList(0, 0).getClass(),
                new NoArgCollectionSerializer());
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
        Object value = bundle.get(name);
        if (value == null) {
            return null;
        }
        if (!(value instanceof String)) {
            // not byte array just return
            return value;
        }

        // maybe byte array key
        String maybeKey = (String) value;
        if (maybeKey.equals("") || !maybeKey.startsWith(OBJECT_PREFIX)) {
            return value;
        }
        String[] keys = maybeKey.split(OBJECT_SPLIT_REGEX);
        if (keys.length != 4) {
            return value;
        }
        // $$MKB_Object$$15dca01c-492e-49cb-9c0d-fbf0b8c8fa30
        if (keys[2].length() != "15dca01c-492e-49cb-9c0d-fbf0b8c8fa30".length()) {
            return value;
        }
        // its the key
        byte[] inputByteArray = (byte[]) getExtra(bundle, maybeKey);
        ByteArrayInputStream inputStream = new ByteArrayInputStream(inputByteArray);
        Input input = new Input(inputStream);
        try {
            ContentContainer container = kryo.readObject(input, ContentContainer.class);
            if (container == null) {
                return null;
            }
            return container.content;
        } catch (KryoException e) {
            e.printStackTrace();
        } finally {
            input.close();
            try {
                inputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }



    public static Intent putExtra(Intent intent, String name, Object value) {
        String uuid = UUID.randomUUID().toString();
        String key = OBJECT_PREFIX + uuid + "$$" + name;
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        Output output = new Output(outputStream);
        kryo.writeObject(output, new ContentContainer(value));
        output.flush();
        try {
            outputStream.flush();
            outputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        output.close();
        //saveToBook(key, value);
        putExtra(intent, name, key);
        byte[] bytes = outputStream.toByteArray();
        putExtra(intent, key, bytes);
        return intent;
    }

    public static Bundle putExtra(Bundle bundle, String name, Object value) {
        String uuid = UUID.randomUUID().toString();
        String key = OBJECT_PREFIX + uuid + "$$" + name;
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        Output output = new Output(outputStream);
        kryo.writeObject(output, new ContentContainer(value));
        output.flush();
        try {
            outputStream.flush();
            outputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        output.close();
        //saveToBook(key, value);
        putExtra(bundle, name, key);
        byte[] bytes = outputStream.toByteArray();
        putExtra(bundle, key, bytes);
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
