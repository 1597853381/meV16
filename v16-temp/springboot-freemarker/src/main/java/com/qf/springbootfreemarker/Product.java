package com.qf.springbootfreemarker;

import java.util.Date;

/**
 * @author xiaoxinmin
 * @Date 2019/8/11
 */
public class Product {
    private Long Id;
    private String name;
    private Long price;
    private Date creatDate;

    public Product() {
    }

    public Product(Long id, String name, Long price, Date creatDate) {
        Id = id;
        this.name = name;
        this.price = price;
        this.creatDate = creatDate;
    }

    public Long getId() {
        return Id;
    }

    public void setId(Long id) {
        Id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getPrice() {
        return price;
    }

    public void setPrice(Long price) {
        this.price = price;
    }

    public Date getCreatDate() {
        return creatDate;
    }

    public void setCreatDate(Date creatDate) {
        this.creatDate = creatDate;
    }
}
