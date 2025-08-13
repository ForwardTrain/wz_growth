package com.school.wz_growth.service;

import com.alibaba.fastjson.JSONObject;
import com.school.wz_growth.common.exception.ServiceException;
import com.school.wz_growth.common.response.DataResponse;
import com.school.wz_growth.common.response.SingleResult;
import com.school.wz_growth.model.front.request.sys.AdminCheckRequestVo;
import com.school.wz_growth.model.front.request.sys.AdminLoginRequestVo;
import com.school.wz_growth.model.front.request.sys.AdminUpPsdRequestVo;
import com.school.wz_growth.model.front.request.sys.IdsRequestVo;

public interface SysAdminService {

    /**
     * 用户登录
     * @param requestVo
     * @return
     * @throws ServiceException
     */
    SingleResult<DataResponse> doLogin(AdminLoginRequestVo requestVo) throws ServiceException;
    /**
     * 用户修改密码
     * @param requestVo
     * @return
     * @throws ServiceException
     */
    SingleResult<DataResponse> doUpPsd(AdminUpPsdRequestVo requestVo) throws ServiceException;

    /**
     * 校验
     * @param requestVo
     * @return
     * @throws ServiceException
     */
    SingleResult<DataResponse> doCheckPsd(AdminCheckRequestVo requestVo) throws ServiceException;





    /** 系统工作人员 */
    SingleResult<DataResponse> query_sys_account_list(JSONObject requestVo) throws ServiceException;
    SingleResult<DataResponse> edit_sys_account_list(JSONObject requestVo) throws ServiceException;
    SingleResult<DataResponse> update_state_sys_account(JSONObject requestVo) throws ServiceException;
    SingleResult<DataResponse> del_sys_account_list(IdsRequestVo requestVo) throws ServiceException;
//    SingleResult<DataResponse> sel_sys_account_list_edit_option_job(JSONObject requestVo) throws ServiceException;


}
