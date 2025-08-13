package com.school.wz_growth.model.front.request.sys;


import com.school.wz_growth.model.front.request.base.BaseRequestVo;

public class RoleRegistorDeleteRequestVo extends BaseRequestVo {
    private static final long serialVersionUID = 6620639994482000193L;
    private String ids;


    public String getIds() {
        return ids;
    }

    public void setIds(String ids) {
        this.ids = ids;
    }
}
