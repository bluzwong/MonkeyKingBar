package com.github.bluzwong.monkeykingbar_processor;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by Bruce-Home on 2016/2/2.
 */
public class Utils {
    public static String getMD5(String info) {
        return getMD5(info.getBytes());
    }

    public static String getMD5(byte[] info) {
        if (null == info || info.length == 0) {
            return null;
        }
        StringBuilder buf = new StringBuilder("");
        MessageDigest md;
        try {
            md = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
        md.update(info);
        byte b[] = md.digest();
        int i;

        for (int offset = 0; offset < b.length; offset++) {
            i = b[offset];
            if (i < 0)
                i += 256;
            if (i < 16)
                buf.append("0");
            buf.append(Integer.toHexString(i));
        }
        return buf.toString();
    }
}
