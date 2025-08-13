package com.school.wz_growth.model.front.request.sys;

import com.school.wz_growth.model.front.request.base.BaseRequestVo;
import org.apache.commons.lang3.StringUtils;

public class AdminUpPsdRequestVo extends BaseRequestVo {
    private String psd;
    private String re_psd;
    private String old_psd;

    @Override
    public boolean validator() {
        if(StringUtils.isBlank(psd) || StringUtils.isBlank(re_psd) || StringUtils.isBlank(old_psd) )
            return false;
        return  true;
    }

    public String getPsd() {
        return psd;
    }

    public void setPsd(String psd) {
        this.psd = psd;
    }

    public String getRe_psd() {
        return re_psd;
    }

    public void setRe_psd(String re_psd) {
        this.re_psd = re_psd;
    }

    public String getOld_psd() {
        return old_psd;
    }

    public void setOld_psd(String old_psd) {
        this.old_psd = old_psd;
    }
}
