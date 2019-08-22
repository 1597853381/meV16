package com.qf.springbootfreemarker;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SpringbootFreemarkerApplicationTests {

    @Autowired
    private Configuration configuration;

    @Test
    public void creatTest() throws IOException, TemplateException {
        Template template = configuration.getTemplate("freemarker.ftl");
        Map<String,Object> data=new HashMap<>();
        data.put("username", "java");
        //1.单个
        FileWriter outputStream=new FileWriter("D:\\项目\\v16\\v16-temp\\springboot-freemarker\\src\\main\\resources\\templates\\freemarker.html");
        //2.对象
        Product product=new Product(1L,"枸杞",100L,new Date());
        data.put("product", product);
        //3.存储集合
        List<Product> productList=new ArrayList<>();
        productList.add(new Product(1L,"枸杞1",100L,new Date()));
        productList.add(new Product(2L,"枸杞2",100L,new Date()));
        productList.add(new Product(3L,"枸杞3",100L,new Date()));
        productList.add(new Product(4L,"枸杞4",100L,new Date()));
        data.put("productList", productList);
        //4.逻辑
        data.put("age",30);
        //5.空对象
        data.put("nullObject", null);
        template.process(data,outputStream);
        System.out.println("success!!!");
    }

}
