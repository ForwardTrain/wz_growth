package com.school.wz_growth.dao;

import com.alibaba.fastjson.JSONObject;
import com.school.wz_growth.model.domain.po.BaseGeneralInfomationShowPo;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 基因一般信息展示
 *
 * @author ${author}
 * @author ${email}
 * @date 2025-07-08 17:08:16
 */
public interface BaseGeneralInfomationShowMapper {

//    List<JSONObject> sel_gen_list(JSONObject vo);
//
//    int sel_gen_list_total(JSONObject vo);

    void add_sys_protein_type(JSONObject vo);
    void add_sys_protein_type_unipro(JSONObject vo);
    void upd_main_table(JSONObject vo);
    void del_main_table(JSONObject vo);
    String getCode();
    List<Map<String ,Object>> sel_protein_type_list(Map<String, Object> params);



    Map<String ,Object> base_general_information_show2(@Param("id") String id);
    Map<String ,Object> base_general_information_familys_show(Map<String, Object> params);
    Map<String ,Object> base_protein_sequence_show2(@Param("id") String id);
    Map<String ,Object> base_protein_function2(@Param("id") String id);
    Map<String ,Object> base_expression_and_location_show2(Map<String, Object> params);
    Map<String ,Object> base_protein_structure_show2(@Param("id") String id);
    Map<String ,Object> base_family_and_domain_show2(@Param("id") String id);
    Map<String ,Object> base_protein_interaction_show2(@Param("id") String id);
    Map<String ,Object> base_drgf_show2(@Param("id") String id);
    List<Map<String ,Object>> base_ptm_details_show2(@Param("id") String id);
    Map<String ,Object> base_mutation_and_disease_show2(@Param("id") String id);


    void insert_base_general_infomation_show(JSONObject obj);
    void insert_base_expression_and_location_show(JSONObject obj);
    void insert_base_expression_and_location_show_hpa(JSONObject obj);
    void insert_base_family_and_domain_show(JSONObject obj);
    void insert_base_mutation_and_disease_show(JSONObject obj);
    void insert_base_ptm_details_show(JSONObject obj);

    void insert_base_protein_function_show(JSONObject obj);
    void insert_base_protein_interaction_show(JSONObject obj);
    void insert_base_protein_sequence_show(JSONObject obj);
    void insert_base_protein_structure_show(JSONObject obj);
    void insert_base_drgf_show(JSONObject obj);


    void update_base_general_infomation_show(JSONObject obj);
    void update_base_expression_and_location_show(JSONObject obj);
    void update_base_expression_and_location_show_hpa(JSONObject obj);
    void update_base_family_and_domain_show(JSONObject obj);
    void update_base_mutation_and_disease_show(JSONObject obj);
    void update_base_ptm_details_show(JSONObject obj);
    void delete_base_ptm_details_show(JSONObject obj);

    void update_base_protein_function_show(JSONObject obj);
    void update_base_protein_interaction_show(JSONObject obj);
    void update_base_protein_sequence_show(JSONObject obj);
    void update_base_protein_structure_show(JSONObject obj);
    void update_base_drgf_show(JSONObject obj);


    int select_uniprot_code_exist(JSONObject obj);
    int select_uniprot_type_exist(JSONObject obj);
    void insert_uniprot_type_info(JSONObject obj);
}