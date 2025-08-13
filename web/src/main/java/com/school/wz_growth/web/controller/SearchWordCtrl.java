package com.school.wz_growth.web.controller;

import com.alibaba.fastjson.JSONObject;
import com.school.wz_growth.common.exception.ServiceException;
import com.school.wz_growth.common.response.SingleResult;
import com.school.wz_growth.service.SearchWordService;
import com.school.wz_growth.web.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;


/**
 * 词云管理
 */
@RequestMapping("/SearchWord")
@RestController
public class SearchWordCtrl extends BaseController {

    @Autowired
    private SearchWordService service;

    /** 词云列表 */
    @RequestMapping("/sel_list")
    @ResponseBody
    public SingleResult sel_list(@RequestBody JSONObject requestVo)throws ServiceException {
        super.putAppUserTokenParams(requestVo);
        return service.sel_list( requestVo);
    }

    /** 词云列表 --删除*/
    @RequestMapping("/del_list")
    @ResponseBody
    public SingleResult del_list(@RequestBody JSONObject requestVo)throws ServiceException {
        super.putAppUserTokenParams(requestVo);
        return service.del_list( requestVo);
    }

    /** 词云列表 --新增 更新*/
    @RequestMapping("/add_upd_list")
    @ResponseBody
    public SingleResult add_upd_list(@RequestBody JSONObject requestVo)throws ServiceException {
        super.putAppUserTokenParams(requestVo);
        return service.add_upd_list( requestVo);
    }

    /** 词云列表 --搜索更新 更新*/
    @RequestMapping("/upd_search_list")
    @ResponseBody
    public SingleResult upd_search_list(@RequestBody JSONObject requestVo)throws ServiceException {
        super.putAppUserTokenParams(requestVo);
        return service.upd_search_list( requestVo);
    }

    /** 词云列表 --搜索设置--列表*/
    @RequestMapping("/search_set_list")
    @ResponseBody
    public SingleResult search_set_list(@RequestBody JSONObject requestVo)throws ServiceException {
        super.putAppUserTokenParams(requestVo);
        return service.search_set_list( requestVo);
    }
    /** 词云列表 --搜索设置--列表*/
    @RequestMapping("/search_type_name_list")
    @ResponseBody
    public SingleResult search_type_name_list(@RequestBody JSONObject requestVo)throws ServiceException {
        super.putAppUserTokenParams(requestVo);
        return service.search_type_name_list( requestVo);
    }

    /** 词云列表 --搜索设置--列表--更新*/
    @RequestMapping("/upd_search_set_list")
    @ResponseBody
    public SingleResult upd_search_set_list(@RequestBody JSONObject requestVo)throws ServiceException {
        super.putAppUserTokenParams(requestVo);
        return service.upd_search_set_list( requestVo);
    }


}
