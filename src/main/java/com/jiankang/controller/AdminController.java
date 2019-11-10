package com.jiankang.controller;


import com.jiankang.bean.*;
import com.jiankang.service.AdminService;
import com.jiankang.service.CourseService;
import com.jiankang.service.UserService;
import com.jiankang.util.Layui;
import org.apache.ibatis.annotations.Param;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.text.SimpleDateFormat;
import java.util.*;

@Controller

public class AdminController {

    @Autowired
    CourseService cs;

    @Autowired
    AdminService as;


    @Autowired
    UserService us;


    /**
     * 到达管理员审核界面
     * @return
     */
    @RequiresPermissions("课程审核")
    @RequestMapping("/tocheckindex")
    public ModelAndView tochecindex(){
        ModelAndView m = new ModelAndView();
        List<CategoryCourse> categorylists = cs.getCategorylist();
        List<CourseDifferent> CourseDifferents = cs.getCourseDifferent();
        m.addObject("categorylists", categorylists);
        m.addObject("CourseDifferents", CourseDifferents);
        m.setViewName("addcourse/Checkindex");
        return m;
    }
    /**
     * 管理员根据条件修改需要查询的课程
     */
    @RequestMapping("/checkquerycourse")
    @ResponseBody
    public Layui<Course> querygnum1(@Param("coursename") String coursename,
                                    @Param("coursestatusid") String coursestatusid,
                                    @Param("categoryid") String categoryid,
                                    @Param("differentid") String differentid,
                                    @Param("starttime") String starttime,
                                    @Param("endtime") String endtime,
                                    @Param("limit") int limit,
                                    @Param("page") int page) {
        int coursestatusid1 = 0;
        if (coursestatusid != null && !coursestatusid.equals("")) {
            coursestatusid1 = Integer.valueOf(coursestatusid);
        }
        int categoryid1 = 0;
        if (categoryid != null && !categoryid.equals("")) {
            categoryid1 = Integer.valueOf(categoryid);
        }
        int differentid1 = 0;
        if (differentid != null && !differentid.equals("")) {
            differentid1 = Integer.valueOf(differentid);
        }
        int page1 = (page - 1) * limit;
        List<Course> list = as.querySelectCourse1(coursename, 2, categoryid1, differentid1, starttime, endtime, limit, page1);
        int count = as.queryCourseCount1(coursename, 2, categoryid1, differentid1, starttime, endtime);
        Layui<Course> lists = new Layui<Course>();
        lists.setCode(0);
        lists.setCount(count);
        lists.setData(list);
        return lists;
    }

    /**
     * 重置管理员的修改课程条件
     */
    @RequestMapping("/checkback")
    public String back() {
        return "redirect:tocheckindex";
    }



    /**
     * 根据courseid进入审核页面
     */
    @RequestMapping(value = "/seecourse/{courseid}", method = RequestMethod.GET)
    public ModelAndView addapp(@PathVariable String courseid) {
        //拿到课节章节集合
        ModelAndView m = new ModelAndView();
        Course course = cs.getCoursebycourseid(Integer.valueOf(courseid));
        List<CourseChapter> courseChapters = cs.getCourseChapter(Integer.valueOf(courseid));
        List<Map<String, List<CourseContent>>> courseChaptersmaps = new ArrayList<Map<String, List<CourseContent>>>();
        for (CourseChapter cc : courseChapters) {
            String coursechaptername = cc.getChaptername();
            List<CourseContent> ccc = cc.getCourseContents();
            Map<String, List<CourseContent>> map = new HashMap<String, List<CourseContent>>();
            map.put(coursechaptername, ccc);
            courseChaptersmaps.add(map);
        }
        m.addObject("courseChaptersmaps", courseChaptersmaps);
        m.addObject("course", course);
        m.setViewName("addcourse/seecheckcourse");
        return m;
    }

    /**
     * 到达审核教员页面
     */
    @RequestMapping("/handleeducationapply")
    public ModelAndView handleeducationapply(){
        ModelAndView m=new ModelAndView();
        m.setViewName("application/educationapplyindex");
        return  m;
    }

    /**
     * 教员根据条件修改需要查询的课程
     */
    @RequestMapping("/educationapply")
    @ResponseBody
    public Layui<RoleApply> querygnum2341(@Param("limit") int limit, @Param("page") int page) {
        int page1 = (page - 1) * limit;
        List<RoleApply> list = as.querySelectRoleApply(limit, page1);

        int count = as.queryRoleApplyCount();
        Layui<RoleApply> lists = new Layui<RoleApply>();
        lists.setCode(0);
        lists.setCount(count);
        lists.setData(list);
        return lists;
    }

    /**
     * 申请教员成功的操作
     */

    @RequestMapping("/passapply")
    @ResponseBody
    public int querygnum2341(String uid) {
        //修改用户权限为教员
        int i=as.updateU_R(Integer.valueOf(uid),2);
        //修改成功操作
        if(i==1){
            //1删除这条申请
            //DELETE from role_apply where uid=1
            i=as.delroleapply(Integer.valueOf(uid));
            //2给消息表增加一条记录回复。
                //拿到session中的sendid
            Subject subject = SecurityUtils.getSubject();
            Session session = subject.getSession();
            UserInfo userInfo= (UserInfo) session.getAttribute("userinfo");
            int sendid=userInfo.getUid();
            int receiveid=Integer.valueOf(uid);
            String sendcontent="您的申请已经通过，恭喜您成为我们的一员！";
            Date date = new Date();
            SimpleDateFormat sd1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String sendtime = sd1.format(date);
            int messagestatus=0;
            Message message=new Message();
            message.setSendid(sendid);
            message.setReceiveid(receiveid);
            message.setSendcontent(sendcontent);
            message.setSendtime(sendtime);
            message.setMessagestatus(messagestatus);
            i=as.addmessage(message);
            return i;
        }else{
            return 0;
        }


    }

    /**
     * 申请教员不通过的操作
     */

    @RequestMapping("/notpassapply")
    @ResponseBody
    public int querygnum3452341(String uid) {
            //1删除这条申请
            //DELETE from role_apply where uid=1
            int i=as.delroleapply(Integer.valueOf(uid));
            //2给消息表增加一条记录回复。
            //拿到session中的sendid
            Subject subject = SecurityUtils.getSubject();
            Session session = subject.getSession();
            UserInfo userInfo= (UserInfo) session.getAttribute("userinfo");
            int sendid=userInfo.getUid();
            int receiveid=Integer.valueOf(uid);
            String sendcontent="您的申请未通过，请重新申请！";
            Date date = new Date();
            SimpleDateFormat sd1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String sendtime = sd1.format(date);
            int messagestatus=0;
            Message message=new Message();
            message.setSendid(sendid);
            message.setReceiveid(receiveid);
            message.setSendcontent(sendcontent);
            message.setSendtime(sendtime);
            message.setMessagestatus(messagestatus);
            i=as.addmessage(message);
            return i;

    }




}
