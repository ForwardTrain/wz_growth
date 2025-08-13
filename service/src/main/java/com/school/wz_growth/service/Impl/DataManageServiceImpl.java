package com.school.wz_growth.service.Impl;


import com.alibaba.fastjson.JSONObject;
import com.school.wz_growth.common.exception.ServiceException;
import com.school.wz_growth.common.response.Code;
import com.school.wz_growth.common.response.DataResponse;
import com.school.wz_growth.common.response.SingleResult;
import com.school.wz_growth.common.validator.StringUnits;
import com.school.wz_growth.dao.DataManageMapper;
import com.school.wz_growth.service.DataManageService;
import javafx.scene.input.DataFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Service
public class DataManageServiceImpl implements DataManageService {
    @Autowired
    DataManageMapper mapper;
    @Override
    public SingleResult<DataResponse> data_list(JSONObject requestVo) throws ServiceException {
        try {
            JSONObject params=new JSONObject(){{
                put("id",requestVo.get("id"));
                put("name",requestVo.get("name"));
                put("start",requestVo.get("start"));
                put("limit",requestVo.get("limit"));
            }};
            List<JSONObject> list=mapper.data_list(params);
           int total=mapper.data_list_total(params);
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
    public SingleResult<DataResponse> upd_data_list(JSONObject requestVo) throws ServiceException {
        try {
            JSONObject params=new JSONObject(){{
                put("c_s_date",requestVo.get("c_s_date"));
                put("status",requestVo.get("status"));
                put("name",requestVo.get("name"));//?更新数据库这个字段暂时我用时间加序号
                put("start",requestVo.get("start"));
                put("limit",requestVo.get("limit"));
            }};
            List<JSONObject> list=mapper.upd_data_list(params);
//            list.forEach(sub_obj_map -> {
//                sub_obj_map.put("database_size",Math.);
//            });
           int total=mapper.upd_data_list_total(params);
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
    public SingleResult<DataResponse> upd_data_list_sel(JSONObject requestVo) throws ServiceException {
        try {
            JSONObject params=new JSONObject(){{
                put("id",requestVo.get("id"));
            }};
         JSONObject result=mapper.upd_data_list_sel(params);
            String databaseSize = result.getString("database_size");
            result.put("database_size", formatNumber(databaseSize));
            return SingleResult.buildSuccess(result);
        } catch (Throwable t) {
            t.printStackTrace();
            throw new ServiceException(t.getMessage(),Code.ERROR.getStatus());
        }
    }
    @Override
    public SingleResult<DataResponse> upd_data_list_sel_db_combobox(JSONObject requestVo) throws ServiceException {
        try {
         List<JSONObject> result=mapper.upd_data_list_sel_db_combobox();
            return SingleResult.buildSuccess(result);
        } catch (Throwable t) {
            t.printStackTrace();
            throw new ServiceException(t.getMessage(),Code.ERROR.getStatus());
        }
    }
    @Override
    public SingleResult<DataResponse> upd_data_list_sel_db_combobox_right_list(JSONObject requestVo) throws ServiceException {
        try {
            JSONObject params=new JSONObject(){{
                put("name",requestVo.getString("name"));
                put("type",requestVo.getString("type"));
                put("start",requestVo.get("start"));
                put("limit",requestVo.get("limit"));
            }};
List<JSONObject> add=new ArrayList<>();
List<JSONObject> del=new ArrayList<>();
List<JSONObject> diff=new ArrayList<>();
            List<JSONObject> list=mapper.upd_data_list_sel_db_combobox_right_list(params);
            List<JSONObject> list_show=mapper.sel_list_show();
          if (params.getString("type").equals("add")||ObjectUtils.isEmpty(params.get("type"))){
              add=list.stream().filter(j->(list_show.stream().filter(js->js.containsValue(j.getString("protein_name"))).collect(Collectors.toList()).size()==0)).collect(Collectors.toList());
          }else if (params.getString("type").equals("del")||ObjectUtils.isEmpty(params.get("type"))){
              List<JSONObject> l=list.stream().collect(Collectors.toList());
              del=list_show.stream().filter(j->(l.stream().filter(js->js.containsValue(j.getString("protein_name"))).collect(Collectors.toList()).size()==0)).collect(Collectors.toList());
          }else if (params.getString("type").equals("diff")||ObjectUtils.isEmpty(params.get("type"))){
            //差异数据
              List<JSONObject> l=new ArrayList<>();
              for (JSONObject jsonObject : list) {
                  JSONObject result=mapper.upd_data_list_details(jsonObject);
                  JSONObject result_show=mapper.upd_data_list_details_show(result);
                  if (ObjectUtils.isEmpty(result_show))
                      continue;
                  Map<String ,Object> mr1=mapper.base_general_information(result.getString("id"));
                  result.put("general_information",mr1==null?new JSONObject():mr1);
                  Map<String ,Object> mr2=mapper.base_protein_sequence(result.getString("id"));
                  result.put("protein_sequence",mr2==null?new JSONObject():new JSONObject(mr2));
                  Map<String ,Object> mr3=mapper.base_protein_function(result.getString("id"));
                  result.put("protein_function",mr3==null?new JSONObject():new JSONObject(mr3));
                  Map<String ,Object> mr4=mapper.base_expression_and_location(result.getString("id"));
                  result.put("expression_and_location",mr4==null?new JSONObject():new JSONObject(mr4));
                  Map<String ,Object> mr5=mapper.base_protein_structure(result.getString("id"));
                  result.put("protein_structure",mr5==null?new JSONObject():new JSONObject(mr5));
                  Map<String ,Object> mr6=mapper.base_family_and_domain(result.getString("id"));
                  result.put("family_and_domain",mr6==null?new JSONObject():new JSONObject(mr6));
                  Map<String ,Object> mr7=mapper.base_protein_interaction(result.getString("id"));
                  result.put("protein_interaction",mr7==null?new JSONObject():new JSONObject(mr7));
                  Map<String ,Object> mr8=mapper.base_mutation_and_disease(result.getString("id"));
                  result.put("mutation_and_disease",mr8==null?new JSONObject():new JSONObject(mr8));
                  Map<String ,Object> mr9=mapper.base_post_translational_modification(result.getString("id"));
                  result.put("post_translational_modification",mr9==null?new JSONObject():new JSONObject(mr9));

                  Map<String ,Object> mrs1=mapper.base_general_information(result_show.getString("id"));
                  result_show.put("general_information",mr1==null?new JSONObject():mrs1);
                  Map<String ,Object> mrs2=mapper.base_protein_sequence(result_show.getString("id"));
                  result_show.put("protein_sequence",mr2==null?new JSONObject():new JSONObject(mrs2));
                  Map<String ,Object> mrs3=mapper.base_protein_function_show(result_show.getString("id"));
                  result_show.put("protein_function",mr3==null?new JSONObject():new JSONObject(mrs3));
                  Map<String ,Object> mrs4=mapper.base_expression_and_location_show(result_show.getString("id"));
                  result_show.put("expression_and_location",mr4==null?new JSONObject():new JSONObject(mrs4));
                  Map<String ,Object> mrs5=mapper.base_protein_structure_show(result_show.getString("id"));
                  result_show.put("protein_structure",mr5==null?new JSONObject():new JSONObject(mrs5));
                  Map<String ,Object> mrs6=mapper.base_family_and_domain_show(result_show.getString("id"));
                  result_show.put("family_and_domain",mr6==null?new JSONObject():new JSONObject(mrs6));
                  Map<String ,Object> mrs7=mapper.base_protein_interaction_show(result_show.getString("id"));
                  result_show.put("protein_interaction",mr7==null?new JSONObject():new JSONObject(mrs7));
                  Map<String ,Object> mrs8=mapper.base_mutation_and_disease_show(result_show.getString("id"));
                  result_show.put("mutation_and_disease",mr8==null?new JSONObject():new JSONObject(mrs8));
                  Map<String ,Object> mrs9=mapper.base_post_translational_modification_show(result_show.getString("id"));
                  result_show.put("post_translational_modification",mrs9==null?new JSONObject():new JSONObject(mrs9));


//                result.put("general_information",new JSONObject(mapper.base_general_information(result.getString("id"))));
//                  result.put("protein_sequence",new JSONObject(mapper.base_protein_sequence(result.getString("id"))));
//                  result.put("protein_function",new JSONObject(mapper.base_protein_function(result.getString("id"))));
//                  result.put("expression_and_location",new JSONObject(mapper.base_expression_and_location(result.getString("id"))));
//                  result.put("protein_structure",new JSONObject(mapper.base_protein_structure(result.getString("id"))));
//                  result.put("family_and_domain",new JSONObject(mapper.base_family_and_domain(result.getString("id"))));
//                  result.put("protein_interaction",new JSONObject(mapper.base_protein_interaction(result.getString("id"))));
//                  result.put("mutation_and_disease",new JSONObject(mapper.base_mutation_and_disease(result.getString("id"))));
//                  result.put("post_translational_modification",new JSONObject(mapper.base_post_translational_modification(result.getString("id"))));
//
//
//result_show.put("general_information",new JSONObject(mapper.base_general_information_show(result_show.getString("id"))));
//                  result_show.put("protein_sequence",new JSONObject(mapper.base_protein_sequence_show(result_show.getString("id"))));
//                  result_show.put("protein_function",new JSONObject(mapper.base_protein_function_show(result_show.getString("id"))));
//                  result_show.put("expression_and_location",new JSONObject(mapper.base_expression_and_location_show(result_show.getString("id"))));
//                  result_show.put("protein_structure",new JSONObject(mapper.base_protein_structure_show(result_show.getString("id"))));
//                  result_show.put("family_and_domain",new JSONObject(mapper.base_family_and_domain_show(result_show.getString("id"))));
//                  result_show.put("protein_interaction",new JSONObject(mapper.base_protein_interaction_show(result_show.getString("id"))));
//                  result_show.put("mutation_and_disease",new JSONObject(mapper.base_mutation_and_disease_show(result_show.getString("id"))));
//                  result_show.put("post_translational_modification",new JSONObject(mapper.base_post_translational_modification_show(result_show.getString("id"))));
//
                  if (compare_diff(result,result_show))
                      l.add(jsonObject);
              }
              diff=l;
          }
            JSONObject r=new JSONObject();
          List<JSONObject> rl=new ArrayList<>();
            if (params.getString("type").equals("add"))
            {
                if (add.size()==0){
                    r.put("total",0);
                    r.put("list",rl);
                }else{
                    for(int i=requestVo.getInteger("start");i<requestVo.getInteger("limit");i++)
                    {
                        if (add.size()<i+1)
                            break;
                        rl.add(add.get(i));
                    }
                    r.put("total",add.size());
                    r.put("list",rl);
                }
            }  else if (params.getString("type").equals("del"))
            {
                if (del.size()==0){
                    r.put("total",0);
                    r.put("list",rl);
                }else{
                    for(int i=requestVo.getInteger("start");i<requestVo.getInteger("limit");i++){
                        if (del.size()<i+1)
                            break;
                        rl.add(del.get(i));
                    }
                    r.put("total",del.size());
                    r.put("list",rl);
                }

            } else if (params.getString("type").equals("diff"))
            {
                if (diff.size()==0){
                    r.put("total",0);
                    r.put("list",rl);
                }else{
                    for(int i=requestVo.getInteger("start");i<requestVo.getInteger("limit");i++){
                        if (diff.size()<i+1)
                            break;
                        rl.add(diff.get(i));
                    }
                    r.put("total",diff.size());
                    r.put("list",rl);
                }

            }else {
                if (list.size()==0){
                    r.put("total",0);
                    r.put("list",rl);
                }else{
                    for(int i=requestVo.getInteger("start");i<requestVo.getInteger("limit");i++){
                        if (list.size()<i+1)
                            break;
                        rl.add(list.get(i));
                    }
                    r.put("total",list.size());
                    r.put("list",rl);
                }

            }
            return SingleResult.buildSuccess(r);
        } catch (Throwable t) {
            t.printStackTrace();
            throw new ServiceException(t.getMessage(),Code.ERROR.getStatus());
        }
    }

    @Override
    public SingleResult<DataResponse> upd_data_list_sel_db_combobox_left_down(JSONObject requestVo) throws ServiceException {
        try {
            JSONObject params=new JSONObject(){{
                put("name",requestVo.getString("name"));
                put("start",requestVo.get("start"));
                put("limit",requestVo.get("limit"));
            }};
List<JSONObject> add=new ArrayList<>();
List<JSONObject> del=new ArrayList<>();
List<JSONObject> diff=new ArrayList<>();
            List<JSONObject> list=mapper.upd_data_list_sel_db_combobox_right_list(params);
            List<JSONObject> list_show=mapper.sel_list_show();
              add=list.stream().filter(j->(list_show.stream().filter(js->js.containsValue(j.getString("protein_name"))).collect(Collectors.toList()).size()==0)).collect(Collectors.toList());
              List<JSONObject> l=list.stream().collect(Collectors.toList());
              del=list_show.stream().filter(j->(l.stream().filter(js->js.containsValue(j.getString("protein_name"))).collect(Collectors.toList()).size()==0)).collect(Collectors.toList());
            //差异数据
              for (JSONObject jsonObject : list) {
                  JSONObject result=mapper.upd_data_list_details(jsonObject);
                  JSONObject result_show=mapper.upd_data_list_details_show(result);
                  if (ObjectUtils.isEmpty(result)||ObjectUtils.isEmpty(result_show))
                      continue;
                  Map<String ,Object> mr1=mapper.base_general_information(result.getString("id"));
                  result.put("general_information",mr1==null?new JSONObject():mr1);
Map<String ,Object> mr2=mapper.base_protein_sequence(result.getString("id"));
                  result.put("protein_sequence",mr2==null?new JSONObject():new JSONObject(mr2));
                  Map<String ,Object> mr3=mapper.base_protein_function(result.getString("id"));
                  result.put("protein_function",mr3==null?new JSONObject():new JSONObject(mr3));
                  Map<String ,Object> mr4=mapper.base_expression_and_location(result.getString("id"));
                  result.put("expression_and_location",mr4==null?new JSONObject():new JSONObject(mr4));
                  Map<String ,Object> mr5=mapper.base_protein_structure(result.getString("id"));
                  result.put("protein_structure",mr5==null?new JSONObject():new JSONObject(mr5));
                  Map<String ,Object> mr6=mapper.base_family_and_domain(result.getString("id"));
                  result.put("family_and_domain",mr6==null?new JSONObject():new JSONObject(mr6));
                  Map<String ,Object> mr7=mapper.base_protein_interaction(result.getString("id"));
                  result.put("protein_interaction",mr7==null?new JSONObject():new JSONObject(mr7));
                  Map<String ,Object> mr8=mapper.base_mutation_and_disease(result.getString("id"));
                  result.put("mutation_and_disease",mr8==null?new JSONObject():new JSONObject(mr8));
                  Map<String ,Object> mr9=mapper.base_post_translational_modification(result.getString("id"));
                  result.put("post_translational_modification",mr9==null?new JSONObject():new JSONObject(mr9));

                  Map<String ,Object> mrs1=mapper.base_general_information(result_show.getString("id"));
                  result_show.put("general_information",mr1==null?new JSONObject():mrs1);
                  Map<String ,Object> mrs2=mapper.base_protein_sequence(result_show.getString("id"));
                  result_show.put("protein_sequence",mr2==null?new JSONObject():new JSONObject(mrs2));
                  Map<String ,Object> mrs3=mapper.base_protein_function_show(result_show.getString("id"));
                  result_show.put("protein_function",mr3==null?new JSONObject():new JSONObject(mrs3));
                  Map<String ,Object> mrs4=mapper.base_expression_and_location_show(result_show.getString("id"));
                  result_show.put("expression_and_location",mr4==null?new JSONObject():new JSONObject(mrs4));
                  Map<String ,Object> mrs5=mapper.base_protein_structure_show(result_show.getString("id"));
                  result_show.put("protein_structure",mr5==null?new JSONObject():new JSONObject(mrs5));
                  Map<String ,Object> mrs6=mapper.base_family_and_domain_show(result_show.getString("id"));
                  result_show.put("family_and_domain",mr6==null?new JSONObject():new JSONObject(mrs6));
                  Map<String ,Object> mrs7=mapper.base_protein_interaction_show(result_show.getString("id"));
                  result_show.put("protein_interaction",mr7==null?new JSONObject():new JSONObject(mrs7));
                  Map<String ,Object> mrs8=mapper.base_mutation_and_disease_show(result_show.getString("id"));
                  result_show.put("mutation_and_disease",mr8==null?new JSONObject():new JSONObject(mrs8));
                  Map<String ,Object> mrs9=mapper.base_post_translational_modification_show(result_show.getString("id"));
                  result_show.put("post_translational_modification",mrs9==null?new JSONObject():new JSONObject(mrs9));



//
//                  result_show.put("general_information",new JSONObject(mapper.base_general_information_show(result_show.getString("id"))));
//                  result_show.put("protein_sequence",new JSONObject(mapper.base_protein_sequence_show(result_show.getString("id"))));
//                  result_show.put("protein_function",new JSONObject(mapper.base_protein_function_show(result_show.getString("id"))));
//                  result_show.put("expression_and_location",new JSONObject(mapper.base_expression_and_location_show(result_show.getString("id"))));
//                  result_show.put("protein_structure",new JSONObject(mapper.base_protein_structure_show(result_show.getString("id"))));
//                  result_show.put("family_and_domain",new JSONObject(mapper.base_family_and_domain_show(result_show.getString("id"))));
//                  result_show.put("protein_interaction",new JSONObject(mapper.base_protein_interaction_show(result_show.getString("id"))));
//                  result_show.put("mutation_and_disease",new JSONObject(mapper.base_mutation_and_disease_show(result_show.getString("id"))));
//                  result_show.put("post_translational_modification",new JSONObject(mapper.base_post_translational_modification_show(result_show.getString("id"))));

                  if (compare_diff(result,result_show))
                      diff.add(jsonObject);
              }
            JSONObject r=new JSONObject();
            r.put("add",add.size());
            r.put("diff",diff.size());
            r.put("del",del.size());
            r.put("total",list.size());
            return SingleResult.buildSuccess(r);
        } catch (Throwable t) {
            t.printStackTrace();
            throw new ServiceException(t.getMessage(),Code.ERROR.getStatus());
        }
    }

    boolean compare_diff(JSONObject j1,JSONObject j2){
        boolean flag=false;
        for (String k : j1.keySet()) {
            if (k.equals("id")||k.equals("protein_name")||k.equals("protein_name_and_unipro_entry"))
                continue;
            JSONObject l1=j1.getJSONObject(k);
            JSONObject l2=j2.getJSONObject(k);
            for (String s : l1.keySet()) {
                if (s.equals("id")||s.equals("create_time")||s.equals("modify_time")||s.equals("c_s_date")||s.equals("p_id"))
                    continue;
                if (ObjectUtils.getDisplayString(l1.get(s)).equals(ObjectUtils.getDisplayString(l2.get(s))))
                    continue;
                else {
                    flag = true;
                    break;
                }
            }
            if (flag)
                break;
        }
        return flag;
    }
    @Override
    public SingleResult<DataResponse> del_data_list(JSONObject requestVo) throws ServiceException {
        try {
            JSONObject params=new JSONObject(){{
                put("ids",requestVo.get("ids"));
            }};
           mapper.del_data_list(params);
            return SingleResult.buildSuccessWithoutData();
        } catch (Throwable t) {
            t.printStackTrace();
            throw new ServiceException(t.getMessage(),Code.ERROR.getStatus());
        }
    }

    JSONObject link_json(JSONObject result){
        result.put("general_information",mapper.base_general_information(result.getString("id")));
        result.put("protein_sequence",mapper.base_protein_sequence(result.getString("id")));
        result.put("protein_function",mapper.base_protein_function(result.getString("id")));
        result.put("expression_and_location",mapper.base_expression_and_location(result.getString("id")));
        result.put("protein_structure",mapper.base_protein_structure(result.getString("id")));
        result.put("family_and_domain",mapper.base_family_and_domain(result.getString("id")));
        result.put("protein_interaction",mapper.base_protein_interaction(result.getString("id")));
        result.put("mutation_and_disease",mapper.base_mutation_and_disease(result.getString("id")));
        result.put("post_translational_modification",mapper.base_post_translational_modification(result.getString("id")));
    return new JSONObject();
    }

    @Override
    public SingleResult<DataResponse> upd_data_list_details(JSONObject requestVo) throws ServiceException {
        try {
            JSONObject params=new JSONObject(){{
                put("id",requestVo.get("id"));
            }};
            JSONObject result=mapper.upd_data_list_details(requestVo);
            result.put("as_know",mapper.sel_as_know(result.getString("id")));
            result.put("general_information",mapper.base_general_information(result.getString("id")));
            result.put("protein_sequence",mapper.base_protein_sequence(result.getString("id")));
            result.put("protein_function",mapper.base_protein_function(result.getString("id")));
            result.put("expression_and_location",mapper.base_expression_and_location(result.getString("id")));
            result.put("protein_structure",mapper.base_protein_structure(result.getString("id")));
            result.put("family_and_domain",mapper.base_family_and_domain(result.getString("id")));
            result.put("protein_interaction",mapper.base_protein_interaction(result.getString("id")));
            result.put("mutation_and_disease",mapper.base_mutation_and_disease(result.getString("id")));
            result.put("post_translational_modification",mapper.base_post_translational_modification(result.getString("id")));
List<JSONObject> list=new ArrayList<>();
            for (String s : result.getJSONObject("expression_and_location").keySet()) {
                if (s.equals("id")||s.equals("p_id")||s.equals("create_time")||s.equals("modify_time")
                ||s.equals("operator")||s.equals("c_s_date"))
                    continue;
                JSONObject base_expression_and_location=new JSONObject();
                base_expression_and_location.put("type","expression_and_location");
                base_expression_and_location.put("name",s);
                base_expression_and_location.put("value",result.getJSONObject("expression_and_location").get(s));
                list.add(base_expression_and_location);
            }
            for (String s : result.getJSONObject("family_and_domain").keySet()) {
                if (s.equals("id")||s.equals("p_id")||s.equals("create_time")||s.equals("modify_time")
                        ||s.equals("operator")||s.equals("c_s_date"))
                    continue;
                JSONObject base_family_and_domain=new JSONObject();
                base_family_and_domain.put("type","family_and_domain");
                base_family_and_domain.put("name",s);
                base_family_and_domain.put("value",result.getJSONObject("family_and_domain").get(s));
                list.add(base_family_and_domain);
            }
            for (String s : result.getJSONObject("general_information").keySet()) {
                if (s.equals("id")||s.equals("p_id")||s.equals("create_time")||s.equals("modify_time")
                        ||s.equals("operator")||s.equals("c_s_date")||s.equals("batch"))
                    continue;
                JSONObject base_general_information=new JSONObject();
                base_general_information.put("type","general_information");
                base_general_information.put("name",s);
                base_general_information.put("value",result.getJSONObject("general_information").get(s));
                list.add(base_general_information);
            }
            for (String s : result.getJSONObject("mutation_and_disease").keySet()) {
                if (s.equals("id")||s.equals("p_id")||s.equals("create_time")||s.equals("modify_time")
                        ||s.equals("operator")||s.equals("c_s_date"))
                    continue;
                JSONObject base_mutation_and_disease=new JSONObject();
                base_mutation_and_disease.put("type","mutation_and_disease");
                base_mutation_and_disease.put("name",s);
                base_mutation_and_disease.put("value",result.getJSONObject("mutation_and_disease").get(s));
                list.add(base_mutation_and_disease);
            }

            for (String s : result.getJSONObject("post_translational_modification").keySet()) {
                if (s.equals("id")||s.equals("p_id")||s.equals("create_time")||s.equals("modify_time")
                        ||s.equals("operator")||s.equals("c_s_date"))
                    continue;
                JSONObject base_post_translational_modification=new JSONObject();
                base_post_translational_modification.put("type","post_translational_modification");
                base_post_translational_modification.put("name",s);
                base_post_translational_modification.put("value",result.getJSONObject("post_translational_modification").get(s));
                list.add(base_post_translational_modification);
            }
            for (String s : result.getJSONObject("protein_function").keySet()) {
                if (s.equals("id")||s.equals("p_id")||s.equals("create_time")||s.equals("modify_time")
                        ||s.equals("operator")||s.equals("c_s_date"))
                    continue;
                JSONObject base_protein_function=new JSONObject();
                base_protein_function.put("type","protein_function");
                base_protein_function.put("name",s);
                base_protein_function.put("value",result.getJSONObject("protein_function").get(s));
                list.add(base_protein_function);
            }
            for (String s : result.getJSONObject("protein_interaction").keySet()) {
                if (s.equals("id")||s.equals("p_id")||s.equals("create_time")||s.equals("modify_time")
                        ||s.equals("operator")||s.equals("c_s_date"))
                    continue;
                JSONObject base_protein_interaction=new JSONObject();
                base_protein_interaction.put("type","protein_interaction");
                base_protein_interaction.put("name",s);
                base_protein_interaction.put("value",result.getJSONObject("protein_interaction").get(s));
                list.add(base_protein_interaction);
            }
            for (String s : result.getJSONObject("protein_sequence").keySet()) {
                if (s.equals("id")||s.equals("p_id")||s.equals("create_time")||s.equals("modify_time")
                        ||s.equals("operator")||s.equals("c_s_date"))
                    continue;
                JSONObject base_protein_sequence=new JSONObject();
                base_protein_sequence.put("type","protein_sequence");
                base_protein_sequence.put("name",s);
                base_protein_sequence.put("value",result.getJSONObject("protein_sequence").get(s));
                list.add(base_protein_sequence);
            }
            for (String s : result.getJSONObject("protein_structure").keySet()) {
                if (s.equals("id")||s.equals("p_id")||s.equals("create_time")||s.equals("modify_time")
                        ||s.equals("operator")||s.equals("c_s_date"))
                    continue;
                JSONObject base_protein_structure=new JSONObject();
                base_protein_structure.put("type","protein_structure");
                base_protein_structure.put("name",s);
                base_protein_structure.put("value",result.getJSONObject("protein_structure").get(s));
                list.add(base_protein_structure);
            }
            result.put("list",list);
            return SingleResult.buildSuccess(result);
        } catch (Throwable t) {
            t.printStackTrace();
            throw new ServiceException(t.getMessage(),Code.ERROR.getStatus());
        }
    }
    @Override
    public SingleResult<DataResponse> add_upd_as_know(JSONObject requestVo) throws ServiceException {
        try {
            JSONObject params=new JSONObject(){{
                put("id",requestVo.get("id"));
                put("name",requestVo.get("name"));
                put("p_id",requestVo.get("p_id"));
            }};
          if (!ObjectUtils.isEmpty(params.get("id")))
              mapper.del_a_k(params);
            if (!ObjectUtils.isEmpty(params.get("name")))
            mapper.add_a_k(params);

         return SingleResult.buildSuccessWithoutData();
        } catch (Throwable t) {
            t.printStackTrace();
            throw new ServiceException(t.getMessage(),Code.ERROR.getStatus());
        }
    }
    @Override
    public SingleResult<DataResponse> diff_data_upd(JSONObject requestVo) throws ServiceException {
        try {
            if (requestVo.getString("type").equals("add")){
                List<JSONObject> current=requestVo.getJSONArray("current").toJavaList(JSONObject.class);
                for (JSONObject jsonObject : current) {
                    mapper.add_show_from_current(jsonObject);
                }
            }else if(requestVo.getString("type").equals("del")){
                String ids="0";
                List<JSONObject> show=requestVo.getJSONArray("show").toJavaList(JSONObject.class);
                for (JSONObject jsonObject : show) {
                    ids+=","+jsonObject.getString("id");
                }
               JSONObject j= new JSONObject();
                j.put("ids",ids);
                mapper.del_data_list(j);
            }else{
                List<JSONObject> show=requestVo.getJSONArray("show").toJavaList(JSONObject.class);
                List<JSONObject> current=requestVo.getJSONArray("current").toJavaList(JSONObject.class);
                for (JSONObject jsonObject : show) {
                    for (JSONObject object : current) {
                        if (jsonObject.getString("name").equals(object.getString("name"))){
                            jsonObject.put("value",object.getString("value"));
                            break;
                        }
                    }
                }
                mapper.diff_data_upd(show);
            }
         return SingleResult.buildSuccessWithoutData();
        } catch (Throwable t) {
            t.printStackTrace();
            throw new ServiceException(t.getMessage(),Code.ERROR.getStatus());
        }
    }
    @Override
    public JSONObject sel_database_info(){
        return mapper.sel_database_info();
    }

    @Override
    public SingleResult<DataResponse> data_upd(JSONObject requestVo) throws ServiceException {
        try {
            if (StringUnits.isNotNullOrEmpty(requestVo.get("type")) == false)
                return SingleResult.buildFailure(Code.ERROR,"请先选择对比操作");//再进行 增加、 删除、修改相应模块的修改

            JSONObject params=new JSONObject(){{
                put("name",requestVo.getString("name"));
                put("type",requestVo.getString("type"));

            }};

            List<JSONObject> add=new ArrayList<>();
            List<JSONObject> del=new ArrayList<>();
            List<JSONObject> diff=new ArrayList<>();
            List<JSONObject> list=mapper.upd_data_list_sel_db_combobox_right_list(params);
            List<JSONObject> list_show=mapper.sel_list_show();//展现数据库
            if (params.getString("type").equals("add")||ObjectUtils.isEmpty(params.get("type")))
            {
                add=list.stream().filter(j->(list_show.stream().filter(js->js.containsValue(j.getString("unipro_entry"))).collect(Collectors.toList()).size()==0)).collect(Collectors.toList());
            }else if (params.getString("type").equals("del"))
            {
                List<JSONObject> l=list.stream().collect(Collectors.toList());
                del=list_show.stream().filter(j->(l.stream().filter(js->js.containsValue(j.getString("unipro_entry"))).collect(Collectors.toList()).size()==0)).collect(Collectors.toList());
            }else if (params.getString("type").equals("diff")||ObjectUtils.isEmpty(params.get("type")))
            {
                //差异数据
                List<JSONObject> l=new ArrayList<>();
                for (JSONObject jsonObject : list)
                {
                    JSONObject result=mapper.upd_data_list_details(jsonObject);
                    result.put("general_information",mapper.base_general_information(result.getString("id")));
                    result.put("protein_sequence",mapper.base_protein_sequence(result.getString("id")));
                    result.put("protein_function",mapper.base_protein_function(result.getString("id")));
                    result.put("expression_and_location",mapper.base_expression_and_location(result.getString("id")));
                    result.put("protein_structure",mapper.base_protein_structure(result.getString("id")));
                    result.put("family_and_domain",mapper.base_family_and_domain(result.getString("id")));
                    result.put("protein_interaction",mapper.base_protein_interaction(result.getString("id")));
                    result.put("mutation_and_disease",mapper.base_mutation_and_disease(result.getString("id")));
                    result.put("post_translational_modification",mapper.base_post_translational_modification(result.getString("id")));
                    JSONObject result_show=mapper.upd_data_list_details_show(result);
                    result_show.put("general_information",mapper.base_general_information_show(result_show.getString("id")));
                    result_show.put("protein_sequence",mapper.base_protein_sequence_show(result_show.getString("id")));
                    result_show.put("protein_function",mapper.base_protein_function_show(result_show.getString("id")));
                    result_show.put("expression_and_location",mapper.base_expression_and_location_show(result_show.getString("id")));
                    result_show.put("protein_structure",mapper.base_protein_structure_show(result_show.getString("id")));
                    result_show.put("family_and_domain",mapper.base_family_and_domain_show(result_show.getString("id")));
                    result_show.put("protein_interaction",mapper.base_protein_interaction_show(result_show.getString("id")));
                    result_show.put("mutation_and_disease",mapper.base_mutation_and_disease_show(result_show.getString("id")));
                    result_show.put("post_translational_modification",mapper.base_post_translational_modification_show(result_show.getString("id")));
                    if (compare_diff(result,result_show))
                        l.add(jsonObject);
                }
                diff=l;
            }
            if (requestVo.getString("type").equals("add"))
            {
                for (JSONObject jsonObject : add)
                {
                    mapper.add_show_from_current(jsonObject);
                }
            }else if(requestVo.getString("type").equals("del"))
            {
                String ids="0";
                for (JSONObject jsonObject : del)
                {
                    ids+=","+jsonObject.getString("id");
                }
               JSONObject j= new JSONObject();
                j.put("ids",ids);
                mapper.del_data_list(j);
            }else if(requestVo.getString("type").equals("diff")){
                String ids="0";
                for (JSONObject jsonObject : diff) {
                    for (JSONObject object : list_show) {
                        if (jsonObject.getString("name").equals(object.getString("name"))){
                            jsonObject.put("value",object.getString("value"));
                            ids+=","+object.getString("id");
                            break;
                        }
                    }
                }
                mapper.del_list_show(ids);
                for (JSONObject jsonObject : diff) {
                    mapper.add_show_from_current(jsonObject);
                }
            }else{
                for (JSONObject jsonObject : add) {
                    mapper.add_show_from_current(jsonObject);
                }
                String ids="0";
                for (JSONObject jsonObject : del) {
                    ids+=","+jsonObject.getString("id");
                }
                JSONObject j= new JSONObject();
                j.put("ids",ids);
                mapper.del_data_list(j);
                 ids="0";
                for (JSONObject jsonObject : diff) {
                    for (JSONObject object : list_show) {
                        if (jsonObject.getString("name").equals(object.getString("name"))){
                            jsonObject.put("value",object.getString("value"));
                            ids+=","+object.getString("id");
                            break;
                        }
                    }
                }
                mapper.del_list_show(ids);
                for (JSONObject jsonObject : diff) {
                    mapper.add_show_from_current(jsonObject);
                }
            }

            mapper.set_his_s_name(params);
         return SingleResult.buildSuccessWithoutData();
        } catch (Throwable t) {
            t.printStackTrace();
            throw new ServiceException(t.getMessage(),Code.ERROR.getStatus());
        }
    }
    @Override
    public SingleResult<DataResponse> diff_data(JSONObject requestVo) throws ServiceException {
        try {
            JSONObject params=new JSONObject(){{
                put("id",requestVo.get("id"));
                put("type",requestVo.get("type"));
            }};
            JSONObject result=mapper.upd_data_list_details(requestVo);
            result.put("as_know",mapper.sel_as_know(result.getString("id")));
            result.put("general_information",mapper.base_general_information(result.getString("id")));
            result.put("protein_sequence",mapper.base_protein_sequence(result.getString("id")));
            result.put("protein_function",mapper.base_protein_function(result.getString("id")));
            result.put("expression_and_location",mapper.base_expression_and_location(result.getString("id")));
            result.put("protein_structure",mapper.base_protein_structure(result.getString("id")));
            result.put("family_and_domain",mapper.base_family_and_domain(result.getString("id")));
            result.put("protein_interaction",mapper.base_protein_interaction(result.getString("id")));
            result.put("mutation_and_disease",mapper.base_mutation_and_disease(result.getString("id")));
            result.put("post_translational_modification",mapper.base_post_translational_modification(result.getString("id")));

            JSONObject result_show=mapper.upd_data_list_details_show(result);
            result_show.put("as_know",mapper.sel_as_know_show(result_show.getString("id")));
            result_show.put("general_information",mapper.base_general_information_show(result_show.getString("id")));
            result_show.put("protein_sequence",mapper.base_protein_sequence_show(result_show.getString("id")));
            result_show.put("protein_function",mapper.base_protein_function_show(result_show.getString("id")));
            result_show.put("expression_and_location",mapper.base_expression_and_location_show(result_show.getString("id")));
            result_show.put("protein_structure",mapper.base_protein_structure_show(result_show.getString("id")));
            result_show.put("family_and_domain",mapper.base_family_and_domain_show(result_show.getString("id")));
            result_show.put("protein_interaction",mapper.base_protein_interaction_show(result_show.getString("id")));
            result_show.put("mutation_and_disease",mapper.base_mutation_and_disease_show(result_show.getString("id")));
            result_show.put("post_translational_modification",mapper.base_post_translational_modification_show(result_show.getString("id")));

            List<JSONObject> list=new ArrayList<>();
            if (!ObjectUtils.isEmpty(result.get("expression_and_location")))
            for (String s : result.getJSONObject("expression_and_location").keySet()) {
                if (s.equals("id")||s.equals("p_id")||s.equals("create_time")||s.equals("modify_time")
                        ||s.equals("operator")||s.equals("c_s_date"))
                    continue;
                JSONObject j=new JSONObject();
                j.put("type","expression_and_location");
                j.put("name",s);
                j.put("value",result.getJSONObject("expression_and_location").get(s));
                list.add(j);
            }
            if (!ObjectUtils.isEmpty(result.get("family_and_domain")))
            for (String s : result.getJSONObject("family_and_domain").keySet()) {
                if (s.equals("id")||s.equals("p_id")||s.equals("create_time")||s.equals("modify_time")
                        ||s.equals("operator")||s.equals("c_s_date"))
                    continue;
                JSONObject j=new JSONObject();
                j.put("type","family_and_domain");
                j.put("name",s);
                j.put("value",result.getJSONObject("family_and_domain").get(s));
                list.add(j);
            }
            if (!ObjectUtils.isEmpty(result.get("general_information")))
            for (String s : result.getJSONObject("general_information").keySet()) {
                if (s.equals("id")||s.equals("p_id")||s.equals("create_time")||s.equals("modify_time")
                        ||s.equals("operator")||s.equals("c_s_date"))
                    continue;
                JSONObject j=new JSONObject();
                j.put("type","general_information");
                j.put("name",s);
                j.put("value",result.getJSONObject("general_information").get(s));
                list.add(j);
            }
            if (!ObjectUtils.isEmpty(result.get("mutation_and_disease")))
            for (String s : result.getJSONObject("mutation_and_disease").keySet()) {
                if (s.equals("id")||s.equals("p_id")||s.equals("create_time")||s.equals("modify_time")
                        ||s.equals("operator")||s.equals("c_s_date"))
                    continue;
                JSONObject j=new JSONObject();
                j.put("type","mutation_and_disease");
                j.put("name",s);
                j.put("value",result.getJSONObject("mutation_and_disease").get(s));
                list.add(j);
            }
            if (!ObjectUtils.isEmpty(result.get("post_translational_modification")))
            for (String s : result.getJSONObject("post_translational_modification").keySet()) {
                if (s.equals("id")||s.equals("p_id")||s.equals("create_time")||s.equals("modify_time")
                        ||s.equals("operator")||s.equals("c_s_date"))
                    continue;
                JSONObject j=new JSONObject();
                j.put("type","post_translational_modification");
                j.put("name",s);
                j.put("value",result.getJSONObject("post_translational_modification").get(s));
                list.add(j);
            }
            if (!ObjectUtils.isEmpty(result.get("protein_function")))
            for (String s : result.getJSONObject("protein_function").keySet()) {
                if (s.equals("id")||s.equals("p_id")||s.equals("create_time")||s.equals("modify_time")
                        ||s.equals("operator")||s.equals("c_s_date"))
                    continue;
                JSONObject j=new JSONObject();
                j.put("type","protein_function");
                j.put("name",s);
                j.put("value",result.getJSONObject("protein_function").get(s));
                list.add(j);
            }
            if (!ObjectUtils.isEmpty(result.get("protein_interaction")))
            for (String s : result.getJSONObject("protein_interaction").keySet()) {
                if (s.equals("id")||s.equals("p_id")||s.equals("create_time")||s.equals("modify_time")
                        ||s.equals("operator")||s.equals("c_s_date"))
                    continue;
                JSONObject j=new JSONObject();
                j.put("type","protein_interaction");
                j.put("name",s);
                j.put("value",result.getJSONObject("protein_interaction").get(s));
                list.add(j);
            }
            if (!ObjectUtils.isEmpty(result.get("protein_sequence")))
            for (String s : result.getJSONObject("protein_sequence").keySet()) {
                if (s.equals("id")||s.equals("p_id")||s.equals("create_time")||s.equals("modify_time")
                        ||s.equals("operator")||s.equals("c_s_date"))
                    continue;
                JSONObject j=new JSONObject();
                j.put("type","protein_sequence");
                j.put("name",s);
                j.put("value",result.getJSONObject("protein_sequence").get(s));
                list.add(j);
            }
            if (!ObjectUtils.isEmpty(result.get("protein_structure")))
            for (String s : result.getJSONObject("protein_structure").keySet()) {
                if (s.equals("id")||s.equals("p_id")||s.equals("create_time")||s.equals("modify_time")
                        ||s.equals("operator")||s.equals("c_s_date"))
                    continue;
                JSONObject j=new JSONObject();
                j.put("type","protein_structure");
                j.put("name",s);
                j.put("value",result.getJSONObject("protein_structure").get(s));
                list.add(j);
            }

            List<JSONObject> list_show=new ArrayList<>();
            if (!ObjectUtils.isEmpty(result_show.get("expression_and_location")))
            for (String s : result_show.getJSONObject("expression_and_location").keySet()) {
                if (s.equals("id")||s.equals("p_id")||s.equals("create_time")||s.equals("modify_time")
                        ||s.equals("operator")||s.equals("c_s_date")||s.equals("db"))
                    continue;
                JSONObject j=new JSONObject();
                j.put("type","expression_and_location");
                j.put("name",s);
                j.put("id",result_show.getJSONObject("expression_and_location").get("id"));
                j.put("value",result_show.getJSONObject("expression_and_location").get(s));
                list_show.add(j);
            }
            if (!ObjectUtils.isEmpty(result_show.get("family_and_domain")))
            for (String s : result_show.getJSONObject("family_and_domain").keySet()) {
                if (s.equals("id")||s.equals("p_id")||s.equals("create_time")||s.equals("modify_time")
                        ||s.equals("operator")||s.equals("c_s_date")||s.equals("db"))
                    continue;
                JSONObject j=new JSONObject();
                j.put("type","family_and_domain");
                j.put("name",s);
                j.put("id",result_show.getJSONObject("family_and_domain").get("id"));
                j.put("value",result_show.getJSONObject("family_and_domain").get(s));
                list_show.add(j);
            }
            if (!ObjectUtils.isEmpty(result_show.get("general_information")))
            for (String s : result_show.getJSONObject("general_information").keySet()) {
                if (s.equals("id")||s.equals("p_id")||s.equals("create_time")||s.equals("modify_time")
                        ||s.equals("operator")||s.equals("c_s_date")||s.equals("db"))
                    continue;
                JSONObject j=new JSONObject();
                j.put("type","general_information");
                j.put("name",s);
                j.put("id",result_show.getJSONObject("general_information").get("id"));
                j.put("value",result_show.getJSONObject("general_information").get(s));
                list_show.add(j);
            }
            if (!ObjectUtils.isEmpty(result_show.get("mutation_and_disease")))
            for (String s : result_show.getJSONObject("mutation_and_disease").keySet()) {
                if (s.equals("id")||s.equals("p_id")||s.equals("create_time")||s.equals("modify_time")
                        ||s.equals("operator")||s.equals("c_s_date")||s.equals("db"))
                    continue;
                JSONObject j=new JSONObject();
                j.put("type","mutation_and_disease");
                j.put("name",s);
                j.put("id",result_show.getJSONObject("mutation_and_disease").get("id"));
                j.put("value",result_show.getJSONObject("mutation_and_disease").get(s));
                list_show.add(j);
            }
            if (!ObjectUtils.isEmpty(result_show.get("post_translational_modification")))
            for (String s : result_show.getJSONObject("post_translational_modification").keySet()) {
                if (s.equals("id")||s.equals("p_id")||s.equals("create_time")||s.equals("modify_time")
                        ||s.equals("operator")||s.equals("c_s_date")||s.equals("db"))
                    continue;
                JSONObject j=new JSONObject();
                j.put("type","post_translational_modification");
                j.put("name",s);
                j.put("id",result_show.getJSONObject("post_translational_modification").get("id"));
                j.put("value",result_show.getJSONObject("post_translational_modification").get(s));
                list_show.add(j);
            }
            if (!ObjectUtils.isEmpty(result_show.get("protein_function")))
            for (String s : result_show.getJSONObject("protein_function").keySet()) {
                if (s.equals("id")||s.equals("p_id")||s.equals("create_time")||s.equals("modify_time")
                        ||s.equals("operator")||s.equals("c_s_date")||s.equals("db"))
                    continue;
                JSONObject j=new JSONObject();
                j.put("type","protein_function");
                j.put("name",s);
                j.put("id",result_show.getJSONObject("protein_function").get("id"));
                j.put("value",result_show.getJSONObject("protein_function").get(s));
                list_show.add(j);
            }
            if (!ObjectUtils.isEmpty(result_show.get("protein_interaction")))
            for (String s : result_show.getJSONObject("protein_interaction").keySet()) {
                if (s.equals("id")||s.equals("p_id")||s.equals("create_time")||s.equals("modify_time")
                        ||s.equals("operator")||s.equals("c_s_date")||s.equals("db"))
                    continue;
                JSONObject j=new JSONObject();
                j.put("type","protein_interaction");
                j.put("name",s);
                j.put("id",result_show.getJSONObject("protein_interaction").get("id"));
                j.put("value",result_show.getJSONObject("protein_interaction").get(s));
                list_show.add(j);
            }
            if (!ObjectUtils.isEmpty(result_show.get("protein_sequence")))
            for (String s : result_show.getJSONObject("protein_sequence").keySet()) {
                if (s.equals("id")||s.equals("p_id")||s.equals("create_time")||s.equals("modify_time")
                        ||s.equals("operator")||s.equals("c_s_date")||s.equals("db"))
                    continue;
                JSONObject j=new JSONObject();
                j.put("type","protein_sequence");
                j.put("name",s);
                j.put("id",result_show.getJSONObject("protein_sequence").get("id"));
                j.put("value",result_show.getJSONObject("protein_sequence").get(s));
                list_show.add(j);
            }
            if (!ObjectUtils.isEmpty(result_show.get("protein_structure")))
            for (String s : result_show.getJSONObject("protein_structure").keySet()) {
                if (s.equals("id")||s.equals("p_id")||s.equals("create_time")||s.equals("modify_time")
                        ||s.equals("operator")||s.equals("c_s_date")||s.equals("db"))
                    continue;
                JSONObject j=new JSONObject();
                j.put("type","protein_structure");
                j.put("name",s);
                j.put("id",result_show.getJSONObject("protein_structure").get("id"));
                j.put("value",result_show.getJSONObject("protein_structure").get(s));
                list_show.add(j);
            }
            for (JSONObject q1 : list) {
                for (JSONObject q2 : list_show) {
                    if (ObjectUtils.getDisplayString(q1.get("name")).equals(ObjectUtils.getDisplayString(q2.get("name")))){
                        if (ObjectUtils.getDisplayString(q1.get("value")).equals("")&&!ObjectUtils.getDisplayString(q2.get("value")).equals("")){
                            //记录表为空展示表有数据表示要删除展示表的数据，展示表标记删除
                            q2.put("op_type","del");
                            q1.put("op_type","del");
                            break;
                        }
                        else if (!ObjectUtils.getDisplayString(q1.get("value")).equals("")&&ObjectUtils.getDisplayString(q2.get("value")).equals("")){
                            //记录表有值展示表为空表示要新增展示表的数据，展示表标记新增
                            q2.put("op_type","add");
                            q1.put("op_type","add");
                            break;
                        }
                        else if (ObjectUtils.getDisplayString(q1.get("value")).equals(ObjectUtils.getDisplayString(q2.get("value")))){
                            //数据相同则不做处理，标记等于
                            q2.put("op_type","equ");
                            q1.put("op_type","equ");
                            break;
                        }else if (!ObjectUtils.getDisplayString(q1.get("value")).equals(ObjectUtils.getDisplayString(q2.get("value")))){
                            //剩下的表示不同，需要更新
                            q2.put("op_type","upd");
                            q1.put("op_type","upd");
                            break;
                        }
                    }
                }
            }
            if (params.getString("type").equals("add")){
                list=list.stream().filter(lp->lp.getString("op_type").equals("add")).collect(Collectors.toList());
                list_show=list_show.stream().filter(lp->lp.getString("op_type").equals("add")).collect(Collectors.toList());
            }else if (params.getString("type").equals("upd")){
                list=list.stream().filter(lp->lp.getString("op_type").equals("upd")).collect(Collectors.toList());
                list_show=list_show.stream().filter(lp->lp.getString("op_type").equals("upd")).collect(Collectors.toList());
            }else if (params.getString("type").equals("del")){
                list=list.stream().filter(lp->lp.getString("op_type").equals("del")).collect(Collectors.toList());
                list_show=list_show.stream().filter(lp->lp.getString("op_type").equals("del")).collect(Collectors.toList());
            }
            result.put("list",list);
            result_show.put("list",list_show);
//            List<JSONObject> list1=new ArrayList<>();
//            Map<String, List<JSONObject>> m=list.stream().collect(Collectors.groupingBy(j->j.getString("type")));
//            for (String s : m.keySet()) {
//
//            }
            JSONObject rj=new JSONObject();
            rj.put("show",result_show);//show表当前展示的详情
            rj.put("current",result);//当前选中记录的详情
            rj.put("type",params.getString("type"));
            return SingleResult.buildSuccess(rj);
        } catch (Throwable t) {
            t.printStackTrace();
            throw new ServiceException(t.getMessage(),Code.ERROR.getStatus());
        }
    }


    @Override
    public SingleResult<DataResponse> sel_protein_type_list(JSONObject requestVo) throws ServiceException {
        try {
            Map<String,Object> params=new HashMap(){{
                put("name",requestVo.get("name"));
            }};
            List<Map<String,Object>> list=mapper.sel_protein_type_list(params);
            for (Map<String, Object> stringObjectMap : list)
            {
                stringObjectMap.put("list",mapper.sel_protein_type_list_detail(stringObjectMap));
            }
            int total=list.size();
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
    public SingleResult<DataResponse> sel_protein_type_list_option(JSONObject requestVo) throws ServiceException {
        try {
            Map<String,Object> params=new HashMap(){{
                put("name",requestVo.get("name"));
            }};
            List<Map<String,Object>> list=mapper.sel_protein_type_list_option(params);
            int total=list.size();
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
    public SingleResult<DataResponse> del_protein_type_list(JSONObject requestVo) throws ServiceException {
        try {
            Map<String,Object> params=new HashMap(){{
                put("ids",requestVo.get("ids"));
            }};
            mapper.del_protein_type_list(params);

            return SingleResult.buildSuccessWithoutData();
        } catch (Throwable t) {
            t.printStackTrace();
            throw new ServiceException(t.getMessage(),Code.ERROR.getStatus());
        }
    }

    @Override
    public SingleResult<DataResponse> edit_protein_type_list(JSONObject requestVo) throws ServiceException {
        try {
            Map<String,Object> params=new HashMap(){{

                put("name",requestVo.get("name"));
                put("desc",requestVo.get("desc"));
            }};
            if (ObjectUtils.isEmpty(requestVo.get("id"))){
                mapper.add_protein_type_list(params);
            }else{
                params.put("id",requestVo.get("id"));

                mapper.update_protein_type_list(params);
                mapper.del_protein_type_list_uniprot(params);
            }

            if (requestVo.get("list")!=null && requestVo.getJSONArray("list").size()>0)
            {
                params.put("list",requestVo.get("list"));
                mapper.batch_insert_protin_type_unipro_list(params);
            }

            return SingleResult.buildSuccessWithoutData();
        } catch (Throwable t) {
            t.printStackTrace();
            throw new ServiceException(t.getMessage(),Code.ERROR.getStatus());
        }
    }

    public static String formatNumber(String input) {
        // 使用正则表达式分离数值和单位
        Pattern pattern = Pattern.compile("([-+]?[0-9]*\\.?[0-9]+)([a-zA-Z]*)");
        Matcher matcher = pattern.matcher(input);

        if (matcher.matches()) {
            String numberPart = matcher.group(1); // 提取数值部分
            String unitPart = matcher.group(2);   // 提取单位部分

            // 将数值部分转换为 BigDecimal 并保留两位小数
            BigDecimal number = new BigDecimal(numberPart).setScale(2, BigDecimal.ROUND_HALF_UP);

            // 返回格式化后的字符串
            return number.toPlainString() + unitPart;
        }

        // 如果输入格式不匹配，返回原始字符串
        return input;
    }
}
