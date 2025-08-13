package com.school.wz_growth.model.front.request.sys;


import com.school.wz_growth.model.front.request.base.BaseRequestVo;

public class RoleMenuUpdateRegistorRequestVo extends BaseRequestVo {
    private static final long serialVersionUID = 6620639994482000193L;

    private Integer role_id;
    private String org_id;
    private String menu_ids;

    public Integer getRole_id() {
        return role_id;
    }

    public void setRole_id(Integer role_id) {
        this.role_id = role_id;
    }

    public String getMenu_ids() {
        return menu_ids;
    }

    public void setMenu_ids(String menu_ids) {
        this.menu_ids = menu_ids;
    }

    public String getOrg_id() {
        return org_id;
    }

    public void setOrg_id(String org_id) {
        this.org_id = org_id;
    }
}
