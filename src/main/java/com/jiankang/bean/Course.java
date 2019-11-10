package com.jiankang.bean;

import java.io.Serializable;

public class Course implements Serializable {
    private int courseid;
    private int uid;
    private String coursename;
    private int rank;
    private int coursestatusid;
    private String simpledescribe;
    private int categoryid;
    private int studynumber;
    private double price;
    private int coursetimelength;
    private String createtime;
    private int differentid;
    private String imgpath;
    private String statusname;
    private String differentname;
    private String  categoryname;

    public String getStatusname() {
        return statusname;
    }

    public void setStatusname(String statusname) {
        this.statusname = statusname;
    }

    public String getDifferentname() {
        return differentname;
    }

    public void setDifferentname(String differentname) {
        this.differentname = differentname;
    }

    public String getCategoryname() {
        return categoryname;
    }

    public void setCategoryname(String categoryname) {
        this.categoryname = categoryname;
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

    public String getCoursename() {
        return coursename;
    }

    public void setCoursename(String coursename) {
        this.coursename = coursename;
    }

    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }

    public int getCoursestatusid() {
        return coursestatusid;
    }

    public void setCoursestatusid(int coursestatusid) {
        this.coursestatusid = coursestatusid;
    }

    public String getSimpledescribe() {
        return simpledescribe;
    }

    public void setSimpledescribe(String simpledescribe) {
        this.simpledescribe = simpledescribe;
    }

    public int getCategoryid() {
        return categoryid;
    }

    public void setCategoryid(int categoryid) {
        this.categoryid = categoryid;
    }

    public int getStudynumber() {
        return studynumber;
    }

    public void setStudynumber(int studynumber) {
        this.studynumber = studynumber;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getCoursetimelength() {
        return coursetimelength;
    }

    public void setCoursetimelength(int coursetimelength) {
        this.coursetimelength = coursetimelength;
    }

    public String getCreatetime() {
        return createtime;
    }

    public void setCreatetime(String createtime) {
        this.createtime = createtime;
    }

    public int getDifferentid() {
        return differentid;
    }

    public void setDifferentid(int differentid) {
        this.differentid = differentid;
    }

    public String getImgpath() {
        return imgpath;
    }

    public void setImgpath(String imgpath) {
        this.imgpath = imgpath;
    }
}
