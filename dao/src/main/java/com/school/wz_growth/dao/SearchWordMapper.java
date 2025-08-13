package com.school.wz_growth.dao;



import com.alibaba.fastjson.JSONObject;
import org.apache.ibatis.annotations.Param;

import java.util.List;


public interface SearchWordMapper {


    List<JSONObject> sel_list(JSONObject vo);
    int sel_list_total(JSONObject vo);
    void del_list(JSONObject vo);

    int sel_word_by_name(JSONObject vo);
    int sel_word_by_name_and_id(JSONObject vo);
    void add_list(JSONObject vo);
    void upd_list(JSONObject vo);
    void upd_num(JSONObject vo);
    void add_new_word(JSONObject vo);
    List<JSONObject> search_set_list(JSONObject vo);
    List<JSONObject> search_type_name_list(JSONObject vo);
    void upd_search_set_list(JSONObject vo);

}
