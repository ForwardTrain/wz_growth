package com.school.wz_growth.model.front.request.sys;


import com.school.wz_growth.model.front.request.base.BaseRequestVo;

public class MenuRegistorDeleteRequestVo extends BaseRequestVo {
    private static final long serialVersionUID = 6620639994482000193L;


    private String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
