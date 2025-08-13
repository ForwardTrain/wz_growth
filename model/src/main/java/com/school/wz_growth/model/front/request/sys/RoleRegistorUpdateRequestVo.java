package com.school.wz_growth.model.front.request.sys;

import com.school.wz_growth.model.front.request.base.BaseRequestVo;
import org.apache.commons.lang3.StringUtils;

public class RoleRegistorUpdateRequestVo extends BaseRequestVo {
    private static final long serialVersionUID = 6620639994482000193L;
    private String role_name;
    private Integer id;
    private String description;
    private Integer org_role_type =1;//角色类型： 1:公司级 2:项目级

    private String menu_id = null;
    private String status;

//    public boolean validator() {
//        if(StringUtils.isBlank(role_name) || null == id )
//            return  false;
//        return  true;
//    }

    public Integer getOrg_role_type() {
        return org_role_type;
    }

    public void setOrg_role_type(Integer org_role_type) {
        this.org_role_type = org_role_type;
    }

    public String getRole_name() {
        return role_name;
    }

    public void setRole_name(String role_name) {
        this.role_name = role_name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }



    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }


    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMenu_id() {
        return menu_id;
    }

    public void setMenu_id(String menu_id) {
        this.menu_id = menu_id;
    }
}
