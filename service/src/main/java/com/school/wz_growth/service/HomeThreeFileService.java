package com.school.wz_growth.service;

import com.alibaba.fastjson.JSONObject;
import com.school.wz_growth.common.exception.ServiceException;
import com.school.wz_growth.common.response.DataResponse;
import com.school.wz_growth.common.response.SingleResult;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public interface HomeThreeFileService {


    SingleResult<DataResponse> sel_GM_list(JSONObject requestVo) throws ServiceException;
    SingleResult<DataResponse> sel_CT_list(JSONObject requestVo) throws ServiceException;

    SingleResult<DataResponse> sel_CT_list_export(JSONObject requestVo) throws ServiceException;
    SingleResult<DataResponse> sel_TL_list_search(JSONObject requestVo) throws ServiceException;
    SingleResult<DataResponse> sel_CT_list_detail(JSONObject requestVo) throws ServiceException;

    SingleResult<DataResponse> sel_TL_list(JSONObject requestVo) throws ServiceException;

    void sel_TL_list_export(JSONObject requestVo, HttpServletResponse response) throws ServiceException, IOException;
}
