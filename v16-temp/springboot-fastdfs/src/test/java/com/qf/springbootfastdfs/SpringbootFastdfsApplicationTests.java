package com.qf.springbootfastdfs;

import com.github.tobato.fastdfs.domain.StorePath;
import com.github.tobato.fastdfs.service.FastFileStorageClient;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SpringbootFastdfsApplicationTests {


    @Autowired
    private FastFileStorageClient client;

    @Test
    public void testUpload() throws FileNotFoundException {
        File file=new File("D:\\项目\\v16\\v16-temp\\springboot-fastdfs\\1.jpg");
        FileInputStream inputStream=new FileInputStream(file);
//        String extName[] = file.getName().split(".");
//        for (String s : extName) {
//            System.out.println(s.toString());
//        }
        //System.out.println(extName);
        StorePath storePath =
                client.uploadImageAndCrtThumbImage(
                        inputStream, file.length(), "jpg", null);
        System.out.println(storePath.getFullPath());
        System.out.println(storePath.getGroup());
        System.out.println(storePath.getPath());


    }

}
