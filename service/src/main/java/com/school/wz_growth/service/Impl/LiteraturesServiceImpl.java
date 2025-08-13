package com.school.wz_growth.service.Impl;

import cn.hutool.json.JSONUtil;
import com.alibaba.fastjson.JSONObject;
import com.school.wz_growth.common.exception.ServiceException;
import com.school.wz_growth.common.response.Code;
import com.school.wz_growth.common.response.DataResponse;
import com.school.wz_growth.common.response.SingleResult;
import com.school.wz_growth.common.validator.StringUnits;
import com.school.wz_growth.dao.LiteraturesMapper;
import com.school.wz_growth.service.LiteraturesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.net.URLDecoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service
public class LiteraturesServiceImpl implements LiteraturesService {

    @Autowired
    private LiteraturesMapper literaturesMapper;


    @Override
    public SingleResult<DataResponse> sel_clinicaltrials_list(JSONObject requestVo) throws ServiceException {
        try {
            Map<String,Object> params=new HashMap();
            params.put("name",requestVo.get("name"));
            params.put("page_index",requestVo.get("start"));
            params.put("page_size",requestVo.get("limit"));

            List<Map<String,Object>> list=literaturesMapper.sel_clinicaltrials_list(params);
            long total=literaturesMapper.sel_clinicaltrials_list_count(params);
            JSONObject result=new JSONObject(){{
                put("list",list);
                put("total",total);
            }};
            return SingleResult.buildSuccess(result);
        } catch (Throwable t) {
            t.printStackTrace();
            throw new ServiceException(t.getMessage(), Code.ERROR.getStatus());
        }
    }


    @Transactional(propagation = Propagation.REQUIRES_NEW,
            rollbackFor = Exception.class, isolation = Isolation.REPEATABLE_READ)
    @Override
    public SingleResult<DataResponse> del_clinicaltrials_list(JSONObject requestVo) throws ServiceException {
        try {
            Map<String,Object> params=new HashMap();
            params.put("ids",requestVo.get("ids"));
            literaturesMapper.del_clinicaltrials_list(params);
            return SingleResult.buildSuccessWithoutData();
        } catch (Throwable t) {
            t.printStackTrace();
            throw new ServiceException(t.getMessage(), Code.ERROR.getStatus());
        }
    }


    @Override
    public SingleResult<DataResponse> sel_GM_list(JSONObject requestVo) throws ServiceException {
        try {
            Map<String, Object> params = new HashMap<>();
            Map<String, Object> res = new HashMap<>();

            params.put("title",requestVo.get("name"));
            params.put("PMID",requestVo.get("PMID"));
            params.put("page_index",requestVo.get("start"));
            params.put("page_size",requestVo.get("limit"));
            try {
                List<Map<String, Object>> res_list = literaturesMapper.sel_GM_list(params);
                res_list.forEach(sub_obj_map -> {

                    sub_obj_map.put("brief",literaturesMapper.sel_GM_brief_list(sub_obj_map));
//                if (sub_obj_map.get("brief") != null && sub_obj_map.get("brief").toString().length() > 0) {
//                    sub_obj_map.put("brief", JSONArray.parseArray(sub_obj_map.get("brief").toString()));
//                } else {
//                    sub_obj_map.put("brief", new ArrayList<>());
//                }
                });
                res.put("list",res_list);
                res.put("total",literaturesMapper.sel_GM_list_count(params));
                return SingleResult.buildSuccess(res);
            } catch (Throwable t) {
                t.printStackTrace();
                throw new ServiceException(t.getMessage(), Code.ERROR.getStatus());
            }
        } catch (Throwable t) {
            t.printStackTrace();
            throw new ServiceException(t.getMessage(), Code.ERROR.getStatus());
        }
    }

    @Override
    public SingleResult<DataResponse> del_GM_list(JSONObject requestVo) throws ServiceException {
        try {
            Map<String,Object> params=new HashMap();
            params.put("ids",requestVo.get("ids"));
            literaturesMapper.del_GM_list(params);
            return SingleResult.buildSuccessWithoutData();
        } catch (Throwable t) {
            t.printStackTrace();
            throw new ServiceException(t.getMessage(), Code.ERROR.getStatus());
        }
    }

    @Override
    public SingleResult<DataResponse> sel_TL_list(JSONObject requestVo) throws ServiceException {
        Map<String, Object> params = new HashMap<>();
        Map<String, Object> res = new HashMap<>();

        params.put("title",requestVo.get("name"));
        params.put("page_index",requestVo.get("start"));
        params.put("page_size",requestVo.get("limit"));
        try {
            res.put("list",literaturesMapper.sel_TL_list(params));
            res.put("total",literaturesMapper.sel_TL_list_count(params));
            return SingleResult.buildSuccess(res);
        } catch (Throwable t) {
            t.printStackTrace();
            throw new ServiceException(t.getMessage(), Code.ERROR.getStatus());
        }



////        抓取数据后 处理的方法 The Lens平台
//        Map<String, Object> params = new HashMap<>();
//        Map<String, Object> res = new HashMap<>();
////      int page_index = 0;
//        int page_size = 10;
//
//        params.put("limit",page_size);
//        try {
//
//            int total = literaturesMapper.sel_TL_list_source_count();
//            int total_page = total/page_size+1;
//            for (int i = 0; i < total_page; i++)
//            {
//                params.put("start",0);
//                List<String> ids_list = literaturesMapper.sel_TL_list_source_ids(params);
//                if (ids_list.size()==0)
//                    break;
//                params.put("ids",ids_list);
//                List<Map<String, Object>> res_list = literaturesMapper.sel_TL_list_source(params);
//                for (Map<String, Object> sub_obj_map : res_list)
//                {
//                    if (StringUnits.isNotNullOrEmpty(sub_obj_map.get("jsonvalue")) == false)
//                        continue;
//
//                    String text = sub_obj_map.get("jsonvalue").toString();
//                    String decode = URLDecoder.decode(text);
////                    decode = decode.replaceAll("\\\\x","\\u").replaceAll("\\\\"," ");
////                    System.out.println("结果："+decode);   replaceAll("False","false").replaceAll("True","true").replaceAll("\\\\"," ")
//                    decode = decode.replaceAll("\\\\x8b","");
//                    cn.hutool.json.JSONObject jsonObject = JSONUtil.parseObj(decode,true);
//                    sub_obj_map.remove("jsonvalue");
//
//                    Map<String, Object> temp_data_map = new HashMap<>();
//                    temp_data_map.put("identifers",jsonObject.get("displayKey"));
//                    if (jsonObject.get("displayKey") == null)
//                        continue;
////
//                    jsonObject = jsonObject.getJSONObject("document");
//                    temp_data_map.put("LensID",jsonObject.get("record_lens_id"));
//                    temp_data_map.put("date_published",jsonObject.get("date_published"));
//                    temp_data_map.put("EarliestPriorityDate",jsonObject.get("earliest_priority_claim_date"));
//
//
//                    if (jsonObject.get("title")!= null &&
//                            jsonObject.getJSONObject("title").get("en")!= null&&
//                            jsonObject.getJSONObject("title").getJSONArray("en").size()>0)
//                        temp_data_map.put("title",jsonObject.getJSONObject("title").getJSONArray("en").getJSONObject(0).get("text"));
//                    if (jsonObject.get("legal_status")!= null)
//                        temp_data_map.put("LegalStatus",jsonObject.getJSONObject("legal_status").get("patent_status"));
//
//                    if (jsonObject.get("application_reference")!= null)
//                    {
//                        cn.hutool.json.JSONObject ref_obj = jsonObject.getJSONObject("application_reference");
//                        temp_data_map.put("ApplicationNumber",ref_obj.get("doc_number"));
//                        temp_data_map.put("jurisdiction",ref_obj.get("jurisdiction"));
//                        temp_data_map.put("filed",ref_obj.get("date"));
//                    }
//
//                    if (jsonObject.get("inventor")!= null && jsonObject.getJSONArray("inventor").size()>0)
//                        temp_data_map.put("inventor",StringUnits.listToString(jsonObject.getJSONArray("inventor"),','));
//
//
//                    if (jsonObject.get("abstract")!= null &&
//                            jsonObject.getJSONObject("abstract").get("en")!= null&&
//                            jsonObject.getJSONObject("abstract").getJSONArray("en").size()>0)
//                        temp_data_map.put("abstract",StringUnits.listToString2(jsonObject.getJSONObject("abstract").getJSONArray("en"),',', "text"));
//                    if (jsonObject.get("legal_status")!= null)
//
//                    literaturesMapper.insert_TL_main(temp_data_map);
//                    literaturesMapper.insert_TL_main_detail(temp_data_map);
//
//                    temp_data_map.put("old_data_id",sub_obj_map.get("id"));
//                    literaturesMapper.update_old_data_status(temp_data_map);
//
//                    System.out.println(" 完成 ++++++++");
//
//                }
//            }
//            System.out.println("所有数据已经完成");
//            return SingleResult.buildSuccess(res);
//        } catch (Throwable t) {
//            t.printStackTrace();
//            throw new ServiceException(t.getMessage(), Code.ERROR.getStatus());
//        }
    }

    @Override
    public SingleResult<DataResponse> del_TL_list(JSONObject requestVo) throws ServiceException {
        try {
            Map<String,Object> params=new HashMap();
            params.put("ids",requestVo.get("ids"));
            literaturesMapper.del_TL_list(params);
            return SingleResult.buildSuccessWithoutData();
        } catch (Throwable t) {
            t.printStackTrace();
            throw new ServiceException(t.getMessage(), Code.ERROR.getStatus());
        }
    }
}
