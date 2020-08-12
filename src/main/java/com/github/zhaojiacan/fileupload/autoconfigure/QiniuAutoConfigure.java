package com.github.zhaojiacan.fileupload.autoconfigure;


import cn.hutool.json.JSONUtil;
import com.github.zhaojiacan.fileupload.compent.AbstractFileUpload;
import com.github.zhaojiacan.fileupload.pojo.FileInfo;
import com.github.zhaojiacan.fileupload.pojo.ResultBody;
import com.github.zhaojiacan.fileupload.propeties.FileServerProperties;
import com.qiniu.common.QiniuException;
import com.qiniu.common.Zone;
import com.qiniu.http.Response;
import com.qiniu.storage.BucketManager;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.DefaultPutRet;
import com.qiniu.util.Auth;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;

/**
 * @classname: QiniuOSSAutoConfigure
 * @author: zhaojiacan
 * @description: 七牛云自动配置
 * @date: 2019/7/29 14:14
 * @version:1.0
 */

@Configuration
@ConditionalOnProperty(name = "file-server.type", havingValue = "qiniu")
@EnableConfigurationProperties(FileServerProperties.class)
public class QiniuAutoConfigure {

    @Autowired
    private FileServerProperties fileProperties;

    /**
     * 华南机房
     */
    @Bean
    public com.qiniu.storage.Configuration qiniuConfig() {
        return new com.qiniu.storage.Configuration(Zone.zone2());
    }

    /**
     * 构建一个七牛上传工具实例
     */
    @Bean
    public UploadManager uploadManager() {
        return new UploadManager(qiniuConfig());
    }

    /**
     * 认证信息实例
     *
     * @return
     */
    @Bean
    public Auth auth() {
        return Auth.create(fileProperties.getQiniu().getAccessKey(), fileProperties.getQiniu().getSecretKey());
    }

    /**
     * 构建七牛空间管理实例
     */
    @Bean
    public BucketManager bucketManager() {
        return new BucketManager(auth(), qiniuConfig());
    }

    @Service
    public class QiniuFileUpload extends AbstractFileUpload {

        private final Logger log = LoggerFactory.getLogger(LocalAutoConfigure.class);

        @Autowired
        private UploadManager uploadManager;
        @Autowired
        private BucketManager bucketManager;
        @Autowired
        private Auth auth;


        @Override
        protected ResultBody<FileInfo> uploadFile(MultipartFile file, FileInfo fileInfo) throws Exception {
            // 调用put方法上传
            Response response = uploadManager.put(file.getBytes(), fileInfo.getName(), auth.uploadToken(fileProperties.getQiniu().getBucketName(),fileInfo.getName()));
            try {
                //解析上传成功的结果
                DefaultPutRet putRet = JSONUtil.toBean(response.bodyString(), DefaultPutRet.class);
                System.out.println(putRet.key);
                System.out.println(putRet.hash);
                fileInfo.setUrl(fileProperties.getQiniu().getEndpoint() + "/" + fileInfo.getName());
                return ResultBody.success(fileInfo);
            } catch (QiniuException ex) {
                Response r = ex.response;
                log.error(r.toString());
                return ResultBody.failed("文件上传失败");
            }
        }

        @Override
        protected ResultBody<FileInfo> uploadFile(File file, FileInfo fileInfo) throws Exception {
            // 调用put方法上传
            Response response = uploadManager.put(file, fileInfo.getName(), auth.uploadToken(fileProperties.getQiniu().getBucketName(),fileInfo.getName()));
            try {
                //解析上传成功的结果
                DefaultPutRet putRet = JSONUtil.toBean(response.bodyString(), DefaultPutRet.class);
                System.out.println(putRet.key);
                System.out.println(putRet.hash);
                fileInfo.setUrl(fileProperties.getQiniu().getEndpoint() + "/" + fileInfo.getName());
                return ResultBody.success(fileInfo);
            } catch (QiniuException ex) {
                Response r = ex.response;
                log.error(r.toString());
                return ResultBody.failed("文件上传失败");
            }
        }

        @Override
        public ResultBody<FileInfo> delete(FileInfo fileInfo) {
            try {
                Response response = bucketManager.delete(fileProperties.getQiniu().getBucketName(), fileInfo.getUrl());
                int retry = 0;
                while (response.needRetry() && retry++ < 3) {
                    response = bucketManager.delete(fileProperties.getQiniu().getBucketName(), fileInfo.getUrl());
                }
            } catch (QiniuException e) {
                return ResultBody.failed("文件删除失败");
            }
            return  ResultBody.success(fileInfo);
        }
    }
}
