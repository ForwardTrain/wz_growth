package com.school.wz_growth.model.front.request.sys;


import com.school.wz_growth.model.front.request.base.BaseRequestVo;

public class RoleMenuRegistorRequestVo extends BaseRequestVo {
    private static final long serialVersionUID = 6620639994482000193L;

    private String roleId;
    private String orgId;

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }

    public String getOrgId() {
        return orgId;
    }

    public void setOrgId(String orgId) {
        this.orgId = orgId;
    }
}
