package com.qf.v16productservice.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.qf.v16.common.base.BaseServiceImpl;
import com.qf.v16.common.base.IBaseDao;
import com.qf.v16.entity.TProduct;
import com.qf.v16.entity.TProductDesc;
import com.qf.v16.mapper.TProductDescMapper;
import com.qf.v16.mapper.TProductMapper;
import com.qf.v16.service.IProductService;
import com.qf.v16.service.ISearchService;
import com.qf.v16.service.vo.ProductVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * @author xiaoxinmin
 * @Date 2019/8/5
 */
@Service
public class ProductServiceImpl extends BaseServiceImpl<TProduct> implements IProductService {

    @Autowired
    private TProductMapper productMapper;

    @Autowired
    private TProductDescMapper productDescMapper;


    @Override
    public IBaseDao<TProduct> getDao() {
        return productMapper;
    }


    @Override
    public PageInfo<TProduct> page(Integer pageIndex, Integer pageSize) {
        PageHelper.startPage(pageIndex, pageSize);
        List<TProduct> productList=productMapper.page();
        return new PageInfo<>(productList,3);
    }

    @Override
    @Transactional
    public Long add(ProductVO vo) {
        //添加商品主键回填
        TProduct product = vo.getProduct();
        productMapper.insertSelective(product);
//        searchService.updateById((long) newId);
        //添加商品描述
        TProductDesc desc =new TProductDesc();
        desc.setProductId(product.getId());
        desc.setProductDesc(vo.getProductDesc());
        productDescMapper.insertSelective(desc);
        return product.getId();
    }

    @Override
    public int delByIds(List<Long> ids) {
        return productMapper.delByIds(ids);
    }

    //逻辑删除商品
    @Override
    public int deleteByPrimaryKey(Long id) {
        TProduct product=new TProduct();
        product.setId(id);
        product.setUpdateTime(new Date());
        product.setFlag(false);
        return productMapper.updateByPrimaryKeySelective(product);

    }
}
