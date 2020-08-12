package com.github.zhaojiacan.fileupload.compent;


import com.github.zhaojiacan.fileupload.pojo.FileInfo;
import com.github.zhaojiacan.fileupload.pojo.ResultBody;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;

/**
 * @classname: FileUpload
 * @author: zhaojiacan
 * @description: ${description}
 * @date: 2019/7/29 14:20
 * @version:1.0
 */
public interface FileUpload {
    ResultBody<FileInfo> upload(File file) throws Exception;

    ResultBody<FileInfo> upload(File file,String fileKey) throws Exception;

    ResultBody<FileInfo> upload(MultipartFile file) throws Exception;

    ResultBody<FileInfo> upload(MultipartFile file,String fileKey) throws Exception;

    ResultBody<FileInfo>  delete(FileInfo fileInfo);
}
