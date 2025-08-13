package com.school.wz_growth.model.domain.sys;

import java.io.Serializable;
import java.util.Date;

/**
 * 系统角色表
 * @author zeng
 * @since 20160615
 */

public class SysRole implements Serializable {

    private static final long serialVersionUID = 7731179216448142050L;

    private Integer id;
    private String roleName;
    private Date createTime;
    private String  description;
    private String  menu_ids;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }


    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getMenu_ids() {
        return menu_ids;
    }

    public void setMenu_ids(String menu_ids) {
        this.menu_ids = menu_ids;
    }
}
