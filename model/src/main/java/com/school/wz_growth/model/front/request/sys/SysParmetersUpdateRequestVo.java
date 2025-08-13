package com.school.wz_growth.model.front.request.sys;


import com.school.wz_growth.model.front.request.base.BaseRequestVo;

public class SysParmetersUpdateRequestVo extends BaseRequestVo {

    private Integer id;
    private Double  value;

    public boolean validator() {
        if( null == value || null == id )
            return false;

        return  true;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Double getValue() {
        return value;
    }

    public void setValue(Double value) {
        this.value = value;
    }
}
