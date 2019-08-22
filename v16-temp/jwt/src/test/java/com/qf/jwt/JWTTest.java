package com.qf.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.junit.Test;

import java.util.Date;

/**
 * @author xiaoxinmin
 * @Date 2019/8/17
 */
public class JWTTest {

    @Test
    public void creatToken(){
        JwtBuilder jwtBuilder= Jwts.builder().setId("1").setSubject("咸鱼").setIssuedAt(new Date())
                //设置自定义属性
                .claim("role", "student")
                .setExpiration(new Date(new Date().getTime()+6000000))
                .signWith(SignatureAlgorithm.HS256,"javaWebToken");

        String compact = jwtBuilder.compact();
        System.out.println(compact);
    }

    @Test
    public void jwtTokenParseTest(){
        String token = "eyJhbGciOiJIUzI1NiJ9.eyJqdGkiOiIxIiwic3ViIjoi5ZK46bG8IiwiaWF0IjoxNTY2MDA5MDk1LCJyb2xlIjoic3R1ZGVudCIsImV4cCI6MTU2NjAxNTA5NX0.A49ljAgX6s8hmvpdE0zIOSsY_lbj5NbNxjjp68WDTUE";

        Claims claims = Jwts.parser().setSigningKey("javaWebToken")
                .parseClaimsJws(token).getBody();

        System.out.println(claims.getId());
        System.out.println(claims.getSubject());
        System.out.println(claims.getIssuedAt());
        System.out.println(claims.getExpiration());
        //获取属性
        System.out.println(claims.get("role"));

    }


}
