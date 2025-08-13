package com.school.wz_growth.model.front.request.sys;


import com.school.wz_growth.model.front.request.base.BaseRequestVo;

public class MenuAllRequestVo extends BaseRequestVo {
    private static final long serialVersionUID = 6620639994482000193L;


    private Integer role_id ;


    public Integer getRole_id() {
        return role_id;
    }

    public void setRole_id(Integer role_id) {
        this.role_id = role_id;
    }
}
