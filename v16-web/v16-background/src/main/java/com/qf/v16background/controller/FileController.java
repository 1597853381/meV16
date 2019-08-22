package com.qf.v16background.controller;

import com.github.tobato.fastdfs.domain.StorePath;
import com.github.tobato.fastdfs.service.FastFileStorageClient;
import com.qf.v16.common.base.pojo.ResultBean;
import com.qf.v16.common.base.pojo.WangEditorResultBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * @author xiaoxinmin
 * @Date 2019/8/7
 * 处理文件的服务接口
 */
@RestController
@RequestMapping("file")
public class FileController {

    @Autowired
    private FastFileStorageClient client;

    @Value("${image.server}")
    private String imageServer;

    @RequestMapping("imageUpload")
    @ResponseBody
    public ResultBean imageUpload(MultipartFile file) {
        //System.out.println(file);
        InputStream inputStream = null;
        try {
            inputStream = file.getInputStream();
            String originalFilename = file.getOriginalFilename();
            String extName = originalFilename.substring(originalFilename.lastIndexOf(".") + 1);
            StorePath storePath = client.uploadImageAndCrtThumbImage(inputStream, file.getSize(), extName, null);
            //传递给客户端展示
            String fullPath = storePath.getFullPath();
            return new ResultBean("200", fullPath);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new ResultBean("500", "上传失败");
    }

    @RequestMapping("multiUpload")
    @ResponseBody
    public WangEditorResultBean multiUpload(MultipartFile[] files) {
        String[] data = new String[files.length];
        for (int i = 0; i < files.length; i++) {
            MultipartFile file = files[i];
            String originalFilename = file.getOriginalFilename();
            String extName = originalFilename.substring(originalFilename.lastIndexOf(".") + 1);
            try {
                InputStream inputStream = file.getInputStream();
                StorePath storePath = client.uploadImageAndCrtThumbImage(inputStream, file.getSize(), extName, null);
                String fullPath = storePath.getFullPath();
                data[i] = imageServer + fullPath;
                System.out.println(storePath);
            } catch (IOException e) {
                e.printStackTrace();
                return new WangEditorResultBean("-1", null);
            }
        }
        return new WangEditorResultBean("0", data);
    }
}
