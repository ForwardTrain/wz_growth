package com.school.wz_growth.dao;

import com.school.wz_growth.model.domain.sys.SysMenu;

import java.util.List;
import java.util.Map;

public interface SysMenuMapper {

    /** admin -  查询菜单列表 */
    List<Map<String,Object>> selSysAdminMenuAllList();
    Integer selSysAdminMenuAllListCount();
    /** 普通管理员  查询管理员列表 */
    List<Map<String,Object>> querySysManagerMenuByPage(Map<String, Object> params) ;
    Integer querySysManagerMenuByPageCount(Map<String, Object> params);

    /**  普通用户 查询菜单列表  */
    List<Map<String,Object>> querySysMenuByMenuId(Map<String, Object> params) ;
    Integer  querySysMenuByMenuIdCount(Map<String, Object> params) ;


    /** 更新 菜单 */
    Integer updateSysMenu(SysMenu sysMenu);
    /**  更新菜单状态 */
    void doUpdateModelMenuState(Map<String, Object> params) ;

    /** 添加 菜单 */
    void insertSysMenu(SysMenu sysMenu);

    /**  删除管理员列表  */
    void deleteSysMenuByMenuId(Integer id) ;

    List<Map<String, Object>> xxx(Map<String, Object> params);
    List<Map<String, Object>> xxx2_test();
}
