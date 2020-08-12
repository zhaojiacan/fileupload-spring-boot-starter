package com.github.zhaojiacan.fileupload.compent;

import com.github.zhaojiacan.fileupload.pojo.FileInfo;
import com.github.zhaojiacan.fileupload.pojo.ResultBody;
import com.github.zhaojiacan.fileupload.util.FileUtil;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;

/**
 * @classname: AbstractIFileService
 * @author: zhaojiacan
 * @description: 文件上传抽象类
 * @date: 2019/7/29 14:25
 * @version:1.0
 */
public abstract class AbstractFileUpload implements FileUpload {

    private static final String FILE_SPLIT = ".";

    @Override
    public ResultBody<FileInfo> upload(File file) throws Exception {
        FileInfo fileInfo = FileUtil.getFileInfo(file);
        if (!fileInfo.getName().contains(".")) {
            throw new IllegalArgumentException("缺少后缀名");
        } else {
            ResultBody<FileInfo> uploadFile = this.uploadFile(file, fileInfo);
            return uploadFile;
        }
    }

    @Override
    public ResultBody<FileInfo> upload(File file, String fileName) throws Exception {
        FileInfo fileInfo = FileUtil.getFileInfo(file);
        if (!fileInfo.getName().contains(".")) {
            throw new IllegalArgumentException("缺少后缀名");
        } else {
            fileInfo.setName(fileName);
            ResultBody<FileInfo> uploadFile = this.uploadFile(file, fileInfo);
            return uploadFile;
        }
    }

    /**
    * 功能描述：文件上传
    * @author zhaojiacan
    * @date 2019/7/29
    * @param file
    * @return com.za.zacms.model.file.FileInfo
    */
    @Override
    public ResultBody<FileInfo> upload(MultipartFile file) throws Exception {
        FileInfo fileInfo = FileUtil.getFileInfo(file);
        if (!fileInfo.getName().contains(FILE_SPLIT)) {
            throw new IllegalArgumentException("缺少后缀名");
        }
        ResultBody<FileInfo> uploadFile = uploadFile(file, fileInfo);
        return uploadFile;
    }

    /**
     * 功能描述：文件上传
     * @author zhaojiacan
     * @date 2019/7/29
     * @param file
     * @return com.za.zacms.model.file.FileInfo
     */
    @Override
    public ResultBody<FileInfo> upload(MultipartFile file,String fileName) throws Exception {
        FileInfo fileInfo = FileUtil.getFileInfo(file);
        if (!fileInfo.getName().contains(FILE_SPLIT)) {
            throw new IllegalArgumentException("缺少后缀名");
        }
        fileInfo.setName(fileName);
        ResultBody<FileInfo> uploadFile = uploadFile(file, fileInfo);
        return uploadFile;
    }


    /**
    * 功能描述：文件上传
    * @author zhaojiacan
    * @date 2019/7/29
    * @param file
    * @param fileInfo
    * @return void
    */
    protected abstract ResultBody<FileInfo> uploadFile(MultipartFile file, FileInfo fileInfo) throws Exception;

    /**
     * 功能描述：文件上传
     * @author zhaojiacan
     * @date 2019/7/29
     * @param file
     * @param fileInfo
     * @return void
     */
    protected abstract ResultBody<FileInfo> uploadFile(File file, FileInfo fileInfo) throws Exception;
    
}
