package com.school.wz_growth.service;

import com.alibaba.fastjson.JSONObject;
import com.school.wz_growth.common.exception.ServiceException;
import com.school.wz_growth.common.response.DataResponse;
import com.school.wz_growth.common.response.SingleResult;

public interface LiteraturesService {

    SingleResult<DataResponse> sel_clinicaltrials_list(JSONObject requestVo) throws ServiceException;
    SingleResult<DataResponse> del_clinicaltrials_list(JSONObject requestVo) throws ServiceException;

    SingleResult<DataResponse> sel_GM_list(JSONObject requestVo) throws ServiceException;
    SingleResult<DataResponse> del_GM_list(JSONObject requestVo) throws ServiceException;

    SingleResult<DataResponse> sel_TL_list(JSONObject requestVo) throws ServiceException;
    SingleResult<DataResponse> del_TL_list(JSONObject requestVo) throws ServiceException;

}
