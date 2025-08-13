package com.school.wz_growth.dao;



import com.school.wz_growth.model.domain.sys.SysRole;

import java.util.List;
import java.util.Map;


public interface SysRoleMapper {
     /** 角色 - 列表 */
     List<Map<String,Object>> querySysRoleByPage(Map<String, Object> params);
     Integer querySysRoleByPageCount(Map<String, Object> params) ;
     /** 角色 - 详情 */
     SysRole querySysRoleById(Integer roleId);
     /** 角色 - 所有角色列表 */
     List<Map<String,Object>> selectAllRole(Map<String,Object> params);

     /** 角色 - 新增 */
     void insertSysRole(Map<String,Object> params);

     /** 角色 - 编辑 */
     Integer updateSysRole(Map<String,Object> params);

     /** 角色 - 删除 */
     void batchDeleteSysRole(Map<String, Object> params);
}
