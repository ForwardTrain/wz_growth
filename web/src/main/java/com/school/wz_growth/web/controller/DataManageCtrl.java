package com.school.wz_growth.web.controller;

import com.alibaba.fastjson.JSONObject;
import com.school.wz_growth.common.exception.ServiceException;
import com.school.wz_growth.common.response.SingleResult;
import com.school.wz_growth.service.DataManageService;
import com.school.wz_growth.web.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;



/**
 * 数据管理
 */
@RequestMapping("/DataManage")
@RestController
public class DataManageCtrl extends BaseController {

    @Autowired
    private DataManageService service;

    /** 数据列表 */
    @RequestMapping("/data_list")
    @ResponseBody
    public SingleResult data_list(@RequestBody JSONObject requestVo)throws ServiceException {
        super.putAppUserTokenParams(requestVo);
        super.putAppUserTokenParams(requestVo);
        return service.data_list( requestVo);
    }
    /** 数据列表删除 */
    @RequestMapping("/del_data_list")
    @ResponseBody
    public SingleResult del_data_list(@RequestBody JSONObject requestVo)throws ServiceException {
        super.putAppUserTokenParams(requestVo);
        return service.del_data_list( requestVo);
    }
    /** 数据更新列表 */
    @RequestMapping("/upd_data_list")
    @ResponseBody
    public SingleResult upd_data_list(@RequestBody JSONObject requestVo)throws ServiceException {
        super.putAppUserTokenParams(requestVo);
        return service.upd_data_list( requestVo);
    }
    /** 数据更新列表--查看 */
    @RequestMapping("/upd_data_list_sel")
    @ResponseBody
    public SingleResult upd_data_list_sel(@RequestBody JSONObject requestVo)throws ServiceException {
        super.putAppUserTokenParams(requestVo);
        return service.upd_data_list_sel( requestVo);
    }
    /** 数据更新列表--查看--数据库名称下拉框 */
    @RequestMapping("/upd_data_list_sel_db_combobox")
    @ResponseBody
    public SingleResult upd_data_list_sel_db_combobox(@RequestBody JSONObject requestVo)throws ServiceException {
        super.putAppUserTokenParams(requestVo);
        return service.upd_data_list_sel_db_combobox( requestVo);
    }
    /** 数据更新列表--查看--对比结果 */
    @RequestMapping("/upd_data_list_sel_db_combobox_left_down")
    @ResponseBody
    public SingleResult upd_data_list_sel_db_combobox_left_down(@RequestBody JSONObject requestVo)throws ServiceException {
        super.putAppUserTokenParams(requestVo);
        return service.upd_data_list_sel_db_combobox_left_down( requestVo);
    }
    /** 数据更新列表--查看--数据库名称下拉框--对应右侧列表 */
    @RequestMapping("/upd_data_list_sel_db_combobox_right_list")
    @ResponseBody
    public SingleResult upd_data_list_sel_db_combobox_right_list(@RequestBody JSONObject requestVo)throws ServiceException {
        super.putAppUserTokenParams(requestVo);
        return service.upd_data_list_sel_db_combobox_right_list( requestVo);
    }

    /** 数据更新--数据详情 */
    @RequestMapping("/upd_data_list_details")
    @ResponseBody
    public SingleResult upd_data_list_details(@RequestBody JSONObject requestVo)throws ServiceException {
        super.putAppUserTokenParams(requestVo);
        return service.upd_data_list_details( requestVo);
    }

    /** 数据更新--数据详情--保存 */
    @RequestMapping("/add_upd_as_know")
    @ResponseBody
    public SingleResult add_upd_as_know(@RequestBody JSONObject requestVo)throws ServiceException {
        super.putAppUserTokenParams(requestVo);
        return service.add_upd_as_know( requestVo);
    }

    /** 数据更新--数据详情--差异数据 */
    @RequestMapping("/diff_data")
    @ResponseBody
    public SingleResult diff_data(@RequestBody JSONObject requestVo)throws ServiceException {
        super.putAppUserTokenParams(requestVo);
        return service.diff_data( requestVo);
    }

    /** 数据更新--数据详情--差异数据 --更新*/
    @RequestMapping("/diff_data_upd")
    @ResponseBody
    public SingleResult diff_data_upd(@RequestBody JSONObject requestVo)throws ServiceException {
        super.putAppUserTokenParams(requestVo);
        return service.diff_data_upd( requestVo);
    }


    /** 数据更新--数据详情-- --更新*/
    @RequestMapping("/data_upd")
    @ResponseBody
    public SingleResult data_upd(@RequestBody JSONObject requestVo)throws ServiceException {
        super.putAppUserTokenParams(requestVo);
        return service.data_upd( requestVo);
    }







    /**  生长蛋白栏目--列表 */
    @RequestMapping("/sel_protein_type_list")
    @ResponseBody
    public SingleResult sel_list(@RequestBody JSONObject requestVo)throws ServiceException {
        super.putAppUserTokenParams(requestVo);
        return service.sel_protein_type_list( requestVo);
    }
    /**  生长蛋白栏目--列表 - 选择*/
    @RequestMapping("/sel_protein_type_list_option")
    @ResponseBody
    public SingleResult sel_protein_type_list_option(@RequestBody JSONObject requestVo)throws ServiceException {
        super.putAppUserTokenParams(requestVo);
        return service.sel_protein_type_list_option(requestVo);
    }

    /** 生长蛋白栏目 --删除*/
    @RequestMapping("/del_protein_type_list")
    @ResponseBody
    public SingleResult del_protein_type_list(@RequestBody JSONObject requestVo)throws ServiceException {
        super.putAppUserTokenParams(requestVo);
        return service.del_protein_type_list( requestVo);
    }
    /** 生长蛋白栏目 -- 新增 更新*/
    @RequestMapping("/edit_protein_type_list")
    @ResponseBody
    public SingleResult edit_protein_type_list(@RequestBody JSONObject requestVo)throws ServiceException {
        super.putAppUserTokenParams(requestVo);
        return service.edit_protein_type_list( requestVo);
    }





//    @Scheduled(cron = "0/5 * * * * ?")
//    public void send_msg( ) throws Exception {
//        System.out.println("数据检查告警信息发送");
//
////        if (WebConstant.is_product)
////            service.send_msg();
//    }

}
