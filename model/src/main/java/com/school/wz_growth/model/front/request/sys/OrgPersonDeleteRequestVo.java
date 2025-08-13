package com.school.wz_growth.model.front.request.sys;

import com.school.wz_growth.model.front.request.base.BaseRequestVo;

import javax.validation.constraints.NotNull;

public class OrgPersonDeleteRequestVo extends BaseRequestVo {

    @NotNull(message = "id不能为空")
    private Integer id;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
