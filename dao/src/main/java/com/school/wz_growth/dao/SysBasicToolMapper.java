package com.school.wz_growth.dao;

import java.util.List;
import java.util.Map;

public interface SysBasicToolMapper {

    /** 省、市、区 节点 */
    List<Map<String,Object>> china_node_code(Map<String,Object> params);




}
