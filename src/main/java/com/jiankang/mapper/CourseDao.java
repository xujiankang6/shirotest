package com.jiankang.mapper;

import com.jiankang.bean.*;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CourseDao {

    /**
     * 查询所有的课程类别
     *
     * @return
     */
    public List<CategoryCourse> getCategorylist();


    /**
     * 查询所有的难度等级
     *
     * @return
     */
    public List<CourseDifferent> getCourseDifferent();

    /**
     * 查询所有的审核情况
     */
    public List<Coursestatus> getCoursestatus();


    /**
     * 根据类别id查询对应的名称
     */
    public String getcategoryname(@Param("categoryid") int categoryid);


    /**
     * 根据难度id查询对应的名称
     */
    public String getcatedifferentname(@Param("differentid") int differentid);

    /**
     * 添加课程详情
     */
    public int addcoursedescribe(@Param("uid") int uid, @Param("coursename") String coursename,
                                 @Param("coursestatusid") int coursestatusid,
                                 @Param("simpledescribe") String simpledescribe, @Param("categoryid") int categoryid,
                                 @Param("price") double price, @Param("createtime") String createtime,
                                 @Param("differentid") int differentid, @Param("imgpath") String imgpath);

    /**
     * 根据userid拿到新增的course
     */
    public Course getCourse(@Param("userid") int userid);


    /**
     * 根据courseid添加coursename
     */
    public int addCourseChapter(@Param("CourseChapter") CourseChapter courseChapter);

    /**
     * 根据courseid拿到chapter集合
     */
    public List<CourseChapter> getCourseChapter(@Param("courseid") int courseid);

    /**
     * 根据课程号拿到所有的章节信息
     */
    public List<CourseChapter> getChaptername(@Param("courseid") int courseid);

    /**
     * 保存课节信息
     */
    public int savecoursecontent(@Param("CourseContent") CourseContent courseContent);


    /**
     * 教员根据条件查询的课程
     * @param coursename
     * @param coursestatusid
     * @param categoryid
     * @param differentid
     * @param starttime
     * @param endtime
     * @param limit
     * @param page
     * @param uid
     * @return
     */
    public List<Course> querySelectCourse(@Param("coursename") String coursename,
                                          @Param("coursestatusid") int coursestatusid,
                                          @Param("categoryid") int categoryid,
                                          @Param("differentid") int differentid,
                                          @Param("starttime") String starttime,
                                          @Param("endtime") String endtime,
                                          @Param("limit") int limit,
                                          @Param("page") int page,
                                          @Param("uid") int uid);

    /**
     * 教员根据条件查询课程数量
     * @param coursename
     * @param coursestatusid
     * @param categoryid
     * @param differentid
     * @param starttime
     * @param endtime
     * @param uid
     * @return
     */
    public int queryCourseCount(@Param("coursename") String coursename,
                                          @Param("coursestatusid") int coursestatusid,
                                          @Param("categoryid") int categoryid,
                                          @Param("differentid") int differentid,
                                          @Param("starttime") String starttime,
                                          @Param("endtime") String endtime,
                                          @Param("uid") int uid);

    /**
     * 根据courseid删除course详情
     */
    public int delCoursedescribe(@Param("courseid") int courseid);



    /**
     * 查看课程是否已经有课节
     */
    public int ishavecontent(@Param("courseid") int courseid);

    /**
     * 根据courseid拿到课程详情
     */
    public  Course getCoursebycourseid(@Param("courseid") int courseid);

    /**
     * 根据courseid删除章节信息
     */
    public int delchapter(@Param("courseid") int courseid);
    /**
     * 拿到课节信息
     */
    public List<CourseContent> querycoursecontentlist(@Param("courseid") int courseid);

    /**
     * 根据courseid删除课节信息
     */
    public int delcoursecontent(@Param("courseid") int courseid);


    /**
     * 教员根据id查询的课程课节
     * @return
     */
    public List<CourseContent> querySelectCourseContent(
                                          @Param("limit") int limit,
                                          @Param("page") int page,
                                          @Param("courseid") int courseid);

    /**
     * 教员根据条件查询课程课节数量
     * @return
     */
    public int queryCourseContentCount(@Param("courseid") int courseid);

    /**
     * 根据contentid查询课节信息
     */
    public CourseContent getcontentbycontentid(@Param("contentid") int contentid);

    /**
     * 根据课节id删除课节
     */
    public int delcoursecontentbyone(@Param("contentid") int contentid);

    /**
     * 更新课节信息
     */
    public int updateCoursecontent(@Param("chapterid") int chapterid,@Param("contentname") String contentname,
                                   @Param("contenturl") String contenturl, @Param("contentid")int contentid);


    /**
     * 拿到该课程最大的章节id,为了获得新增需要的的id
     */
    public int getmaxchapterid(@Param("courseid") int courseid);

    /**
     * 更新课程状态
     */
    public int updatecoursestatus(@Param("courseid") int courseid,@Param("coursestatusid") int coursestatusid);


    /**
     * 根据用户名拿到course集合
     */
    public List<Course> getCoursebyuid(@Param("uid") int uid);
}
