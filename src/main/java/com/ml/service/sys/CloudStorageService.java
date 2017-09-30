package com.ml.service.sys;

import com.ml.entity.sys.CloudStorage;
import com.ml.utils.Dates;
import org.apache.commons.lang.StringUtils;

import java.io.InputStream;
import java.util.Date;
import java.util.UUID;

/**
 * 云存储(七牛、阿里云、腾讯云)
 *
 * @author panda.
 * @since 2017-09-30 11:00.
 */
public abstract class CloudStorageService {
    /**
     * 云存储配置信息
     */
    CloudStorage config;

    /**
     * 文件路径
     *
     * @param prefix 前缀
     * @return 返回上传路径
     */
    public String getPath(String prefix) {
        //生成uuid
        String uuid = UUID.randomUUID().toString().replaceAll("-", "");
        //文件路径
        String path = Dates.format(new Date(), "yyyyMMdd") + "/" + uuid;

        if (StringUtils.isNotBlank(prefix)) {
            path = prefix + "/" + path;
        }

        return path;
    }

    public abstract void init();

    /**
     * 文件上传
     *
     * @param data 文件字节数组
     * @param path 文件路径，包含文件名
     * @return 返回http地址
     */
    public abstract String upload(byte[] data, String path);

    /**
     * 文件上传
     *
     * @param data 文件字节数组
     * @return 返回http地址
     */
    public abstract String upload(byte[] data);

    /**
     * 文件上传
     *
     * @param inputStream 字节流
     * @param path        文件路径，包含文件名
     * @return 返回http地址
     */
    public abstract String upload(InputStream inputStream, String path);

    /**
     * 文件上传
     *
     * @param inputStream 字节流
     * @return 返回http地址
     */
    public abstract String upload(InputStream inputStream);
}
