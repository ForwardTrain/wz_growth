package com.school.wz_growth.dao;


import com.alibaba.fastjson.JSONObject;

import java.util.List;
import java.util.Map;


public interface ContentMapper {


    List<JSONObject> sel_list(JSONObject vo);
    int sel_list_total(JSONObject vo);
    void del_list(JSONObject vo);

JSONObject sel_list_details(JSONObject vo);
List<JSONObject> sel_file_list(JSONObject vo);
void del_file(JSONObject vo);
void add_file(List<JSONObject> list);
    void add_list(JSONObject vo);
    void upd_list(Map<String,Object> vo);


    List<JSONObject> sel_support_list(JSONObject vo);
    int sel_support_list_total(JSONObject vo);

    JSONObject sel_support_list_support(JSONObject vo);
    JSONObject sel_support_list_report(JSONObject vo);

    void del_support_list(JSONObject vo);
    void add_upd_support(JSONObject vo);
    void add_report(JSONObject vo);
    void upd_report(JSONObject vo);
    void add_example(JSONObject vo);
    void upd_example(JSONObject vo);
    void del_example(JSONObject vo);
    List<JSONObject> sel_example(JSONObject vo);
    int sel_example_total(JSONObject vo);


    List<JSONObject> sel_hot_list(JSONObject vo);
    int sel_hot_list_total(JSONObject vo);

    List<JSONObject> sel_item_list(JSONObject vo);
    List<JSONObject> sel_item_children(JSONObject vo);
    List<JSONObject> clinical_research(JSONObject vo);
    int clinical_research_total(JSONObject vo);

    void upd_hot_list(JSONObject vo);


    List<JSONObject> search_combobox(Map<String, Object> params);
    List<JSONObject> search_combobox_children(JSONObject vo);


    List<Map<String,Object>> search_combobox_type_num_count(Map<String, Object> vo);


    List<Map<String,Object>> search_result_list(Map<String,Object> params);

    List<Map<String, Object>> search_result_list_byId(Map<String, Object> params);

    Map<String,Object> select_family_info(String id);
    int search_result_list_total(Map<String,Object> params);
    void update_search_combobox_num(Map<String,Object> params);
//    int search_search_combobox_list_total(Map<String,Object> params);


    List<JSONObject> left_search_result_list(List<JSONObject> list);
    List<Map<String,Object>> sel_key_words();
    List<String> sel_key_words_example();
    List<JSONObject> sel_lp();
    Map<String,Object> sel_databases_brief();

    void add_down_first(JSONObject vo);
    void add_down_second(List<JSONObject> list);

    List<Map<String,Object>> sys_cooperate_manage_list(Map<String, Object> params) ;

    List<JSONObject> select_protein_type();

    void update_protein_type_desc(Map<String, Object> params);

    List<JSONObject> query_example_data(JSONObject requestVo);

    void add_example_data(JSONObject vo);

    List<JSONObject> query_protein_data(JSONObject requestVo);

    List<JSONObject> query_search_example_data();

    void del_search_example_data(JSONObject requestVo);
}
