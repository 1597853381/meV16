package com.qf.v16.common.base;

import java.util.List;

/**
 * @author xiaoxinmin
 * @Date 2019/8/5
 */
public interface IBaseService<T> {


    int deleteByPrimaryKey(Long id);

    int insert(T t);

    int insertSelective(T t);

    T selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(T t);

    int updateByPrimaryKeyWithBLOBs(T t);

    int updateByPrimaryKey(T t);

    List<T> list();
}
