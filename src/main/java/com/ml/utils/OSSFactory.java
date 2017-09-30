package com.ml.utils;

import com.ml.constants.Constant;
import com.ml.entity.sys.CloudStorage;
import com.ml.enums.SysEnums.CloudTypeEnum;
import com.ml.service.sys.*;

/**
 * @author panda.
 * @since 2017-09-30 15:40.
 */
public final class OSSFactory {
    private static ConfigService configService;

    static {
        OSSFactory.configService = (ConfigService) SpringContexts.getBean("configService");
    }

    public static CloudStorageService build(){
        //获取云存储配置信息
        CloudStorage config = configService.getConfigObject(Constant.CLOUD_STORAGE_CONFIG_KEY, CloudStorage.class);

        if(CloudTypeEnum.QINIU.eq(config.getType())){
            return new CloudStorageQiniuService(config);
        }else if(CloudTypeEnum.ALIYUN.eq(config.getType())){
            return new CloudStorageAliyunService(config);
        }else if(CloudTypeEnum.QCLOUD.eq(config.getType())){
            return new CloudStorageQcloudService(config);
        }

        return null;
    }
}
