package com.school.wz_growth.web.controller;


import com.alibaba.fastjson.JSONObject;
import com.school.wz_growth.common.exception.ServiceException;
import com.school.wz_growth.common.response.SingleResult;
import com.school.wz_growth.service.BannerService;
import com.school.wz_growth.web.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;


/**
 * Banner
 */
@RequestMapping("/Banner")
@RestController
public class BannerController extends BaseController {

    @Autowired
    private BannerService service;

//    /** banner 列表  */
//    @RequestMapping("/banner_list")
//    @ResponseBody
//    public SingleResult banner_list(@RequestBody JSONObject requestVo)throws ServiceException {
//        super.putAppUserTokenParams(requestVo);
//        return service.banner_list(requestVo);
//    }
//    /**  banner启停  */
//    @RequestMapping("/banner_open_close")
//    @ResponseBody
//    public SingleResult banner_open_close(@RequestBody JSONObject requestVo)throws ServiceException {
//        super.putAppUserTokenParams(requestVo);
//        return service.banner_open_close(requestVo);
//    }
//    /**  banner删除  */
//    @RequestMapping("/banner_del")
//    @ResponseBody
//    public SingleResult banner_del(@RequestBody JSONObject requestVo)throws ServiceException {
//        super.putAppUserTokenParams(requestVo);
//        return service.banner_del(requestVo);
//    }
//
//    /**  banner新增 */
//    @RequestMapping("/banner_add")
//    @ResponseBody
//    public SingleResult banner_add(@RequestBody JSONObject requestVo)throws ServiceException {
//        super.putAppUserTokenParams(requestVo);
//        return service.banner_add(requestVo);
//    }

    /**  banner查看 */
    @RequestMapping("/banner_sel_detail")
    @ResponseBody
    public SingleResult banner_sel_detail(@RequestBody JSONObject requestVo)throws ServiceException {
        super.putAppUserTokenParams(requestVo);
        return service.banner_sel_detail(requestVo);
    }
    /**  banner更新  */
    @RequestMapping("/banner_upd")
    @ResponseBody
    public SingleResult banner_upd(@RequestBody JSONObject requestVo)throws ServiceException {
        super.putAppUserTokenParams(requestVo);
        return service.banner_upd(requestVo);
    }






}
