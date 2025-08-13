package com.school.wz_growth.web.controller;


import com.school.wz_growth.common.exception.ServiceException;
import com.school.wz_growth.common.response.DataResponse;
import com.school.wz_growth.common.response.SingleResult;
import com.school.wz_growth.model.front.request.sys.RoleMenuRegistorRequestVo;
import com.school.wz_growth.model.front.request.sys.RoleMenuUpdateRegistorRequestVo;
import com.school.wz_growth.service.SysRoleMenuService;
import com.school.wz_growth.web.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/** 角色菜单绑定 */
@RequestMapping("/SysRoleMenu")
@RestController
public class SysRoleMenuController extends BaseController {


    @Autowired
    private SysRoleMenuService roleMenuService;


    /** 角色菜单绑定 - 详情 */
    @RequestMapping("/detail")
    public SingleResult<DataResponse> detail(@Valid @RequestBody RoleMenuRegistorRequestVo requestVo, BindingResult result)throws ServiceException {
        //必须要调用validate方法才能实现输入参数的合法性校验
        super.validatorParam(requestVo);
        return roleMenuService.queryRoleMenu(requestVo);
    }

    /** 角色菜单绑定 - 编辑 */
    @RequestMapping("/update")
    public SingleResult<DataResponse> update(@Valid @RequestBody RoleMenuUpdateRegistorRequestVo requestVo, BindingResult result)throws ServiceException{
        //必须要调用validate方法才能实现输入参数的合法性校验
        super.validatorParam(requestVo);
        return roleMenuService.doUpdateOrAdd(requestVo);
    }

}
