package com.school.wz_growth.service.Impl;


import com.alibaba.fastjson.JSONObject;
import com.school.wz_growth.common.exception.ServiceException;
import com.school.wz_growth.common.qiniu.QiniuUpload;
import com.school.wz_growth.common.response.Code;
import com.school.wz_growth.common.response.DataResponse;
import com.school.wz_growth.common.response.SingleResult;
import com.school.wz_growth.common.validator.StringUnits;
import com.school.wz_growth.dao.BannerMapper;
import com.school.wz_growth.service.BannerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.util.ObjectUtils;

import java.util.List;

@Service
public class BannerServiceImpl implements BannerService {


    @Autowired
    private BannerMapper mapper;


//    @Override
//    public SingleResult<DataResponse> banner_list(JSONObject requestVo) throws ServiceException {
//        try {
//            JSONObject vo = new JSONObject();
//            vo.put("start", requestVo.get("start"));
//            vo.put("limit", requestVo.get("limit"));
//            vo.put("name", requestVo.get("name"));
//            vo.put("user_name", requestVo.get("user_name"));
//            vo.put("status", requestVo.get("status"));
//            vo.put("start_time", requestVo.get("start_time"));
//            vo.put("end_time", requestVo.get("end_time"));
//            vo.put("c_s_date", requestVo.get("c_s_date"));
//            JSONObject result = new JSONObject();
//            result.put("list", mapper.banner_list(vo));
//            result.put("total", mapper.banner_list_count(vo));
//            return SingleResult.buildSuccess(result);
//        } catch (Throwable t) {
//            t.printStackTrace();
//            return SingleResult.buildFailure(Code.ERROR, t.getMessage());
//        }
//    }
//
//
//    @Override
//    public SingleResult<DataResponse> banner_open_close(JSONObject requestVo) throws ServiceException {
//        try {
//            JSONObject vo = new JSONObject();
//            vo.put("id", requestVo.get("id"));
//            vo.put("status", requestVo.get("status"));
//            mapper.banner_open_close(vo);
//            return SingleResult.buildSuccess(Code.SUCCESS);
//        } catch (Throwable t) {
//            t.printStackTrace();
//            return SingleResult.buildFailure(Code.ERROR, t.getMessage());
//        }
//    }
//
//    @Override
//    public SingleResult<DataResponse> banner_del(JSONObject requestVo) throws ServiceException {
//        try {
//            JSONObject vo = new JSONObject();
//            vo.put("ids", requestVo.get("ids"));
//            mapper.banner_del(vo);
//            return SingleResult.buildSuccess(Code.SUCCESS);
//        } catch (Throwable t) {
//            t.printStackTrace();
//            return SingleResult.buildFailure(Code.ERROR, t.getMessage());
//        }
//    }
//
//    @Override
//    @Transactional(propagation = Propagation.REQUIRES_NEW,
//            rollbackFor = Exception.class, isolation = Isolation.REPEATABLE_READ)
//    public SingleResult<DataResponse> banner_add(JSONObject requestVo) throws ServiceException {
//        try {
//            JSONObject vo = new JSONObject();
//            vo.put("name", requestVo.get("name"));
//            vo.put("status", requestVo.get("status"));
//            vo.put("start_time", requestVo.get("start_time"));
//            vo.put("end_time", requestVo.get("end_time"));
//            vo.put("u_id", requestVo.get("uId"));
//            vo.put("time_lag", requestVo.get("time_lag")); //轮播时间
//            int i1 = mapper.sel_banner_time(vo);
//            if (i1 > 0)
//                return SingleResult.buildFailure(Code.ERROR, "Banner时间重复");
//            mapper.banner_add(vo);
//            List<JSONObject> list = requestVo.getJSONArray("details").toJavaList(JSONObject.class);
//            int i = 1;
//            for (JSONObject jsonObject : list) {
//                jsonObject.put("data_id", vo.get("id"));
////                if (StringUnits.isNotNullOrEmpty(jsonObject.get("name")))
////                    jsonObject.put("name", jsonObject.getString("name").replaceAll(QiniuUpload.qiniu_base_url,""));
////                else
//                jsonObject.put("name", jsonObject.get("name"));
////                jsonObject.put("path", jsonObject.getString("path").replace(QiniuUpload.qiniu_base_url,""));
//
//                if (StringUnits.isNotNullOrEmpty(jsonObject.get("path")))
//                    jsonObject.put("path", jsonObject.getString("path").replaceAll(QiniuUpload.qiniu_base_url,""));
//                else
//                    jsonObject.put("path", "");
//
//                jsonObject.put("sequence", i++);
//            }
//            mapper.banner_add_details(list);
//            return SingleResult.buildSuccess(Code.SUCCESS);
//        } catch (Throwable t) {
//            t.printStackTrace();
//            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
//            return SingleResult.buildFailure(Code.ERROR, t.getMessage());
//        }
//    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW,
            rollbackFor = Exception.class, isolation = Isolation.REPEATABLE_READ)
    public SingleResult<DataResponse> banner_sel_detail(JSONObject requestVo) throws ServiceException {
        try {
            JSONObject vo = new JSONObject();

            JSONObject result = mapper.banner_sel_detail(vo);
            vo.put("id", result.get("id"));
            List<JSONObject> l=mapper.banner_sel_detail_d(vo);
            for (JSONObject jsonObject : l) {
                if (!ObjectUtils.isEmpty(jsonObject.get("path")))
                    jsonObject.put("path", QiniuUpload.qiniu_base_url+jsonObject.getString("path"));
            }
            result.put("details",l );
            return SingleResult.buildSuccess(result);
        } catch (Throwable t) {
            t.printStackTrace();
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return SingleResult.buildFailure(Code.ERROR, t.getMessage());
        }
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW,
            rollbackFor = Exception.class, isolation = Isolation.REPEATABLE_READ)
    public SingleResult<DataResponse> banner_upd(JSONObject requestVo) throws ServiceException {
        try {
            JSONObject vo = new JSONObject();
            vo.put("id", requestVo.get("id"));
            vo.put("name", requestVo.get("name"));
//            vo.put("status", requestVo.get("status"));
            vo.put("start_time", requestVo.get("start_time"));
            vo.put("end_time", requestVo.get("end_time"));
            vo.put("u_id", requestVo.get("uId"));
            vo.put("time_lag", requestVo.get("time_lag")); //轮播时间

            int i1 = mapper.sel_banner_time(vo);
            if (i1 > 0)
                return SingleResult.buildFailure(Code.ERROR, "Banner时间重复");
            mapper.banner_upd(vo);
            List<JSONObject> list = requestVo.getJSONArray("details").toJavaList(JSONObject.class);
            int i = 1;
            for (JSONObject jsonObject : list) {
                jsonObject.put("data_id", vo.get("id"));
                jsonObject.put("sequence", i++);

//                if (StringUnits.isNotNullOrEmpty(jsonObject.get("name")))
//                    jsonObject.put("name", jsonObject.getString("name").replaceAll(QiniuUpload.qiniu_base_url,""));
//                else
                jsonObject.put("name", jsonObject.getString("name"));

                if (StringUnits.isNotNullOrEmpty(jsonObject.get("path")))
                    jsonObject.put("path", jsonObject.getString("path").replaceAll(QiniuUpload.qiniu_base_url,""));
                else
                    jsonObject.put("path", "");
            }
            mapper.del_banner(vo);
            mapper.banner_add_details(list);
            return SingleResult.buildSuccess(Code.SUCCESS);
        } catch (Throwable t) {
            t.printStackTrace();
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return SingleResult.buildFailure(Code.ERROR, t.getMessage());
        }
    }


}


