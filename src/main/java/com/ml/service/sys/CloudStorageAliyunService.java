package com.ml.service.sys;

import com.aliyun.oss.OSSClient;
import com.ml.entity.sys.CloudStorage;
import com.ml.exception.RtException;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

/**
 * @author panda.
 * @since 2017-09-30 11:03.
 */
public class CloudStorageAliyunService extends CloudStorageService {
    private OSSClient client;

    public CloudStorageAliyunService(CloudStorage config) {
        this.config =config;
        init();
    }
    @Override
    public void init() {
        client = new OSSClient(config.getAliyunEndPoint(), config.getAliyunAccessKeyId(),
                config.getAliyunAccessKeySecret());
    }

    @Override
    public String upload(byte[] data, String path) {
        return upload(new ByteArrayInputStream(data), path);
    }

    @Override
    public String upload(InputStream inputStream, String path) {
        try {
            client.putObject(config.getAliyunBucketName(), path, inputStream);
        } catch (Exception e){
            throw new RtException("上传文件失败，请检查配置信息", e);
        }

        return config.getAliyunDomain() + "/" + path;
    }

    @Override
    public String upload(byte[] data) {
        return upload(data, getPath(config.getAliyunPrefix()));
    }

    @Override
    public String upload(InputStream inputStream) {
        return upload(inputStream, getPath(config.getAliyunPrefix()));
    }
}
