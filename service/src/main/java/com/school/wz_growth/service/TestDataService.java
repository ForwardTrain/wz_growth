package com.school.wz_growth.service;


import com.alibaba.fastjson.JSONObject;

import java.util.List;

public interface TestDataService {

    void add_base_drgf(JSONObject requestVo) throws Exception;
    void add_lptm(List<JSONObject> list) throws Exception;
    void add_base_general_infomation(JSONObject requestVo)  throws Exception;
    void add_DRGF_code(JSONObject requestVo)  throws Exception;
    void add_base_protein_sequence(JSONObject requestVo)  throws Exception;
    void add_base_protein_function(JSONObject requestVo) throws Exception;
    void add_base_expression_and_location(JSONObject requestVo) throws Exception;
    void add_base_protein_structure(JSONObject requestVo)  throws Exception;
    void add_base_family_and_domain(JSONObject requestVo)  throws Exception;
    void add_base_protein_interaction(JSONObject requestVo)  throws Exception;
    void add_base_mutation_and_disease(JSONObject requestVo)  throws Exception;
    void add_base_post_translational_modification(JSONObject requestVo)  throws Exception;
    JSONObject sel_c_s_date_and_batch() throws Exception;
    List<JSONObject> sel_ex_list(JSONObject requestVo) throws Exception;
    void add_his(JSONObject vo) throws Exception;



    List<JSONObject> sel_excel_data_list() throws Exception;


    void update_base_protein_sequence(JSONObject requestVo)  throws Exception;
    void update_base_protein_fuction(JSONObject requestVo)  throws Exception;

}
