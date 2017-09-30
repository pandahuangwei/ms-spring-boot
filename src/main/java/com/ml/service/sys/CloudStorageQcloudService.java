package com.ml.service.sys;

import com.ml.entity.sys.CloudStorage;
import com.ml.exception.RtException;
import com.qcloud.cos.COSClient;
import com.qcloud.cos.ClientConfig;
import com.qcloud.cos.auth.BasicCOSCredentials;
import com.qcloud.cos.model.PutObjectRequest;
import com.qcloud.cos.model.PutObjectResult;
import com.qcloud.cos.region.Region;
import org.apache.commons.io.IOUtils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

/**
 * @author panda.
 * @since 2017-09-30 11:13.
 */
public class CloudStorageQcloudService  extends CloudStorageService {
    private COSClient client;

    public CloudStorageQcloudService(CloudStorage config){
        this.config = config;
        init();
    }

    @Override
    public void init() {
        BasicCOSCredentials credentials = new BasicCOSCredentials(config.getQcloudAppId(), config.getQcloudSecretId(),
                config.getQcloudSecretKey());

        //初始化客户端配置 //设置bucket所在的区域，华南：gz 华北：tj 华东：sh
        ClientConfig clientConfig = new ClientConfig(new Region(config.getQcloudRegion()));
        client = new COSClient(credentials,clientConfig);
    }

    @Override
    public String upload(byte[] data, String path) {
        //腾讯云必需要以"/"开头
        if(!path.startsWith("/")) {
            path = "/" + path;
        }

        //上传到腾讯云
        PutObjectRequest putObjectRequest = new PutObjectRequest(config.getQcloudBucketName(), config.getQcloudPrefix(), new File(path));
        PutObjectResult result = client.putObject(putObjectRequest);

       /* JSONObject jsonObject = JSON.parseObject(putObjectRequest.);
        if(jsonObject.getIntValue("code") != 0) {
            throw new RtException("文件上传失败，" + jsonObject.getString("message"));
        }*/

        return config.getQcloudDomain() + path;
    }

    @Override
    public String upload(InputStream inputStream, String path) {
        try {
            byte[] data = IOUtils.toByteArray(inputStream);
            return this.upload(data, path);
        } catch (IOException e) {
            throw new RtException("上传文件失败", e);
        }
    }

    @Override
    public String upload(byte[] data) {
        return upload(data, getPath(config.getQcloudPrefix()));
    }

    @Override
    public String upload(InputStream inputStream) {
        return upload(inputStream, getPath(config.getQcloudPrefix()));
    }
}
