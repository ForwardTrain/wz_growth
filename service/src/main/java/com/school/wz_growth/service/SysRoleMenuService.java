package com.school.wz_growth.service;


import com.school.wz_growth.common.response.DataResponse;
import com.school.wz_growth.common.response.SingleResult;
import com.school.wz_growth.model.front.request.sys.RoleMenuRegistorRequestVo;
import com.school.wz_growth.model.front.request.sys.RoleMenuUpdateRegistorRequestVo;

public interface SysRoleMenuService {

    /** 角色菜单绑定 - 列表 */
    SingleResult<DataResponse> queryRoleMenu(RoleMenuRegistorRequestVo requestVo) ;

    /** 角色菜单绑定 - 编辑  */
    SingleResult<DataResponse> doUpdateOrAdd(RoleMenuUpdateRegistorRequestVo requestVo) ;



}
