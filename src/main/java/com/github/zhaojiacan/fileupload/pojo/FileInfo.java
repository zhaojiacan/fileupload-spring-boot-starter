package com.github.zhaojiacan.fileupload.pojo;

import java.util.Date;

/**
 * @classname: FileInfo
 * @author: zhaojiacan
 * @description: file实体类
 * @date: 2020/6/2 16:40
 * @version:1.0
 */
public class FileInfo {

    /**
     * 文件标识id
     */
    private String id;

    /**
     * 原始文件名
     */
    private String name;
    /**
     * 是否图片
     */
    private Boolean isImg;

    /**
     * 上传文件类型
     */
    private String contentType;

    /**
     * 上传文件具体类型
     */
    private String fileType;

    /**
     * 文件大小
     */
    private long size;

    /**
     * oss访问路径
     */
    private String url;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 修改时间
     */
    private Date updateTime;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getImg() {
        return isImg;
    }

    public void setImg(Boolean img) {
        isImg = img;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    public long getSize() {
        return size;
    }

    public void setSize(long size) {
        this.size = size;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}
