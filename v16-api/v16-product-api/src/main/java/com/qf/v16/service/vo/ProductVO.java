package com.qf.v16.service.vo;

import com.qf.v16.entity.TProduct;

import java.io.Serializable;

/**
 * @author xiaoxinmin
 * @Date 2019/8/6
 */
public class ProductVO implements Serializable {
    private TProduct Product;
    private String productDesc;

    public TProduct getProduct() {
        return Product;
    }

    public void setProduct(TProduct product) {
        Product = product;
    }

    public String getProductDesc() {
        return productDesc;
    }

    public void setProductDesc(String productDesc) {
        this.productDesc = productDesc;
    }
}
