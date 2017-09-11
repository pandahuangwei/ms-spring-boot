package com.ml.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * @author panda.
 * @since 2017-07-02 22:11.
 */
public class Encrypts {

    private static final String ENCRYPTS_SALT = "ms";

    public static String encodeMD5(String plainText) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update((ENCRYPTS_SALT + plainText).getBytes());
            byte b[] = md.digest();
            int i;
            StringBuffer buf = new StringBuffer("");
            for (int offset = 0; offset < b.length; offset++) {
                i = b[offset];
                if (i < 0)
                    i += 256;
                if (i < 16)
                    buf.append("0");
                buf.append(Integer.toHexString(i));
            }
            //32位加密
            return buf.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 多参数MD5
     *
     * @param args 多参数
     * @return md5Str
     */
    public static String encodeMD5(String... args) {
        StringBuffer sb = new StringBuffer();
        for (String str : args) {
            sb.append(str.trim());
        }
        return encodeMD5(sb.toString());
    }

    public static void main(String[] args) {
        System.out.println(encodeMD5("123456"));
    }
}
