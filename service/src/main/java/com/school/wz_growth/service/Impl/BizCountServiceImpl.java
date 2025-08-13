package com.school.wz_growth.service.Impl;

import com.alibaba.fastjson.JSONObject;
import com.school.wz_growth.common.exception.ServiceException;
import com.school.wz_growth.common.response.Code;
import com.school.wz_growth.common.response.DataResponse;
import com.school.wz_growth.common.response.SingleResult;
import com.school.wz_growth.common.validator.DateUtils;
import com.school.wz_growth.dao.BizCountMapper;
import com.school.wz_growth.service.BizCountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class BizCountServiceImpl implements BizCountService {

    @Autowired
    private BizCountMapper bizCountMapper;

    @Override
    public SingleResult<DataResponse> sel_count_data(JSONObject requestVo) throws ServiceException {
        Map<String, Object> params = new HashMap<>();
        Map<String, Object> res = new HashMap<>();
        try {

            params.put("today", DateUtils.getCurrDate());
            params.put("yesterday", DateUtils.getYesterday());
            params.put("before_yesterday",DateUtils.getBeforeYesterday());

            Map<String, Object> stringObjectMap = bizCountMapper.sel_count_data(params);
            if (stringObjectMap.get("size_in_KB") == null) {
                stringObjectMap.put("size_in_KB", 0.0);
            } else {
                Double currentValue = Double.valueOf(String.valueOf(stringObjectMap.get("size_in_KB")));
                stringObjectMap.put("size_in_KB", currentValue + 80.8);
            }

            return SingleResult.buildSuccess(stringObjectMap);
        } catch (Throwable t) {
            t.printStackTrace();
            throw new ServiceException(t.getMessage(), Code.ERROR.getStatus());
        }
    }


    @Override
    public SingleResult<DataResponse> sel_area_count_data(JSONObject requestVo) throws ServiceException {
        Map<String, Object> params = new HashMap<>();
        Map<String, Object> res = new HashMap<>();

        params.put("c_s_date", requestVo.get("c_s_date"));
        params.put("is_overseas", requestVo.get("is_overseas"));

        try {
            List<Map<String, Object>> res_list = null;
            if (requestVo.get("is_overseas").equals("1")) {
                res_list = bizCountMapper.sel_area_word_count_data(params);
            } else {
                res_list = bizCountMapper.sel_area_china_count_data(params);
            }
            res.put("total", res_list.size());
            res.put("list", res_list);
            return SingleResult.buildSuccess(res);
        } catch (Throwable t) {
            t.printStackTrace();
            throw new ServiceException(t.getMessage(), Code.ERROR.getStatus());
        }
    }

    @Override
    public SingleResult<DataResponse> sel_browser_count_data(JSONObject requestVo) throws ServiceException {
        Map<String, Object> params = new HashMap<>();
        Map<String, Object> res = new HashMap<>();
        try {
            List<Map<String,Object>> res_list = bizCountMapper.sel_browser_count_data(params);
            res.put("total", res_list.size());
            res.put("list",res_list);
            return SingleResult.buildSuccess(res);
        } catch (Throwable t) {
            t.printStackTrace();
            throw new ServiceException(t.getMessage(), Code.ERROR.getStatus());
        }
    }

    @Override
    public SingleResult<DataResponse> sel_browser_page_count_data(JSONObject requestVo) throws ServiceException {
        Map<String, Object> params = new HashMap<>();
        Map<String, Object> res = new HashMap<>();
        try {
            List<Map<String,Object>> res_list = bizCountMapper.sel_browser_page_count_data(params);
            res.put("total",res_list.size());
            res.put("list",res_list);
            return SingleResult.buildSuccess(res);
        } catch (Throwable t) {
            t.printStackTrace();
            throw new ServiceException(t.getMessage(), Code.ERROR.getStatus());
        }
    }

    @Override
    public SingleResult<DataResponse> sel_literature_count_data(JSONObject requestVo) throws ServiceException {
        Map<String, Object> params = new HashMap<>();
        Map<String, Object> res = new HashMap<>();
        try {
            List<Map<String,Object>> res_list = bizCountMapper.sel_db_version_data(params);
            res.put("total",res_list.size());
            res.put("list",res_list);
            return SingleResult.buildSuccess(res);
        } catch (Throwable t) {
            t.printStackTrace();
            throw new ServiceException(t.getMessage(), Code.ERROR.getStatus());
        }
    }


    @Override
    public SingleResult<DataResponse> sel_customer_list(JSONObject requestVo) throws ServiceException {
        Map<String, Object> params = new HashMap<>();
        Map<String, Object> res = new HashMap<>();
        try {
            params.put("start",requestVo.get("start"));
            params.put("limit",requestVo.get("limit"));
            List<Map<String,Object>> res_list = bizCountMapper.sel_biz_ip_list(params);
            res.put("total",res_list.size());
            res.put("list",res_list);
            return SingleResult.buildSuccess(res);
        } catch (Throwable t) {
            t.printStackTrace();
            throw new ServiceException(t.getMessage(), Code.ERROR.getStatus());
        }
    }

    @Override
    public SingleResult<DataResponse> sel_customer_list_recode(JSONObject requestVo) throws ServiceException {
        Map<String, Object> params = new HashMap<>();
        Map<String, Object> res = new HashMap<>();
        try {
            params.put("start",requestVo.get("start"));
            params.put("limit",requestVo.get("limit"));
            params.put("ip",requestVo.get("ip"));
            List<Map<String,Object>> res_list = bizCountMapper.sel_biz_ip_list_history(params);
            res.put("total",res_list.size());
            res.put("list",res_list);
            return SingleResult.buildSuccess(res);
        } catch (Throwable t) {
            t.printStackTrace();
            throw new ServiceException(t.getMessage(), Code.ERROR.getStatus());
        }
    }


    @Override
    public SingleResult<DataResponse> upd_customer_status(JSONObject requestVo) throws ServiceException {
        Map<String, Object> params = new HashMap<>();
//        Map<String, Object> res = new HashMap<>();
        try {
            params.put("status",requestVo.get("status"));
            params.put("ip",requestVo.get("ip"));
            bizCountMapper.upd_customer_status(params);
            return SingleResult.buildSuccessWithoutData();
        } catch (Throwable t) {
            t.printStackTrace();
            throw new ServiceException(t.getMessage(), Code.ERROR.getStatus());
        }
    }


}
