package com.qf.v16cartservice;

import com.qf.v16.common.base.pojo.ResultBean;
import com.qf.v16cartservice.service.CartServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest
public class V16CartServiceApplicationTests {

    @Autowired
    private CartServiceImpl cartService;

    @Resource(name = "redisTemplate4String")
    private RedisTemplate redisTemplate;

    @Test
    public void testAdd() {
        ResultBean resultBean = cartService.add("123456", 1, 200);
        System.out.println(resultBean.getData());
    }

    @Test
    public void testDel() {
        ResultBean resultBean = cartService.del("123456", 2);
        System.out.println(resultBean.getData());
    }

    @Test
    public void testUpdate(){
        ResultBean resultBean = cartService.update("123456", 2, 1001);
        System.out.println(resultBean.getData());
    }

    @Test
    public void testQuery(){
        ResultBean resultBean = cartService.query("123456");
        System.out.println(resultBean.getData());
    }

}
