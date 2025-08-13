package com.school.wz_growth.service.Impl;

import com.school.wz_growth.common.response.Code;
import com.school.wz_growth.common.response.DataResponse;
import com.school.wz_growth.common.response.SingleResult;
import com.school.wz_growth.common.validator.StringUnits;
import com.school.wz_growth.dao.SysRoleMapper;
import com.school.wz_growth.dao.SysRoleMenuMapper;
import com.school.wz_growth.model.domain.sys.SysRole;
import com.school.wz_growth.model.front.request.sys.*;
import com.school.wz_growth.service.SysRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class SysRoleServiceImpl implements SysRoleService {

    @Autowired
    private SysRoleMapper sysRoleMapper;
    @Autowired
    private SysRoleMenuMapper roleMenuMapper;

    @Override
    public SingleResult<DataResponse> selectAllRole(RoleRegistorRequestVo requestVo)  {

        try {
            Map<String, Object> params = new HashMap<>();
//            params.put("pageIndex",(requestVo.getPageIndex()-1)*requestVo.getPageSize());
//            params.put("pageSize", requestVo.getPageSize());
            params.put("name", requestVo.getName());

            Map<String, Object> result = new HashMap<>();
            List<Map<String,Object>> list = sysRoleMapper.selectAllRole(params);
            result.put("list",list);
            result.put("total",list.size());
            return SingleResult.buildSuccess(result);
        } catch (Throwable t) {
            return SingleResult.buildFailure(Code.ERROR,t.getMessage());

        }
    }

    public SingleResult<DataResponse> doAdd(RoleRegistorAddRequestVo requestVo)  {
        try {


//            String fir_menu_ids_str = requestVo.getMenu_ids();
//            if (StringUnits.isNotNullOrEmpty(fir_menu_ids_str) == false)
//                return SingleResult.buildFailure(Code.ERROR,"菜单为空");


            Map<String,Object> role_map = new HashMap<>();
            role_map.put("role_name",requestVo.getRole_name());
            role_map.put("description",requestVo.getDescription());
            role_map.put("status",requestVo.getStatus());
            role_map.put("operation",requestVo.getU_name());

//            SysRole sysRole=new SysRole();
//            sysRole.setRoleName(requestVo.getRole_name());
//            sysRole.setDescription(requestVo.getDescription());
            sysRoleMapper.insertSysRole(role_map);


            if (StringUnits.isNotNullOrEmpty(requestVo.getMenu_id()))
            {
                String fir_menu_ids_str = requestVo.getMenu_id();

                //增加 查询所有父集菜单
                fir_menu_ids_str = roleMenuMapper.selectAllParentMenuId(fir_menu_ids_str);
                if (fir_menu_ids_str.startsWith(",") == false)
                    fir_menu_ids_str = ","+fir_menu_ids_str;
                if (fir_menu_ids_str.endsWith(",") ==false)
                    fir_menu_ids_str = fir_menu_ids_str + ",";

                Map<String,Object> params = new HashMap<>();
                params.put("role_id",role_map.get("id"));
                params.put("menu_ids",fir_menu_ids_str);
                roleMenuMapper.insertSysRoleMenu(params);
            }


            return SingleResult.buildSuccessWithMessage("操作成功");
        } catch (Throwable t) {
            t.printStackTrace();
            return SingleResult.buildFailure(Code.ERROR,t.getMessage());
        }
    }

    public SingleResult<DataResponse> doUpdate(RoleRegistorUpdateRequestVo requestVo)  {
        boolean tag=false;
        try {
            SysRole sysRole=sysRoleMapper.querySysRoleById(requestVo.getId());
            if(null ==  sysRole){
                return SingleResult.buildFailure(Code.ERROR,"数据为空");
            }else{
//                String fir_menu_ids_str = requestVo.getMenu_ids();
//                if (StringUnits.isNotNullOrEmpty(fir_menu_ids_str) == false)
//                    return SingleResult.buildFailure(Code.ERROR,"菜单为空");


                if (StringUnits.isNotNullOrEmpty(requestVo.getRole_name()) ||
                        StringUnits.isNotNullOrEmpty(requestVo.getDescription()) ||
                        StringUnits.isNotNullOrEmpty(requestVo.getStatus()))
                {
                    Map<String,Object> role_map = new HashMap<>();
                    role_map.put("role_name",requestVo.getRole_name());
                    role_map.put("description",requestVo.getDescription());
                    role_map.put("status",requestVo.getStatus());
                    role_map.put("operation",requestVo.getU_name());
                    role_map.put("id",requestVo.getId());

                    tag =sysRoleMapper.updateSysRole(role_map) ==1?true:false;
                }


                if (StringUnits.isNotNullOrEmpty(requestVo.getMenu_id()))
                {
                    Map<String,Object> params = new HashMap<>();
                    params.put("role_id",requestVo.getId());
                    //删除 当前角色id的数据
                    roleMenuMapper.deleteSysRoleMenu(params);

                    String  fir_menu_ids_str = requestVo.getMenu_id();
                    //增加 查询所有父集菜单
                    fir_menu_ids_str = roleMenuMapper.selectAllParentMenuId(fir_menu_ids_str);
                    if (fir_menu_ids_str.startsWith(",") == false)
                        fir_menu_ids_str = ","+fir_menu_ids_str;
                    if (fir_menu_ids_str.endsWith(",") ==false)
                        fir_menu_ids_str = fir_menu_ids_str + ",";

                    params.put("menu_ids",fir_menu_ids_str);
                    roleMenuMapper.insertSysRoleMenu(params);
                }

            }
            if(tag)
                return SingleResult.buildSuccessWithMessage("更新成功");
            else
                return SingleResult.buildFailure(Code.ERROR,"更新失败");

        } catch (Throwable t) {
            return SingleResult.buildFailure(Code.ERROR,t.getMessage());

        }
    }

//    @Override
//    public SingleResult<DataResponse> selectAllRole(RoleRegistorRequestVo requestVo)  {
//        Map<String, Object> result = new HashMap<>();
//        Map<String,Object> params=new HashMap<String, Object>();
//        try {
//            params.put("pageIndex",(requestVo.getPageIndex()-1)*requestVo.getPageSize());
//            params.put("pageSize", Integer.parseInt(requestVo.getPageSize().toString()));
//            List<Map<String,Object>> data=sysRoleMapper.querySysRoleByPage(params);
//            Integer count=sysRoleMapper.querySysRoleByPageCount(params);
//            result.put("total",count);
//            result.put("data",data);
//            return SingleResult.buildSuccess(result);
//
//        } catch (Throwable t) {
//            return SingleResult.buildFailure(Code.ERROR,t.getMessage());
//        }
//    }

//    @Override
//    public SingleResult<DataResponse> querySysRoleById(RoleRegistorDetailRequestVo requestVo)  {
//        try {
//            SysRole sysRole=sysRoleMapper.querySysRoleById(Integer.parseInt(requestVo.getId().toString()));
//            if(null == sysRole)
//                return SingleResult.buildFailure(Code.ERROR,"数据为空");
//            else{
//                return SingleResult.buildSuccess(sysRole);
//            }
//        } catch (Throwable t) {
//            return SingleResult.buildFailure(Code.ERROR,t.getMessage());
//        }
//    }


    public SingleResult<DataResponse> doDelete(RoleRegistorDeleteRequestVo requestVo) {
        Map<String,Object> params=new HashMap();
        try {
            params.put("ids",requestVo.getIds());
            sysRoleMapper.batchDeleteSysRole(params);
            return SingleResult.buildSuccessWithMessage("删除成功");
        } catch (Throwable t) {
            return SingleResult.buildFailure(Code.ERROR,t.getMessage());
        }
    }


}

