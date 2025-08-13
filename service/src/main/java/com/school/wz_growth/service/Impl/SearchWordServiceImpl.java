package com.school.wz_growth.service.Impl;


import com.alibaba.fastjson.JSONObject;
import com.school.wz_growth.common.exception.ServiceException;
import com.school.wz_growth.common.response.Code;
import com.school.wz_growth.common.response.DataResponse;
import com.school.wz_growth.common.response.SingleResult;
import com.school.wz_growth.dao.SearchWordMapper;
import com.school.wz_growth.service.SearchWordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SearchWordServiceImpl implements SearchWordService {
    @Autowired
    SearchWordMapper mapper;
    @Override
    public SingleResult<DataResponse> sel_list(JSONObject requestVo) throws ServiceException {
        try {
            JSONObject params=new JSONObject(){{
                put("status",requestVo.get("status"));
                put("name",requestVo.get("name"));
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
    public SingleResult<DataResponse> add_upd_list(JSONObject requestVo) throws ServiceException {
        try {
            JSONObject params=new JSONObject(){{
                put("id",requestVo.get("id"));
                put("name",requestVo.get("name"));
                put("num",requestVo.get("num"));
                put("status",requestVo.get("status"));
                put("operator",requestVo.get("u_name"));
            }};
            if (ObjectUtils.isEmpty(params.get("id"))){
                int is_exit=mapper.sel_word_by_name(params);
                if (is_exit>0)
                    return SingleResult.buildFailure(Code.ERROR,"该词已存在");
                mapper.add_list(params);
            }else{
                int is_exit=mapper.sel_word_by_name_and_id(params);
                if (is_exit>0)
                    return SingleResult.buildFailure(Code.ERROR,"该词已存在");
                mapper.upd_list(params);
            }
            return SingleResult.buildSuccessWithoutData();
        } catch (Throwable t) {
            t.printStackTrace();
            throw new ServiceException(t.getMessage(),Code.ERROR.getStatus());
        }
    }

    @Override
    public SingleResult<DataResponse> upd_search_list(JSONObject requestVo) throws ServiceException {
        try {
            JSONObject params=new JSONObject(){{
                put("name",requestVo.get("name"));
                put("operator",requestVo.get("u_name"));
            }};
                int is_exit=mapper.sel_word_by_name(params);
                if (is_exit>0)
                    mapper.upd_num(params);
                else
                mapper.add_new_word(params);

            return SingleResult.buildSuccessWithoutData();
        } catch (Throwable t) {
            t.printStackTrace();
            throw new ServiceException(t.getMessage(),Code.ERROR.getStatus());
        }
    }
    @Override
    public SingleResult<DataResponse> search_set_list(JSONObject requestVo) throws ServiceException {
        try {
            JSONObject params=new JSONObject(){{
                put("type_name",requestVo.get("type_name"));
            }};
            List<JSONObject> list=mapper.search_set_list(params);
            return SingleResult.buildSuccess(list);
        } catch (Throwable t) {
            t.printStackTrace();
            throw new ServiceException(t.getMessage(),Code.ERROR.getStatus());
        }
    }
    @Override
    public SingleResult<DataResponse> search_type_name_list(JSONObject requestVo) throws ServiceException {
        try {

            List<JSONObject> list=mapper.search_type_name_list(new JSONObject());
            return SingleResult.buildSuccess(list);
        } catch (Throwable t) {
            t.printStackTrace();
            throw new ServiceException(t.getMessage(),Code.ERROR.getStatus());
        }
    }
    @Override
    public SingleResult<DataResponse> upd_search_set_list(JSONObject requestVo) throws ServiceException {
        try {
            JSONObject params=new JSONObject(){{
                put("id",requestVo.get("id"));
            }};
            mapper.upd_search_set_list(params);
            return SingleResult.buildSuccessWithoutData();
        } catch (Throwable t) {
            t.printStackTrace();
            throw new ServiceException(t.getMessage(),Code.ERROR.getStatus());
        }
    }





}
