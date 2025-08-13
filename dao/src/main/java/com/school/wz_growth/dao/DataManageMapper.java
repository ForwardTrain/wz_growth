package com.school.wz_growth.dao;



import com.alibaba.fastjson.JSONObject;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;


public interface DataManageMapper {


    List<JSONObject> data_list(JSONObject vo);
    int data_list_total(JSONObject vo);
    void del_data_list(JSONObject vo);
    void add_show_from_current(JSONObject vo);
    void set_his_s_name(JSONObject vo);

    JSONObject sel_database_info();

    List<JSONObject> upd_data_list(JSONObject vo);
    int upd_data_list_total(JSONObject vo);

    JSONObject upd_data_list_details(JSONObject vo);

    List<JSONObject> sel_as_know(@Param("id") String id);
    Map<String ,Object> base_expression_and_location(@Param("id") String id);
    Map<String ,Object> base_family_and_domain(@Param("id") String id);
    Map<String ,Object> base_general_information(@Param("id") String id);
    Map<String ,Object> base_mutation_and_disease(@Param("id") String id);
    List<Map<String ,Object>> base_ptm_details(@Param("id") String id);
    List<Map<String ,Object>> base_ptm_details_show(@Param("id") String id);
    Map<String ,Object> base_post_translational_modification(@Param("id") String id);
    Map<String ,Object> base_protein_function(@Param("id") String id);
    Map<String ,Object> base_protein_interaction(@Param("id") String id);
    Map<String ,Object> base_protein_sequence(@Param("id") String id);
    Map<String ,Object> base_protein_structure(@Param("id") String id);
    Map<String ,Object> base_drgf(@Param("id") String id);
    Map<String ,Object> base_drgf_show(@Param("id") String id);

    JSONObject upd_data_list_details_show(JSONObject vo);
    JSONObject sel_as_know_show(@Param("id") String id);
    Map<String ,Object> base_expression_and_location_show(@Param("id") String id);

    Map<String ,Object> base_family_and_domain_show(@Param("id") String id);
    Map<String ,Object> base_general_information_show(@Param("id") String id);
    Map<String ,Object> base_mutation_and_disease_show(@Param("id") String id);
    Map<String ,Object> base_post_translational_modification_show(@Param("id") String id);
    Map<String ,Object> base_protein_function_show(@Param("id") String id);
    Map<String ,Object> base_protein_interaction_show(@Param("id") String id);
    Map<String ,Object> base_protein_sequence_show(@Param("id") String id);
    Map<String ,Object> base_protein_structure_show(@Param("id") String id);




    JSONObject upd_data_list_sel(JSONObject vo);
    List<JSONObject> upd_data_list_sel_db_combobox();


    void upd_p_name(JSONObject vo);
    JSONObject sel_p_id(JSONObject vo);
    void del_as_know(JSONObject vo);
    void add_a_k_s(List<JSONObject> list);
    void add_a_k(JSONObject vo);
    void del_a_k(JSONObject vo);

    void diff_data_upd(List<JSONObject> list);
    void del_list_show(@Param("ids") String ids);


    List<JSONObject> upd_data_list_sel_db_combobox_right_list(JSONObject vo);
    List<JSONObject> sel_list_show();
    int upd_data_list_sel_db_combobox_right_list_total(JSONObject vo);


    List<Map<String,Object>> sel_protein_type_list(Map<String,Object> params);
    List<Map<String,Object>> sel_protein_type_list_detail(Map<String,Object> params);
    List<Map<String,Object>> sel_protein_type_list_option(Map<String,Object> params);
    void del_protein_type_list(Map<String,Object> vo);



    void add_protein_type_list(Map<String,Object> vo);
    void update_protein_type_list(Map<String,Object> vo);
    void del_protein_type_list_uniprot(Map<String,Object> vo);
    void batch_insert_protin_type_unipro_list(Map<String,Object> list);



    Map<String ,Object> base_general_information_show2(@Param("id") String id);
    List<Map<String ,Object>> base_general_information_familys_show(Map<String, Object> params);
    Map<String ,Object> base_protein_sequence_show2(@Param("id") String id);
    Map<String ,Object> base_protein_function2(@Param("id") String id);
    Map<String ,Object> base_expression_and_location_show2(Map<String, Object> params);
    Map<String ,Object> base_protein_structure_show2(@Param("id") String id);
    Map<String ,Object> base_family_and_domain_show2(@Param("id") String id);
    Map<String ,Object> base_protein_interaction_show2(@Param("id") String id);
    Map<String ,Object> base_drgf_show2(@Param("id") String id);
    List<Map<String ,Object>> base_ptm_details_show2(@Param("id") String id);
    Map<String ,Object> base_mutation_and_disease_show2(@Param("id") String id);


//    void update_sys_version_status(@Param("name") String name);
}
