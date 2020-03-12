package com.jiankang.service.impl;

import com.jiankang.bean.Course;
import com.jiankang.mapper.CourseCenterDao;
import com.jiankang.service.CourseCenterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * CourseCenterServiceImpl
 *
 * @author jiankang.xu
 * @date 2020/3/9
 */
@Service
public class CourseCenterServiceImpl implements CourseCenterService {

    @Autowired
    CourseCenterDao courseCenterDao;


    @Override
    public List<Course> querySelectCourse(String coursename, int coursestatusid, int categoryid, int differentid, String starttime, String endtime,String nickname, int limit, int page) {
        return courseCenterDao.querySelectCourse(coursename, coursestatusid, categoryid, differentid, starttime, endtime,nickname, limit, page);
    }

    @Override
    public int queryCourseCount(String coursename, int coursestatusid, int categoryid, int differentid, String starttime, String endtime,String nickname) {
        return courseCenterDao.queryCourseCount(coursename, coursestatusid, categoryid, differentid, starttime, endtime,nickname);
    }

    @Override
    public int checkIsAddCourse(int courseid, int uid) {
        return courseCenterDao.checkIsAddCourse(courseid,uid);
    }

    @Override
    public int AddCourseStudy(int courseid, int uid) {
        return courseCenterDao.AddCourseStudy(courseid,uid);
    }
}
