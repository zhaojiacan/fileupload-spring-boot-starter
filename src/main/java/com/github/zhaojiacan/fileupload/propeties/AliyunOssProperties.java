package com.github.zhaojiacan.fileupload.propeties;

/**
 * @classname: QiniuProperties
 * @author: zhaojiacan
 * @description: 七牛云相关配置
 * @date: 2019/7/29 13:55
 * @version:1.0
 */
public class AliyunOssProperties {

    /**
     * 密钥key
     */
    private String accessKey;

    /**
     * 密钥密码
     */
    private String secretKey;

    /**
     * 端点
     */
    private String endpoint;

    /**
     * bucket名称
     */
    private String bucketName;

    public String getAccessKey() {
        return accessKey;
    }

    public void setAccessKey(String accessKey) {
        this.accessKey = accessKey;
    }

    public String getSecretKey() {
        return secretKey;
    }

    public void setSecretKey(String secretKey) {
        this.secretKey = secretKey;
    }

    public String getEndpoint() {
        return endpoint;
    }

    public void setEndpoint(String endpoint) {
        this.endpoint = endpoint;
    }

    public String getBucketName() {
        return bucketName;
    }

    public void setBucketName(String bucketName) {
        this.bucketName = bucketName;
    }
}
