package com.qf.springdataredis;

import com.qf.spring_data_redis.Student;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SessionCallback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.concurrent.TimeUnit;

/**
 * @author xiaoxinmin
 * @Date 2019/8/14
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:redis.xml")
public class RedisTest {

    @Autowired
    private RedisTemplate redisTemplate;

    @Test
    public void testString(){
        redisTemplate.opsForValue().set("k101", "v101");
        Object k100 = redisTemplate.opsForValue().get("k101");
        System.out.println(k100);

        Student student=new Student(1L,"lisi");
        redisTemplate.opsForValue().set("student", student);
        Object student1 = redisTemplate.opsForValue().get("student");
        System.out.println(student1);

    }

    @Test
    public void testChaoShi(){
        redisTemplate.execute(new SessionCallback() {
            public Object execute(RedisOperations operations) throws DataAccessException {
                operations.opsForValue().set("k100000", "v100000");
                operations.expire("k1", 60, TimeUnit.SECONDS);
                Long expire = operations.getExpire("k100000", TimeUnit.SECONDS);//ttl
                System.out.println("还有多久过期:"+expire);
                operations.persist("k100000");
                expire = operations.getExpire("k100000", TimeUnit.SECONDS);
                System.out.println("还有多久过期:"+expire);
                return null;
            }
        });

    }
}
