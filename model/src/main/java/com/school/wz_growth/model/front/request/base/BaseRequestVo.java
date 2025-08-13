package com.school.wz_growth.model.front.request.base;


import com.school.wz_growth.common.validator.AbstractValidator;

public class BaseRequestVo extends AbstractValidator {

    //用户id
    private Long uId;
    private Integer role_type;
    private String u_name;
    private Integer pageIndex=1;
    private Integer pageSize=20;

    public Integer getPageIndex() {
        return pageIndex;
    }

    public void setPageIndex(Integer pageIndex) {
        this.pageIndex = pageIndex;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Long getuId() {
        return uId;
    }

    public void setuId(Long uId) {
        this.uId = uId;
    }

    public boolean validator() {
        return  true;
    }

    public Integer getRole_type() {
        return role_type;
    }

    public void setRole_type(Integer role_type) {
        this.role_type = role_type;
    }

    public String getU_name() {
        return u_name;
    }

    public void setU_name(String u_name) {
        this.u_name = u_name;
    }
}
