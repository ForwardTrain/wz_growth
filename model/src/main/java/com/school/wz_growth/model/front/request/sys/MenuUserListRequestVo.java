package com.school.wz_growth.model.front.request.sys;


import com.school.wz_growth.model.front.request.base.BaseRequestVo;

public class MenuUserListRequestVo extends BaseRequestVo {
    private static final long serialVersionUID = 6620639994482000193L;

    private  String user_name;

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }
}
