package com.qf.v16productservice.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.qf.v16.common.base.BaseServiceImpl;
import com.qf.v16.common.base.IBaseDao;
import com.qf.v16.common.base.IBaseService;
import com.qf.v16.entity.TProductType;
import com.qf.v16.mapper.TProductTypeMapper;
import com.qf.v16.service.IProductService;
import com.qf.v16.service.IProductTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.List;

/**
 * @author xiaoxinmin
 * @Date 2019/8/8
 */
@Service
public class ProductTypeServiceImpl extends BaseServiceImpl<TProductType> implements IProductTypeService {

    @Autowired
    private TProductTypeMapper productTypeMapper;

    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    public IBaseDao<TProductType> getDao() {
        return productTypeMapper;
    }

    @Override
    public List<TProductType> list() {
        //去缓存库获取集合信息
        String key ="productType:list";
        List<TProductType> cacheList  = (List<TProductType>) redisTemplate.opsForValue().get(key);
        //如果缓存有
        if(cacheList!=null){
            //直接返回缓存数据
            return cacheList;
        }

        System.out.println("从数据库中获取数据");
        List<TProductType> list = super.list();
        if (list!=null){
            redisTemplate.opsForValue().set(key, list);
        }
        return list;


    }
}
