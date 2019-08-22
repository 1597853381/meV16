package com.qf.v16.common.base.pojo;

import java.io.Serializable;

/**
 * @author xiaoxinmin
 * @Date 2019/8/6
 * 统一后端给前端
 */
public class ResultBean<T> implements Serializable {
    private String statusCode;
    private T data;

    public ResultBean() {
    }

    public ResultBean(String statusCode, T data) {
        this.statusCode = statusCode;
        this.data = data;
    }

    public String getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(String statusCode) {
        this.statusCode = statusCode;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
