package com.school.wz_growth.model.domain.sys;

import com.school.wz_growth.model.front.request.base.BaseRequestVo;

public class BasicAddRequestVo extends BaseRequestVo {
    private String address;
    private String sys_name;


    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getSys_name() {
        return sys_name;
    }

    public void setSys_name(String sys_name) {
        this.sys_name = sys_name;
    }
}
