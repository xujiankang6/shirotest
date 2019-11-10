package com.jiankang.bean;

import java.io.Serializable;
import java.util.List;

public class Role implements Serializable
{

    private int rid;
    private String rname;
    private List<Permission> permissions;

    public int getRid() {
        return rid;
    }

    public void setRid(int rid) {
        this.rid = rid;
    }

    public String getRname() {
        return rname;
    }

    public void setRname(String rname) {
        this.rname = rname;
    }

    public List<Permission> getPermissions() {
        return permissions;
    }

    public void setPermissions(List<Permission> permissions) {
        this.permissions = permissions;
    }
}
