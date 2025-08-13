package com.school.wz_growth.service;

import com.alibaba.fastjson.JSONObject;
import com.school.wz_growth.common.exception.ServiceException;
import com.school.wz_growth.common.response.DataResponse;
import com.school.wz_growth.common.response.SingleResult;


public interface SearchWordService {


    SingleResult<DataResponse>  sel_list( JSONObject requestVo) throws ServiceException;
    SingleResult<DataResponse>  del_list( JSONObject requestVo) throws ServiceException;
    SingleResult<DataResponse>  add_upd_list( JSONObject requestVo) throws ServiceException;
    SingleResult<DataResponse>  upd_search_list( JSONObject requestVo) throws ServiceException;
    SingleResult<DataResponse>  search_set_list( JSONObject requestVo) throws ServiceException;
    SingleResult<DataResponse>  search_type_name_list( JSONObject requestVo) throws ServiceException;
    SingleResult<DataResponse>  upd_search_set_list( JSONObject requestVo) throws ServiceException;






}



