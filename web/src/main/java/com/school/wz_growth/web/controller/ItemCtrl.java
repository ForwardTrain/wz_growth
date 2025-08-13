package com.school.wz_growth.web.controller;

import com.alibaba.fastjson.JSONObject;
import com.school.wz_growth.common.exception.ServiceException;
import com.school.wz_growth.common.response.SingleResult;
import com.school.wz_growth.service.ItemService;
import com.school.wz_growth.web.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;


/**
 * 栏目
 */
@RequestMapping("/Item")
@RestController
public class ItemCtrl extends BaseController {

    @Autowired
    private ItemService service;

    /** 栏目列表 */
    @RequestMapping("/sel_list")
    @ResponseBody
    public SingleResult sel_list(@RequestBody JSONObject requestVo)throws ServiceException {
        super.putAppUserTokenParams(requestVo);
        return service.sel_list( requestVo);
    }
    /** 栏目列表 --删除*/
    @RequestMapping("/del_list")
    @ResponseBody
    public SingleResult del_list(@RequestBody JSONObject requestVo)throws ServiceException {
        super.putAppUserTokenParams(requestVo);
        return service.del_list( requestVo);
    }
    /** 栏目列表 --上级栏目*/
    @RequestMapping("/p_item_list")
    @ResponseBody
    public SingleResult p_item_list(@RequestBody JSONObject requestVo)throws ServiceException {
        super.putAppUserTokenParams(requestVo);
        return service.p_item_list( requestVo);
    }
    /** 栏目列表 --新增 更新*/
    @RequestMapping("/add_upd_list")
    @ResponseBody
    public SingleResult add_upd_list(@RequestBody JSONObject requestVo)throws ServiceException {
        super.putAppUserTokenParams(requestVo);
        return service.add_upd_list( requestVo);
    }
}
