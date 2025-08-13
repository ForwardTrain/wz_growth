package com.school.wz_growth.model.front.request.sys;



import com.school.wz_growth.model.front.request.base.BaseRequestVo;
import org.hibernate.validator.constraints.NotEmpty;


public class OrgAddRequestVo extends BaseRequestVo {

    @NotEmpty(message = "公司名称不能为空")
    private String org_name;
    private Integer p_id;

    public String getOrg_name() {
        return org_name;
    }

    public void setOrg_name(String org_name) {
        this.org_name = org_name;
    }

    public Integer getP_id() {
        return p_id;
    }

    public void setP_id(Integer p_id) {
        this.p_id = p_id;
    }
}
