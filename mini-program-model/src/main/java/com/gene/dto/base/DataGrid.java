package com.gene.dto.base;

import java.util.List;

/**
 * 返回分页模型
 * @author fengjian
 *
 * @param <T>
 */
public class DataGrid<T>{

    private Long count;

    private List<T> data;

    public DataGrid(Long count, List<T> data) {
        this.count = count;
        this.data = data;
    }

    public Long getCount() {
        return count;
    }

    public void setCount(Long count) {
        this.count = count;
    }

    public List<T> getData() {
        return data;
    }

    public void setData(List<T> data) {
        this.data = data;
    }
}
