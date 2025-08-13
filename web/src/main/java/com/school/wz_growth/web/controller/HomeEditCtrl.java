package com.school.wz_growth.web.controller;

import com.alibaba.fastjson.JSONObject;
import com.school.wz_growth.common.exception.ServiceException;
import com.school.wz_growth.common.response.SingleResult;
import com.school.wz_growth.service.ContentService;
import com.school.wz_growth.service.HomeEditService;
import com.school.wz_growth.web.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;


/**
 * 编辑页面
 */
@RequestMapping("/HomeEdit")
@RestController
public class HomeEditCtrl extends BaseController {

    @Autowired
    private HomeEditService service;


    @Autowired
    private ContentService cService;



    /** 搜索详情 */
    @RequestMapping("/sel_search_details")
    @ResponseBody
    public SingleResult sel_search_details(@RequestBody JSONObject requestVo)throws ServiceException {
//        return service.sel_search_details( requestVo);
        return service.sel_search_details_show( requestVo);
    }
    /** 编辑 - 家族列表 */
    @RequestMapping("/sel_search_details_familys")
    @ResponseBody
    public SingleResult sel_search_details_familys(@RequestBody JSONObject requestVo)throws ServiceException {
        return service.sel_search_details_familys( requestVo);
    }

    /** 获取自增drgfId和extry */
    @RequestMapping("/getCode")
    @ResponseBody
    public SingleResult getCode()throws ServiceException {
        return service.getCode( );
    }

    /** 更新数据 */
    @RequestMapping("/updData")
    @ResponseBody
    public SingleResult upd_gen_data(@RequestBody JSONObject requestVo)throws ServiceException {
//        super.putAppUserTokenParams(requestVo);
        return service.upd_gen_data( requestVo);
//        return SingleResult.buildSuccessWithoutData();
    }


















//
//    /** 基因一般信息 */
//    @RequestMapping("/upd_base_general_infomation_show")
//    @ResponseBody
//    public SingleResult upd_base_general_infomation_show(@RequestBody BaseGeneralInfomationShowReq requestVo)throws ServiceException {
//        super.putAppUserTokenParams(requestVo);
//        return service.upd_base_general_infomation_show( requestVo);
//    }
//
//    /** 蛋白质序列 */
//    @RequestMapping("/upd_base_protein_sequence_show")
//    @ResponseBody
//    public SingleResult upd_base_protein_sequence_show(@RequestBody BaseProteinSequenceShowReq requestVo)throws ServiceException {
//        super.putAppUserTokenParams(requestVo);
//        return service.upd_base_protein_sequence_show( requestVo);
//    }
//    /** 蛋白质功能展示 */
//    @RequestMapping("/upd_base_protein_function_show")
//    @ResponseBody
//    public SingleResult upd_base_protein_function_show(@RequestBody BaseProteinFunctionShowReq requestVo)throws ServiceException {
//        super.putAppUserTokenParams(requestVo);
//        return service.upd_base_protein_function_show( requestVo);
//    }
//    /** 表情和位置展示 */
//    @RequestMapping("/upd_base_expression_and_location_show")
//    @ResponseBody
//    public SingleResult upd_base_expression_and_location_show(@RequestBody BaseExpressionAndLocationShowReq requestVo)throws ServiceException {
//        super.putAppUserTokenParams(requestVo);
//        return service.upd_base_expression_and_location_show( requestVo);
//    }
//    /** 表情和位置hpa展示 */
//    @RequestMapping("/upd_base_expression_and_location_show_hpa")
//    @ResponseBody
//    public SingleResult upd_base_expression_and_location_show_hpa(@RequestBody BaseExpressionAndLocationShowHpaReq requestVo)throws ServiceException {
//        super.putAppUserTokenParams(requestVo);
//        return service.upd_base_expression_and_location_show_hpa( requestVo);
//    }
//
//    /** 蛋白质结构展示 */
//    @RequestMapping("/upd_base_protein_structure_show")
//    @ResponseBody
//    public SingleResult upd_base_protein_structure_show(@RequestBody BaseProteinStructureShowReq requestVo)throws ServiceException {
//        super.putAppUserTokenParams(requestVo);
//        return service.upd_base_protein_structure_show( requestVo);
//    }


}
