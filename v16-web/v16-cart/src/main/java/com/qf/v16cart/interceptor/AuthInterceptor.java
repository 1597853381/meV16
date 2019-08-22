package com.qf.v16cart.interceptor;

import com.alibaba.dubbo.config.annotation.Reference;
import com.qf.v16.common.base.pojo.ResultBean;
import com.qf.v16.service.IUserService;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author xiaoxinmin
 * @Date 2019/8/18
 */
@Component
public class AuthInterceptor implements HandlerInterceptor {

    @Reference
    private IUserService userService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        //获取当前已登录的用户信息
        Cookie[] cookies = request.getCookies();
        if(cookies!=null){
            for (Cookie cookie : cookies) {
                if("user_token".equals(cookie.getName())){
                    String jwtToken = cookie.getValue();
                    //解析jwtToken
                    ResultBean resultBean = userService.parseTokenGetId(jwtToken);
                    if("200".equals(resultBean.getStatusCode())){
                        String id = (String) resultBean.getData();
                        //opo[[
                        request.setAttribute("userId", id);
                        return true;
                    }
                }

            }

        }
        //无论是否登录都能操作购物车
        return true;
    }
}
