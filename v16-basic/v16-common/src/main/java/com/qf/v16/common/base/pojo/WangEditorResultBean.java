package com.qf.v16.common.base.pojo;

/**
 * @author xiaoxinmin
 * @Date 2019/8/7
 */
public class WangEditorResultBean {

    private String errno;
    private String [] data;

    public WangEditorResultBean() {
    }

    public WangEditorResultBean(String errno, String[] data) {
        this.errno = errno;
        this.data = data;
    }

    public String getErrno() {
        return errno;
    }

    public void setErrno(String errno) {
        this.errno = errno;
    }

    public String[] getData() {
        return data;
    }

    public void setData(String[] data) {
        this.data = data;
    }
}
