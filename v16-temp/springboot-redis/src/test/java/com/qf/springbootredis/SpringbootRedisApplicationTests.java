package com.qf.springbootredis;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SpringbootRedisApplicationTests {

//    @Autowired
//    private RedisTemplate redisTemplate;

    @Resource(name = "objectRedisTemplate1")
    private RedisTemplate<String,Object> redisTemplate;

    @Test
    public void stringTest() {

        redisTemplate.opsForValue().set("l1", "l1");
        Object l1 = redisTemplate.opsForValue().get("l1");
        System.out.println(l1);
    }

    @Test
    public void testTemplate() {

        redisTemplate.opsForValue().set("l1", "l1");
        Object l1 = redisTemplate.opsForValue().get("l1");
        System.out.println(l1);

        Student student=new Student(1L,"list");
        redisTemplate.opsForValue().set("student", student);
        Object student1 = redisTemplate.opsForValue().get("student");
        System.out.println(student1);

    }

}
