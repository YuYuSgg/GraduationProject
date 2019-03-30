package com.yuyu.service.Impl;

import com.yuyu.service.IFileService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

public class FileServiceImpl implements IFileService {

    private Logger logger = LoggerFactory.getLogger(FileServiceImpl.class);

    @Override
    public String upload(MultipartFile file, String path) {
        String fileName = file.getOriginalFilename();
        //获取扩展名
        String fileExtensionName = fileName.substring(fileName.lastIndexOf("."));
        //
        String uploadfileName = UUID.randomUUID().toString() + "" + fileExtensionName;
        logger.info("开始上传文件，上传文件的文件名:{},上传的路径:{},新文件名:{}", fileName, path, uploadfileName);
        File fileDir = new File(path);
        if (!fileDir.exists()) {
            fileDir.setExecutable(true);
            fileDir.mkdirs();
        }
        File targetFile = new File(path, uploadfileName);
        try {
            file.transferTo(targetFile);
            //todo 将targetFile上传到我们的FTP服务器上

            //todo 上传完之后，删除upload下面的文件
        } catch (IOException e) {
            logger.error("上传文件异常", e);
            return null;
        }
        return targetFile.getName();
    }
}