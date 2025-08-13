package com.school.wz_growth.dao;

import java.util.List;
import java.util.Map;

public interface LiteraturesMapper {

    List<Map<String,Object>> sel_clinicaltrials_list(Map<String,Object> params);
    long sel_clinicaltrials_list_count(Map<String,Object> params);
    void del_clinicaltrials_list(Map<String,Object> params);

    List<Map<String,Object>> sel_GM_list(Map<String,Object> params);
    long sel_GM_list_count(Map<String,Object> params);
    List<Map<String,Object>> sel_GM_brief_list(Map<String,Object> params);
    void del_GM_list(Map<String,Object> params);



    List<Map<String,Object>> sel_TL_list(Map<String,Object> params);
    long sel_TL_list_count(Map<String,Object> params);
    void del_TL_list(Map<String,Object> params);


    List<Map<String,Object>> sel_TL_list_source(Map<String,Object> params);
    List<String> sel_TL_list_source_ids(Map<String,Object> params);
    int sel_TL_list_source_count();

    void insert_TL_main(Map<String,Object> params);
    void insert_TL_main_detail(Map<String,Object> params);
    void update_old_data_status(Map<String,Object> params);
}
