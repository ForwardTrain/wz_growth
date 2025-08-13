package com.school.wz_growth.model.front.request.sys;

import com.school.wz_growth.model.front.request.base.BaseRequestVo;

import javax.validation.constraints.NotNull;

public class OrgPersonUpdateStateRequestVo extends BaseRequestVo {

    @NotNull(message = "状态不能为空")
    private Integer state;
    @NotNull(message = "id不能为空")
    private Integer id;

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
