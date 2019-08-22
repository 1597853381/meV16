package com.qf.v16cart.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.qf.api.ICartService;
import com.qf.v16.common.base.constant.RabbitMQConstant;
import com.qf.v16.common.base.pojo.ResultBean;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

/**
 * @author xiaoxinmin
 * @Date 2019/8/17
 */
@Controller
@RequestMapping("cart")
public class CartController {

    @Reference
    private ICartService cartService;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @RequestMapping("add/{productId}/{count}")
    @ResponseBody
    public ResultBean add(@PathVariable(name="productId") Integer productId,
                          @PathVariable(name="count") Integer count,
                          @CookieValue(name="cart_token",required = false) String cart_token,
                          HttpServletResponse response,
                          HttpServletRequest request){

        String userId = (String) request.getAttribute("userId");
        if(userId!=null){
            //TODO 发送异步消息 更新数据库信息
            //rabbitTemplate.
            //已登录
            return cartService.add(userId, productId, count);
        }
        if(cart_token==null || "".equals(cart_token)){
            cart_token= UUID.randomUUID().toString();
            flushCookie(cart_token, response,30*24*60*1000);
        }
        return cartService.add(cart_token, productId, count);
    }



    @RequestMapping("query")
    @ResponseBody
    public ResultBean query(@CookieValue(name="cart_token",required = false) String cart_token,
                            HttpServletResponse response,
                            HttpServletRequest request){


        String userId = (String) request.getAttribute("userId");
        if(userId!=null){
            //已登录
            return cartService.query(userId);
        }
        //未登录
        ResultBean resultBean = cartService.query(cart_token);
        if(cart_token==null || "".equals(cart_token)){
            new ResultBean("404","你的购物车是空的");
        }
        flushCookie(cart_token, response,30*24*60*1000);
        return  cartService.query(cart_token);
    }


    @RequestMapping("update/{productId}/{count}")
    @ResponseBody
    public ResultBean update(@PathVariable(name="productId") Integer productId,
                             @PathVariable(name="count") Integer count,
                             @CookieValue(name="cart_token",required = false) String cart_token,
                             HttpServletResponse response){
        if(cart_token==null || "".equals(cart_token)){
            new ResultBean("404","你的购物车是空的");
        }
        flushCookie(cart_token, response,30*24*60*1000);
        return cartService.update(cart_token, productId, count);
    }

    @RequestMapping("del/{productId}")
    @ResponseBody
    public ResultBean del(@PathVariable(name="productId") Integer productId,
                          @CookieValue(name="cart_token",required = false) String cart_token,
                          HttpServletResponse response){
        if(cart_token==null || "".equals(cart_token)){
            new ResultBean("404","你的购物车是空的");
        }
        flushCookie(cart_token, response,30*24*60*1000);
        return cartService.del(cart_token, productId);
    }

    @RequestMapping("merge")
    @ResponseBody
    public ResultBean merge(@CookieValue(name = "cart_token",required = false) String cart_token,
                            HttpServletResponse response,
                            HttpServletRequest request){

        //获取当前用户的状态
        String userId= (String) request.getAttribute("userId");
        if(userId!=null){
            //已登录
            //删除未登录购物车cookie
            flushCookie(cart_token, response, 0);
            return cartService.merge(cart_token, userId);
        }
            //未登录
        return new ResultBean("404","无需合并");
    }

    private void flushCookie(@CookieValue(name = "cart_token", required = false) String cart_token, HttpServletResponse response,int expire) {
        //写入cookie 更新有效期
        Cookie cookie=new Cookie("cart_token",cart_token);
        cookie.setHttpOnly(true);
        cookie.setDomain("qf.com");
        cookie.setPath("/");
        cookie.setMaxAge(expire);
        response.addCookie(cookie);
    }
}
