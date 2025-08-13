package com.school.wz_growth.dao;

import java.util.Map;

public interface HomeBasicMapper {


    Map<String, Object> sel_access_ip(Map<String,Object> params);

    void insert_access_ip(Map<String,Object> params);
    void insert_access_ip_history(Map<String,Object> params);

     void insert_view_recode(Map<String,Object> params);
    void insert_biz_ip(Map<String,Object> params);

    Map<String, Object> sel_home_info(Map<String,Object> params);
    Map<String, Object> sel_contact_us_info(Map<String,Object> params);

    Map<String, Object> sel_db_size();


}
