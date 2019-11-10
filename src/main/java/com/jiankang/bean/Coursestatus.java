package com.jiankang.bean;

import java.io.Serializable;

public class Coursestatus implements Serializable {

    private int coursestatusid;
    private String statusname;

    public int getCoursestatusid() {
        return coursestatusid;
    }

    public void setCoursestatusid(int coursestatusid) {
        this.coursestatusid = coursestatusid;
    }

    public String getStatusname() {
        return statusname;
    }

    public void setStatusname(String statusname) {
        this.statusname = statusname;
    }
}
