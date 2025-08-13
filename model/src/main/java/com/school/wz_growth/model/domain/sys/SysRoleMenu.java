package com.school.wz_growth.model.domain.sys;

import java.io.Serializable;

/**
 * 管理员权限菜单
 * @author zeng
 * @since 20160615
 */

public class SysRoleMenu implements Serializable {

    private static final long serialVersionUID = 7731179216448142050L;

    private Integer id;
    private Integer roleId;
    private String menuId;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getMenuId() {
        return menuId;
    }

    public void setMenuId(String menuId) {
        this.menuId = menuId;
    }

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

}
