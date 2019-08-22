package com.qf.v16.service;

import com.qf.v16.common.base.pojo.ResultBean;

/**
 * @author xiaoxinmin
 * @Date 2019/8/9
 */
public interface ISearchService {


    /**
    * 全讲数据库的数据导入到索引库中（系统初始化时执行一次）
    */
    public ResultBean synAllData();

    public ResultBean queryByKeywords(String keywords);

    public ResultBean updateById(Long id);
}
