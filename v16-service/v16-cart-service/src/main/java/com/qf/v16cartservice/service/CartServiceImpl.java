package com.qf.v16cartservice.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.qf.api.ICartService;
import com.qf.api.pojo.CartItem;
import com.qf.v16.common.base.pojo.ResultBean;
import org.springframework.data.redis.core.RedisTemplate;

import javax.annotation.Resource;
import java.util.*;

/**
 * @author xiaoxinmin
 * @Date 2019/8/17
 */
@Service
public class CartServiceImpl implements ICartService {

    @Resource(name = "redisTemplate4String")
    private RedisTemplate redisTemplate;

    @Override
    public ResultBean add(String key, Integer productId, Integer count) {
        StringBuilder redisKey=new StringBuilder("cart_token:").append(key);
        Map cart = redisTemplate.opsForHash().entries(redisKey.toString());
        if(cart.get(productId)!=null){
            CartItem cartItem= (CartItem) cart.get(productId);
            cartItem.setCount(cartItem.getCount()+count);
            cartItem.setUpdateDate(new Date());
            cart.put(productId, cartItem);
            redisTemplate.opsForHash().putAll(redisKey.toString(), cart);
            return new ResultBean("200","添加成功");
        }
        CartItem cartItem=new CartItem();
        cartItem.setProductId(productId);
        cartItem.setCount(count);
        cartItem.setUpdateDate(new Date());
        cart.put(productId, cartItem);
        redisTemplate.opsForHash().putAll(redisKey.toString(),cart);
        return new ResultBean("200","添加成功");
    }

    @Override
    public ResultBean del(String key, Integer productId) {
        StringBuilder redisKey=new StringBuilder("cart_token:").append(key);
        Long delete = redisTemplate.opsForHash().delete(redisKey.toString(), productId);
        if(delete>0){
            return new ResultBean("200","删除成功");
        }
        return new ResultBean("404","删除失败");
    }

    @Override
    public ResultBean update(String key, Integer productId, Integer count) {
        StringBuilder redisKey=new StringBuilder("cart_token:").append(key);
        CartItem cartItem  = (CartItem) redisTemplate.opsForHash().get(redisKey.toString(), productId);
        if(cartItem!=null){
            cartItem.setCount(count);
            cartItem.setUpdateDate(new Date());
            redisTemplate.opsForHash().put(redisKey.toString(), productId, cartItem);
            return new ResultBean("200","更新成功");
        }
        return new ResultBean("404","更新失败，不存在该商品");
    }



    @Override
    public ResultBean query(String key) {
        StringBuilder redisKey=new StringBuilder("cart_token:").append(key);
        Map cart = redisTemplate.opsForHash().entries(redisKey.toString());
        if(cart.size()>0){
            //排序处理
            Collection values = cart.values();
            TreeSet<CartItem> target=new TreeSet<>();
            for (Object value : values) {
                target.add((CartItem) value);
            }
            return new ResultBean("200",target);
        }
        return new ResultBean("404","当前购物车为空");
    }

    @Override
    public ResultBean merge(String noLoginKey, String loginKey) {
        //未登录购物车
        StringBuilder noLoginRedisKey=new StringBuilder("cart_token:").append(noLoginKey);
        Map noLoginCart = redisTemplate.opsForHash().entries(noLoginRedisKey.toString());
        //已登录购物车
        StringBuilder loginRedisKey=new StringBuilder("cart_token:").append(loginKey);
        Map loginCart = redisTemplate.opsForHash().entries(loginRedisKey.toString());
        //未登录购物车为空
        if(noLoginCart.size()==0){
            return new ResultBean("200","合并成功，未登录购物车为空");
        }
        //已登录购物车为空
        if(loginCart.size()==0){
            redisTemplate.opsForHash().putAll(loginKey, noLoginCart);
            return new ResultBean("200","合并成功，已登录购物车为空");
        }
        //未登录和已登录购物车都不为空
        Set<Map.Entry<Object,Object>> noLoginEntrys = noLoginCart.entrySet();
        for (Map.Entry<Object,Object> noLoginEntry : noLoginEntrys) {
            if (redisTemplate.opsForHash().get(loginRedisKey.toString(), noLoginEntry.getKey())!=null) {
                //获得未登录购物车商品
                CartItem noLoginCartItem = (CartItem) noLoginEntry.getValue();
                //获得已登录购物车商品
                CartItem loginCartItem= (CartItem) redisTemplate.opsForHash().get(loginRedisKey.toString(), noLoginEntry.getKey());
                //合并商品数量
                loginCartItem.setCount(noLoginCartItem.getCount()+loginCartItem.getCount());
                //更新修改时间
                loginCartItem.setUpdateDate(new Date());
                //重新写回购物车
                System.out.println("重复");
                redisTemplate.opsForHash().put(loginRedisKey.toString(), noLoginEntry.getKey(), loginCartItem);
            }else{
                System.out.println("不重复");
                redisTemplate.opsForHash().put(loginRedisKey.toString(), noLoginEntry.getKey(), noLoginEntry.getValue());
            }
        }
        //最后清除未登录redis记录
        redisTemplate.delete(noLoginRedisKey.toString());
        return new ResultBean("200","合并购物车成功");
    }
}
