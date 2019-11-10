package com.jiankang.bean;

import java.io.Serializable;

public class CourseContent implements Serializable {

    private int courseid;
    private int chapterid;
    private int contentid;
    private String contentname;
    private String contenturl;
    private String contenttime;
    private String chaptername;

    public String getChaptername() {
        return chaptername;
    }

    public void setChaptername(String chaptername) {
        this.chaptername = chaptername;
    }

    public int getCourseid() {
        return courseid;
    }

    public void setCourseid(int courseid) {
        this.courseid = courseid;
    }

    public int getChapterid() {
        return chapterid;
    }

    public void setChapterid(int chapterid) {
        this.chapterid = chapterid;
    }

    public int getContentid() {
        return contentid;
    }

    public void setContentid(int contentid) {
        this.contentid = contentid;
    }

    public String getContentname() {
        return contentname;
    }

    public void setContentname(String contentname) {
        this.contentname = contentname;
    }

    public String getContenturl() {
        return contenturl;
    }

    public void setContenturl(String contenturl) {
        this.contenturl = contenturl;
    }

    public String getContenttime() {
        return contenttime;
    }

    public void setContenttime(String contenttime) {
        this.contenttime = contenttime;
    }
}
