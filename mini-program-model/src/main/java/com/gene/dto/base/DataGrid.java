package com.gene.dto.base;

import java.util.ArrayList;
import java.util.List;

/**
 * 返回分页模型
 * @author fengjian
 *
 * @param <T>
 */
public class DataGrid<T>{

    private Long total = 0L;

    private List<T> rows = new ArrayList<>();

    public DataGrid(Long total, List<T> rows) {
        this.total = total;
        this.rows = rows;
    }

    public Long getTotal() {
        return total;
    }

    public void setTotal(Long total) {
        this.total = total;
    }

    public List<T> getRows() {
        return rows;
    }

    public void setRows(List<T> rows) {
        this.rows = rows;
    }
}
