package com.school.wz_growth.service.Impl;


import com.school.wz_growth.common.exception.ServiceException;
import com.school.wz_growth.common.forPidUtil.MenuUtil;
import com.school.wz_growth.common.response.Code;
import com.school.wz_growth.common.response.DataResponse;
import com.school.wz_growth.common.response.SingleResult;
import com.school.wz_growth.common.validator.StringUnits;
import com.school.wz_growth.dao.SysOrgPersonMapper;
import com.school.wz_growth.dao.SysRoleMenuMapper;
import com.school.wz_growth.model.domain.sys.SysMenu;
import com.school.wz_growth.model.front.request.sys.*;
import com.school.wz_growth.service.SysMenuService;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import com.school.wz_growth.dao.SysMenuMapper;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class SysMenuServiceImpl implements SysMenuService {

    @Resource
    private SysMenuMapper menuMapper;
    @Resource
    private SysOrgPersonMapper orgPersonMapper;
    @Resource
    private SysRoleMenuMapper roleMenuMapper;

    public SingleResult<DataResponse> doAdd(MenuRegistorAddRequestVo requestVo) throws ServiceException {
        try {
            SysMenu sysMenu=new SysMenu();
            sysMenu.setName(requestVo.getName());
            sysMenu.setSequence(Integer.parseInt(requestVo.getSequence().toString()));
            sysMenu.setStatus(requestVo.getStatus());
            sysMenu.setAbsoluteUrl(requestVo.getAbsolute_url());
            sysMenu.setpId(requestVo.getPid());
            sysMenu.setIsConfig(requestVo.getIs_config());
            menuMapper.insertSysMenu(sysMenu);
            return SingleResult.buildSuccessWithMessage("添加成功");
        } catch (Throwable t) {
            return SingleResult.buildFailure(Code.ERROR,t.getMessage());
        }
    }

    public SingleResult<DataResponse> doUpdate(MenuRegistorUpdateRequestVo requestVo) throws ServiceException  {
        Map<String, Object> result = new HashMap<>();
        try {
            SysMenu sysMenu=new SysMenu();
            if(!StringUtils.isBlank(requestVo.getAbsolute_url()) )
                sysMenu.setAbsoluteUrl(requestVo.getAbsolute_url());
            if(!StringUtils.isBlank(requestVo.getStatus()))
                sysMenu.setStatus(Integer.parseInt(requestVo.getStatus()));
            if(!StringUtils.isBlank(requestVo.getSequence()) )
                sysMenu.setSequence(Integer.parseInt(requestVo.getSequence()));
            if(!StringUtils.isBlank(requestVo.getIs_config()) )
                sysMenu.setIsConfig(Integer.parseInt(requestVo.getIs_config()));
            if(!StringUtils.isBlank(requestVo.getName()) )
                sysMenu.setName(requestVo.getName());

            sysMenu.setId(Integer.parseInt(requestVo.getId()));
//            sysMenu.setpId(Integer.parseInt(StringUnits.isNotNullOrEmpty(requestVo.getPid())?requestVo.getPid():"0"));
            boolean tag= menuMapper.updateSysMenu(sysMenu) == 1?true:false;
            if(!tag)
            {
                return SingleResult.buildFailure(Code.ERROR,"更新失败");
            }
            else{
                return SingleResult.buildSuccessWithMessage("操作成功");
            }

        } catch (Throwable t) {
            return SingleResult.buildFailure(Code.ERROR,t.getMessage());

        }
    }

    public SingleResult<DataResponse> doDelete(MenuRegistorDeleteRequestVo requestVo) throws ServiceException  {
        try {
            menuMapper.deleteSysMenuByMenuId(Integer.parseInt(requestVo.getId()));
            return SingleResult.buildSuccessWithMessage("操作成功");
        } catch (Throwable t) {
            return SingleResult.buildFailure(Code.ERROR,t.getMessage());
        }
    }


    public SingleResult<DataResponse> queryAllMenuByPage(MenuAllRequestVo requestVo)throws ServiceException{

        Map<String,Object> res=new HashMap<>();
        try {

            List<Map<String,Object>> data= MenuUtil.recursionMenu(menuMapper.selSysAdminMenuAllList(),null);
            res.put("list",data);
            res.put("total",menuMapper.selSysAdminMenuAllListCount());

            return SingleResult.buildSuccess(res);
        } catch (Throwable t) {
            return SingleResult.buildFailure(Code.ERROR,t.getMessage());
        }
    }


    public SingleResult<DataResponse> queryUserMenuByPage(MenuUserListRequestVo requestVo)throws ServiceException{
        Map<String,Object> params=new HashMap<>();
        Map<String,Object> result=new HashMap<>();

        try {
            if(requestVo.getU_name().equals("admin"))
            {
                List<Map<String,Object>> data= MenuUtil.recursionMenu(menuMapper.selSysAdminMenuAllList(),null);
                result.put("children",data);
                result.put("total",menuMapper.selSysAdminMenuAllListCount());
            }else if (requestVo.getRole_type()==1){
                List<Map<String,Object>> data= MenuUtil.recursionMenu(menuMapper.querySysManagerMenuByPage(params),null);
                result.put("children",data);
                result.put("total",menuMapper.querySysManagerMenuByPageCount(params));
            }else
            {
                params.put("id",requestVo.getuId());
                Map<String,Object> user=orgPersonMapper.selOrgPersonByTelOrId(params);
                if(null == user || user.get("role_id")==null)
                {
                    result.put("children",new ArrayList<>());
                    result.put("total",0);
                }else
                {
                    params.put("role_id",user.get("role_id"));
                    Map<String,Object> role_menu=roleMenuMapper.queryMenuByRoleId(params);
                    if(null == role_menu.get("menu_id") || role_menu.get("menu_id").toString().length()<1)
                    {
                        result.put("children",new ArrayList<>());
                        result.put("total",0);
                    }else
                    {
                        params.put("menuIds",role_menu.get("menu_id").toString().substring(1,role_menu.get("menu_id").toString().length()-1));
                        List<Map<String,Object>> data= MenuUtil.recursionMenu(menuMapper.querySysMenuByMenuId(params),null);
                        result.put("children",data);
                        result.put("total",menuMapper.querySysMenuByMenuIdCount(params));
                    }
                }
            }
            result.put("role_type",requestVo.getRole_type());
            return SingleResult.buildSuccess(result);
        } catch (Throwable t) {
            return SingleResult.buildFailure(Code.ERROR,t.getMessage());
        }
    }


}
