package com.school.wz_growth.web.controller;

import com.alibaba.fastjson.JSONObject;
import com.school.wz_growth.common.exception.ServiceException;
import com.school.wz_growth.common.response.DataResponse;
import com.school.wz_growth.common.response.SingleResult;
import com.school.wz_growth.model.domain.sys.BasicAddRequestVo;
import com.school.wz_growth.model.front.request.base.BaseRequestVo;
import com.school.wz_growth.service.SysBasicService;
import com.school.wz_growth.web.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.validation.Valid;

/** 公司信息 */
@RequestMapping("/SysBasic")
@RestController
public class SysBasicController extends BaseController {

    @Autowired
    private SysBasicService sysBasicService;


    /**  基本信息公开的 - 详情 */
    @RequestMapping("/sys_info")
    public SingleResult<DataResponse> sel_sys_info(@Valid @RequestBody JSONObject requestVo, BindingResult result)throws ServiceException {
        //必须要调用validate方法才能实现输入参数的合法性校验
        return sysBasicService.sel_sys_info(requestVo);
    }


//    /**
//     * 公司 - 信息编辑
//     * @param
//     * @return
//     */
//    @RequestMapping("/update_org_info")
//    public SingleResult<DataResponse> add(BasicAddRequestVo requestVo, MultipartHttpServletRequest request, BindingResult result) throws ServiceException {
//        super.validatorParam(requestVo);
//        super.putAppUserTokenParams(requestVo);
//        return sysBasicService.doAddBasic(requestVo,request);
//    }

    /**
     *  公司 - 详情
     * @param
     * @return
     */
    @RequestMapping("/detail")
    public SingleResult<DataResponse> detail(@Valid @RequestBody BaseRequestVo requestVo, BindingResult result) throws ServiceException {
        super.validatorParam(requestVo);
        return sysBasicService.queryBasic(requestVo);
    }



    /** 系统参数 - 列表 */
    @RequestMapping("/sel_sys_params_list")
    public SingleResult<DataResponse> sel_sys_params_list(@Valid @RequestBody JSONObject requestVo, BindingResult result)throws ServiceException {
        //必须要调用validate方法才能实现输入参数的合法性校验
        return sysBasicService.sel_sys_params_list(requestVo);
    }
    /** 系统参数 - 更新 */
    @RequestMapping("/update_sys_params_list")
    public SingleResult<DataResponse> update_sys_params_list(@Valid @RequestBody JSONObject requestVo, BindingResult result)throws ServiceException {
        //必须要调用validate方法才能实现输入参数的合法性校验
        return sysBasicService.update_sys_params_list(requestVo);
    }
}
