package com.school.wz_growth.model.front.request.sys;

import com.school.wz_growth.model.front.request.base.BaseRequestVo;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;

public class OrgUpdateRequestVo extends BaseRequestVo {

    @NotEmpty(message = "公司名称不能为空")
    private String org_name;
    @NotNull(message = "id不能为空")
    private Integer id;

    public String getOrg_name() {
        return org_name;
    }

    public void setOrg_name(String org_name) {
        this.org_name = org_name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
