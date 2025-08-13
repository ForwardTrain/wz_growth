package com.school.wz_growth.service.Impl;


import com.alibaba.fastjson.JSONObject;
import com.school.wz_growth.common.exception.ServiceException;
import com.school.wz_growth.common.qiniu.QiniuUpload;
import com.school.wz_growth.common.response.Code;
import com.school.wz_growth.common.response.DataResponse;
import com.school.wz_growth.common.response.SingleResult;
import com.school.wz_growth.common.validator.FileUploadUtils;
import com.school.wz_growth.common.validator.StringUnits;
import com.school.wz_growth.dao.SysBasicMapper;
import com.school.wz_growth.model.front.request.base.BaseRequestVo;
import com.school.wz_growth.service.SysBasicService;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class SysBasicServiceImpl implements SysBasicService {
    @Resource
    private SysBasicMapper sysBasicMapper;


    @Override
    public SingleResult<DataResponse> sel_sys_info(JSONObject requestVo) throws ServiceException {
        try {

            Map<String, Object> res = new HashMap<>();
            Map<String,Object> data= sysBasicMapper.selSysBasic();
            if(null!=data && StringUnits.isNotNullOrEmpty(data.get("logo"))){
                res.put("logo", QiniuUpload.qiniu_base_url+data.get("logo"));
            }else {
                res.put("logo","");
            }
            res.put("sys_name",data.get("sys_name"));
            return SingleResult.buildSuccess(res);
        } catch (Throwable t) {
            return SingleResult.buildFailure(Code.ERROR,t.getMessage());
        }
    }

    public SingleResult<DataResponse> doAddBasic(@Valid @RequestBody JSONObject requestVo) throws ServiceException {
        Map<String, Object> vo = new HashMap<>();
        try {

//            vo.put("status",1);

            if (StringUnits.isNotNullOrEmpty(requestVo.get("logo")))
                vo.put("logo",requestVo.get("logo").toString().replaceAll(QiniuUpload.qiniu_base_url,""));
            if (StringUnits.isNotNullOrEmpty(requestVo.get("introduction_brief")))
                vo.put("introduction_brief", FileUploadUtils.parseNewsDataMarket(requestVo.get("uId").toString(),requestVo.get("introduction_brief").toString()));
            if (StringUnits.isNotNullOrEmpty(requestVo.get("sys_name")))
                vo.put("sys_name",requestVo.get("sys_name"));
            if (StringUnits.isNotNullOrEmpty(requestVo.get("e_name")))
                vo.put("e_name",requestVo.get("e_name"));

            if (StringUnits.isNotNullOrEmpty(requestVo.get("p_code")))
                vo.put("p_code",requestVo.get("p_code"));
            if (StringUnits.isNotNullOrEmpty(requestVo.get("c_code")))
                vo.put("c_code",requestVo.get("c_code"));
            if (StringUnits.isNotNullOrEmpty(requestVo.get("a_code")))
                vo.put("a_code",requestVo.get("a_code"));
            if (StringUnits.isNotNullOrEmpty(requestVo.get("address")))
                vo.put("address",requestVo.get("address"));

//            if (StringUnits.isNotNullOrEmpty(requestVo.get("picture_url")))
//                vo.put("picture_url",requestVo.getString("picture_url").replaceAll(QiniuUpload.qiniu_base_url,""));
            if (StringUnits.isNotNullOrEmpty(requestVo.get("contact_tel")))
                vo.put("contact_tel",requestVo.get("contact_tel"));
            if (StringUnits.isNotNullOrEmpty(requestVo.get("email")))
                vo.put("email",requestVo.get("email"));
            if (StringUnits.isNotNullOrEmpty(requestVo.get("operate_user")))
                vo.put("operate_user",requestVo.get("u_name"));

            if (StringUnits.isNotNullOrEmpty(requestVo.get("customer_service_tel")))
                vo.put("customer_service_tel",requestVo.get("customer_service_tel"));
            if (StringUnits.isNotNullOrEmpty(requestVo.get("customer_service_time")))
                vo.put("customer_service_time",requestVo.get("customer_service_time"));
            if (StringUnits.isNotNullOrEmpty(requestVo.get("org_name")))
                vo.put("org_name",requestVo.get("org_name"));

            if (StringUnits.isNotNullOrEmpty(requestVo.get("notice_content")))
                vo.put("notice_content", requestVo.get("notice_content"));
            else
                vo.put("notice_content","");

            if (StringUnits.isNotNullOrEmpty(requestVo.get("detail_brief")))
                vo.put("detail_brief", requestVo.get("detail_brief"));


            sysBasicMapper.updateSysBasic(vo);
            return SingleResult.buildSuccessWithoutData();
        } catch (Throwable t) {
            return SingleResult.buildFailure(Code.ERROR,t.getMessage());
        }
    }



    public SingleResult<DataResponse>  queryBasic(BaseRequestVo requestVo) throws ServiceException{
        try {
            Map<String,Object> data= sysBasicMapper.selSysBasic();
            if(null!=data && StringUnits.isNotNullOrEmpty(data.get("logo"))){
                data.put("logo", QiniuUpload.qiniu_base_url+data.get("logo"));
            }
            return SingleResult.buildSuccess(data);
        } catch (Throwable t) {
            return SingleResult.buildFailure(Code.ERROR,t.getMessage());
        }
    }


    @Override
    public SingleResult<DataResponse> sel_sys_params_list(JSONObject requestVo) throws ServiceException {
        try {
            List< Map<String,Object>> list=sysBasicMapper.sel_sys_params_list();

            Map<String,Object> result=new HashMap<>();
            result.put("list",list);
            result.put("total",list.size());
            return SingleResult.buildSuccess(result);
        } catch (Throwable t) {
            return SingleResult.buildFailure(Code.ERROR,t.getMessage());
        }
    }

    @Override
    public SingleResult<DataResponse> update_sys_params_list(JSONObject requestVo) throws ServiceException {
        try {

            Map<String,Object> params=new HashMap<>();
            params.put("id",requestVo.get("id"));
            params.put("value",requestVo.get("value"));
            params.put("desc",requestVo.get("desc"));
            sysBasicMapper.update_sys_params(params);
            return SingleResult.buildSuccessWithoutData();
        } catch (Throwable t) {
            return SingleResult.buildFailure(Code.ERROR,t.getMessage());
        }
    }
}
