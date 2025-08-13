package com.school.wz_growth.model.domain;

import com.alibaba.fastjson.JSONArray;

import java.io.Serializable;

public class CSDateModel implements Serializable {

    private static final long serialVersionUID = 7731179216448142050L;
    private String c_s_date;
    private JSONArray details;


    public String getC_s_date() {
        return c_s_date;
    }

    public void setC_s_date(String c_s_date) {
        this.c_s_date = c_s_date;
    }

    public JSONArray getDetails() {
        return details;
    }

    public void setDetails(String details) {
        if(null == details){
            this.details=new JSONArray();
        }
        else{
            this.details=JSONArray.parseArray(details);
        }
    }
}
