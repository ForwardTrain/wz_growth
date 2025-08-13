package com.school.wz_growth.model.front.request.sys;



import com.school.wz_growth.model.front.request.base.BaseRequestVo;
import org.apache.commons.lang3.StringUtils;

public class OrgPersonAddRequestVo extends BaseRequestVo {

    private String name;
    private String tel;
    private Integer org_id;
    private String psd;
    private String re_psd;
    private Integer state;
    private Integer role_id;
    private Integer job_id;//岗位id 可以为空

    public boolean validator() {
        if(StringUtils.isBlank(name) || StringUtils.isBlank(tel) || null == org_id ||  StringUtils.isBlank(psd) ||  StringUtils.isBlank(re_psd) ||  null == state ||  null == role_id)
        {
            return  false;
        }
        if(!psd.equals(re_psd))
        {
            return  false;
        }
        return  true;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public Integer getOrg_id() {
        return org_id;
    }

    public void setOrg_id(Integer org_id) {
        this.org_id = org_id;
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

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public Integer getRole_id() {
        return role_id;
    }

    public void setRole_id(Integer role_id) {
        this.role_id = role_id;
    }

    public Integer getJob_id() {
        return job_id;
    }

    public void setJob_id(Integer job_id) {
        this.job_id = job_id;
    }
}
