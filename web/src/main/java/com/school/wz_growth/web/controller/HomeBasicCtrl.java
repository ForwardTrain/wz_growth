package com.school.wz_growth.web.controller;

import com.alibaba.fastjson.JSONObject;
import com.school.wz_growth.common.constant.WebConstant;
import com.school.wz_growth.common.exception.ServiceException;
import com.school.wz_growth.common.response.SingleResult;
import com.school.wz_growth.model.front.request.sys.AdminUpPsdRequestVo;
import com.school.wz_growth.service.HomeBasicService;
import com.school.wz_growth.web.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;


/**
 * 网站基础信息
 */
@RequestMapping("/HomeBasic")
@RestController
public class HomeBasicCtrl extends BaseController {

    @Autowired
    private HomeBasicService homeBasicService;

    /** 客户端信息 */
    @RequestMapping("/info")
    @ResponseBody
    public SingleResult select_info(@RequestBody JSONObject requestVo, HttpServletRequest request)throws ServiceException {
        return homeBasicService.select_info(request, requestVo);
    }

    /** 界面 浏览记录 */
    @RequestMapping("/sel_view_recode")
    @ResponseBody
    public SingleResult sel_view_recode(@RequestBody JSONObject requestVo, HttpServletRequest request)throws ServiceException {
        return homeBasicService.sel_view_recode(request, requestVo);
    }

    /** 客户IP状态 */
    @RequestMapping("/sel_ip_status")
    @ResponseBody
    public SingleResult sel_ip_status(@RequestBody JSONObject requestVo, HttpServletRequest request)throws ServiceException {
        return homeBasicService.sel_ip_status(request, requestVo);
    }



    /** 首页零散信息统计 */
    @RequestMapping("/sel_home_info")
    @ResponseBody
    public SingleResult sel_home_info(@RequestBody JSONObject requestVo, HttpServletRequest request)throws ServiceException {
        return homeBasicService.sel_home_info(request, requestVo);
    }

    /** 首页零散信息统计 */
    @RequestMapping("/sel_contact_us")
    @ResponseBody
    public SingleResult sel_contact_us(@RequestBody JSONObject requestVo, HttpServletRequest request)throws ServiceException {
        return homeBasicService.sel_contact_us(request, requestVo);
    }


    /**  banner查看    */
    @RequestMapping("/banner_sel_detail")
    @ResponseBody
    public SingleResult banner_sel_detail(@RequestBody JSONObject requestVo)throws ServiceException {
        return homeBasicService.banner_sel_detail(requestVo);
    }

    /**  首页蛋白数据统计    */
    @RequestMapping("/sel_home_protein_num_count")
    @ResponseBody
    public SingleResult sel_home_protein_num_count(@RequestBody JSONObject requestVo)throws ServiceException {
        return homeBasicService.sel_home_family_count(requestVo);
    }

//    /**  family数据统计
//     *       /HomeBasic/sel_home_family_count
//     * */
//    @RequestMapping("/sel_home_family_count")
//    @ResponseBody
//    public SingleResult sel_home_family_count(@RequestBody JSONObject requestVo)throws ServiceException {
//        return homeBasicService.sel_home_family_count(requestVo);
//    }


//    @Scheduled(cron = "0 0 */1 * * ?")
//    @RequestMapping("/scheduled_task")
//    public void scheduled_task() throws Exception {
//        System.out.println("数据检查告警信息发送");
//
////        if (WebConstant.is_product)
//            homeBasicService.scheduled_task();
//    }

}
