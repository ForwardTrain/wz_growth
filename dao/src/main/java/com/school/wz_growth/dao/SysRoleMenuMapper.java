package com.school.wz_growth.dao;




import com.school.wz_growth.model.domain.sys.SysRoleMenu;
import org.apache.ibatis.annotations.Param;

import java.util.Map;

public interface SysRoleMenuMapper {

    /** 角色菜单绑定表 - 详情 根据id */
    SysRoleMenu querySysRoleMenuByRoleId(Map<String, Object> params);
    /** 角色菜单绑定表 - menu_id 根据id */
    Map<String,Object> queryMenuByRoleId(Map<String, Object> params);

    /** 角色菜单绑定表 - 新增 */
    void insertSysRoleMenu(Map<String, Object> params);

    /** 角色菜单绑定表 - 编辑 */
    Integer updateSysRoleMenu(SysRoleMenu sysRoleMenu);

    /** 角色菜单绑定表 - 删除 */
    void deleteSysRoleMenu(Map<String, Object> params);

    /** 查询 - 包含上级菜单的所有菜单 */
    String selectAllParentMenuId(@Param(value = "menu_id") String menu_id);
}
