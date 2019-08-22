package com.qf.v16.service;

import com.github.pagehelper.PageInfo;
import com.qf.v16.common.base.IBaseService;
import com.qf.v16.entity.TProduct;
import com.qf.v16.service.vo.ProductVO;

import java.util.List;

/**
 * @author xiaoxinmin
 * @Date 2019/8/5
 */
public interface IProductService extends IBaseService<TProduct> {


    PageInfo<TProduct> page(Integer pageIndex, Integer pageSize);

    Long add(ProductVO vo);

    int delByIds(List<Long> ids);
}
