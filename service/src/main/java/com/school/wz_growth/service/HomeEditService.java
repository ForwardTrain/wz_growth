package com.school.wz_growth.service;

import com.alibaba.fastjson.JSONObject;
import com.school.wz_growth.common.exception.ServiceException;
import com.school.wz_growth.common.response.DataResponse;
import com.school.wz_growth.common.response.SingleResult;


public interface HomeEditService {


//    SingleResult<DataResponse>  upd_base_general_infomation_show( BaseGeneralInfomationShowReq requestVo) throws ServiceException;
//    SingleResult<DataResponse>  upd_base_protein_sequence_show( BaseProteinSequenceShowReq requestVo) throws ServiceException;
//    SingleResult<DataResponse>  upd_base_protein_function_show( BaseProteinFunctionShowReq requestVo) throws ServiceException;
//    SingleResult<DataResponse>  upd_base_expression_and_location_show( BaseExpressionAndLocationShowReq requestVo) throws ServiceException;
//    SingleResult<DataResponse>  upd_base_expression_and_location_show_hpa( BaseExpressionAndLocationShowHpaReq requestVo) throws ServiceException;
//    SingleResult<DataResponse>  upd_base_protein_structure_show( BaseProteinStructureShowReq requestVo) throws ServiceException;


    SingleResult<DataResponse>  sel_search_details_show( JSONObject requestVo) throws ServiceException;
    SingleResult<DataResponse>  sel_search_details_familys( JSONObject requestVo) throws ServiceException;
    SingleResult<DataResponse>  upd_gen_data( JSONObject requestVo) throws ServiceException;
    SingleResult<DataResponse>  getCode() throws ServiceException;


}



