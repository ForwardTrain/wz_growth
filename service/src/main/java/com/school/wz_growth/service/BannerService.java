package com.school.wz_growth.service;

import com.alibaba.fastjson.JSONObject;
import com.school.wz_growth.common.exception.ServiceException;
import com.school.wz_growth.common.response.DataResponse;
import com.school.wz_growth.common.response.SingleResult;


public interface BannerService {

//    /**  banner列表 */
//    SingleResult<DataResponse> banner_list(JSONObject requestVo) throws ServiceException;
//    /** banner启停  */
//    SingleResult<DataResponse> banner_open_close(JSONObject requestVo) throws ServiceException;
//    /** banner删除  */
//    SingleResult<DataResponse> banner_del(JSONObject requestVo) throws ServiceException;
//    /**  banner新增 */
//    SingleResult<DataResponse> banner_add(JSONObject requestVo) throws ServiceException;
    /**  banner详情  */
    SingleResult<DataResponse> banner_sel_detail(JSONObject requestVo) throws ServiceException;
    /**  banner更新  */
    SingleResult<DataResponse> banner_upd(JSONObject requestVo) throws ServiceException;

}
