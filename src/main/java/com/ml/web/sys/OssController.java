package com.ml.web.sys;

import com.alibaba.fastjson.JSON;
import com.ml.constants.Constant;
import com.ml.entity.R;
import com.ml.entity.sys.CloudStorage;
import com.ml.entity.sys.Oss;
import com.ml.enums.SysEnums.CloudTypeEnum;
import com.ml.exception.RtException;
import com.ml.service.sys.ConfigService;
import com.ml.service.sys.OssService;
import com.ml.utils.OSSFactory;
import com.ml.utils.validator.AliyunGroup;
import com.ml.utils.validator.QcloudGroup;
import com.ml.utils.validator.QiniuGroup;
import com.ml.utils.validator.Validators;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author Panda.HuangWei313.
 * @since 2017-09-30 10:33.
 */
@RestController
@RequestMapping("sys/oss")
public class OssController {
    private final static String KEY = Constant.CLOUD_STORAGE_CONFIG_KEY;

    @Autowired
    private OssService ossService;
    @Autowired
    private ConfigService configService;



    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("sys:oss:all")
    public R list(@RequestParam Map<String, Object> params){
        //查询列表数据
        return R.success().putPage(ossService.findPage(params));
    }


    /**
     * 云存储配置信息
     */
    @RequestMapping("/config")
    @RequiresPermissions("sys:oss:all")
    public R config(){
        CloudStorage config = configService.getConfigObject(KEY, CloudStorage.class);
        return R.success().put("config", config);
    }


    /**
     * 保存云存储配置信息
     */
    @RequestMapping("/saveConfig")
    @RequiresPermissions("sys:oss:all")
    public R saveConfig(@RequestBody CloudStorage config){
        //校验类型
        Validators.validateEntity(config);

        if(CloudTypeEnum.QINIU.eq(config.getType())){
            //校验七牛数据
            Validators.validateEntity(config, QiniuGroup.class);
        }else if(CloudTypeEnum.ALIYUN.eq(config.getType())){
            //校验阿里云数据
            Validators.validateEntity(config, AliyunGroup.class);
        }else if(CloudTypeEnum.QCLOUD.eq(config.getType())){
            //校验腾讯云数据
            Validators.validateEntity(config, QcloudGroup.class);
        }
        configService.updateValueByKey(KEY, JSON.toJSONString(config));
        return R.success();
    }


    /**
     * 上传文件
     */
    @RequestMapping("/upload")
    @RequiresPermissions("sys:oss:all")
    public R upload(@RequestParam("file") MultipartFile file) throws Exception {
        if (file.isEmpty()) {
            throw new RtException("上传文件不能为空");
        }

        //上传文件
        String url = OSSFactory.build().upload(file.getBytes());

        //保存文件信息
        Oss ossEntity = new Oss();
        ossEntity.setUrl(url);
        ossEntity.setCreateDt(new Date());
        ossService.save(ossEntity);

        return R.success().put("url", url);
    }


    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("sys:oss:all")
    public R delete(@RequestBody Long[] ids){
        ossService.deleteBatch(ids);
        return R.success();
    }

}
