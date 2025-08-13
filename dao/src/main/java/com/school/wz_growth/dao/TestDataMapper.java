package com.school.wz_growth.dao;



import com.alibaba.fastjson.JSONObject;

import java.util.List;


public interface TestDataMapper {


     void add_base_drgf(JSONObject vo);
     void add_lptm(List<JSONObject> list);
     void add_base_general_infomation(JSONObject vo);
     String select_DRGF_code_max_code();
     void add_DRGF_code(JSONObject vo);
     void add_base_protein_sequence(JSONObject vo);
     void add_base_protein_function(JSONObject vo);
     void add_base_expression_and_location(JSONObject vo);
     void add_base_protein_structure(JSONObject vo);
     void add_base_family_and_domain(JSONObject vo);
     void add_base_protein_interaction(JSONObject vo);
     void add_base_mutation_and_disease(JSONObject vo);
     void add_base_post_translational_modification(JSONObject vo);
     JSONObject sel_c_s_date_and_batch(JSONObject vo);
     List<JSONObject> sel_ex_list(JSONObject requestVo);
     void add_his(JSONObject vo);

     List<JSONObject> sel_excel_data_list();

     void update_base_protein_sequence(JSONObject requestVo);
     void update_base_protein_fuction(JSONObject requestVo);

}
