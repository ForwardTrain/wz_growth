package com.school.wz_growth.service;


import com.school.wz_growth.common.exception.ServiceException;
import com.school.wz_growth.common.response.DataResponse;
import com.school.wz_growth.common.response.SingleResult;
import com.school.wz_growth.model.front.request.sys.*;


public interface SysMenuService {
    /** 菜单 - 添加 */
    SingleResult<DataResponse> doAdd(MenuRegistorAddRequestVo requestVo)throws ServiceException;
    /** 菜单 - 编辑 */
    SingleResult<DataResponse> doUpdate(MenuRegistorUpdateRequestVo requestVo) throws ServiceException ;
    /** 菜单 - 删除 */
    SingleResult<DataResponse> doDelete(MenuRegistorDeleteRequestVo requestVo) throws ServiceException ;


    /** 菜单 - 所有菜单的列表 */
    SingleResult<DataResponse> queryAllMenuByPage(MenuAllRequestVo requestVo)throws ServiceException ;
    /** 菜单 - 用户菜单列表 */
    SingleResult<DataResponse> queryUserMenuByPage(MenuUserListRequestVo requestVo)throws ServiceException ;


}
