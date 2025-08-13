package com.school.wz_growth.service.Impl;


import com.school.wz_growth.common.response.Code;
import com.school.wz_growth.common.response.DataResponse;
import com.school.wz_growth.common.response.SingleResult;
import com.school.wz_growth.dao.SysMenuMapper;
import com.school.wz_growth.dao.SysRoleMenuMapper;
import com.school.wz_growth.model.domain.sys.SysRoleMenu;
import com.school.wz_growth.model.front.request.sys.RoleMenuRegistorRequestVo;
import com.school.wz_growth.model.front.request.sys.RoleMenuUpdateRegistorRequestVo;
import com.school.wz_growth.service.SysRoleMenuService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

@Service
public class SysRoleMenuServiceImpl implements SysRoleMenuService {

    @Resource
    private SysRoleMenuMapper roleMenuMapper;


    @Override
    public SingleResult<DataResponse> queryRoleMenu(RoleMenuRegistorRequestVo requestVo)  {
        Map<String,Object> params=new HashMap<>();
        try {
            params.put("role_id",requestVo.getRoleId());
            //检测当前角色有哪些模块
            SysRoleMenu sysRoleMenu= roleMenuMapper.querySysRoleMenuByRoleId(params);
            return SingleResult.buildSuccess(sysRoleMenu);
        } catch (Throwable t) {
            return SingleResult.buildFailure(Code.ERROR,t.getMessage());
        }
    }


    public SingleResult<DataResponse> doUpdateOrAdd(RoleMenuUpdateRegistorRequestVo requestVo)  {
        Map<String,Object> params=new HashMap<>();
        try {
            params.put("role_id",requestVo.getRole_id());
            //删除 当前角色id的数据
            roleMenuMapper.deleteSysRoleMenu(params);

            params.put("menu_ids",requestVo.getMenu_ids());
            roleMenuMapper.insertSysRoleMenu(params);
            return SingleResult.buildSuccessWithMessage("操作成功");
        } catch (Throwable t) {
            return SingleResult.buildFailure(Code.ERROR,t.getMessage());
        }
    }


}
