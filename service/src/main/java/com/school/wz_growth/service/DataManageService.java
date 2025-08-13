package com.school.wz_growth.service;

import com.alibaba.fastjson.JSONObject;
import com.school.wz_growth.common.exception.ServiceException;
import com.school.wz_growth.common.response.DataResponse;
import com.school.wz_growth.common.response.SingleResult;


public interface DataManageService {


    SingleResult<DataResponse>  data_list( JSONObject requestVo) throws ServiceException;
    SingleResult<DataResponse>  del_data_list( JSONObject requestVo) throws ServiceException;
    SingleResult<DataResponse>  upd_data_list( JSONObject requestVo) throws ServiceException;
    SingleResult<DataResponse>  upd_data_list_sel( JSONObject requestVo) throws ServiceException;
    SingleResult<DataResponse>  upd_data_list_sel_db_combobox( JSONObject requestVo) throws ServiceException;
    SingleResult<DataResponse>  upd_data_list_sel_db_combobox_right_list( JSONObject requestVo) throws ServiceException;
    SingleResult<DataResponse>  upd_data_list_sel_db_combobox_left_down( JSONObject requestVo) throws ServiceException;
    SingleResult<DataResponse>  upd_data_list_details( JSONObject requestVo) throws ServiceException;
    SingleResult<DataResponse>  add_upd_as_know( JSONObject requestVo) throws ServiceException;
    SingleResult<DataResponse>  diff_data( JSONObject requestVo) throws ServiceException;
    SingleResult<DataResponse>  diff_data_upd( JSONObject requestVo) throws ServiceException;
    SingleResult<DataResponse>  data_upd( JSONObject requestVo) throws ServiceException;

    JSONObject sel_database_info();






    SingleResult<DataResponse>  sel_protein_type_list( JSONObject requestVo) throws ServiceException;
    SingleResult<DataResponse>  sel_protein_type_list_option( JSONObject requestVo) throws ServiceException;
    SingleResult<DataResponse>  del_protein_type_list( JSONObject requestVo) throws ServiceException;
    SingleResult<DataResponse>  edit_protein_type_list( JSONObject requestVo) throws ServiceException;

}



