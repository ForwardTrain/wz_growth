package com.school.wz_growth.web.controller;


import com.school.wz_growth.common.exception.ServiceException;
import com.school.wz_growth.common.response.DataResponse;
import com.school.wz_growth.common.response.SingleResult;
import com.school.wz_growth.model.front.request.sys.RoleRegistorAddRequestVo;
import com.school.wz_growth.model.front.request.sys.RoleRegistorDeleteRequestVo;
import com.school.wz_growth.model.front.request.sys.RoleRegistorRequestVo;
import com.school.wz_growth.model.front.request.sys.RoleRegistorUpdateRequestVo;
import com.school.wz_growth.service.SysRoleService;
import com.school.wz_growth.web.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;


/**
 * 角色管理
 */
@RequestMapping("/SysRole")
@RestController
public class SysRoleController extends BaseController {
    @Autowired
    private SysRoleService roleService;

//    @RequestMapping("/list")
//    public SingleResult<DataResponse> list(@Valid @RequestBody RoleRegistorRequestVo request, BindingResult result)throws ServiceException {
//        //必须要调用validate方法才能实现输入参数的合法性校验
//        super.validatorParam(request);
//        return roleService.querySysRoleByPage(request);
//    }

    /** 角色管理 - 编辑 */
    @RequestMapping("/update")
    public SingleResult<DataResponse> update(@Valid @RequestBody RoleRegistorUpdateRequestVo request, HttpServletRequest requestinfo, BindingResult result)throws ServiceException {
        //必须要调用validate方法才能实现输入参数的合法性校验
        super.validatorParam(request);
        return roleService.doUpdate(request);
    }

    /** 角色管理 - 删除 */
    @RequestMapping("/delete")
    public SingleResult<DataResponse> delete(@Valid @RequestBody RoleRegistorDeleteRequestVo request, HttpServletRequest requestinfo, BindingResult result)throws ServiceException {
        //必须要调用validate方法才能实现输入参数的合法性校验
        super.validatorParam(request);
        return roleService.doDelete(request);
    }

    /** 角色管理 - 添加 */
    @RequestMapping("/add")
    public SingleResult<DataResponse> add(@Valid @RequestBody RoleRegistorAddRequestVo request, HttpServletRequest requestinfo, BindingResult result)throws ServiceException {
        //必须要调用validate方法才能实现输入参数的合法性校验
        super.validatorParam(request);
        return roleService.doAdd(request);
    }


    /**
     * 获取所有角色 不分页
     * @return
     * @
     */
    @RequestMapping("/all/list")
    public SingleResult<DataResponse> allList(@Valid @RequestBody RoleRegistorRequestVo request, HttpServletRequest requestinfo, BindingResult result)throws ServiceException {
        //必须要调用validate方法才能实现输入参数的合法性校验
        super.validatorParam(request);
        return roleService.selectAllRole(request);
    }


}
