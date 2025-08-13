package com.school.wz_growth.dao;
import com.alibaba.fastjson.JSONObject;

import java.util.List;
import java.util.Map;


public interface BannerMapper {

    List<JSONObject> banner_list(JSONObject vo);

    int banner_list_count(JSONObject vo);

    int banner_open_close(JSONObject vo);

    int banner_del(JSONObject vo);
    int del_banner(JSONObject vo);

    int banner_add(JSONObject vo);

    int banner_add_details(List<JSONObject> list);

    JSONObject banner_sel_detail(JSONObject vo);
    List<JSONObject> banner_sel_detail_d(JSONObject vo);
    int sel_banner_time(JSONObject vo);

    int banner_upd(JSONObject vo);


    List<Map<String,Object>> sel_home_protein_num_count();

    List<Map<String,Object>> sel_home_family_count();
    String sel_home_family_count_bg(Map<String,Object> params);
}
