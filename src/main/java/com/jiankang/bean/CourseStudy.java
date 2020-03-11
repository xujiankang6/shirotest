package com.jiankang.bean;

import java.io.Serializable;

/**
 * CourseStudy
 *
 * @author jiankang.xu
 * @date 2020/3/11
 */
public class CourseStudy implements Serializable {
    private int id;
    private int courseid;
    private int uid;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCourseid() {
        return courseid;
    }

    public void setCourseid(int courseid) {
        this.courseid = courseid;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }
}
