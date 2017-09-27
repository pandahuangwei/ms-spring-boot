package com.ml.cache;

import com.ml.utils.Dates;
import org.apache.commons.lang3.tuple.Pair;

import java.util.Date;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 验证码缓存.
 *
 * @author panda.
 * @since 2017-09-27 10:16.
 */
public class KaptchaCache {
    private Map<String, Pair<String, Date>> map = new ConcurrentHashMap<>(128);
    private final static int expMin = -2;

    private KaptchaCache() {
    }

    private static class SingletonHolder {
        private final static KaptchaCache instance = new KaptchaCache();
    }

    /**
     * 获取实例
     *
     * @return
     */
    public static KaptchaCache getInstance() {
        return SingletonHolder.instance;
    }

    /**
     * 获取key-uuid对应的验证码
     *
     * @param key uuid
     * @return 验证码
     */
    public String getValue(String key) {
        Pair<String, Date> pair = map.get(key);
        String vrifyCode = pair == null ? null : pair.getLeft();
        remove(key);
        return vrifyCode;
    }

    /**
     * 往内存中存放验证码
     *
     * @param key   uuid
     * @param value 验证码
     */
    public void add(String key, String value) {
        map.put(key, Pair.of(value, new Date()));
    }

    /**
     * 将失效的验证码从内存中清除
     */
    public void remove() {
        if (map.isEmpty()) {
            return;
        }
        Iterator<String> it = map.keySet().iterator();
        Date expDate = Dates.addMinutes(new Date(), expMin);
        while (it.hasNext()) {
            String key = it.next();
            Pair<String, Date> pair = map.get(key);
            if (pair.getRight().before(expDate)) {
                it.remove();
            }
        }
    }

    /**
     * 移除key所对应的元素
     *
     * @param key key
     */
    public void remove(String key) {
        map.keySet().remove(key);
    }
}
