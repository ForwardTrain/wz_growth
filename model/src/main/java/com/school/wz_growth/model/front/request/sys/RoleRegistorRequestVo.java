package com.school.wz_growth.model.front.request.sys;

import com.school.wz_growth.model.front.request.base.BaseRequestVo;

public class RoleRegistorRequestVo extends BaseRequestVo {
    private static final long serialVersionUID = 6620639994482000193L;

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }
    private Long org_id;
    private String name;

    public Long getOrg_id() {
        return org_id;
    }

    public void setOrg_id(Long org_id) {
        this.org_id = org_id;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
