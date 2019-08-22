package com.qf.api.pojo;

import java.io.Serializable;
import java.util.Date;

/**
 * @author xiaoxinmin
 * @Date 2019/8/17
 */
public class CartItem implements Serializable,Comparable<CartItem> {

    private Integer productId;
    private Integer count;
    private Date updateDate;

    public CartItem(Integer productId, Integer count, Date updateDate) {
        this.productId = productId;
        this.count = count;
        this.updateDate = updateDate;
    }

    public CartItem() {
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    @Override
    public String toString() {
        return "CartItem{" +
                "productId=" + productId +
                ", count=" + count +
                ", updateDate=" + updateDate +
                '}';
    }


    public int compareTo(CartItem o) {
        return (int) (o.getUpdateDate().getTime()-this.getUpdateDate().getTime());
    }
}
