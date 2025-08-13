package com.school.wz_growth.web.controller;

import com.alibaba.fastjson.JSONObject;
import com.school.wz_growth.common.exception.ServiceException;
import com.school.wz_growth.common.response.SingleResult;
import com.school.wz_growth.service.BizCountService;
import com.school.wz_growth.web.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * 数据统计
 */
@RequestMapping("/BizCount")
@RestController
public class BizCountCtrl extends BaseController {

    @Autowired
    private BizCountService bizCountService;



    /** 流量概况 */
    @RequestMapping("/sel_count_data")
    @ResponseBody
    public SingleResult sel_count_data(@RequestBody JSONObject requestVo)throws ServiceException {
        return bizCountService.sel_count_data( requestVo);
    }

    /** 地域概况 */
    @RequestMapping("/sel_area_count_data")
    @ResponseBody
    public SingleResult sel_area_count_data(@RequestBody JSONObject requestVo)throws ServiceException {
        return bizCountService.sel_area_count_data( requestVo);
    }

    /** 浏览器访问量统计 */
    @RequestMapping("/sel_browser_count_data")
    @ResponseBody
    public SingleResult sel_browser_count_data(@RequestBody JSONObject requestVo)throws ServiceException {
        return bizCountService.sel_browser_count_data( requestVo);
    }

    /** 受访页面统计 */
    @RequestMapping("/sel_browser_page_count_data")
    @ResponseBody
    public SingleResult sel_browser_page_count_data(@RequestBody JSONObject requestVo)throws ServiceException {
        return bizCountService.sel_browser_page_count_data( requestVo);
    }

    /** 数据库 */
    @RequestMapping("/sel_literature_count_data")
    @ResponseBody
    public SingleResult sel_literature_count_data(@RequestBody JSONObject requestVo)throws ServiceException {
        return bizCountService.sel_literature_count_data( requestVo);
    }





    /** 客户列表 */
    @RequestMapping("/sel_customer_list")
    @ResponseBody
    public SingleResult sel_customer_list(@RequestBody JSONObject requestVo)throws ServiceException {
        super.putAppUserTokenParams(requestVo);
        return bizCountService.sel_customer_list( requestVo);
    }
    /** 客户列表  - 访问记录*/
    @RequestMapping("/sel_customer_list_recode")
    @ResponseBody
    public SingleResult sel_customer_list_recode(@RequestBody JSONObject requestVo)throws ServiceException {
        super.putAppUserTokenParams(requestVo);
        return bizCountService.sel_customer_list_recode( requestVo);
    }

    /** 客户列表 - 启、禁用*/
    @RequestMapping("/upd_customer_status")
    @ResponseBody
    public SingleResult upd_customer_status(@RequestBody JSONObject requestVo)throws ServiceException {
        super.putAppUserTokenParams(requestVo);
        return bizCountService.upd_customer_status( requestVo);
    }

}
