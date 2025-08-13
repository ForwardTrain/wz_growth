package com.school.wz_growth.dao;



import com.alibaba.fastjson.JSONObject;

import java.util.List;


public interface ItemMapper {


    List<JSONObject> sel_list(JSONObject vo);
    int sel_list_total(JSONObject vo);
    void del_list(JSONObject vo);

    List<JSONObject> p_item_list();


    void add_list(JSONObject vo);
    void upd_list(JSONObject vo);


}
