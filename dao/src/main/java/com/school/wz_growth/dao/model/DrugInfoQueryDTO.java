package com.school.wz_growth.dao.model;

import lombok.Data;

/**
 * 药品信息查询条件
 */
@Data
public class DrugInfoQueryDTO {
    private Integer pageNum = 1; // 当前页码，默认第一页
    private Integer pageSize = 10; // 每页数量，默认10条
    private String drugName; // 药品名称(中文或英文)

    private String sortOrder;
} 