package com.jiankang.bean;

import java.io.Serializable;
import java.util.List;

public class CourseChapter implements Serializable {
    private int chapterid;
    private int courseid;
    private String chaptername;
    private List<CourseContent> courseContents;

    public int getChapterid() {
        return chapterid;
    }

    public void setChapterid(int chapterid) {
        this.chapterid = chapterid;
    }

    public int getCourseid() {
        return courseid;
    }

    public void setCourseid(int courseid) {
        this.courseid = courseid;
    }

    public String getChaptername() {
        return chaptername;
    }

    public void setChaptername(String chaptername) {
        this.chaptername = chaptername;
    }


    public List<CourseContent> getCourseContents() {
        return courseContents;
    }

    public void setCourseContents(List<CourseContent> courseContents) {
        this.courseContents = courseContents;
    }
}
