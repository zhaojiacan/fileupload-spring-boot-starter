package com.github.zhaojiacan.fileupload.propeties;

import cn.hutool.core.util.StrUtil;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @classname: FileServerProperties
 * @author: zhaojiacan
 * @description: 文件上传配置属性
 * @date: 2019/7/29 14:31
 * @version:1.0
 */
@Component
@ConfigurationProperties(prefix = "file-server")
public class FileServerProperties {

    /**
     * 为以下3个值，指定不同的自动化配置
     * qiniu：七牛oss
     * aliyun：阿里云oss
     * local：真奥科技文件服务
     */
    private String type="local";

    private String accessPath;


    public String getAccessPath() {
        switch (type){
            case "qiniu":
                accessPath=qiniu.getEndpoint();
                break;
            case "alioss":
                accessPath=null;
                String endpoint=alioss.getEndpoint();
                if(StrUtil.isNotBlank(endpoint)){
                    String[] split = endpoint.split("//");
                    if(split.length==2){
                        String bucketName = alioss.getBucketName();
                        accessPath = new StringBuffer(split[0]).append("//").append(bucketName).append(".").append(split[1]).toString();
                    }
                }
                break;
            case "local":
                accessPath=local.getEndpoint()+local.getFilDir();
                break;
            default:
                accessPath=local.getEndpoint()+local.getFilDir();
        }
        return accessPath;
    }

    /**
     * qiniu配置
     */
    QiniuProperties qiniu = new QiniuProperties();

    /**
     * alioss配置
     */
    AliyunOssProperties alioss = new AliyunOssProperties();

    /**
     * 本地上传配置
     */
    LocalProperties local = new LocalProperties();



    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setAccessPath(String accessPath) {
        this.accessPath = accessPath;
    }

    public QiniuProperties getQiniu() {
        return qiniu;
    }

    public void setQiniu(QiniuProperties qiniu) {
        this.qiniu = qiniu;
    }

    public LocalProperties getLocal() {
        return local;
    }

    public void setLocal(LocalProperties local) {
        this.local = local;
    }

    public AliyunOssProperties getAlioss() {
        return alioss;
    }

    public void setAlioss(AliyunOssProperties alioss) {
        this.alioss = alioss;
    }
}
