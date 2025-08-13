package com.school.wz_growth.service.Impl;


import com.alibaba.fastjson.JSONObject;
import com.school.wz_growth.common.exception.ServiceException;
import com.school.wz_growth.common.response.Code;
import com.school.wz_growth.common.response.DataResponse;
import com.school.wz_growth.common.response.SingleResult;
import com.school.wz_growth.dao.ItemMapper;
import com.school.wz_growth.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import java.util.List;

@Service
public class ItemServiceImpl implements ItemService {
    @Autowired
    ItemMapper mapper;
    @Override
    public SingleResult<DataResponse> sel_list(JSONObject requestVo) throws ServiceException {
        try {
            JSONObject params=new JSONObject(){{
                put("left_show",requestVo.get("left_show"));
                put("p_name",requestVo.get("p_name"));
                put("type",requestVo.get("type"));
                put("start",requestVo.get("start"));
                put("limit",requestVo.get("limit"));
            }};
            List<JSONObject> list=mapper.sel_list(params);
           int total=mapper.sel_list_total(params);
           JSONObject result=new JSONObject(){{
               put("list",list);
               put("total",total);
           }};
            return SingleResult.buildSuccess(result);
        } catch (Throwable t) {
            t.printStackTrace();
            throw new ServiceException(t.getMessage(),Code.ERROR.getStatus());
        }
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW,
            rollbackFor = Exception.class, isolation = Isolation.REPEATABLE_READ)
    @Override
    public SingleResult<DataResponse> del_list(JSONObject requestVo) throws ServiceException {
        try {
            JSONObject params=new JSONObject(){{
                put("ids",requestVo.get("ids"));
            }};
            mapper.del_list(params);

            return SingleResult.buildSuccessWithoutData();
        } catch (Throwable t) {
            t.printStackTrace();
            throw new ServiceException(t.getMessage(),Code.ERROR.getStatus());
        }
    }
    @Override
    public SingleResult<DataResponse> p_item_list(JSONObject requestVo) throws ServiceException {
        try {
            return SingleResult.buildSuccess(mapper.p_item_list());
        } catch (Throwable t) {
            t.printStackTrace();
            throw new ServiceException(t.getMessage(),Code.ERROR.getStatus());
        }
    }

    @Override
    public SingleResult<DataResponse> add_upd_list(JSONObject requestVo) throws ServiceException {
        try {
            JSONObject params=new JSONObject(){{
                put("id",requestVo.get("id"));
                put("name",requestVo.get("name"));
                put("p_id",requestVo.get("p_id"));
                put("status",requestVo.get("status"));
                put("operator",requestVo.get("u_name"));
                put("left_show",requestVo.get("left_show"));
                put("sort",requestVo.get("sort"));
                put("type",requestVo.get("type"));
            }};
            if (ObjectUtils.isEmpty(params.get("id"))){
                mapper.add_list(params);
            }else{
                mapper.upd_list(params);
            }
            return SingleResult.buildSuccessWithoutData();
        } catch (Throwable t) {
            t.printStackTrace();
            throw new ServiceException(t.getMessage(),Code.ERROR.getStatus());
        }
    }





}
