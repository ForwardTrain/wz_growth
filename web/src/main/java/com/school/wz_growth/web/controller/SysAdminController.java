package com.school.wz_growth.web.controller;



import com.alibaba.fastjson.JSONObject;
import com.school.wz_growth.common.exception.ServiceException;
import com.school.wz_growth.common.qiniu.QiniuUpload;
import com.school.wz_growth.common.response.SingleResult;
import com.school.wz_growth.model.front.request.sys.AdminCheckRequestVo;
import com.school.wz_growth.model.front.request.sys.AdminLoginRequestVo;
import com.school.wz_growth.model.front.request.sys.AdminUpPsdRequestVo;
import com.school.wz_growth.model.front.request.sys.IdsRequestVo;
import com.school.wz_growth.service.SysAdminService;
import com.school.wz_growth.web.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;


/**
 * 用户管理逻辑
 */
@RequestMapping("/SysAdmin")
@RestController
public class SysAdminController extends BaseController {

    @Autowired
    private SysAdminService adminService;

    /**  用户登录 */
    @RequestMapping("/login")
    @ResponseBody
    public SingleResult login(@RequestBody AdminLoginRequestVo requestVo)throws ServiceException {
        super.validatorParam(requestVo);
        return adminService.doLogin(requestVo);
    }
    /**  修改密码 */
    @RequestMapping("/update/psd")
    @ResponseBody
    public SingleResult upPsd(@RequestBody AdminUpPsdRequestVo requestVo)throws ServiceException {
        super.validatorParam(requestVo);
        super.putAppUserTokenParams(requestVo);
        return adminService.doUpPsd(requestVo);
    }
    /** 校验是否需要修改默认密码 */
    @RequestMapping("/check/psd")
    @ResponseBody
    public SingleResult checkPsd(@RequestBody AdminCheckRequestVo requestVo)throws ServiceException {
        super.putAppUserTokenParams(requestVo);
        return adminService.doCheckPsd(requestVo);
    }



    /**
     * qiniutoken
     * @param
     * @return
     * @throws ServiceException
     */
    @RequestMapping("/sel_qiniu_token")
    @ResponseBody
    public String sel_qiniu_token(@RequestBody JSONObject requestVo ) throws Exception {
        return new QiniuUpload().getUpToken();
    }




    /** 系统工作人员-列表 */
    @RequestMapping("/sys_account_list")
    @ResponseBody
    public SingleResult sys_account_list(@RequestBody JSONObject requestVo)throws ServiceException {
        super.putAppUserTokenParams(requestVo);
        return adminService.query_sys_account_list(requestVo);
    }
    /**  系统工作人员--新增更新  */
    @RequestMapping("/edit_sys_account_list")
    @ResponseBody
    public SingleResult edit_sys_account_list(@RequestBody JSONObject requestVo)throws ServiceException {
        super.putAppUserTokenParams(requestVo);
        return adminService.edit_sys_account_list(requestVo);
    }
    /** 启、禁用帐户 */
    @RequestMapping("/update_state_sys_account")
    @ResponseBody
    public SingleResult update_state_sys_account(@RequestBody JSONObject requestVo)throws ServiceException {
        super.putAppUserTokenParams(requestVo);
        return adminService.update_state_sys_account(requestVo);
    }
    /** 系统工作人员--删除 */
    @RequestMapping("/del_sys_account_list")
    @ResponseBody
    public SingleResult del_sys_account_list(@RequestBody IdsRequestVo requestVo)throws ServiceException {
        super.putAppUserTokenParams(requestVo);
        return adminService.del_sys_account_list(requestVo);
    }
}
