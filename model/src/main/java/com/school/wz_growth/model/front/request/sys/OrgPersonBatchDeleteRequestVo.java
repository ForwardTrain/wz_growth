package com.school.wz_growth.model.front.request.sys;

import com.school.wz_growth.model.front.request.base.BaseRequestVo;

import javax.validation.constraints.NotNull;


public class OrgPersonBatchDeleteRequestVo extends BaseRequestVo {

    @NotNull(message = "id集合不能为空")
    private String ids;

    public String getIds() {
        return ids;
    }

    public void setIds(String ids) {
        this.ids = ids;
    }
}
