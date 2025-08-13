package com.school.wz_growth.service;


import com.alibaba.fastjson.JSONObject;
import com.school.wz_growth.common.exception.ServiceException;
import com.school.wz_growth.common.response.DataResponse;
import com.school.wz_growth.common.response.SingleResult;
import com.school.wz_growth.model.front.request.sys.SysBasicChinaCodeRequestVo;


public interface SysBasicToolService {


    /**省、市、区 - 列表*/
    SingleResult<DataResponse> queryChinaCode(SysBasicChinaCodeRequestVo requestVo) throws ServiceException;



}
