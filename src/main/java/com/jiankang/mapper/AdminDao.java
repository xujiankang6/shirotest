package com.jiankang.mapper;

import com.jiankang.bean.Course;
import com.jiankang.bean.Message;
import com.jiankang.bean.RoleApply;
import org.apache.ibatis.annotations.Param;
import java.util.List;

public interface AdminDao {

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
     * @return
     */
    public List<Course> querySelectCourse1(@Param("coursename") String coursename,
                                          @Param("coursestatusid") int coursestatusid,
                                          @Param("categoryid") int categoryid,
                                          @Param("differentid") int differentid,
                                          @Param("starttime") String starttime,
                                          @Param("endtime") String endtime,
                                          @Param("limit") int limit,
                                          @Param("page") int page
    );

    /**
     * 教员根据条件查询课程数量
     * @param coursename
     * @param coursestatusid
     * @param categoryid
     * @param differentid
     * @param starttime
     * @param endtime
     * @return
     */
    public int queryCourseCount1(@Param("coursename") String coursename,
                                @Param("coursestatusid") int coursestatusid,
                                @Param("categoryid") int categoryid,
                                @Param("differentid") int differentid,
                                @Param("starttime") String starttime,
                                @Param("endtime") String endtime);
























    /**
     * 查询提交的教员申请
     * @param limit
     * @param page
     * @return
     */
    public List<RoleApply> querySelectRoleApply(
                                          @Param("limit") int limit,
                                          @Param("page") int page);
    /**
     * 查询提交的教员申请数量

     * @return
     */
    public int queryRoleApplyCount();


    /**
     * 删除申请教员的消息
     */
    public int delroleapply(@Param("uid") int uid);

    /**
     * 添加消息表记录
     */
    public int addmessage(@Param("message") Message message);


    /**
     * 根据uid修改u_r中该用户的权限
     */
    public int updateU_R(@Param("uid") int uid,@Param("rid") int rid);

}
