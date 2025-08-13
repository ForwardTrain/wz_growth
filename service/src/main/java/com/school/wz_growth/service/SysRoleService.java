package com.school.wz_growth.service;

import com.school.wz_growth.common.response.DataResponse;
import com.school.wz_growth.common.response.SingleResult;
import com.school.wz_growth.model.front.request.sys.*;

public interface SysRoleService {

    /** 角色管理 - 列表 */
    SingleResult<DataResponse> selectAllRole(RoleRegistorRequestVo requestVo) ;

    /** 角色管理 - 添加 */
    SingleResult<DataResponse> doAdd(RoleRegistorAddRequestVo requestVo) ;

    /** 角色管理 - 更新 */
    SingleResult<DataResponse> doUpdate(RoleRegistorUpdateRequestVo requestVo) ;

    /** 角色管理 - 删除 */
    SingleResult<DataResponse> doDelete(RoleRegistorDeleteRequestVo requestVo) ;

}
