//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.d5.framework.bean;

import com.github.pagehelper.PageInfo;
import java.io.Serializable;
import java.util.List;

public final class PageResult<T> implements Serializable {
    private List<T> rows;
    private Long total;

    public PageResult() {
    }

    public PageResult(List<T> data) {
        PageInfo<T> pageInfo = new PageInfo(data);
        this.total = pageInfo.getTotal();
        this.rows = data;
    }

    public PageResult(List<T> data, Long total) {
        this.total = total;
        this.rows = data;
    }

    public List<T> getRows() {
        return this.rows;
    }

    public void setRows(List<T> rows) {
        this.rows = rows;
    }

    public Long getTotal() {
        return this.total;
    }

    public void setTotal(Long total) {
        this.total = total;
    }
}
