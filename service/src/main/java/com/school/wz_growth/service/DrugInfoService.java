package com.school.wz_growth.service;


import com.school.wz_growth.dao.model.DrugInfo;
import com.school.wz_growth.dao.model.DrugInfoQueryDTO;
import com.school.wz_growth.model.PageResult;

/**
 * 药品信息服务接口
 */
public interface DrugInfoService {
    /**
     * 添加药品信息
     */
    int save(DrugInfo drugInfo);

    /**
     * 更新药品信息
     */
    int update(DrugInfo drugInfo);

    /**
     * 根据ID查询药品信息
     */
    DrugInfo getById(Integer id);

    /**
     * 分页查询药品信息
     */
    PageResult<DrugInfo> page(DrugInfoQueryDTO queryDTO);
} 