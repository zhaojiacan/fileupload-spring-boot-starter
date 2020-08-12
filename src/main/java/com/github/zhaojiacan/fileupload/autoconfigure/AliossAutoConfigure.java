package com.github.zhaojiacan.fileupload.autoconfigure;


import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.github.zhaojiacan.fileupload.compent.AbstractFileUpload;
import com.github.zhaojiacan.fileupload.pojo.FileInfo;
import com.github.zhaojiacan.fileupload.pojo.ResultBody;
import com.github.zhaojiacan.fileupload.propeties.FileServerProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.File;

/**
 * @classname: QiniuOSSAutoConfigure
 * @author: zhaojiacan
 * @description: 阿里云oss自动配置
 * @date: 2019/7/29 14:14
 * @version:1.0
 */

@Configuration
@ConditionalOnProperty(name = "file-server.type", havingValue = "alioss")
@EnableConfigurationProperties(FileServerProperties.class)
public class AliossAutoConfigure {

    @Autowired
    private FileServerProperties fileProperties;

    /**
     * 创建aliyunoss 客户端
     */
    @Bean
    public OSS oss() {
        String endpoint = fileProperties.getAlioss().getEndpoint();
        String accessKey = fileProperties.getAlioss().getAccessKey();
        String secretKey = fileProperties.getAlioss().getSecretKey();
        return new OSSClientBuilder().build(endpoint, accessKey, secretKey);
    }

    @Service
    public class AliossFileUpload extends AbstractFileUpload {

        private final Logger log = LoggerFactory.getLogger(LocalAutoConfigure.class);

        @Autowired
        private OSS oss;

        @Override
        protected ResultBody<FileInfo> uploadFile(MultipartFile file, FileInfo fileInfo) throws Exception {
            // 调用put方法上传
            try{
                oss.putObject(fileProperties.getAlioss().getBucketName(), fileInfo.getName(), new ByteArrayInputStream(file.getBytes()));
                fileInfo.setUrl(fileProperties.getAccessPath() + "/" + fileInfo.getName());
                return ResultBody.success(fileInfo);
            }catch (Exception e){
                return ResultBody.failed("文件上传失败");
            }finally {
                oss.shutdown();
            }


        }

        @Override
        protected ResultBody<FileInfo> uploadFile(File file, FileInfo fileInfo) throws Exception {
            // 调用put方法上传
            try{
                oss.putObject(fileProperties.getAlioss().getBucketName(), fileInfo.getName(), file);
                fileInfo.setUrl(fileProperties.getAccessPath() + "/" + fileInfo.getName());
                return ResultBody.success(fileInfo);
            }catch (Exception e){
                return ResultBody.failed("文件上传失败");
            }finally {
                oss.shutdown();
            }
        }

        @Override
        public ResultBody<FileInfo> delete(FileInfo fileInfo) {
            try {
                oss.deleteObject(fileProperties.getAlioss().getEndpoint(), fileInfo.getUrl());
            } catch (Exception e) {
                return ResultBody.failed("文件删除失败");
            }
            return  ResultBody.success(fileInfo);
        }
    }
}
