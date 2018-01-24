package com.yy.blog.article.controller;

import com.github.tobato.fastdfs.domain.StorePath;
import com.github.tobato.fastdfs.service.FastFileStorageClient;
import org.apache.commons.io.FilenameUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * @author liwenxing
 * @date 2018/1/24 10:13
 */
@RestController
@RequestMapping(value = "file")
public class FileController {

    @Value("${fdfs.trackerList}")
    private String fastdfs_url;

    @Autowired
    private FastFileStorageClient storageClient;

    private final static Logger LOGGER = Logger.getLogger(FileController.class);



    /**
     * 图片上传
     * @author liwenxing
     * @date 2018-1-23 22:37:15
     * @param multipartFile
     * @return
     * @throws IOException
     */
    @RequestMapping(value = "/uploadFile")
    public String uploadFile(@RequestParam("file") MultipartFile multipartFile) throws IOException {
        StorePath storePath = storageClient.uploadFile(multipartFile.getInputStream(), multipartFile.getSize(), FilenameUtils.getExtension(multipartFile.getOriginalFilename()), null);
        String url = "http://"+fastdfs_url.split(":")[0] + "/" + storePath.getFullPath();
        LOGGER.info("文件上传成功，文件访问地址：" + url);
        return url;
    }

}