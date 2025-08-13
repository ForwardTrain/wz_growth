package com.school.wz_growth.dao;

import java.util.List;
import java.util.Map;

public interface BizCountMapper {


    Map<String,Object> sel_count_data(Map<String,Object> params);

    List<Map<String,Object>> sel_area_word_count_data(Map<String,Object> params);
    List<Map<String,Object>> sel_area_china_count_data(Map<String,Object> params);
    List<Map<String,Object>> sel_browser_count_data(Map<String,Object> params);
    List<Map<String,Object>> sel_browser_page_count_data(Map<String,Object> params);
    List<Map<String,Object>> sel_db_version_data(Map<String,Object> params);


    List<Map<String,Object>> sel_biz_ip_list(Map<String,Object> params);
    List<Map<String,Object>> sel_biz_ip_list_history(Map<String,Object> params);
    void upd_customer_status(Map<String,Object> params);

}
