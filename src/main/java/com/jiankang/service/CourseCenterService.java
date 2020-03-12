package com.jiankang.service;

import com.jiankang.bean.Course;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * CourseCenterService
 *
 * @author jiankang.xu
 * @date 2020/3/9
 */
public interface CourseCenterService {


    /**
     * 查询所有的课程
     *
     * @param coursename
     * @param coursestatusid
     * @param categoryid
     * @param differentid
     * @param starttime
     * @param endtime
     * @param limit
     * @param page
     * @return
     */
    public List<Course> querySelectCourse(@Param("coursename") String coursename,
                                          @Param("coursestatusid") int coursestatusid,
                                          @Param("categoryid") int categoryid,
                                          @Param("differentid") int differentid,
                                          @Param("starttime") String starttime,
                                          @Param("endtime") String endtime,
                                          @Param("nickname") String nickname,
                                          @Param("limit") int limit,
                                          @Param("page") int page);


    /**
     * 根据条件查询教员课程数量
     *
     * @param coursename
     * @param coursestatusid
     * @param categoryid
     * @param differentid
     * @param starttime
     * @param endtime
     * @return
     */
    public int queryCourseCount(@Param("coursename") String coursename,
                                @Param("coursestatusid") int coursestatusid,
                                @Param("categoryid") int categoryid,
                                @Param("differentid") int differentid,
                                @Param("starttime") String starttime,
                                @Param("endtime") String endtime,
                                @Param("nickname") String nickname);

    /**
     * 检查是否已经加入课程
     * @param courseid
     * @param uid
     * @return
     */
    public int checkIsAddCourse(@Param("courseid") int courseid,
                                @Param("uid") int uid);

    /**
     * 检查是否已经加入课程
     * @param courseid
     * @param uid
     * @return
     */
    public int AddCourseStudy(@Param("courseid") int courseid,
                                @Param("uid") int uid);
}
