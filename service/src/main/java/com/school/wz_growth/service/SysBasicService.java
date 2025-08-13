package com.school.wz_growth.service;


import com.alibaba.fastjson.JSONObject;
import com.school.wz_growth.common.exception.ServiceException;
import com.school.wz_growth.common.response.DataResponse;
import com.school.wz_growth.common.response.SingleResult;
import com.school.wz_growth.model.domain.sys.BasicAddRequestVo;
import com.school.wz_growth.model.front.request.base.BaseRequestVo;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.validation.Valid;

public interface SysBasicService {

    SingleResult<DataResponse> sel_sys_info(JSONObject requestVo) throws ServiceException;
    /**
     * 更新
     * @param
     * @return
     * @throws ServiceException
     */
    SingleResult<DataResponse> doAddBasic(@Valid @RequestBody JSONObject requestVo) throws ServiceException;


    /**
     * 查询
     * @param
     * @return
     * @throws ServiceException
     */
    SingleResult<DataResponse>  queryBasic(BaseRequestVo requestVo) throws ServiceException;



    SingleResult<DataResponse>  sel_sys_params_list(JSONObject requestVo) throws ServiceException;
    SingleResult<DataResponse>  update_sys_params_list(JSONObject requestVo) throws ServiceException;

}
