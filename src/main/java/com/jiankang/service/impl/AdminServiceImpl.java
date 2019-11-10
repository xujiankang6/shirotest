package com.jiankang.service.impl;

import com.jiankang.bean.Course;
import com.jiankang.bean.Message;
import com.jiankang.bean.RoleApply;
import com.jiankang.mapper.AdminDao;
import com.jiankang.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;


@Service
public class AdminServiceImpl implements AdminService {

    @Autowired
    AdminDao ad;


    @Override
    public List<Course> querySelectCourse1(String coursename, int coursestatusid, int categoryid, int differentid, String starttime, String endtime, int limit, int page) {
        return ad.querySelectCourse1(coursename, coursestatusid, categoryid, differentid, starttime, endtime, limit, page);
    }

    @Override
    public int queryCourseCount1(String coursename, int coursestatusid, int categoryid, int differentid, String starttime, String endtime) {
        return ad.queryCourseCount1(coursename, coursestatusid, categoryid, differentid, starttime, endtime);
    }

    @Override
    public List<RoleApply> querySelectRoleApply(int limit, int page) {
        return ad.querySelectRoleApply(limit, page);
    }

    @Override
    public int queryRoleApplyCount() {
        return ad.queryRoleApplyCount();
    }

    @Override
    public int delroleapply(int uid) {
        return ad.delroleapply(uid);
    }

    @Override
    public int addmessage(Message message) {
        return ad.addmessage(message);
    }

    @Override
    public int updateU_R(int uid, int rid) {
        return ad.updateU_R(uid,rid);
    }
}
