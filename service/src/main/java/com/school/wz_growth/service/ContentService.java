package com.school.wz_growth.service;

import com.alibaba.fastjson.JSONObject;
import com.school.wz_growth.common.exception.ServiceException;
import com.school.wz_growth.common.response.DataResponse;
import com.school.wz_growth.common.response.SingleResult;

import java.util.List;


public interface ContentService {


    SingleResult<DataResponse>  sel_list( JSONObject requestVo) throws ServiceException;
    SingleResult<DataResponse>  sel_list_details( JSONObject requestVo) throws ServiceException;
    SingleResult<DataResponse>  del_list( JSONObject requestVo) throws ServiceException;
    SingleResult<DataResponse>  add_upd_list( JSONObject requestVo) throws ServiceException;
    SingleResult<DataResponse>  sel_hot_list( JSONObject requestVo) throws ServiceException;
    SingleResult<DataResponse>  upd_hot_list( JSONObject requestVo) throws ServiceException;

    SingleResult<DataResponse>  sel_support_list( JSONObject requestVo) throws ServiceException;
    SingleResult<DataResponse>  sel_support_list_details( JSONObject requestVo) throws ServiceException;
    SingleResult<DataResponse>  del_support_list( JSONObject requestVo) throws ServiceException;
    SingleResult<DataResponse>  add_upd_support( JSONObject requestVo) throws ServiceException;
    SingleResult<DataResponse>  search_combobox( JSONObject requestVo) throws ServiceException;
    SingleResult<DataResponse>  search_combobox2( JSONObject requestVo) throws ServiceException;
    SingleResult<DataResponse>  search_combobox_type_num_count() throws ServiceException;

    SingleResult<DataResponse>  search_result_list( JSONObject requestVo) throws ServiceException;

    SingleResult<DataResponse> search_result_list_byId(JSONObject requestVo) throws ServiceException;
    SingleResult<DataResponse>  left_search_result_list( JSONObject requestVo) throws ServiceException;
    SingleResult<DataResponse>  sel_by_key_words_search_result_list( JSONObject requestVo) throws ServiceException;
    SingleResult<DataResponse>  sel_key_words( JSONObject requestVo) throws ServiceException;
    SingleResult<DataResponse>  sel_key_words_example( JSONObject requestVo) throws ServiceException;
    SingleResult<DataResponse>  sel_browse( JSONObject requestVo) throws ServiceException;
    SingleResult<DataResponse>  sel_databases_brief( JSONObject requestVo) throws ServiceException;
    SingleResult<DataResponse>  query_example_data( JSONObject requestVo) throws ServiceException;

    SingleResult<DataResponse>  add_upd_report( JSONObject requestVo) throws ServiceException;
    SingleResult<DataResponse>  add_upd_example( JSONObject requestVo) throws ServiceException;
    SingleResult<DataResponse>  del_example( JSONObject requestVo) throws ServiceException;
    SingleResult<DataResponse>  sel_example( JSONObject requestVo) throws ServiceException;
    void  add_down_first( JSONObject requestVo) throws ServiceException;
    void  add_down_second( List<JSONObject> list) throws ServiceException;

    SingleResult<DataResponse> sys_cooperate_manage_list(JSONObject requestVo) throws ServiceException;
    SingleResult<DataResponse> data_statistics_information(JSONObject requestVo) throws ServiceException;
    SingleResult<DataResponse> clinical_research(JSONObject requestVo) throws ServiceException;
    SingleResult<DataResponse>  sel_item_list( JSONObject requestVo) throws ServiceException;
    SingleResult<DataResponse>  sel_search_details( JSONObject requestVo) throws ServiceException;
    SingleResult<DataResponse>  sel_search_details_show( JSONObject requestVo) throws ServiceException;

    SingleResult<DataResponse>  selectProteinType() throws ServiceException;

    /**
     * 更新蛋白质类型描述
     * @param id 蛋白质类型ID
     * @param desc 描述内容
     */
    void updateProteinTypeDesc(Long id, String desc);

    SingleResult<DataResponse> add_example_data(JSONObject requestVo);

    SingleResult<DataResponse> query_protein_data(JSONObject requestVo);

    SingleResult<DataResponse> query_search_example_data();

    SingleResult del_search_example_data(JSONObject requestVo);
}



