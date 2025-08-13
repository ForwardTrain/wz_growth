package com.school.wz_growth.web.controller;


import com.alibaba.fastjson.JSONObject;

import com.school.wz_growth.common.exception.ServiceException;
import com.school.wz_growth.common.response.SingleResult;
import com.school.wz_growth.model.front.request.sys.SysBasicChinaCodeRequestVo;
import com.school.wz_growth.service.SysBasicToolService;
import com.school.wz_growth.web.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * 用户管理逻辑
 */
@RequestMapping("/SysBasicTool")
@RestController
public class SysBasicToolCtrl extends BaseController {

    @Autowired
    private SysBasicToolService service;


    /** 省、市、区*/
    @RequestMapping("/china")
    @ResponseBody
    public SingleResult china(@RequestBody SysBasicChinaCodeRequestVo requestVo)throws ServiceException {
        return service.queryChinaCode(requestVo);
    }



}
