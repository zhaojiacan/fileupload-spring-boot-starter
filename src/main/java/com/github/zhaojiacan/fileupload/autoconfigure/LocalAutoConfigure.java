package com.github.zhaojiacan.fileupload.autoconfigure;

import com.github.zhaojiacan.fileupload.compent.AbstractFileUpload;
import com.github.zhaojiacan.fileupload.pojo.FileInfo;
import com.github.zhaojiacan.fileupload.pojo.ResultBody;
import com.github.zhaojiacan.fileupload.propeties.FileServerProperties;
import com.github.zhaojiacan.fileupload.util.FileUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;

/**
 * @classname: LocalAutoConfigure
 * @author: zhaojiacan
 * @description: 本地文件存储自动配置
 * @date: 2020/4/2 14:40
 * @version:1.0
 */
@Configuration
@ConditionalOnProperty(name = "file-server.type", havingValue = "local")
@EnableConfigurationProperties(FileServerProperties.class)
public class LocalAutoConfigure {

    private final static Logger log = LoggerFactory.getLogger(LocalAutoConfigure.class);

    @Autowired
    private FileServerProperties fileProperties;

    @Service
    public class LocalFileUpload extends AbstractFileUpload {

        @Override
        protected ResultBody<FileInfo> uploadFile(MultipartFile file, FileInfo fileInfo){
            try {
                String filePath=fileProperties.getLocal().getFilePath()+"/"+fileInfo.getName();
                String s = FileUtil.saveFile(file, filePath);
                if(s==null){
                    return ResultBody.failed("文件上传失败");
                }
                fileInfo.setUrl(fileInfo.getName());
                return ResultBody.success(fileInfo);
            } catch (Exception ex) {
                log.error("本地文件上传失败",ex);
                return ResultBody.failed("文件上传失败");
            }
        }

        @Override
        protected ResultBody<FileInfo> uploadFile(File file, FileInfo fileInfo) throws Exception {
            try {
                String filePath=fileProperties.getLocal().getFilePath()+"/"+fileInfo.getName();
                File newFile = cn.hutool.core.io.FileUtil.newFile(filePath);
                File copy = cn.hutool.core.io.FileUtil.copy(file, newFile,true);
                if(copy==null){
                    return ResultBody.failed("文件上传失败");
                }
                fileInfo.setUrl(fileInfo.getName());
                return ResultBody.success(fileInfo);
            } catch (Exception ex) {
                log.error("本地文件上传失败",ex);
                return ResultBody.failed("文件上传失败");
            }
        }

        @Override
        public ResultBody<FileInfo> delete(FileInfo fileInfo) {
            try {
                String filePath=fileProperties.getLocal().getFilePath()+fileInfo.getName();
                boolean b = FileUtil.deleteFile(filePath);
                if(b){
                    return ResultBody.failed("文件删除失败");
                }

            } catch (Exception ex) {
                log.error("本地文件删除失败",ex);
                return ResultBody.failed("本地文件删除失败");
            }
            return ResultBody.success("abc");
        }
    }
}
