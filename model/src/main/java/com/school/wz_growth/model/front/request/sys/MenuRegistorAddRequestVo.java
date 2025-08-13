package com.school.wz_growth.model.front.request.sys;


import com.school.wz_growth.model.front.request.base.BaseRequestVo;

public class MenuRegistorAddRequestVo extends BaseRequestVo {
    private static final long serialVersionUID = 6620639994482000193L;

    private String name;
    private Integer status;
    private Integer sequence;
    private Integer pid;
    private Integer is_config;
    private String absolute_url;


    public boolean validator() {
        return  true;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getSequence() {
        return sequence;
    }

    public void setSequence(Integer sequence) {
        this.sequence = sequence;
    }

    public String getAbsolute_url() {
        return absolute_url;
    }

    public void setAbsolute_url(String absolute_url) {
        this.absolute_url = absolute_url;
    }

    public Integer getPid() {
        return pid;
    }

    public void setPid(Integer pid) {
        this.pid = pid;
    }

    public Integer getIs_config() {
        return is_config;
    }

    public void setIs_config(Integer is_config) {
        this.is_config = is_config;
    }


}
