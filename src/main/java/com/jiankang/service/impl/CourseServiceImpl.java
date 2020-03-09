package com.jiankang.service.impl;


import com.jiankang.bean.*;
import com.jiankang.mapper.CourseDao;
import com.jiankang.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CourseServiceImpl implements CourseService {


    @Autowired
    CourseDao cd;


    @Override
    public List<CategoryCourse> getCategorylist() {
        return cd.getCategorylist();
    }

    @Override
    public List<CourseDifferent> getCourseDifferent() {
        return cd.getCourseDifferent();
    }

    @Override
    public List<Coursestatus> getCoursestatus() {
        return cd.getCoursestatus();
    }

    @Override
    public String getcategoryname(int categoryid) {
        return cd.getcategoryname(categoryid);
    }

    @Override
    public String getcatedifferentname(int differentid) {
        return cd.getcatedifferentname(differentid);
    }

    @Override
    public int addcoursedescribe(int uid, String coursename, int coursestatusid, String simpledescribe, int categoryid, double price, String createtime, int differentid,String imgpath) {
        return cd.addcoursedescribe(uid, coursename, coursestatusid, simpledescribe, categoryid, price, createtime, differentid,imgpath);
    }

    @Override
    public Course getCourse(int userid) {
        return cd.getCourse(userid);
    }

    @Override
    public int addCourseChapter(CourseChapter courseChapter) {
        return cd.addCourseChapter(courseChapter);
    }

    @Override
    public List<CourseChapter> getCourseChapter(int courseid) {
        return cd.getCourseChapter(courseid);
    }

    @Override
    public List<CourseChapter> getChaptername(int courseid) {
        return cd.getChaptername(courseid);
    }

    @Override
    public int savecoursecontent(CourseContent courseContent) {
        return cd.savecoursecontent(courseContent);
    }

    @Override
    public List<Course> querySelectCourse(String coursename, int coursestatusid, int categoryid, int differentid, String starttime, String endtime, int limit, int page, int uid) {
        return cd.querySelectCourse(coursename, coursestatusid, categoryid, differentid, starttime, endtime, limit, page, uid);
    }

    @Override
    public int queryCourseCount(String coursename, int coursestatusid, int categoryid, int differentid, String starttime, String endtime, int uid) {
        return cd.queryCourseCount(coursename, coursestatusid, categoryid, differentid, starttime, endtime, uid);
    }

    @Override
    @Transactional
    public int delCoursedescribe(int courseid) {
        return cd.delCoursedescribe(courseid);
    }

    @Override
    public int ishavecontent(int courseid) {
        return cd.ishavecontent(courseid);
    }

    @Override
    public Course getCoursebycourseid(int courseid) {

        return cd.getCoursebycourseid(courseid);
    }

    @Override
    public int delchapter(int courseid) {
        return cd.delchapter(courseid);
    }

    @Override
    public List<CourseContent> querycoursecontentlist(int courseid) {
        return cd.querycoursecontentlist(courseid);
    }

    @Override
    public int delcoursecontent(int courseid) {
        return cd.delcoursecontent(courseid);
    }

    @Override
    public List<CourseContent> querySelectCourseContent(int limit, int page, int courseid) {
        return cd.querySelectCourseContent(limit, page, courseid);
    }

    @Override
    public int queryCourseContentCount(int courseid) {
        return cd.queryCourseContentCount(courseid);
    }

    @Override
    public CourseContent getcontentbycontentid(int contentid) {
        return cd.getcontentbycontentid(contentid);
    }

    @Override
    public int delcoursecontentbyone(int contentid) {
        return cd.delcoursecontentbyone(contentid);
    }

    @Override
    public int updateCoursecontent(int chapterid, String contentname, String contenturl, int contentid) {
        return cd.updateCoursecontent(chapterid, contentname, contenturl, contentid);
    }

    @Override
    public int getmaxchapterid(int courseid) {
        return cd.getmaxchapterid(courseid);
    }

    @Override
    public int updatecoursestatus(int courseid, int coursestatusid) {
        return cd.updatecoursestatus(courseid, coursestatusid);
    }

    @Override
    public List<Course> getCoursebyuid(int uid) {
        return cd.getCoursebyuid(uid);
    }


}
