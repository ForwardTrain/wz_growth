package com.school.wz_growth.model.front.request.sys;

import com.school.wz_growth.model.front.request.base.BaseRequestVo;
import org.apache.commons.lang3.StringUtils;

public class AdminLoginRequestVo extends BaseRequestVo {
    private String user_name;
    private String psd;

    @Override
    public boolean validator() {
        if(StringUtils.isBlank(user_name) || StringUtils.isBlank(user_name))
            return false;
        return  true;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getPsd() {
        return psd;
    }

    public void setPsd(String psd) {
        this.psd = psd;
    }
}
