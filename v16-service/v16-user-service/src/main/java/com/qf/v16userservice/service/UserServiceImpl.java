package com.qf.v16userservice.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.qf.v16.common.base.BaseServiceImpl;
import com.qf.v16.common.base.IBaseDao;
import com.qf.v16.common.base.pojo.ResultBean;
import com.qf.v16.entity.TUser;
import com.qf.v16.mapper.TUserMapper;
import com.qf.v16.service.IUserService;
import com.qf.v16userservice.util.JWTUtil;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;


import javax.annotation.Resource;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * @author xiaoxinmin
 * @Date 2019/8/15
 */
@Service
public class UserServiceImpl extends BaseServiceImpl<TUser> implements IUserService {

    @Autowired
    private TUserMapper userMapper;

    @Resource(name = "redisTemplate4String")
    private RedisTemplate redisTemplate;

    @Autowired
    JWTUtil jwtUtil=new JWTUtil();

    @Override
    public IBaseDao<TUser> getDao() {
        return userMapper;
    }

    //将用户随机生成的uuid存放在redis里  然后存取用户登录状态
//    @Override
//    public ResultBean checkLogin(TUser user) {
//        TUser currentUser=userMapper.selectUserByUsername(user.getUsername());
//        if(currentUser!=null){
//            if (user.getPassword().equals(currentUser.getPassword())) {
//                String uuid = UUID.randomUUID().toString();
//                StringBuilder key=new StringBuilder("userToken:").append(uuid);
//                currentUser.setPassword(null);
//                redisTemplate.opsForValue().set(key.toString(),currentUser);
//                redisTemplate.expire(key.toString(), 30, TimeUnit.MINUTES);
//                return new ResultBean("200",uuid);
//            }
//        }
//        return new ResultBean("500","登陆失败");
//    }


    //用jwt生成加密算法字符串 存在cookie中 取出验证私钥是否正确判断登录
    @Override
    public ResultBean checkLogin(TUser user) {
        TUser currentUser=userMapper.selectUserByUsername(user.getUsername());
        if(currentUser!=null){
            if (user.getPassword().equals(currentUser.getPassword())) {
                jwtUtil.setSecretKey("wzxnhhd.");
                jwtUtil.setTtl(30*60*1000);
                String jwtToken = jwtUtil.createJwtToken(currentUser.getId().toString(), currentUser.getUsername());
                return new ResultBean("200",jwtToken);
            }
        }
        return new ResultBean("500","登陆失败");
    }


    @Override
    public ResultBean checkIsLogin(String jwtToken) {
        try {
            Claims claims = jwtUtil.parseJwtToken(jwtToken);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResultBean("404","当前用户未登录");
        }
        return new ResultBean("200","已登录");
    }


    @Override
    public ResultBean logout(String jwtToken) {
        StringBuilder key=new StringBuilder("userToken").append(jwtToken);
        redisTemplate.delete(key.toString());
        return new ResultBean("200","注销成功");
    }

    @Override
    public ResultBean parseTokenGetId(String jwtToken) {
        jwtUtil.setSecretKey("wzxnhhd.");
        try {
            Claims claims = jwtUtil.parseJwtToken(jwtToken);
            String id = claims.getId();
            return new ResultBean("200",id);
        } catch (Exception e) {
            return new ResultBean("404","令牌解析错误");
        }
    }
}
