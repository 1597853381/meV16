package com.qf.springbootemail;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.ParentRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.junit4.statements.RunBeforeTestClassCallbacks;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SpringbootEmailApplicationTests {

    @Autowired
    private JavaMailSender mailSender;

//    @Value("mail:fromAddr")
//    private String froAddr;

    @Test
    public void contextLoads() {
        SimpleMailMessage mailMessage=new SimpleMailMessage();
        mailMessage.setFrom("13387902138@163.com");
        mailMessage.setTo("1318920837@qq.com");
        mailMessage.setSubject("标题1");
        mailMessage.setText("内容");

        mailSender.send(mailMessage);

    }

}
