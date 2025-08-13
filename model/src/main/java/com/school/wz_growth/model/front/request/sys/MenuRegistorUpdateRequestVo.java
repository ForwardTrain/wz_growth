package com.school.wz_growth.model.front.request.sys;


import com.school.wz_growth.model.front.request.base.BaseRequestVo;

public class MenuRegistorUpdateRequestVo extends BaseRequestVo {
    private static final long serialVersionUID = 6620639994482000193L;

    private String name;
    private String status;
    private String sequence;
    private String absolute_url;
    private String id;
    private String pid;
    private String is_config;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getSequence() {
        return sequence;
    }

    public void setSequence(String sequence) {
        this.sequence = sequence;
    }

    public String getAbsolute_url() {
        return absolute_url;
    }

    public void setAbsolute_url(String absolute_url) {
        this.absolute_url = absolute_url;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getIs_config() {
        return is_config;
    }

    public void setIs_config(String is_config) {
        this.is_config = is_config;
    }

}
