package com.ml.service.sys;

import com.ml.entity.Page;
import com.ml.entity.Query;
import com.ml.mapper.sys.GeneratorDao;
import com.ml.utils.Generators;
import com.ml.utils.Pages;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipOutputStream;

/**
 * @author panda.
 * @since 2017-09-06 15:21.
 */
@Service("generatorService")
public class GeneratorService {
    @Autowired
    private GeneratorDao generatorDao;

    public Page findPage(Map<String, Object> params) {
        Query query = new Query(params);
        return Pages.findPage(generatorDao.findList(query), query);
    }

    public Map<String, String> queryTable(String tableName) {
        return generatorDao.findTable(tableName);
    }

    public List<Map<String, String>> queryColumns(String tableName) {
        return generatorDao.findColumns(tableName);
    }


    public byte[] generatorCode(String[] tableNames) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        ZipOutputStream zip = new ZipOutputStream(outputStream);

        for (String tableName : tableNames) {
            //查询表信息
            Map<String, String> table = queryTable(tableName);
            //查询列信息
            List<Map<String, String>> columns = queryColumns(tableName);
            //生成代码
            Generators.generatorCode(table, columns, zip);
        }
        IOUtils.closeQuietly(zip);
        return outputStream.toByteArray();
    }
}
