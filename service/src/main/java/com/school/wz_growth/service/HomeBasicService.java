package com.school.wz_growth.service;

import com.alibaba.fastjson.JSONObject;
import com.school.wz_growth.common.exception.ServiceException;
import com.school.wz_growth.common.response.DataResponse;
import com.school.wz_growth.common.response.SingleResult;
import com.school.wz_growth.model.front.request.base.BaseRequestVo;
import com.school.wz_growth.model.front.request.sys.AdminCheckRequestVo;

import javax.servlet.http.HttpServletRequest;

public interface HomeBasicService {


    /** 客户端信息  */
    SingleResult<DataResponse>  select_info(HttpServletRequest request, JSONObject requestVo) throws ServiceException;
    /** 界面 浏览记录  */
    SingleResult<DataResponse>  sel_view_recode(HttpServletRequest request, JSONObject requestVo) throws ServiceException;

    SingleResult<DataResponse> sel_ip_status(HttpServletRequest request, JSONObject requestVo) throws ServiceException;

    SingleResult<DataResponse> sel_home_info(HttpServletRequest request, JSONObject requestVo) throws ServiceException;
    SingleResult<DataResponse> sel_contact_us(HttpServletRequest request, JSONObject requestVo) throws ServiceException;



    /**  banner详情  */
    SingleResult<DataResponse> banner_sel_detail(JSONObject requestVo) throws ServiceException;
    SingleResult<DataResponse> sel_home_protein_num_count(JSONObject requestVo) throws ServiceException;


    SingleResult<DataResponse> sel_home_family_count(JSONObject requestVo) throws ServiceException;


    void scheduled_task();

}



