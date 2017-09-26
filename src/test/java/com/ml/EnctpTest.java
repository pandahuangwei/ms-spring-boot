package com.ml;

import org.apache.shiro.crypto.hash.Md5Hash;

/**
 * @author panda.
 * @since 2017-09-26 16:56.
 */
public class EnctpTest {
    public static void main(String[] args) {
        String md5Hash = new Md5Hash("admin1qaz2wsx").toHex();
        System.out.println(md5Hash);
        md5Hash = new Md5Hash("panda 123456").toHex();
        System.out.println(md5Hash);
        md5Hash = new Md5Hash("abc1234567").toHex();
        System.out.println(md5Hash);
    }
}
