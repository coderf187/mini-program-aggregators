package com.gene.dto.base;

import java.util.List;

/**
 * 返回分页模型
 *
 * @param <T>
 * @author fengjian
 */
public class DataGrid<T> {

    /**
     * 状态码, 0表示成功
     */
    private int code;

    /**
     * 提示信息
     */
    private String msg;

    /**
     * 总数量, bootstrapTable是total
     */
    private long count;

    /**
     * 当前数据, bootstrapTable是rows
     */
    private List<T> data;

    public DataGrid(Long count, List<T> data) {
        this.code = 0;
        this.count = count;
        this.data = data;
    }

    public DataGrid(List<T> data) {
        this.code = 0;
        this.count = data.size();
        this.data = data;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public long getCount() {
        return count;
    }

    public void setCount(long count) {
        this.count = count;
    }

    public List<T> getData() {
        return data;
    }

    public void setData(List<T> data) {
        this.data = data;
    }
}
