package com.qianfeng.jedis;

import org.junit.Test;
import redis.clients.jedis.Jedis;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author xiaoxinmin
 * @Date 2019/8/14
 */
public class JedisTest {

    @Test
    public void stringTest(){
        //1.建立和redis服务器的连接
        Jedis jedis=new Jedis("39.107.252.103",6379);
        jedis.auth("123");
        /*172.24.11.132*/
        jedis.set("key1", "value1");
        String key1 = jedis.get("key1");
        System.out.println(key1);
    }

    @Test
    public void hashTest(){
        //1.建立和redis服务器的连接
        Jedis jedis=new Jedis("39.107.252.103",6379);
        jedis.auth("123");

        jedis.hset("key2", "name","mayun");
        String key1 = jedis.hget("key2","name");
        Map<String,String> map=new HashMap<String, String>();
        map.put("name", "mayun");
        map.put("age", "11");
        jedis.hmset("key3",map);
        Map<String, String> key3 = jedis.hgetAll("key3");
        System.out.println(key1);
        System.out.println(key3);
        String hget = jedis.hget("key3", "age");
        System.out.println(hget);
    }

    @Test
    public void testList(){
        Jedis jedis=new Jedis("39.107.252.103",6379);
        jedis.auth("123");

        jedis.lpush("book", "book1","book2","book3","book4");
        List<String> book = jedis.lrange("book", 0, -1);
        for (String s : book) {
            System.out.println(s);
        }
    }
}
