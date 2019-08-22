package com.qf.v16.common.base;

import java.util.List;

/**
 * @author xiaoxinmin
 * @Date 2019/8/5
 */
public abstract class BaseServiceImpl<T> implements IBaseService<T> {



    public abstract IBaseDao<T> getDao();

    public int deleteByPrimaryKey(Long id) {
        return getDao().deleteByPrimaryKey(id);
    }

    public int insert(T t) {
        return getDao().insert(t);
    }

    public int insertSelective(T t) {
        return getDao().insertSelective(t);
    }

    public T selectByPrimaryKey(Long id) {
        return getDao().selectByPrimaryKey(id);
    }

    public int updateByPrimaryKeySelective(T t) {
        return getDao().updateByPrimaryKeySelective(t);
    }

    public int updateByPrimaryKeyWithBLOBs(T t) {
        return getDao().updateByPrimaryKeySelective(t);
    }

    public int updateByPrimaryKey(T t) {
        return getDao().updateByPrimaryKeySelective(t);
    }

    public List<T> list() {
        return getDao().list();
    }
}
