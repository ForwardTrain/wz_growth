package com.school.wz_growth.model.front.request.sys;


import com.school.wz_growth.model.front.request.base.BaseRequestVo;

public class SysBasicChinaCodeRequestVo extends BaseRequestVo {


    private String code;
    private String name=null;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
