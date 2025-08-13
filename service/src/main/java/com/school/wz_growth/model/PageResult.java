package com.school.wz_growth.model;

import lombok.Data;

import java.util.List;

/**
 * 分页结果
 */
@Data
public class PageResult<T> {
    private List<T> list; // 数据列表
    private Long total; // 总记录数
    private Integer pageNum; // 当前页码
    private Integer pageSize; // 每页数量

    public PageResult(List<T> list, Long total, Integer pageNum, Integer pageSize) {
        this.list = list;
        this.total = total;
        this.pageNum = pageNum;
        this.pageSize = pageSize;
    }
} 