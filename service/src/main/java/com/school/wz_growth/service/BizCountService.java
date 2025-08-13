package com.school.wz_growth.service;

import com.alibaba.fastjson.JSONObject;
import com.school.wz_growth.common.exception.ServiceException;
import com.school.wz_growth.common.response.DataResponse;
import com.school.wz_growth.common.response.SingleResult;

public interface BizCountService{


    /** 流量概况  */
    SingleResult<DataResponse> sel_count_data(JSONObject requestVo) throws ServiceException;


    /** 地域概况  */
    SingleResult<DataResponse> sel_area_count_data(JSONObject requestVo) throws ServiceException;
    /** 浏览器访问量统计  */
    SingleResult<DataResponse> sel_browser_count_data(JSONObject requestVo) throws ServiceException;
    /** 受访页面统计  */
    SingleResult<DataResponse> sel_browser_page_count_data(JSONObject requestVo) throws ServiceException;
    /** 文献统计  */
    SingleResult<DataResponse> sel_literature_count_data(JSONObject requestVo) throws ServiceException;




    /** 客户列表  */
    SingleResult<DataResponse> sel_customer_list(JSONObject requestVo) throws ServiceException;
    /** 客户列表 - 访问记录*/
    SingleResult<DataResponse> sel_customer_list_recode(JSONObject requestVo) throws ServiceException;
    /** 客户列表 - 启、禁用*/
    SingleResult<DataResponse> upd_customer_status(JSONObject requestVo) throws ServiceException;



}
