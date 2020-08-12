package com.github.zhaojiacan.fileupload.propeties;


/**
 * @classname: QiniuProperties
 * @author: zhaojiacan
 * @description: 七牛云相关配置
 * @date: 2019/7/29 13:55
 * @version:1.0
 */
public class LocalProperties {
    public static String sysPath=System.getProperty("user.dir");

    private String endpoint="http://127.0.0.1:8080";

    private String filePath=sysPath+"/upload";

    private String filDir="/upload";

    public String getEndpoint() {
        return endpoint;
    }

    public void setEndpoint(String endpoint) {
        this.endpoint = endpoint;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public String getFilDir() {
        return filDir;
    }

    public void setFilDir(String filDir) {
        this.filDir = filDir;
    }
}
