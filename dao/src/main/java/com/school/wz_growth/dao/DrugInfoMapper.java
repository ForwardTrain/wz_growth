package com.school.wz_growth.dao;


import com.school.wz_growth.dao.model.DrugInfo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 药品信息Mapper接口
 */

public interface DrugInfoMapper {
    /**
     * 添加药品信息
     */
    int insert(DrugInfo drugInfo);

    /**
     * 更新药品信息
     */
    int update(DrugInfo drugInfo);

    /**
     * 根据ID查询药品信息
     */
    DrugInfo selectById(Integer id);

    /**
     * 分页查询药品信息(可根据药品名称模糊搜索)
     */
    List<DrugInfo> selectPage(@Param("drugName") String drugName,
                              @Param("offset") Integer offset,
                              @Param("pageSize") Integer pageSize, @Param("sortOrder") String sortOrder);

    /**
     * 获取符合条件的总记录数
     */
    Long selectCount(@Param("drugName") String drugName);
} 