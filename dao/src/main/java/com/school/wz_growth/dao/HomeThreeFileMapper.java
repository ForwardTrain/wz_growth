package com.school.wz_growth.dao;

import java.util.List;
import java.util.Map;

public interface HomeThreeFileMapper {

    List<Map<String, Object>> sel_GM_list(Map<String, Object> params);
    int sel_GM_list_count(Map<String, Object> params);
    List<Map<String, Object>> sel_GM_brief_list(Map<String, Object> params);

    List<Map<String, Object>> sel_CT_list(Map<String, Object> params);

    List<Map<String, Object>> sel_CT_list_export(Map<String, Object> params);
    int sel_CT_list_count(Map<String, Object> params);
    Map<String, Object> sel_CT_list_detail(Map<String, Object> params);

    List<Map<String, Object>> sel_TL_list(Map<String, Object> params);

    List<Map<String, Object>> sel_TL_list_export(Map<String, Object> params);
    int sel_TL_list_count(Map<String, Object> params);


    List<String> sel_CT_disease_types();
    List<String> sel_CT_other_disease();

}
