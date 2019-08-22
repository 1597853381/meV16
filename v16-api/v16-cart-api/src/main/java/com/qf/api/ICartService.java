package com.qf.api;

import com.qf.v16.common.base.pojo.ResultBean;

/**
 * @author xiaoxinmin
 * @Date 2019/8/17
 */
public interface ICartService {

    /**
     * 购物车添加
     * @param key 购物车cookie标识
     * @param productId 添加的商品id
     * @param count 添加的商品数量
     * @return
     */
    public ResultBean add(String key,Integer productId,Integer count);

    /**
     * 修改购物车
     * @param key
     * @param productId
     * @param count
     * @return
     */
    public ResultBean update(String key,Integer productId,Integer count);

    /**
     * 删除购物车sahngp
     * @param key
     * @param productId
     * @return
     */
    public ResultBean del(String key,Integer productId);

    /**
     * 查看购物车
     * @param key
     * @return
     */
    public ResultBean query(String key);

    /**
     * 合并未登录购物车和已登录购物车
     * @param noLoginKey 未登录key
     * @param loginKey 已登录key
     * @return
     */
    public ResultBean merge(String noLoginKey,String loginKey);
}
