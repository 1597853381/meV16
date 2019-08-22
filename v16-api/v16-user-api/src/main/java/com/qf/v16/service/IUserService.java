package com.qf.v16.service;

import com.qf.v16.common.base.IBaseService;
import com.qf.v16.common.base.pojo.ResultBean;
import com.qf.v16.entity.TUser;

/**
 * @author xiaoxinmin
 * @Date 2019/8/15
 */
public interface IUserService extends IBaseService<TUser> {

    /**
     * 用户登陆的接口
     * @param user 存储用户名和密码的对象
     * @return
     */
    public ResultBean checkLogin(TUser user);

    /**
     * 判断页面用户是否登陆
     * @param uuid 存储在cookie中的用户登陆标识
     * @return
     */
    public ResultBean checkIsLogin(String uuid);

    /**
     * 用户注销
     * @param uuid 存储在cookie中的用户登陆标识
     * @return
     */
    public ResultBean logout (String uuid);

    /**
     * 解析jwtToken获得用户ID
     * @param jwtToken
     * @return
     */
    public ResultBean parseTokenGetId(String jwtToken);
}
