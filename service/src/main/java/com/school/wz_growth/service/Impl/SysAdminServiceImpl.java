package com.school.wz_growth.service.Impl;


import com.alibaba.fastjson.JSONObject;
import com.school.wz_growth.common.constant.WebConstant;
import com.school.wz_growth.common.encrypt.Md5Encrypt;
import com.school.wz_growth.common.exception.ServiceException;
import com.school.wz_growth.common.filter.JwtUtil;
import com.school.wz_growth.common.redis.JedisUtil;
import com.school.wz_growth.common.response.Code;
import com.school.wz_growth.common.response.DataResponse;
import com.school.wz_growth.common.response.SingleResult;
import com.school.wz_growth.common.sysEnum.StateCode;
import com.school.wz_growth.common.validator.StringUnits;
import com.school.wz_growth.dao.SysAdminMapper;
import com.school.wz_growth.dao.SysOrgPersonMapper;
import com.school.wz_growth.model.front.request.sys.AdminCheckRequestVo;
import com.school.wz_growth.model.front.request.sys.AdminLoginRequestVo;
import com.school.wz_growth.model.front.request.sys.AdminUpPsdRequestVo;
import com.school.wz_growth.model.front.request.sys.IdsRequestVo;
import com.school.wz_growth.service.SysAdminService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import redis.clients.jedis.Jedis;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class SysAdminServiceImpl implements SysAdminService {

    @Resource
    private SysAdminMapper adminMapper;

    @Resource
    private SysOrgPersonMapper orgPersonMapper;

    public SingleResult<DataResponse> doLogin(AdminLoginRequestVo requestVo) throws ServiceException {
        Map<String,Object> params=new HashMap<>();
        try {
            if(requestVo.getUser_name().equals("admin"))
            {
                params.put("user_name",requestVo.getUser_name());
                Map<String,Object> userMap= adminMapper.selAdminByUserName(params);
                if(null == userMap){
                    return SingleResult.buildFailure(Code.ERROR,"用户名或密码错误");
                }
                else{
                    System.out.println(Md5Encrypt.md5(requestVo.getPsd()));
                    if(Md5Encrypt.md5(requestVo.getPsd()).equalsIgnoreCase(userMap.get("psd").toString()))
                    {
                        Map<String,Object> data=new HashMap<>();
                        data.put("token", JwtUtil.generateToken(userMap.get("user_name").toString(),Long.parseLong(userMap.get("id").toString()), "1"));
                        data.put("name",userMap.get("name").toString());
                        //放进redis
                        Jedis jedis = JedisUtil.jedisPool.getResource();
                        jedis.setex(WebConstant.JWT_TOKEN_KEY_+"_"+1+"_"+userMap.get("id").toString(),
                                WebConstant.JWT_TOKEN_EXPIRE_,data.get("token").toString());
//                        JedisUtil.jedisPool.close();
                        jedis.close();
                        return SingleResult.buildSuccess(data);
                    }
                    else{
                        return SingleResult.buildFailure(Code.ERROR,"用户名或密码错误");
                    }
                }
            }else
            {
                params.put("user_name",requestVo.getUser_name());
                Map<String,Object>  userMap=adminMapper.selAdminByUserName(params);
                if(null == userMap || Integer.parseInt(userMap.get("status").toString()) == StateCode.不可用.value())
                {
                    return SingleResult.buildFailure(Code.ERROR,"用户名或密码错误");
                }else
                {
                    System.out.println(Md5Encrypt.md5(requestVo.getPsd()));
                    if(Md5Encrypt.md5(requestVo.getPsd()).equalsIgnoreCase(userMap.get("psd").toString())){
                        Map<String,Object> data=new HashMap<>();
                        data.put("token", JwtUtil.generateToken(userMap.get("name").toString(),Long.parseLong(userMap.get("id").toString()), "2"));
                        data.put("name",userMap.get("name").toString());
                        data.put("tel",userMap.get("tel").toString());

                        //放进redis
                        Jedis jedis = JedisUtil.jedisPool.getResource();
                        jedis.setex(WebConstant.JWT_TOKEN_KEY_+"_"+"2"+"_"+userMap.get("id").toString(),
                                WebConstant.JWT_TOKEN_EXPIRE_,data.get("token").toString());
//                        JedisUtil.jedisPool.close();
                        jedis.close();

                        return SingleResult.buildSuccess(data);
                    }
                    else{
                        return SingleResult.buildFailure(Code.ERROR,"用户名或密码错误");
                    }
                }
            }

        } catch (Throwable t) {
            return SingleResult.buildFailure(Code.ERROR,t.getMessage());
        }
    }

    public SingleResult<DataResponse> doUpPsd(AdminUpPsdRequestVo requestVo) throws ServiceException{
        Map<String,Object> params=new HashMap<>();
        try {
            if (requestVo.getPsd().equals(requestVo.getRe_psd()) == false)
                return SingleResult.buildFailure(Code.ERROR,"重复密码不一致");

//            params.put("u_id",requestVo.getuId());
//            if(requestVo.getRole_type() == 1)
//            {
//                Map<String,Object> userMap= adminMapper.selAdminByUserName(params);
//                if (!Md5Encrypt.md5(requestVo.getOld_psd()).equalsIgnoreCase(userMap.get("psd").toString())) {
//                    return SingleResult.buildFailure(Code.ERROR,"旧密码输入错误");
//                }
//                else{
//                    params.put("psd",Md5Encrypt.md5(requestVo.getRe_psd()));
//                    adminMapper.updateAdminById(params);
//                }
//            }else
//            {
//                Map<String,Object>  userMap=orgPersonMapper.selOrgPersonByTelOrId(params);
//                if (!Md5Encrypt.md5(requestVo.getOld_psd()).equalsIgnoreCase(userMap.get("psd").toString())) {
//                    return SingleResult.buildFailure(Code.ERROR,"旧密码输入错误");
//                }
//                else{
//                    params.put("psd",Md5Encrypt.md5(requestVo.getRe_psd()));
//                    orgPersonMapper.updateOrgPersonPsd(params);
//                } if (requestVo.getPsd().equals(requestVo.getRe_psd()) == false)
//                return SingleResult.buildFailure(Code.ERROR,"重复密码不一致");
//
//                params.put("u_id",requestVo.getuId());
//                if(requestVo.getRole_type() == 1)
//                {
//                    Map<String,Object> userMap= adminMapper.selAdminByUserName(params);
//                    if (!Md5Encrypt.md5(requestVo.getOld_psd()).equalsIgnoreCase(userMap.get("psd").toString())) {
//                        return SingleResult.buildFailure(Code.ERROR,"旧密码输入错误");
//                    }
//                    else{
//                        params.put("psd",Md5Encrypt.md5(requestVo.getRe_psd()));
//                        adminMapper.updateAdminById(params);
//                    }
//                }else
//                {
//                    Map<String,Object>  userMap=orgPersonMapper.selOrgPersonByTelOrId(params);
//                    if (!Md5Encrypt.md5(requestVo.getOld_psd()).equalsIgnoreCase(userMap.get("psd").toString())) {
//                        return SingleResult.buildFailu
//            }
            return SingleResult.buildSuccessWithMessage("操作成功");
        } catch (Throwable t) {
            return SingleResult.buildFailure(Code.ERROR,t.getMessage());
        }
    }

    public SingleResult<DataResponse> doCheckPsd(AdminCheckRequestVo requestVo) throws ServiceException{
        Map<String,Object> params=new HashMap<>();
        Map<String,Object> result=new HashMap<>();
        try {
//            params.put("u_id",requestVo.getuId());
//            if(requestVo.getRole_type() != 1 )
//            {
//                Map<String,Object> userMap= orgPersonMapper.selOrgPersonByTelOrId(params);
//                if (userMap.get("psd").equals(Md5Encrypt.md5("abc123"))) {
//                    result.put("code",StateCode.可用.value());
//                }else{
//                    result.put("code",StateCode.不可用.value());
//                }
//            }else
//            {
//                result.put("code",StateCode.不可用.value());
//            }
            return SingleResult.buildSuccess(result);

        } catch (Throwable t) {
            return SingleResult.buildFailure(Code.ERROR,t.getMessage());
        }
    }


    @Override
    public SingleResult<DataResponse> query_sys_account_list(JSONObject requestVo) throws ServiceException {
        try {
            Map<String,Object> params=new HashMap<>();
            params.put("name",requestVo.get("name"));
            params.put("user_name",requestVo.get("user_name"));
            params.put("tel",requestVo.get("tel"));
            params.put("status",requestVo.get("status"));

            params.put("start",requestVo.get("start"));
            params.put("limit",requestVo.get("limit"));
            List< Map<String,Object>> list=adminMapper.select_sys_account_list(params);
            int count=adminMapper.select_sys_account_list_count(params);

            Map<String,Object> result=new HashMap<>();
            result.put("list",list);
            result.put("total",count);
            return SingleResult.buildSuccess(result);

        } catch (Throwable t) {
            t.printStackTrace();
            return SingleResult.buildFailure(Code.ERROR,t.getMessage());
        }
    }

    @Override
    public SingleResult<DataResponse> edit_sys_account_list(JSONObject requestVo) throws ServiceException {

        try {

            Map<String,Object> params=new HashMap<>();
            params.put("user_name",requestVo.get("user_name"));
            params.put("psd", StringUnits.isNotNullOrEmpty(requestVo.get("psd"))?Md5Encrypt.md5(requestVo.get("psd").toString()):"");

            params.put("name",requestVo.get("name"));
            params.put("tel",requestVo.get("tel"));
            params.put("state",requestVo.get("state"));
            params.put("operator",requestVo.get("u_name"));


            String role_ids_str = null;
            if (StringUnits.isNotNullOrEmpty(requestVo.get("role_ids")))
            {
                role_ids_str = requestVo.getString("role_ids");
                if (role_ids_str.startsWith(",")==false)
                    role_ids_str = ","+role_ids_str;
                if (role_ids_str.endsWith(",")==false)
                    role_ids_str = role_ids_str + ",";
            }
            params.put("role_ids",role_ids_str);

            String job_ids_str = null;
            if (StringUnits.isNotNullOrEmpty(requestVo.get("job_ids")))
            {
                job_ids_str = requestVo.getString("job_ids");
                if (job_ids_str.startsWith(",")==false)
                    job_ids_str = ","+job_ids_str;
                if (job_ids_str.endsWith(",")==false)
                    job_ids_str = job_ids_str + ",";
            }
            params.put("job_ids",job_ids_str);


            if (StringUnits.isNotNullOrEmpty(requestVo.get("id")) ) //编辑
            {
                params.put("not_id",requestVo.get("id"));
                if (adminMapper.select_account_info(params)!= null)
                    return SingleResult.buildFailure(Code.ERROR,"该帐号 已经存在");

                if (StringUnits.isNotNullOrEmpty(params.get("psd")))
                    params.put("psd",Md5Encrypt.md5(requestVo.get("psd").toString()));

                params.put("id",requestVo.get("id"));
                adminMapper.update_account_info(params);
            }else {

                if (adminMapper.select_account_info(params)!= null)
                    return SingleResult.buildFailure(Code.ERROR,"该帐号 已经存在");

                adminMapper.insert_account_info(params);
            }

            return SingleResult.buildSuccessWithoutData();

        } catch (Throwable t) {
            t.printStackTrace();
            return SingleResult.buildFailure(Code.ERROR,t.getMessage());
        }
    }

//    @Override
//    public SingleResult<DataResponse> sel_sys_account_list_edit_option_job(JSONObject requestVo) throws ServiceException {
//        try {
////            Map<String,Object> params=new HashMap<>();
////            params.put("stu_id",requestVo.get("stu_id"));
//
//            List<Map<String,Object>> res_list = adminMapper.sel_sys_account_list_edit_option_job();
//
//            Map<String,Object> res=new HashMap<>();
//            res.put("list",res_list);
//            res.put("total",res_list.size());
//
//            return SingleResult.buildSuccess(res);
//
//        } catch (Throwable t) {
//            t.printStackTrace();
//            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
//            throw new ServiceException(t.getMessage(),Code.ERROR.getStatus());
//        }
//    }

    @Override
    public SingleResult<DataResponse> update_state_sys_account(JSONObject requestVo) throws ServiceException {
        try {
            Map<String,Object> params=new HashMap<>();
            params.put("status",requestVo.get("state"));
            params.put("operator",requestVo.get("u_name"));
            params.put("id",requestVo.get("id"));
            adminMapper.update_account_info_status(params);
            return SingleResult.buildSuccessWithoutData();

        } catch (Throwable t) {
            t.printStackTrace();
            return SingleResult.buildFailure(Code.ERROR,t.getMessage());
        }
    }

    @Override
    public SingleResult<DataResponse> del_sys_account_list(IdsRequestVo requestVo) throws ServiceException {
        try {

            if (String.format(",%s,",requestVo.getIds()).indexOf(",1,")>=0)
                return SingleResult.buildFailure(Code.ERROR,"系统帐号不能删除");

            Map<String,Object> params=new HashMap<>();
            params.put("ids",requestVo.getIds());
            adminMapper.delete_account_info(params);
            return SingleResult.buildSuccessWithoutData();

        } catch (Throwable t) {
            t.printStackTrace();
            return SingleResult.buildFailure(Code.ERROR,t.getMessage());
        }
    }



}

