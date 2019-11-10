package com.jiankang.bean;

import java.io.Serializable;

public class CourseDifferent implements Serializable {
    private int differentid;
    private String differentname;

    public int getDifferentid() {
        return differentid;
    }

    public void setDifferentid(int differentid) {
        this.differentid = differentid;
    }

    public String getDifferentname() {
        return differentname;
    }

    public void setDifferentname(String differentname) {
        this.differentname = differentname;
    }
}
