package com.jiankang.bean;

import org.apache.shiro.SecurityUtils;

import java.io.Serializable;

public class Permission  implements Serializable {
    private int pid;
    private String pname;
    private int permission_group_id;
    private String permission_group_name;
    private String href;

    public int getPid() {
        return pid;
    }

    public void setPid(int pid) {
        this.pid = pid;
    }

    public String getPname() {
        return pname;
    }

    public void setPname(String pname) {
        this.pname = pname;
    }

    public int getPermission_group_id() {
        return permission_group_id;
    }

    public void setPermission_group_id(int permission_group_id) {
        this.permission_group_id = permission_group_id;
    }

    public String getPermission_group_name() {
        return permission_group_name;
    }

    public void setPermission_group_name(String permission_group_name) {
        this.permission_group_name = permission_group_name;
    }

    public String getHref() {
        return href;
    }

    public void setHref(String href) {
        this.href = href;
    }

    @Override
    public String toString() {
        return "Permission{" +
                "pid=" + pid +
                ", pname='" + pname + '\'' +
                ", permission_group_id=" + permission_group_id +
                ", permission_group_name='" + permission_group_name + '\'' +
                ", href='" + href + '\'' +
                '}';
    }
}
