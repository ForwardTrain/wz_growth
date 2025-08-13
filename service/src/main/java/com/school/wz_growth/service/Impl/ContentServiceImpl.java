package com.school.wz_growth.service.Impl;


import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.school.wz_growth.common.exception.ServiceException;
import com.school.wz_growth.common.qiniu.QiniuUpload;
import com.school.wz_growth.common.response.Code;
import com.school.wz_growth.common.response.DataResponse;
import com.school.wz_growth.common.response.SingleResult;
import com.school.wz_growth.common.validator.FileUploadUtils;
import com.school.wz_growth.common.validator.StringUnits;
import com.school.wz_growth.dao.ContentMapper;
import com.school.wz_growth.dao.DataManageMapper;
import com.school.wz_growth.service.ContentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Service
public class ContentServiceImpl implements ContentService {
    @Autowired
    ContentMapper mapper;
    @Autowired
    DataManageMapper dmapper;
    @Override
    public SingleResult<DataResponse> sel_list(JSONObject requestVo) throws ServiceException {
        try {
            JSONObject params=new JSONObject(){{
                put("status",requestVo.get("status"));
                put("item_name",requestVo.get("item_name"));
                put("name",requestVo.get("name"));
                put("type",requestVo.get("type"));
                put("start",requestVo.get("start"));
                put("limit",requestVo.get("limit"));
                put("qiniu_base_url",QiniuUpload.qiniu_base_url);
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
    public SingleResult<DataResponse> sel_list_details(JSONObject requestVo) throws ServiceException {
        try {
            JSONObject params=new JSONObject(){{
                put("id",requestVo.get("id"));
            }};
            JSONObject result=mapper.sel_list_details(params);
            if (!ObjectUtils.isEmpty(result.get("img")))
                result.put("img", QiniuUpload.qiniu_base_url+result.getString("img"));
            List<JSONObject> list=mapper.sel_file_list(params);
            for (JSONObject jsonObject : list) {
                if (!ObjectUtils.isEmpty(jsonObject.get("url")))
                    jsonObject.put("url", QiniuUpload.qiniu_base_url+jsonObject.getString("url"));
            }
            result.put("file_list",list);
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
    public SingleResult<DataResponse> sel_hot_list(JSONObject requestVo) throws ServiceException {
        try {
            JSONObject params=new JSONObject(){{
                put("type",requestVo.get("type"));
                put("item_name",requestVo.get("item_name"));
                put("name",requestVo.get("name"));
                put("start",requestVo.get("start"));
                put("limit",requestVo.get("limit"));
            }};
            List<JSONObject> list=mapper.sel_hot_list(params);
            for (JSONObject jsonObject : list) {
                if (!ObjectUtils.isEmpty(jsonObject.get("img"))){
                    jsonObject.put("img",QiniuUpload.qiniu_base_url+jsonObject.getString("img"));
                }
            }
            int total=mapper.sel_hot_list_total(params);
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
    public SingleResult<DataResponse> upd_hot_list(JSONObject requestVo) throws ServiceException {
        try {


            JSONObject params=new JSONObject(){{
                put("id",requestVo.get("id"));
                put("is_push",requestVo.get("is_push"));
            }};
           mapper.upd_hot_list(params);
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
                put("source",requestVo.get("source"));
                put("source_url",requestVo.get("source_url"));
                put("is_push",requestVo.get("is_push"));
                if (!ObjectUtils.isEmpty(requestVo.get("img")))
                    requestVo.put("img",requestVo.getString("img").replaceAll(QiniuUpload.qiniu_base_url,""));
                put("img",requestVo.get("img"));
                if (!ObjectUtils.getDisplayString(requestVo.get("type")).equals("2"))
                put("content", FileUploadUtils.parseNewsDataMarket(requestVo.getString("uId"),requestVo.getString("content")));
                put("publish_type",requestVo.get("publish_type"));
                put("publish_time",requestVo.get("publish_time"));
                put("brief",requestVo.get("brief"));
                put("type",requestVo.get("type"));
                put("item_id",requestVo.get("item_id"));
                put("status",requestVo.get("status"));
                put("operator",requestVo.get("u_name"));
            }};
            if (ObjectUtils.isEmpty(params.get("id"))){
                mapper.add_list(params);
            }else{
//                if (StringUnits.isNotNullOrEmpty(params.get("status")))
//                    params.put("status", params.getString("status"));
                mapper.upd_list(params);
            }
            if (ObjectUtils.isEmpty(requestVo.get("status"))||requestVo.getString("status").equals("1")||requestVo.getString("status").equals("5")){

                if (!ObjectUtils.isEmpty(requestVo.get("file_list"))){
                    mapper.del_file(params);
                    List<JSONObject> list=requestVo.getJSONArray("file_list").toJavaList(JSONObject.class);
                    for (JSONObject jsonObject : list) {
                        jsonObject.put("p_id",params.get("id"));
                        jsonObject.put("operator",params.get("operator"));
                        jsonObject.put("url",params.getString("url").replaceAll(QiniuUpload.qiniu_base_url,""));
                    }
                    mapper.add_file(list);
                }
            }
            return SingleResult.buildSuccessWithoutData();
        } catch (Throwable t) {
            t.printStackTrace();
            throw new ServiceException(t.getMessage(),Code.ERROR.getStatus());
        }
    }


    @Override
    public SingleResult<DataResponse> sel_support_list(JSONObject requestVo) throws ServiceException {
        try {
            JSONObject params=new JSONObject(){{
                put("is_show",requestVo.get("is_show"));
                put("tel",requestVo.get("tel"));
                put("title",requestVo.get("title"));
                put("operator",requestVo.get("operator"));
                put("start",requestVo.get("start"));
                put("limit",requestVo.get("limit"));
            }};
            List<JSONObject> list=mapper.sel_support_list(params);
            int total=mapper.sel_support_list_total(params);
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
    public SingleResult<DataResponse> sel_support_list_details(JSONObject requestVo) throws ServiceException {
        try {
            JSONObject params=new JSONObject(){{
                put("id",requestVo.get("id"));
            }};
            JSONObject result= new JSONObject();
            result.put("support",mapper.sel_support_list_support(params));
            result.put("report",mapper.sel_support_list_report(params));

            return SingleResult.buildSuccess(result);
        } catch (Throwable t) {
            t.printStackTrace();
            throw new ServiceException(t.getMessage(),Code.ERROR.getStatus());
        }
    }

    @Override
    public SingleResult<DataResponse> del_support_list(JSONObject requestVo) throws ServiceException {
        try {
            JSONObject params=new JSONObject(){{
                put("ids",requestVo.get("ids"));
            }};
          mapper.del_support_list(params);
            return SingleResult.buildSuccessWithoutData();
        } catch (Throwable t) {
            t.printStackTrace();
            throw new ServiceException(t.getMessage(),Code.ERROR.getStatus());
        }
    }

    @Override
    public SingleResult<DataResponse> add_upd_report(JSONObject requestVo) throws ServiceException {
        try {
            JSONObject params=new JSONObject(){{
                put("id",requestVo.get("id"));
                put("status",requestVo.get("status"));
                put("name",requestVo.get("name"));
                put("p_id",requestVo.get("p_id"));
                put("content",FileUploadUtils.parseNewsDataMarket(requestVo.getString("uId"),requestVo.getString("content")));
                put("operator",requestVo.get("u_name"));
            }};
            if (ObjectUtils.isEmpty(params.get("id"))){
                mapper.add_report(params);
            }else{
                mapper.upd_report(params);
            }
            return SingleResult.buildSuccessWithoutData();
        } catch (Throwable t) {
            t.printStackTrace();
            throw new ServiceException(t.getMessage(),Code.ERROR.getStatus());
        }
    }
    @Override
    public SingleResult<DataResponse> add_upd_example(JSONObject requestVo) throws ServiceException {
        try {
            JSONObject params=new JSONObject(){{
                put("id",requestVo.get("id"));
                put("sort",requestVo.get("sort"));
                put("name",requestVo.get("name"));
                put("operator",requestVo.get("u_name"));
            }};
            if (ObjectUtils.isEmpty(params.get("id"))){
                mapper.add_example(params);
            }else{
                mapper.upd_example(params);
            }
            return SingleResult.buildSuccessWithoutData();
        } catch (Throwable t) {
            t.printStackTrace();
            throw new ServiceException(t.getMessage(),Code.ERROR.getStatus());
        }
    }
    @Override
    public SingleResult<DataResponse> del_example(JSONObject requestVo) throws ServiceException {
        try {
            JSONObject params=new JSONObject(){{
                put("ids",requestVo.get("ids"));

            }};
                mapper.del_example(params);
            return SingleResult.buildSuccessWithoutData();
        } catch (Throwable t) {
            t.printStackTrace();
            throw new ServiceException(t.getMessage(),Code.ERROR.getStatus());
        }
    }
    @Override
    public SingleResult<DataResponse> sel_example(JSONObject requestVo) throws ServiceException {
        try {
            JSONObject params=new JSONObject();
            List<JSONObject> list=mapper.sel_example(params);
            int total=mapper.sel_example_total(params);
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
    public void add_down_first(JSONObject requestVo) throws ServiceException {
        try {
        mapper.add_down_first(requestVo);
        } catch (Throwable t) {
            t.printStackTrace();
            throw new ServiceException(t.getMessage(),Code.ERROR.getStatus());
        }
    }
    @Override
    public void add_down_second(List<JSONObject> l) throws ServiceException {
        try {
        mapper.add_down_second(l);
        } catch (Throwable t) {
            t.printStackTrace();
            throw new ServiceException(t.getMessage(),Code.ERROR.getStatus());
        }
    }


    @Override
    public SingleResult<DataResponse> add_upd_support(JSONObject requestVo) throws ServiceException {
        try {
            JSONObject params=new JSONObject(){{
                put("name",requestVo.get("name"));
                put("sex",requestVo.get("sex"));
                put("tel",requestVo.get("tel"));
                put("email",requestVo.get("email"));
                put("title",requestVo.get("title"));
                put("type",requestVo.get("type"));
                put("is_show",requestVo.get("is_show"));
                put("content",FileUploadUtils.parseNewsDataMarket(requestVo.getString("uId"),requestVo.getString("content")));
                put("operator",requestVo.get("u_name"));
            }};
                mapper.add_upd_support(params);

            return SingleResult.buildSuccessWithoutData();
        } catch (Throwable t) {
            t.printStackTrace();
            throw new ServiceException(t.getMessage(),Code.ERROR.getStatus());
        }
    }

    @Override
    public SingleResult<DataResponse> search_combobox(JSONObject requestVo) throws ServiceException {
        try {
            Map<String, Object> params=new HashMap<>();
            List<Map> params_list= new ArrayList<>();
            if (requestVo.get("list")!=null)
            {
                params_list=requestVo.getJSONArray("list").toJavaList(Map.class);
//                for (Map<String,Object> jsonObject : params_list)
//                    jsonObject.put("search_value",String.format("%s",jsonObject.get("search_value"),""));//StringUnits.isNotNullOrEmpty(jsonObject.get("value"))?jsonObject.get("value").toString():"")
                for (Map<String,Object> jsonObject : params_list) //处理高级过滤器里面的内容
                {
                    if (StringUnits.isNotNullOrEmpty(jsonObject.get("search_value")) &&
                            jsonObject.get("type").toString().startsWith("2-"))
                    {
                        jsonObject.put("search_value",String.format("%s '%s'",jsonObject.get("search_value"),(StringUnits.isNotNullOrEmpty(jsonObject.get("value"))?jsonObject.get("value"):"")));
                    }else
                    {
                       //别的搜索条件，直接用就好
                    }
                }
            }

            //传入的type:   1 by famiy   2by ligand  3by receptor 4by organism 5 by all
            String search_type = "1-";//默认为by all
            if (StringUnits.isNotNullOrEmpty(requestVo.get("type")))
            {
                switch (Integer.parseInt(requestVo.getString("type")))
                {
                    case 1:
                        search_type="3-";
                        break;
                    case 2:
                        search_type="4-";
                        break;
                    case 3:
                        search_type="5-";
                        break;
                    case 4:
                        search_type="6-";
                        break;
                }
            }

            params.put("type",search_type);
            params.put("family_type",requestVo.get("type"));
            params.put("keywords2",requestVo.get("key_words2"));
            params.put("keywords",requestVo.get("key_words"));
            params.put("family_id",requestVo.get("family_id"));
           List<JSONObject> search_list=mapper.search_combobox(params);
            List<JSONObject> res_list = new ArrayList<>();
            for (JSONObject jsonObject : search_list)
            {
                List<JSONObject> child_list = mapper.search_combobox_children(jsonObject);
                List<Map>  temp_sub_list2 = new ArrayList<>();
                for (int i = 0; i < child_list.size(); i++)
                {
                    Map<String,Object> child_map = child_list.get(i);
                    List<Map> temp_params_list = new ArrayList();
                    temp_params_list.addAll(params_list);
                    temp_params_list.add(child_map);
                    params.put("list",temp_params_list);

                    Predicate<Map> predicate =  mapper -> child_map.get("type").equals(mapper.get("type").toString());
                    List<Map> res_temp_child_list = params_list.stream().filter(predicate).collect(Collectors.toList());
                    child_map.put("is_selected",(res_temp_child_list.size()>0?2:1));

                    int temp_num = mapper.search_result_list_total(params);
                     child_map.put("num",temp_num);

                    if (temp_num>0)
                        temp_sub_list2.add(child_map);
                }
                if (temp_sub_list2.size()>0)
                {
                    jsonObject.put("children",temp_sub_list2);
                    res_list.add(jsonObject);
                }
            }

            return SingleResult.buildSuccess(res_list);
        } catch (Throwable t) {
            t.printStackTrace();
            throw new ServiceException(t.getMessage(),Code.ERROR.getStatus());
        }
    }

    @Override
    public SingleResult<DataResponse> search_combobox2(JSONObject requestVo) throws ServiceException {
        try {
            Map<String, Object> params=new HashMap<>();
            params.put("type","2-");

            List<JSONObject> list=mapper.search_combobox(params);
            for (JSONObject jsonObject : list)
            {
                jsonObject.put("children",mapper.search_combobox_children(jsonObject));
            }

            return SingleResult.buildSuccess(list);
        } catch (Throwable t) {
            t.printStackTrace();
            throw new ServiceException(t.getMessage(),Code.ERROR.getStatus());
        }
    }

    @Override
    public SingleResult<DataResponse> search_combobox_type_num_count() throws ServiceException {
        try {
            Map<String, Object> params=new HashMap<>();
            params.put("type","1-");

            List<Map<String,Object>> list=mapper.search_combobox_type_num_count(params);
            for (Map<String, Object> jsonObject : list)
            {
                Map<String,Object> temp_params=new HashMap<>();
                temp_params.put("list",new ArrayList(){{add(jsonObject);}});
                jsonObject.put("num",mapper.search_result_list_total(temp_params));

                mapper.update_search_combobox_num(jsonObject);
            }


            return SingleResult.buildSuccess(list);
        } catch (Throwable t) {
            t.printStackTrace();
            throw new ServiceException(t.getMessage(),Code.ERROR.getStatus());
        }
    }

    @Override
    public SingleResult<DataResponse> search_result_list(JSONObject requestVo) throws ServiceException {
        try {
            List<Map> list=requestVo.getJSONArray("list").toJavaList(Map.class);

            for (Map<String,Object> jsonObject : list) //处理高级过滤器里面的内容
            {
                if (StringUnits.isNotNullOrEmpty(jsonObject.get("search_value")) &&
                        StringUnits.isNotNullOrEmpty(jsonObject.get("value")) &&
                        jsonObject.get("type").toString().startsWith("2-"))
                {
                    jsonObject.put("search_value",String.format("%s '%s'",jsonObject.get("search_value"),jsonObject.get("value")));
                }
            }

            Map<String,Object> res = new HashMap<>();
            Map<String,Object> parmas = new HashMap<>();
            parmas.put("keywords",requestVo.get("key_words"));
            parmas.put("list",list);
            parmas.put("start",requestVo.get("start"));
            parmas.put("limit",requestVo.get("limit"));

            //type:1 by famiy   2by ligand  3 by receptor 4by organism   5 by all
//            int family_type =5;//默认为by all
//            if (StringUnits.isNotNullOrEmpty(requestVo.get("type")))
//                 family_type = requestVo.getInteger("type");
            if (StringUnits.isNotNullOrEmpty(requestVo.get("family_id")))
            {
                Map<String,Object> family_map = mapper.select_family_info(requestVo.getString("family_id"));
                res.put("family_info",family_map);
                parmas.put("family_id",requestVo.get("family_id"));
            }
//            List<JSONObject> l=new ArrayList(){{
//                p.keySet().forEach(key->{
//                    if (key.equals("uId")||key.equals("u_name")
//                            ||key.equals("role_type")||key.equals("start")||key.equals("limit")
//                            ||key.equals("pageIndex")||key.equals("pageSize")||key.equals("name")){}
//                    else
//                    add(new JSONObject(){{
//                        put("name",key);
//                        put("value",p.get(key));
//                    }});
//                });
//            }};


            //传入的type:   1 by famiy   2by ligand  3by receptor 4by organism 5 by all
            int family_type = 5;//默认为by all
            if (StringUnits.isNotNullOrEmpty(requestVo.get("type")))
            {
                switch (Integer.parseInt(requestVo.getString("type")))
                {
                    case 1:
                        family_type=1;
                        break;
                    case 2:
                        family_type=2;
                        break;
                    case 3:
                        family_type=3;
                        break;
                    case 4:
                        family_type=4;
                        break;
                }
            }
            parmas.put("family_type",family_type);
            parmas.put("keywords2",requestVo.get("key_words2"));

            List<Map<String,Object>> res_list=mapper.search_result_list(parmas);
            int total = mapper.search_result_list_total(parmas);
//            List<JSONObject> res_list = result.stream().filter(o->o.getInteger("rownum")>=requestVo.getInteger("start")
//                    && o.getInteger("rownum")<requestVo.getInteger("limit")+requestVo.getInteger("start")).collect(Collectors.toList());
//            if (family_type==3)
//            {
//                for (Map<String, Object> stringObjectMap : res_list)
//                {
//                    if (!ObjectUtils.isEmpty(stringObjectMap)&&!ObjectUtils.isEmpty(stringObjectMap.get("protein_complex")))
//                        stringObjectMap.put("protein_complex",JSONArray.parseArray(stringObjectMap.get("protein_complex").toString()));
//                }
//            }else {
//                for (Map<String, Object> stringObjectMap : res_list)
//                    stringObjectMap.remove("protein_complex");
//            }


            if (StringUnits.isNotNullOrEmpty(requestVo.get("key_words"))) {
                String key_words = requestVo.getString("key_words");
//                String down_keyWord = key_words.toLowerCase();
//                String up_keyWord = key_words.toUpperCase();
//                String replase_str = String.format("<span style=\"background-color: yellow;\">%s</span>", key_words);
                for (Map<String, Object> jsonObject : res_list) {
                    if (family_type == 4) {
//                        jsonObject.put("organism", (StringUnits.isNotNullOrEmpty(jsonObject.get("organism")) ? (jsonObject.get("organism").toString().replaceAll(down_keyWord, replase_str)) : ""));
//                        jsonObject.put("organism", (StringUnits.isNotNullOrEmpty(jsonObject.get("organism")) ? (jsonObject.get("organism").toString().replaceAll(up_keyWord, replase_str)) : ""));
//                        jsonObject.put("organism", (StringUnits.isNotNullOrEmpty(jsonObject.get("organism")) ? (jsonObject.get("organism").toString().replaceAll(key_words, replase_str)) : ""));
                        if (StringUnits.isNotNullOrEmpty(key_words) && StringUnits.isNotNullOrEmpty(jsonObject.get("organism"))) {
                            jsonObject.put("organism", convertHighLight(key_words, jsonObject.get("organism").toString()));
                        }
                    } else if (family_type == 1 || family_type == 2 || family_type == 3) {
//                        jsonObject.put("family", (StringUnits.isNotNullOrEmpty(jsonObject.get("family")) ? (jsonObject.get("family").toString().replaceAll(down_keyWord, replase_str)) : ""));
//                        jsonObject.put("family", (StringUnits.isNotNullOrEmpty(jsonObject.get("family")) ? (jsonObject.get("family").toString().replaceAll(up_keyWord, replase_str)) : ""));
//                        jsonObject.put("family", (StringUnits.isNotNullOrEmpty(jsonObject.get("family")) ? (jsonObject.get("family").toString().replaceAll(key_words, replase_str)) : ""));
                        if (StringUnits.isNotNullOrEmpty(key_words) && StringUnits.isNotNullOrEmpty(jsonObject.get("family"))) {
                            jsonObject.put("family", convertHighLight(key_words, jsonObject.get("family").toString()));
                        }
                    }
                }
            }

            //高级搜索结果展示
            if (StringUnits.isNotNullOrEmpty(requestVo.get("key_words2"))) {
                String key_words2 = requestVo.getString("key_words2");
//                String replase_str = String.format("<span style=\"background-color: yellow;\">%s</span>", key_words2);
//                String down_keyWord2 = key_words2.toLowerCase();
//                String up_keyWord2 = key_words2.toUpperCase();
                for (Map<String, Object> jsonObject : res_list) {
//                    jsonObject.put("protein_name", (StringUnits.isNotNullOrEmpty(jsonObject.get("protein_name")) ? (jsonObject.get("protein_name").toString().replaceAll(up_keyWord2, replase_str)) : ""));
//                    jsonObject.put("gene_names", (StringUnits.isNotNullOrEmpty(jsonObject.get("gene_names")) ? (jsonObject.get("gene_names").toString().replaceAll(up_keyWord2, replase_str)) : ""));
//                    jsonObject.put("gene_also_known_as", (StringUnits.isNotNullOrEmpty(jsonObject.get("gene_also_known_as")) ? (jsonObject.get("gene_also_known_as").toString().replaceAll(up_keyWord2, replase_str)) : ""));
//                    jsonObject.put("unipro_entry", (StringUnits.isNotNullOrEmpty(jsonObject.get("protein_name")) ? (jsonObject.get("unipro_entry").toString().replaceAll(up_keyWord2, replase_str)) : ""));
//                    jsonObject.put("DRGF_code", (StringUnits.isNotNullOrEmpty(jsonObject.get("DRGF_code")) ? (jsonObject.get("DRGF_code").toString().replaceAll(up_keyWord2, replase_str)) : ""));
//                    jsonObject.put("family", (StringUnits.isNotNullOrEmpty(jsonObject.get("family")) ? (jsonObject.get("family").toString().replaceAll(up_keyWord2, replase_str)) : ""));
//                    jsonObject.put("gene_name", (StringUnits.isNotNullOrEmpty(jsonObject.get("gene_name")) ? (jsonObject.get("gene_name").toString().replaceAll(up_keyWord2, replase_str)) : ""));
//
//                    jsonObject.put("protein_name", (StringUnits.isNotNullOrEmpty(jsonObject.get("protein_name")) ? (jsonObject.get("protein_name").toString().replaceAll(down_keyWord2, replase_str)) : ""));
//                    jsonObject.put("gene_names", (StringUnits.isNotNullOrEmpty(jsonObject.get("gene_names")) ? (jsonObject.get("gene_names").toString().replaceAll(down_keyWord2, replase_str)) : ""));
//                    jsonObject.put("gene_also_known_as", (StringUnits.isNotNullOrEmpty(jsonObject.get("gene_also_known_as")) ? (jsonObject.get("gene_also_known_as").toString().replaceAll(down_keyWord2, replase_str)) : ""));
//                    jsonObject.put("unipro_entry", (StringUnits.isNotNullOrEmpty(jsonObject.get("protein_name")) ? (jsonObject.get("unipro_entry").toString().replaceAll(down_keyWord2, replase_str)) : ""));
//                    jsonObject.put("DRGF_code", (StringUnits.isNotNullOrEmpty(jsonObject.get("DRGF_code")) ? (jsonObject.get("DRGF_code").toString().replaceAll(down_keyWord2, replase_str)) : ""));
//                    jsonObject.put("family", (StringUnits.isNotNullOrEmpty(jsonObject.get("family")) ? (jsonObject.get("family").toString().replaceAll(down_keyWord2, replase_str)) : ""));
//                    jsonObject.put("gene_name", (StringUnits.isNotNullOrEmpty(jsonObject.get("gene_name")) ? (jsonObject.get("gene_name").toString().replaceAll(down_keyWord2, replase_str)) : ""));
//
//                    jsonObject.put("protein_name", (StringUnits.isNotNullOrEmpty(jsonObject.get("protein_name")) ? (jsonObject.get("protein_name").toString().replaceAll(key_words2, replase_str)) : ""));
//                    jsonObject.put("gene_names", (StringUnits.isNotNullOrEmpty(jsonObject.get("gene_names")) ? (jsonObject.get("gene_names").toString().replaceAll(key_words2, replase_str)) : ""));
//                    jsonObject.put("gene_also_known_as", (StringUnits.isNotNullOrEmpty(jsonObject.get("gene_also_known_as")) ? (jsonObject.get("gene_also_known_as").toString().replaceAll(key_words2, replase_str)) : ""));
//                    jsonObject.put("unipro_entry", (StringUnits.isNotNullOrEmpty(jsonObject.get("protein_name")) ? (jsonObject.get("unipro_entry").toString().replaceAll(key_words2, replase_str)) : ""));
//                    jsonObject.put("DRGF_code", (StringUnits.isNotNullOrEmpty(jsonObject.get("DRGF_code")) ? (jsonObject.get("DRGF_code").toString().replaceAll(key_words2, replase_str)) : ""));
//                    jsonObject.put("family", (StringUnits.isNotNullOrEmpty(jsonObject.get("family")) ? (jsonObject.get("family").toString().replaceAll(key_words2, replase_str)) : ""));
//                    jsonObject.put("gene_name", (StringUnits.isNotNullOrEmpty(jsonObject.get("gene_name")) ? (jsonObject.get("gene_name").toString().replaceAll(key_words2, replase_str)) : ""));
                    if (StringUnits.isNotNullOrEmpty(key_words2) && StringUnits.isNotNullOrEmpty(jsonObject.get("protein_name"))) {
                        jsonObject.put("protein_name", convertHighLight(key_words2, jsonObject.get("protein_name").toString()));
                    }

                    if (StringUnits.isNotNullOrEmpty(key_words2) && StringUnits.isNotNullOrEmpty(jsonObject.get("gene_names"))) {
                        jsonObject.put("gene_names", convertHighLight(key_words2, jsonObject.get("gene_names").toString()));
                    }

                    if (StringUnits.isNotNullOrEmpty(key_words2) && StringUnits.isNotNullOrEmpty(jsonObject.get("gene_also_known_as"))) {
                        jsonObject.put("gene_also_known_as", convertHighLight(key_words2, jsonObject.get("gene_also_known_as").toString()));
                    }

                    if (StringUnits.isNotNullOrEmpty(key_words2) && StringUnits.isNotNullOrEmpty(jsonObject.get("unipro_entry"))) {
                        jsonObject.put("unipro_entry", convertHighLight(key_words2, jsonObject.get("unipro_entry").toString()));
                    }

                    if (StringUnits.isNotNullOrEmpty(key_words2) && StringUnits.isNotNullOrEmpty(jsonObject.get("DRGF_code"))) {
                        jsonObject.put("DRGF_code", convertHighLight(key_words2, jsonObject.get("DRGF_code").toString()));
                    }

                    if (StringUnits.isNotNullOrEmpty(key_words2) && StringUnits.isNotNullOrEmpty(jsonObject.get("family"))) {
                        jsonObject.put("family", convertHighLight(key_words2, jsonObject.get("family").toString()));
                    }

                    if (StringUnits.isNotNullOrEmpty(key_words2) && StringUnits.isNotNullOrEmpty(jsonObject.get("gene_name"))) {
                        jsonObject.put("gene_name", convertHighLight(key_words2, jsonObject.get("gene_name").toString()));
                    }
                }
            }

            res_list.stream().forEach(s -> {
                if (s.get("gene_names") != null) {
                    String gene_names = s.get("gene_names").toString().trim();
                    s.put("gene_names", gene_names);
                }
                if (s.get("organism") != null) {
                    String organism = s.get("organism").toString().replaceAll("\\(\\s*null\\s*\\)", "")
                            .replaceAll("\\((?=\\S)", " (");
                    s.put("organism", organism);
                }

            });

            res.put("total",total);
            res.put("list",res_list);
            return SingleResult.buildSuccess(res);
        } catch (Throwable t) {
            t.printStackTrace();
            throw new ServiceException(t.getMessage(),Code.ERROR.getStatus());
        }
    }

    public static String convertHighLight(String keyWords, String text) {
        // 进行不区分大小写的匹配
        Pattern pattern = Pattern.compile(keyWords, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(text);

        // 使用 StringBuffer 来构造替换后的字符串
        StringBuffer result = new StringBuffer();

        while (matcher.find()) {
            // 保留原始大小写的内容，并添加 <span> 标签
            matcher.appendReplacement(result, "<span style=\"background-color: yellow;\">" + matcher.group() + "</span>");
        }
        return matcher.appendTail(result).toString();
    }

    public static void main(String[] args) {
        String organism = "Bos taurus (Bovine)(null)".replaceAll("\\(\\s*null\\s*\\)", "")
                .replaceAll("\\((?=\\S)", "( ");
        System.out.println("organism = " + organism);
    }

    @Override
    public SingleResult<DataResponse> search_result_list_byId(JSONObject requestVo) throws ServiceException {
        List<Map<String, Object>> maps = mapper.search_result_list_byId(requestVo);
        return SingleResult.buildSuccess(maps);
    }

    @Override
    public SingleResult<DataResponse> left_search_result_list(JSONObject requestVo) throws ServiceException {
        try {

            List<JSONObject> l=new ArrayList(){{
                if (!ObjectUtils.isEmpty(requestVo.get("params"))){
                    for (String params : requestVo.getString("params").split(",")) {
                        add(new JSONObject(){{put("name",params);}});
                    }
                }
            }};
            List<JSONObject> result=mapper.left_search_result_list(l);
            JSONObject r=new JSONObject();
            r.put("total",result.size());
            r.put("list",result.stream().filter(o->o.getInteger("rownum")>=requestVo.getInteger("start")
                    && o.getInteger("rownum")<requestVo.getInteger("limit")+requestVo.getInteger("start")).collect(Collectors.toList()));
            return SingleResult.buildSuccess(r);
        } catch (Throwable t) {
            t.printStackTrace();
            throw new ServiceException(t.getMessage(),Code.ERROR.getStatus());
        }
    }
    @Override
    public SingleResult<DataResponse> sel_by_key_words_search_result_list(JSONObject requestVo) throws ServiceException {
        try {

            List<JSONObject> l=new ArrayList(){{
                        add(new JSONObject(){{put("keywords",requestVo.get("keywords"));}});
            }};
            List<JSONObject> result=mapper.left_search_result_list(l);
            JSONObject r=new JSONObject();
            r.put("total",result.size());
            r.put("list",result.stream().filter(o->o.getInteger("rownum")>=requestVo.getInteger("start")
                    && o.getInteger("rownum")<requestVo.getInteger("limit")+requestVo.getInteger("start")).collect(Collectors.toList()));
            return SingleResult.buildSuccess(r);
        } catch (Throwable t) {
            t.printStackTrace();
            throw new ServiceException(t.getMessage(),Code.ERROR.getStatus());
        }
    }
    @Override
    public SingleResult<DataResponse> clinical_research(JSONObject requestVo) throws ServiceException {
        try {
            JSONObject params=new JSONObject(){{
                put("keywords",requestVo.get("keywords"));
                put("start",requestVo.get("start"));
                put("limit",requestVo.get("limit"));
            }};
            List<JSONObject> list=mapper.clinical_research(params);
            int total=mapper.clinical_research_total(params);
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
    public SingleResult<DataResponse> sel_item_list(JSONObject requestVo) throws ServiceException {
        try {
            JSONObject params=new JSONObject(){{
                put("left_show",requestVo.get("left_show"));
                put("p_name",requestVo.get("p_name"));
                put("type",requestVo.get("type"));
                put("start",requestVo.get("start"));
                put("limit",requestVo.get("limit"));
            }};
            List<JSONObject> list=mapper.sel_item_list(params);
            for (JSONObject jsonObject : list) {
                jsonObject.put("children",mapper.sel_item_children(jsonObject));
            }
            JSONObject result=new JSONObject(){{
                put("list",list);
            }};
            return SingleResult.buildSuccess(result);
        } catch (Throwable t) {
            t.printStackTrace();
            throw new ServiceException(t.getMessage(),Code.ERROR.getStatus());
        }
    }

    @Override
    public SingleResult<DataResponse> sel_search_details(JSONObject requestVo) throws ServiceException {
        try {
            JSONObject params=new JSONObject(){{
                put("id",requestVo.get("id"));
            }};
            JSONObject result=new JSONObject();
            result.put("general_information",dmapper.base_general_information(result.getString("id")));
            Map<String,Object> protein_sequence=dmapper.base_protein_sequence(result.getString("id"));

            if (!ObjectUtils.isEmpty(protein_sequence)&&!ObjectUtils.isEmpty(protein_sequence.get("complete_form"))){
                int i=1;
               List<JSONObject> l=new ArrayList<>();
               if (!ObjectUtils.isEmpty(protein_sequence.get("complete_form")))
                for (String complete_form : stringToStringArray(protein_sequence.get("complete_form").toString(), 10)) {
                    JSONObject jl=new JSONObject();
                    jl.put("length",i*10);
                    jl.put("data",complete_form.substring((i-1)*10,i*10));
                    l.add(jl);
                }
                protein_sequence.put("list",l);
            }
            result.put("protein_sequence",protein_sequence);
            result.put("protein_function",dmapper.base_protein_function(result.getString("id")));
            result.put("expression_and_location",dmapper.base_expression_and_location(result.getString("id")));
            result.put("protein_structure",dmapper.base_protein_structure(result.getString("id")));
            result.put("family_and_domain",dmapper.base_family_and_domain(result.getString("id")));
            result.put("protein_interaction",dmapper.base_protein_interaction(result.getString("id")));
            result.put("mutation_and_disease",dmapper.base_mutation_and_disease(result.getString("id")));
            result.put("post_translational_modification",dmapper.base_ptm_details(result.getString("id")));
            Map<String,Object> kegg_pathways=dmapper.base_drgf(result.getString("id"));
            List<JSONObject> lk=new ArrayList<>();
            if (!ObjectUtils.isEmpty(kegg_pathways)&&!ObjectUtils.isEmpty(kegg_pathways.get("pathway_data")))
            for (String pathway_data : kegg_pathways.get("pathway_data").toString().split(";")) {
                JSONObject j=new JSONObject();
                j.put("col1",pathway_data.split(" ")[0]);
                j.put("col2",pathway_data.split(" ")[1]);
                lk.add(j);
            }
            result.put("kegg_pathways",lk);

            return SingleResult.buildSuccess(result);
        } catch (Throwable t) {
            t.printStackTrace();
            throw new ServiceException(t.getMessage(),Code.ERROR.getStatus());
        }
    }
    @Override
    public SingleResult<DataResponse> sel_search_details_show(JSONObject requestVo) throws ServiceException {
        try {
            JSONObject params=new JSONObject(){{
                put("id",requestVo.get("id"));
            }};
            JSONObject result=new JSONObject();
            Map<String,Object> general_information=dmapper.base_general_information_show2(params.getString("id"));
            if (StringUnits.isNotNullOrEmpty(general_information.get("gene_id")))
                general_information.put("gene_url","https://www.ncbi.nlm.nih.gov/gene/"+general_information.get("gene_id").toString());
            else
                general_information.put("gene_url","");

            if (StringUnits.isNotNullOrEmpty(general_information.get("organism"))) {
                String organism = general_information.get("organism").toString().replaceAll("\\(\\s*null\\s*\\)", "")
                        .replaceAll("\\((?=\\S)", " (");
                general_information.put("organism", organism);
            }

            general_information.put("family_list",dmapper.base_general_information_familys_show(general_information));
            result.put("general_information",general_information);

            Map<String,Object> protein_sequence=dmapper.base_protein_sequence_show2(params.getString("id"));
            List<String> complete_list = new ArrayList<>();
            String complete_form_str ="";

            if (StringUnits.isNotNullOrEmpty(protein_sequence.get("complete_form")))
                complete_form_str=protein_sequence.get("complete_form").toString();
            if (StringUnits.isNotNullOrEmpty(protein_sequence.get("complete_form")))
            {
                int length = complete_form_str.length();
                for (int i = 0; i < length; i += 10)
                {
                    // 计算截取的终点，避免超出字符串长度
                    int end = Math.min(length, i + 10);
                    // 截取子串并添加到结果列表中
                    complete_list.add(complete_form_str.substring(i, end));
                }
            }
            protein_sequence.put("complete_list",complete_list);
            JSONArray protein_list=new JSONArray();
            if (StringUnits.isNotNullOrEmpty(protein_sequence.get("precursor")) &&
                    "/".equals(protein_sequence.get("precursor"))==false)
            {
                JSONArray temp_data_list= JSONArray.parseArray(protein_sequence.get("precursor").toString());
                for (int j = 0; j < temp_data_list.size(); j++)
                {
                    JSONObject jsonObject = temp_data_list.getJSONObject(j);
                    jsonObject.put("type","Propeptide");
//                    jsonObject.put("description","Signal peptide");这个逻辑去掉
                    List<String> temp_list = new ArrayList<>();
                    if (StringUnits.isNotNullOrEmpty(jsonObject.get("start")) &&
                            StringUnits.isNotNullOrEmpty(jsonObject.get("end")))
                    {
                        int temp_data_start = jsonObject.getInteger("start");
                        int temp_data_end = jsonObject.getInteger("end");
                        String data_form_str =complete_form_str.substring(temp_data_start-1,temp_data_end);
                        int length = data_form_str.length();
                        for (int i = 0; i < length; i += 10)
                        {
                            // 计算截取的终点，避免超出字符串长度
                            int end = Math.min(length, i + 10);
                            // 截取子串并添加到结果列表中
                            temp_list.add(data_form_str.substring(i, end));
                        }
                    }
                    jsonObject.put("list",temp_list);
                }
                protein_list.addAll(temp_data_list);
                protein_sequence.remove("precursor");
            }
            if (StringUnits.isNotNullOrEmpty(protein_sequence.get("mature_form"))  &&
                    "/".equals(protein_sequence.get("mature_form"))==false)
            {
                JSONArray temp_data_list= JSONArray.parseArray(protein_sequence.get("mature_form").toString());
                for (int j = 0; j < temp_data_list.size(); j++)
                {
                    JSONObject jsonObject = temp_data_list.getJSONObject(j);
                    jsonObject.put("type","Mature form");
                    List<String> temp_list = new ArrayList<>();
                    if (StringUnits.isNotNullOrEmpty(jsonObject.get("start")) &&
                            StringUnits.isNotNullOrEmpty(jsonObject.get("end")))
                    {
                        int temp_data_start = jsonObject.getInteger("start");
                        int temp_data_end = jsonObject.getInteger("end");
                        String data_form_str =complete_form_str.substring(temp_data_start-1,temp_data_end);
                        int length = data_form_str.length();
                        for (int i = 0; i < length; i += 10)
                        {
                            // 计算截取的终点，避免超出字符串长度
                            int end = Math.min(length, i + 10);
                            // 截取子串并添加到结果列表中
                            temp_list.add(data_form_str.substring(i, end));
                        }
                    }
                    jsonObject.put("list",temp_list);
                }
                protein_list.addAll(temp_data_list);
                protein_sequence.remove("mature_form");
            }
            protein_sequence.put("list",protein_list);
            result.put("protein_sequence",protein_sequence);

//            String complete_form=protein_sequence.get("complete_form").toString();
//            String mature_form=ObjectUtils.getDisplayString(protein_sequence.get("mature_form"));
//            String precursor=ObjectUtils.getDisplayString(protein_sequen            String complete_form=protein_sequence.get("complete_form").toString();
////            String mature_form=ObjectUtils.getDisplayString(protein_sequence.get("mature_form"));
////            String precursor=ObjectUtils.getDisplayString(protein_sequence.get("precursor"));
////            List<JSONObject> mmf=new ArrayList<>();
////            List<JSONObject> ppf=new ArrayList<>();
////            if (mature_form.length()>1)
////            {
////                for (String s : mature_form.split(";")) {
////                    JSONObject j=new JSONObject();
////                    j.put("mature_form",s);
////                    j.put("mature_form_length",s.length());
////                    j.put("mature_form_start",complete_form.indexOf(s));
////                    j.put("mature_form_send",complete_form.indexOf(s)+s.length());
////                    mmf.add(j);
////                }
////            }
////            if (precursor.length()>1)
////            {
////                for (String s : precursor.split(";")) {
////                    JSONObject j=new JSONObject();
////                    j.put("precursor",s);
////                    j.put("precursor_length",s.length());
////                    j.put("precursor_start",complete_form.indexOf(s));
////                    j.put("precursor_send",complete_form.indexOf(s)+s.length());
////                    ppf.add(j);
////                }
////            }
////            protein_sequence.put("mature_form",mmf);
////            protein_sequence.put("precursor",ppf);
////            List<JSONObject> lp=mapper.sel_lp();
////
////            if (!ObjectUtils.isEmpty(protein_sequence)&&!ObjectUtils.isEmpty(protein_sequence.get("complete_form"))){
////                int i=1;
////               List<JSONObject> l=new ArrayList<>();
////               if (!ObjectUtils.isEmpty(protein_sequence.get("complete_form")))
////                for (String cf : stringToStringArray(protein_sequence.get("complete_form").toString(), 10)) {
////                    JSONObject jl=new JSONObject();
////                    jl.put("length",i*10);
////                    jl.put("data",cf);
////                    l.add(jl);
////                    i++;
////                }
////                protein_sequence.put("list",l);
////            }ce.get("precursor"));
//            List<JSONObject> mmf=new ArrayList<>();
//            List<JSONObject> ppf=new ArrayList<>();
//            if (mature_form.length()>1)
//            {
//                for (String s : mature_form.split(";")) {
//                    JSONObject j=new JSONObject();
//                    j.put("mature_form",s);
//                    j.put("mature_form_length",s.length());
//                    j.put("mature_form_start",complete_form.indexOf(s));
//                    j.put("mature_form_send",complete_form.indexOf(s)+s.length());
//                    mmf.add(j);
//                }
//            }
//            if (precursor.length()>1)
//            {
//                for (String s : precursor.split(";")) {
//                    JSONObject j=new JSONObject();
//                    j.put("precursor",s);
//                    j.put("precursor_length",s.length());
//                    j.put("precursor_start",complete_form.indexOf(s));
//                    j.put("precursor_send",complete_form.indexOf(s)+s.length());
//                    ppf.add(j);
//                }
//            }
//            protein_sequence.put("mature_form",mmf);
//            protein_sequence.put("precursor",ppf);
//            List<JSONObject> lp=mapper.sel_lp();
//
//            if (!ObjectUtils.isEmpty(protein_sequence)&&!ObjectUtils.isEmpty(protein_sequence.get("complete_form"))){
//                int i=1;
//               List<JSONObject> l=new ArrayList<>();
//               if (!ObjectUtils.isEmpty(protein_sequence.get("complete_form")))
//                for (String cf : stringToStringArray(protein_sequence.get("complete_form").toString(), 10)) {
//                    JSONObject jl=new JSONObject();
//                    jl.put("length",i*10);
//                    jl.put("data",cf);
//                    l.add(jl);
//                    i++;
//                }
//                protein_sequence.put("list",l);
//            }
//            result.put("protein_sequence",protein_sequence);


            Map<String,Object> protein_function=dmapper.base_protein_function2(params.getString("id"));
            if (StringUnits.isNotNullOrEmpty(protein_function.get("function")))
            {
                // 处理蛋白质功能数据
                JSONArray functionArray = JSONArray.parseArray(protein_function.get("function").toString());
                // 更新原始数据
                protein_function.put("function", parsePMIDDatas(functionArray));//
            }

            JSONArray lre=new JSONArray();
            List<String> lpf=new ArrayList<>();
            if(!ObjectUtils.isEmpty(protein_function)&&!ObjectUtils.isEmpty(protein_function.get("gene_ontology_biological_process")))
            {
                lre = JSONArray.parseArray( protein_function.get("gene_ontology_biological_process").toString());
                for (int i = 0; i < lre.size(); i++)
                {
                    JSONObject temp_lre_obj = lre.getJSONObject(i);
                    if (StringUnits.isNotNullOrEmpty(temp_lre_obj.get("goId")))
                    {
                        String go_id = temp_lre_obj.getString("goId").replace("GO:","");
                        temp_lre_obj.put("goId",go_id);
                        temp_lre_obj.put("goUrl","https://www.ebi.ac.uk/QuickGO/term/GO:"+go_id);
                    }
                }
            }
//            for (String s : lpf) {
//                JSONObject j=new JSONObject();
//                j.put("ASPECT","Biological Process");
//                j.put("TERM",s.split("-")[0]);
//                j.put("pubMed",s.split("-")[1]);
//                j.put("goId",s.split("-")[2]);
//                j.put("goUrl","https://www.ebi.ac.uk/QuickGO/term/GO:"+j.get("goId"));
//                lre.add(j);
//            }
//            protein_function.put("gene_ontology_biological_process",lpf);
//            List<String> lcc=new ArrayList<>();
//            if(!ObjectUtils.isEmpty(protein_function)&&!ObjectUtils.isEmpty(protein_function.get("gene_ontology_cellular_component")))
//                for (String gene_ontology_biological_process : protein_function.get("gene_ontology_cellular_component").toString().replaceAll("\\[null\\]", ";").split(";")) {
//                lcc.add(gene_ontology_biological_process);
//            }
//            for (String s : lcc) {
//                JSONObject j=new JSONObject();
//                j.put("ASPECT","Cellular Component");
//                j.put("TERM",s.split("-")[0]);
//                j.put("pubMed",s.split("-")[1]);
//                j.put("goId",s.split("-")[2]);
//                lre.add(j);
//            }
//            protein_function.put("gene_ontology_cellular_component",lpf);
//            List<String> lmf=new ArrayList<>();
//            if(!ObjectUtils.isEmpty(protein_function)&&!ObjectUtils.isEmpty(protein_function.get("gene_ontology_molecular_function")))
//            for (String gene_ontology_biological_process : protein_function.get("gene_ontology_molecular_function").toString().replaceAll("\\[null\\]", ";").split(";")) {
//                lmf.add(gene_ontology_biological_process);
//            }
//
//            for (String s : lmf) {
//                JSONObject j=new JSONObject();
//                j.put("ASPECT","Molecular Function");
//                j.put("TERM",s.split("-")[0]);
//                j.put("pubMedId",s.split("-")[1]);
//                j.put("goId",s.split("-")[2]);
//                lre.add(j);
//            }
//            protein_function.put("gene_ontology_molecular_function",lmf);
            protein_function.put("new_list",lre);
            result.put("protein_function",protein_function);


            params.put("qiniu_base_url",QiniuUpload.qiniu_base_url);

            Map<String,Object> expression_and_location_info = dmapper.base_expression_and_location_show2(params);
//            b.
            if (StringUnits.isNotNullOrEmpty(expression_and_location_info.get("hpa_RNA_EXPRESSION_OVERVIEW")))
            {
                JSONArray temp_ex_loc_arr = JSONArray.parseArray(expression_and_location_info.get("hpa_RNA_EXPRESSION_OVERVIEW").toString());
                expression_and_location_info.put("hpa_RNA_EXPRESSION_OVERVIEW",temp_ex_loc_arr);
                double max_loc_value = 0;
                for (int i = 0; i < temp_ex_loc_arr.size(); i++)
                    max_loc_value = Math.max(max_loc_value,temp_ex_loc_arr.getJSONObject(i).getDoubleValue("value"));

                expression_and_location_info.put("rna_max_value",(temp_ex_loc_arr.size()>0?max_loc_value:null));
            }

            if (!ObjectUtils.isEmpty(expression_and_location_info)&&!ObjectUtils.isEmpty(expression_and_location_info.get("developmental_stage")))
            {
                JSONArray temp_source_data_array = JSONArray.parseArray(expression_and_location_info.get("developmental_stage").toString());
                expression_and_location_info.put("developmental_stage",parsePMIDDatas(temp_source_data_array));//
            }
            if (!ObjectUtils.isEmpty(expression_and_location_info)&&!ObjectUtils.isEmpty(expression_and_location_info.get("induction")))
            {
                JSONArray temp_source_data_array = JSONArray.parseArray(expression_and_location_info.get("induction").toString());
                expression_and_location_info.put("induction",parsePMIDDatas(temp_source_data_array));//
            }
            result.put("expression_and_location",expression_and_location_info);


            Map<String,Object> protein_structured=dmapper.base_protein_structure_show2(params.getString("id"));
            if (!ObjectUtils.isEmpty(protein_structured)&&!ObjectUtils.isEmpty(protein_structured.get("PDB_ID")))
            {
                JSONArray temp_structured_list = JSONArray.parseArray(protein_structured.get("PDB_ID").toString());
                for (int i = 0; i < temp_structured_list.size(); i++)
                {
                    JSONObject temp_structured_obj = temp_structured_list.getJSONObject(i);
                    if (StringUnits.isNotNullOrEmpty(temp_structured_obj.get("source")) &&
                            "AlphaFoldDB".equals(temp_structured_obj.get("source")))
                        temp_structured_obj.put("source","AlphaFold");
                }
                protein_structured.put("structured_list",temp_structured_list);
                protein_structured.remove("PDB_ID");
            }

            result.put("protein_structure",protein_structured);

            String complete_form = "";
            if (StringUnits.isNotNullOrEmpty(protein_sequence.get("complete_form")))
                complete_form=protein_sequence.get("complete_form").toString();
            Map<String,Object> family_and_domain=dmapper.base_family_and_domain_show2(params.getString("id"));
            if(ObjectUtils.isEmpty(family_and_domain.get("domain")))
            {
                family_and_domain.put("domain","");
            }else{
                String dm=family_and_domain.get("domain").toString();
                List<JSONObject> dml=new ArrayList<>();
                String[] dm_arr=dm.split(";");
                List<String> img_list = new ArrayList(){{
                    add("/img-detail-01.png");
                    add("/img-detail-02.png");
                    add("/img-detail-03.png");
                    add("/img-detail-04.png");
                    add("/img-detail-05.png");
                }};
                for (int k=0;k<dm_arr.length;k++)
                {
                    String s = dm_arr[k];
                    if (s.startsWith(" calcium-binding"))
                        continue;  //25年7月4号，过滤一下脏数据

                    JSONObject j=new JSONObject();
                    j.put("type","Domain");
                    String[] temp_s_array = s.split(",");

                    if (temp_s_array.length>0)
                        j.put("position",temp_s_array[0]);
                    if (temp_s_array.length>1)
                        j.put("egfLike", temp_s_array[1]);
                    if (temp_s_array.length>2)
                        j.put("url",temp_s_array[2]);

                    j.put("img",QiniuUpload.qiniu_base_url+(img_list.get(k%img_list.size())));

                    if (StringUnits.isNotNullOrEmpty(complete_form)&&
                            StringUnits.isNotNullOrEmpty(j.get("position")))
                    {
                        String[] start_end_arr =  j.getString("position").split("-");
                        if (start_end_arr.length ==2)
                        {
                            String cf =  family_and_domain.get("complete_form").toString();
                            j.put("start",s.split(",")[0]);
                            j.put("sequence",complete_form.substring(Integer.parseInt(start_end_arr[0])-1,Integer.parseInt(start_end_arr[1])));
                        }
                    }else {
                        j.put("sequence","");
                    }
                    dml.add(j);
                }
                family_and_domain.put("domain",dml);
            }
            result.put("family_and_domain",family_and_domain);

            Map<String,Object> protein_interaction=dmapper.base_protein_interaction_show2(params.getString("id"));
            if (!ObjectUtils.isEmpty(protein_interaction)&&!ObjectUtils.isEmpty(protein_interaction.get("protein_complex")))
            {
                JSONArray temp_interaction_array = JSONArray.parseArray(protein_interaction.get("protein_complex").toString());
                protein_interaction.put("protein_complex",parsePMIDDatas(temp_interaction_array));//temp_interaction_array
            }
            result.put("protein_interaction",protein_interaction);

            Map<String,Object> kegg_pathways=dmapper.base_drgf_show2(params.getString("id"));
            kegg_pathways.put("drgfCode", general_information.get("drgfCode"));
            if (!ObjectUtils.isEmpty(kegg_pathways)&&!ObjectUtils.isEmpty(kegg_pathways.get("pathway_data")))
            {
                if (!ObjectUtils.isEmpty(kegg_pathways)&&!ObjectUtils.isEmpty(kegg_pathways.get("pathway_data")))
                {
                    JSONArray temp_kegg_pathways_arr = JSONArray.parseArray(kegg_pathways.get("pathway_data").toString());
                    for (int j = 0; j < temp_kegg_pathways_arr.size(); j++)
                    {
                        JSONObject temp_kegg_pathways_obj = temp_kegg_pathways_arr.getJSONObject(j);
                        temp_kegg_pathways_obj.put("pathway_url",kegg_pathways.get("pathways"));
                    }
                    kegg_pathways.put("pathway_data",temp_kegg_pathways_arr);
                }
                result.put("kegg_pathways",kegg_pathways);
            }
            result.put("kegg_pathways",kegg_pathways);

            result.put("post_translational_modification",dmapper.base_ptm_details_show2(params.getString("id")));

            Map<String,Object> mutation_and_disease=dmapper.base_mutation_and_disease_show2(params.getString("id"));
            if (!ObjectUtils.isEmpty(mutation_and_disease)&&!ObjectUtils.isEmpty(mutation_and_disease.get("disease_json")))
            {
                JSONArray temp_data_jsonArray = JSONArray.parseArray(mutation_and_disease.get("disease_json").toString());
                for (int i = 0; i < temp_data_jsonArray.size(); i++)
                {
                    JSONObject temp_data_jsonObj = temp_data_jsonArray.getJSONObject(i);
                    if (StringUnits.isNotNullOrEmpty(temp_data_jsonObj.get("featureCrossReferences")))
                    {
                        String description_str = StringUnits.isNotNullOrEmpty(temp_data_jsonObj.get("description"))?temp_data_jsonObj.getString("description"):"";
                        JSONArray temp_featureCrossReferences_array = JSONArray.parseArray(temp_data_jsonObj.get("featureCrossReferences").toString());
                        for (int i1 = 0; i1 < temp_featureCrossReferences_array.size(); i1++)
                        {
                            String temp_data_str = String.format("%s:%s",temp_featureCrossReferences_array.getJSONObject(i1).get("database"),temp_featureCrossReferences_array.getJSONObject(i1).get("id"));
                            description_str = description_str.replaceAll(temp_data_str,"");
                        }
                        temp_data_jsonObj.put("description",description_str);
                        temp_data_jsonObj.put("featureCrossReferences",temp_featureCrossReferences_array);
                    }
                }
                mutation_and_disease.put("disease_json",temp_data_jsonArray);
            }
            result.put("mutation_and_disease",mutation_and_disease);

            return SingleResult.buildSuccess(result);
        } catch (Throwable t) {
            t.printStackTrace();
            throw new ServiceException(t.getMessage(),Code.ERROR.getStatus());
        }
    }

    @Override
    public SingleResult<DataResponse> selectProteinType() throws ServiceException {
        return SingleResult.buildSuccess(mapper.select_protein_type());
    }

    @Override
    public void updateProteinTypeDesc(Long id, String desc) {
        HashMap<String, Object> objectObjectHashMap = new HashMap<>();
        objectObjectHashMap.put("id", id);
        objectObjectHashMap.put("desc", desc);
        mapper.update_protein_type_desc(objectObjectHashMap);
    }

    @Override
    public SingleResult<DataResponse> add_example_data(JSONObject requestVo) {
        mapper.add_example_data(requestVo);
        return SingleResult.buildSuccessWithoutData();
    }

    @Override
    public SingleResult query_protein_data(JSONObject requestVo) {
        return SingleResult.buildSuccess(mapper.query_protein_data(requestVo));
    }

    @Override
    public SingleResult<DataResponse> query_search_example_data() {
        List<JSONObject> jsonObjects = mapper.query_search_example_data();
        jsonObjects.stream().forEach(s -> {
            String unipro_entry = (String) s.get("unipro_entry");
            JSONObject param = new JSONObject();
            param.put("unipro_entry", unipro_entry);
            List<JSONObject> jsonObjects1 = mapper.query_protein_data(param);
            JSONObject jsonObject = jsonObjects1.get(0);
            s.put("pid", jsonObject.get("id"));
        });
        return SingleResult.buildSuccess(jsonObjects);
    }

    @Override
    public SingleResult del_search_example_data(JSONObject requestVo) {
        mapper.del_search_example_data(requestVo);
        return SingleResult.buildSuccessWithoutData();
    }


    public SingleResult<DataResponse> sel_key_words(JSONObject requestVo) throws ServiceException {
        try {
            return SingleResult.buildSuccess(mapper.sel_key_words());
        } catch (Throwable t) {
            t.printStackTrace();
            throw new ServiceException(t.getMessage(),Code.ERROR.getStatus());
        }
    }

    public SingleResult<DataResponse> sel_key_words_example(JSONObject requestVo) throws ServiceException {
        try {
            return SingleResult.buildSuccess(mapper.sel_key_words_example());
        } catch (Throwable t) {
            t.printStackTrace();
            throw new ServiceException(t.getMessage(),Code.ERROR.getStatus());
        }
    }
    public SingleResult<DataResponse> sel_browse(JSONObject requestVo) throws ServiceException {
        try {
            return SingleResult.buildSuccess(new ArrayList(){{
                add(new JSONObject(){{
                    put("name","general_information");
                    put("name","protein_sequence");
                    put("name","protein_function");
                    put("name","expression_and_location");
                    put("name","protein_structure");
                    put("name","family_and_domain");
                    put("name","protein_interaction");
                    put("name","mutation_and_disease");
                    put("name","post_translational_modification");
                }});
            }});
        } catch (Throwable t) {
            t.printStackTrace();
            throw new ServiceException(t.getMessage(),Code.ERROR.getStatus());
        }
    }

    public SingleResult<DataResponse> sel_databases_brief(JSONObject requestVo) throws ServiceException {
        try {
            return SingleResult.buildSuccess(mapper.sel_databases_brief());
        } catch (Throwable t) {
            t.printStackTrace();
            throw new ServiceException(t.getMessage(),Code.ERROR.getStatus());
        }
    }

    @Override
    public SingleResult<DataResponse> query_example_data(JSONObject requestVo) throws ServiceException {
        try {
            return SingleResult.buildSuccess(mapper.query_example_data(requestVo));
        } catch (Throwable t) {
            t.printStackTrace();
            throw new ServiceException(t.getMessage(),Code.ERROR.getStatus());
        }
    }


    @Override
    public SingleResult<DataResponse> sys_cooperate_manage_list(JSONObject requestVo) throws ServiceException {

        Map<String,Object> params=new HashMap<>();
        Map<String,Object> res=new HashMap<>();
        try {
            params.put("qiniu_base_url", QiniuUpload.qiniu_base_url);

            List<Map<String,Object>> res_list = mapper.sys_cooperate_manage_list(params);
            res.put("list",res_list);
            res.put("total",res_list.size());

            return SingleResult.buildSuccess(res);
        } catch (Throwable t) {
            t.printStackTrace();
            throw new ServiceException("失败", Code.ERROR.getStatus());
        }
    }
    @Override
    public SingleResult<DataResponse> data_statistics_information(JSONObject requestVo) throws ServiceException {

        try {
            return SingleResult.buildSuccess(new JSONObject(){{
                put("human",new ArrayList(){{
                    add(new JSONObject(){{
                        put("name","LR pairs");
                        put("value","3398");
                        put("brief","xxxxxxxxxx");
                    }});
                    add(new JSONObject(){{
                        put("name","Ligands");
                        put("value","815");
                        put("brief","xxxxxxxxxx");
                    }});
                }});
                put("mouse",new ArrayList(){{
                    add(new JSONObject(){{
                        put("name","LR pairs");
                        put("value","3398");
                        put("brief","xxxxxxxxxx");
                    }});
                    add(new JSONObject(){{
                        put("name","Ligands");
                        put("value","815");
                        put("brief","xxxxxxxxxx");
                    }});
                }});
            }});
        } catch (Throwable t) {
            t.printStackTrace();
            throw new ServiceException("失败", Code.ERROR.getStatus());
        }
    }


    public static String[] stringToStringArray(String text, int length) {
        //检查参数是否合法
        if (StringUtils.isEmpty(text)) {
            return null;
        }

        if (length <= 0) {
            return null;
        }
        //获取整个字符串可以被切割成字符子串的个数
        int n = (text.length() + length - 1) / length;
        String[] splitArr = new String[n];
        for (int i = 0; i < n; i++) {
            if (i < (n - 1)) {
                splitArr[i] = text.substring(i * length, (i + 1) * length);
            } else {
                splitArr[i] = text.substring(i * length);
            }
        }
        return splitArr;
    }


   private JSONArray parsePMIDDatas(JSONArray functionArray){

       for (int i = 0; i < functionArray.size(); i++)
       {
           JSONObject functionObj = functionArray.getJSONObject(i);
           if (StringUnits.isNotNullOrEmpty(functionObj.get("value")) == false)
               continue;
           String originalValue = functionObj.getString("value");
           StringBuilder processedValue = new StringBuilder();

           // 处理旧数据格式（没有div结尾）
           if (!originalValue.endsWith("</div>"))
           {
               processedValue.append("<p>")  // 添加16px字号
                       .append(originalValue);

               // 添加PMID数据链接
               if (functionObj.containsKey("evidences"))
               {
                   JSONArray evidences = functionObj.getJSONArray("evidences");
                   if (evidences != null && !evidences.isEmpty()) {
                       processedValue.append("<span style=\"font-size: 16px;\">(PMID:");  // 包裹内容并设置16px字号
                       for (int j = 0; j < evidences.size(); j++) {
                           if (j > 0) {
                               processedValue.append(",");
                           }
                           JSONObject evidence = evidences.getJSONObject(j);
                           String pmid = evidence.getString("id");
                           // 添加强制不换行和16px字号样式
                           processedValue.append(String.format(
                                   "<a href=\"https://pubmed.ncbi.nlm.nih.gov/%s/\" " +
                                           "target=\"_blank\" " +
                                           "style=\"white-space: nowrap; display: inline-block; font-size: 16px; color: rgb(19, 82, 135);\">%s</a>",
                                   pmid, pmid
                           ));
                       }
                       processedValue.append(")</span>");  // 结束包裹的span
                   }
               }
               processedValue.append("</p>");
           } else// 处理新数据格式
           {
               // 提取最后一个</p>之前的内容
               int lastPIndex = originalValue.lastIndexOf("</p>");
               String contentBeforeLastP = originalValue.substring(0, lastPIndex);
               processedValue.append(contentBeforeLastP);

               // 添加PMID数据链接
               if (functionObj.containsKey("evidences"))
               {
                   JSONArray evidences = functionObj.getJSONArray("evidences");
                   if (evidences != null && !evidences.isEmpty()) {
                       processedValue.append("<span style=\"font-size: 16px;\">(PMID:");  // 包裹内容并设置16px字号
                       for (int j = 0; j < evidences.size(); j++) {
                           if (j > 0) {
                               processedValue.append(",");
                           }
                           JSONObject evidence = evidences.getJSONObject(j);
                           String pmid = evidence.getString("id");
                           // 添加强制不换行和16px字号样式
                           processedValue.append(String.format(
                                   "<a href=\"https://pubmed.ncbi.nlm.nih.gov/%s/\" " +
                                           "target=\"_blank\" " +
                                           "style=\"white-space: nowrap; display: inline-block; font-size: 16px;\">%s</a>",
                                   pmid, pmid
                           ));
                       }
                       processedValue.append(")</span>");  // 结束包裹的span
                   }
               }
               // 添加最后一个</p>之后的内容
               processedValue.append(originalValue.substring(lastPIndex));
           }
           // 更新处理后的值
           functionObj.put("value", processedValue.toString());
           functionObj.put("evidences",new JSONArray());
       }
       return functionArray;
   }

}
