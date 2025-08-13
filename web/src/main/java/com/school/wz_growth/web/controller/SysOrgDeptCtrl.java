package com.school.wz_growth.web.controller;

import com.alibaba.fastjson.JSONObject;
import com.school.wz_growth.common.exception.ServiceException;
import com.school.wz_growth.common.response.DataResponse;
import com.school.wz_growth.common.response.SingleResult;
import com.school.wz_growth.model.domain.sys.BasicAddRequestVo;
import com.school.wz_growth.service.SysBasicService;
import com.school.wz_growth.web.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.validation.Valid;


/**  零时的类*/
@RequestMapping("/SysOrgDept")
@RestController
public class SysOrgDeptCtrl extends BaseController {



    @Autowired
    private SysBasicService sysBasicService;



    /**
     * 公司 - 信息编辑
     * @param
     * @return
     */
    @RequestMapping("/update_org_info")
    public SingleResult<DataResponse> update_org_info(@Valid @RequestBody JSONObject requestVo, BindingResult result)throws ServiceException {
        //必须要调用validate方法才能实现输入参数的合法性校验
        putAppUserTokenParams(requestVo);
        return sysBasicService.doAddBasic(requestVo);
    }
}
