package com.school.wz_growth.web.controller;

import com.alibaba.fastjson.JSONObject;
import com.mongodb.util.Hash;
import com.school.wz_growth.common.exception.ServiceException;
import com.school.wz_growth.common.response.SingleResult;
import com.school.wz_growth.service.LiteraturesService;
import com.school.wz_growth.web.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * 文献管理
 */
@RequestMapping("/Literatures")
@RestController
public class LiteraturesCtrl extends BaseController {

    @Autowired
    private LiteraturesService service;


    /** clinicaltrials列表 */
    @RequestMapping("/sel_clinicaltrials_list")
    @ResponseBody
    public SingleResult sel_clinicaltrials_list(@RequestBody JSONObject requestVo)throws ServiceException {
        super.putAppUserTokenParams(requestVo);
        return service.sel_clinicaltrials_list( requestVo);
    }

    /** clinicaltrials列表 - 删除 */
    @RequestMapping("/del_clinicaltrials_list")
    @ResponseBody
    public SingleResult del_clinicaltrials_list(@RequestBody JSONObject requestVo)throws ServiceException {
        super.putAppUserTokenParams(requestVo);
        return service.del_clinicaltrials_list( requestVo);
    }


    /** GM列表 */
    @RequestMapping("/sel_GM_list")
    @ResponseBody
    public SingleResult sel_GM_list(@RequestBody JSONObject requestVo)throws ServiceException {
        super.putAppUserTokenParams(requestVo);
        return service.sel_GM_list( requestVo);
    }
    /** GM列表 - 删除 */
    @RequestMapping("/del_GM_list")
    @ResponseBody
    public SingleResult del_GM_list(@RequestBody JSONObject requestVo)throws ServiceException {
        super.putAppUserTokenParams(requestVo);
        return service.del_GM_list( requestVo);
    }
    /** TL列表 */
    @RequestMapping("/sel_TL_list")
    @ResponseBody
    public SingleResult sel_TL_list(@RequestBody JSONObject requestVo)throws ServiceException {
        super.putAppUserTokenParams(requestVo);
        return service.sel_TL_list( requestVo);
    }
    /** TL列表 - 删除 */
    @RequestMapping("/del_TL_list")
    @ResponseBody
    public SingleResult del_TL_list(@RequestBody JSONObject requestVo)throws ServiceException {
        super.putAppUserTokenParams(requestVo);
        return service.del_TL_list( requestVo);
    }


//    /** TL列表 */
//    @RequestMapping("/sel_TL_list_test")
//    @ResponseBody
//    public SingleResult sel_TL_list_test(@RequestBody JSONObject requestVo)throws ServiceException {
//        return service.sel_TL_list( requestVo);
//    }



    /** 测试用 */
    @RequestMapping("/test")
    @ResponseBody
    public SingleResult test(@RequestBody JSONObject requestVo)throws ServiceException {

       requestVo.put("url","https://www.uniprot.org/uniprotkb/Q6UW88/entry");
        return SingleResult.buildSuccess(requestVo);
    }




}
