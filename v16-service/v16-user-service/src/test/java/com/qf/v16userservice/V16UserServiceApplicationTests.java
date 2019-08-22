package com.qf.v16userservice;

import com.qf.v16.common.base.pojo.ResultBean;
import com.qf.v16.entity.TUser;
import com.qf.v16.service.IUserService;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;

@RunWith(SpringRunner.class)
@SpringBootTest
public class V16UserServiceApplicationTests {

    @Autowired
    private IUserService userService;


    @Test
    public void testLogin() {
        TUser user=new TUser();
        user.setUsername("xxm");
        user.setPassword("123");
        ResultBean resultBean = userService.checkLogin(user);
        System.out.println(resultBean.getData());
    }

    @Test
    public void testIsLogin(){
        String uuid="eyJhbGciOiJIUzI1NiJ9.eyJqdGkiOiIxIiwic3ViIjoieHhtIiwiaWF0IjoxNTY2MDEwOTI4LCJleHAiOjE1NjYwMTI3Mjh9.514sSwywvCnhtuSmiuXehL7RBvNLU5PlqfmCeaBR2DY";
        ResultBean resultBean = userService.checkIsLogin(uuid);
        System.out.println(resultBean.getData());
    }

    @Test
    public void jwtTokenCreateTest(){
        JwtBuilder builder = Jwts.builder()
                .setId("666").setSubject("行走在牛A的路上")
                .setIssuedAt(new Date())
                //添加自定义属性
                .claim("role","admin")
                .setExpiration(new Date(new Date().getTime()+600000))
                .signWith(SignatureAlgorithm.HS256,"huangguizhao");

        String jwtToken = builder.compact();
        System.out.println(jwtToken);
    }
}
