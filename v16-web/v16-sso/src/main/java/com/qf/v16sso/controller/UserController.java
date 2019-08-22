package com.qf.v16sso.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.qf.v16.common.base.pojo.ResultBean;
import com.qf.v16.entity.TUser;
import com.qf.v16.service.IUserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author xiaoxinmin
 * @Date 2019/8/15
 */
@Controller
@RequestMapping("user")
public class UserController {

    @Reference
    private IUserService userService;

    @RequestMapping("login")
    public String login(){
        return "login";
    }

    /**
     * 登录返回结果对象
     * @param user
     * @param response
     * @return
     */
    @RequestMapping("checkLogin")
    @ResponseBody
    public ResultBean checkLogin(TUser user, HttpServletResponse response){
        ResultBean resultBean = userService.checkLogin(user);
        if("200".equals(resultBean.getStatusCode())){
            String uuid = resultBean.getData().toString();
            if("200".equals(resultBean.getStatusCode())){
                Cookie cookie=new Cookie("user_token", uuid);
                cookie.setPath("/");
                cookie.setHttpOnly(true);
                cookie.setDomain("qf.com");
                response.addCookie(cookie);

                //TODO 登录成功 合并购物车
                //同步或异步

            }
            return resultBean;
        }
        return new ResultBean("404","登录失败");
    }

//    /**
//     * 登录跳转页面  uuid 存放在redis中
//     * @param user
//     * @param response
//     * @return
//     */
//    @RequestMapping("checkLogin4PC")
//    @ResponseBody
//    public String checkLogin4PC(TUser user, HttpServletResponse response){
//        ResultBean resultBean = userService.checkLogin(user);
//        if("200".equals(resultBean.getStatusCode())){
//            String uuid = resultBean.getData().toString();
//            if("200".equals(resultBean.getStatusCode())){
//                Cookie cookie=new Cookie("user_token", uuid);
//                cookie.setPath("/");
//                cookie.setDomain("qf.com");
//                cookie.setHttpOnly(true);
//                response.addCookie(cookie);
//            }
//            return "redirect:http://www.qf.com:9092/index/home";
//        }
//        return "login";
//    }

    /**
     * 登录跳转页面  jwtToken
     * @param user
     * @param response
     * @return
     */
    @RequestMapping("checkLogin4PC")
    @ResponseBody
    public String checkLogin4PC(TUser user, HttpServletResponse response){
        ResultBean resultBean = userService.checkLogin(user);
        if("200".equals(resultBean.getStatusCode())){
            String jwtToken = resultBean.getData().toString();
            if("200".equals(resultBean.getStatusCode())){
                Cookie cookie=new Cookie("user_token", jwtToken);
                cookie.setPath("/");
                cookie.setDomain("qf.com");
                cookie.setHttpOnly(true);
                response.addCookie(cookie);
            }
            return "redirect:http://www.qf.com:9092/index/home";
        }
        return "login";
    }

    /**
     * 判断是否登录
     * @param request
     * @return
     */
    @RequestMapping("checkIsLogin")
    @CrossOrigin(origins = "*",allowCredentials = "true")
    @ResponseBody
    public ResultBean checkIsLogin(HttpServletRequest request){
        Cookie[] cookies = request.getCookies();
        if (cookies!=null) {
            for (Cookie cookie : cookies) {
                if ("user_token".equals(cookie.getName())) {
                    String jwtToken = cookie.getValue();
                    ResultBean resultBean = userService.checkIsLogin(jwtToken);
                    if("200".equals(resultBean.getStatusCode())){
                        return resultBean;
                    }
                }
            }
        }
        return new ResultBean("404","未登录");
    }

    /**
     * 注销
     * @param jwtToken
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("logout")
    @ResponseBody
    public ResultBean logout(@CookieValue(name = "user_token" ) String jwtToken,HttpServletRequest request,HttpServletResponse response){
        //ResultBean resultBean = userService.logout(uuid);
        Cookie[] cookies = request.getCookies();
        Cookie cookie=new Cookie("user_token", jwtToken);
        cookie.setPath("/");
        cookie.setHttpOnly(true);
        cookie.setMaxAge(0);
        cookie.setDomain("qf.com");
        response.addCookie(cookie);
        return new ResultBean("404","当前未登录");
    }

}
