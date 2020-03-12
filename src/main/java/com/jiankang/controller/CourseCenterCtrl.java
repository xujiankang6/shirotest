package com.jiankang.controller;

import cn.hutool.core.util.StrUtil;
import com.jiankang.bean.*;
import com.jiankang.service.CourseCenterService;
import com.jiankang.service.CourseService;
import com.jiankang.util.Layui;
import com.sun.istack.NotNull;
import org.apache.ibatis.annotations.Param;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * CourseCenterCtrl
 *
 * @author jiankang.xu
 * @date 2020/3/9
 */
@Controller
@RequestMapping("/market")
public class CourseCenterCtrl {

    Logger logger = LoggerFactory.getLogger(CourseCenterCtrl.class);


    @Autowired
    CourseService cs;

    @Autowired
    CourseCenterService courseCenterService;

    @Autowired
    CourseService courseService;

    @RequestMapping("/all")
    @ResponseBody
    public Layui<Course> querygnumf1(@Param("coursename") String coursename,

                                     @Param("coursestatusid") String coursestatusid,
                                     @Param("categoryid") String categoryid,
                                     @Param("differentid") String differentid,
                                     @Param("starttime") String starttime,
                                     @Param("endtime") String endtime,
                                     @Param("nickname") String nickname,
                                     @Param("limit") int limit,
                                     @Param("page") int page) {
        int coursestatusid1 = 5;
        int categoryid1 = 0;
        if (categoryid != null && !categoryid.equals("")) {
            categoryid1 = Integer.valueOf(categoryid);
        }
        int differentid1 = 0;
        if (differentid != null && !differentid.equals("")) {
            differentid1 = Integer.valueOf(differentid);
        }
        int page1 = (page - 1) * limit;
        List<Course> list = courseCenterService.querySelectCourse(coursename, coursestatusid1, categoryid1, differentid1, starttime, endtime,nickname, limit, page1);
        int count = courseCenterService.queryCourseCount(coursename, coursestatusid1, categoryid1, differentid1, starttime, endtime,nickname);
        Layui<Course> lists = new Layui<Course>();
        lists.setCode(0);
        lists.setCount(count);
        lists.setData(list);
        return lists;
    }


    @RequestMapping("/allcourse")
    public ModelAndView updatecourse() {
        ModelAndView m = new ModelAndView();
        List<CategoryCourse> categorylists = cs.getCategorylist();
        List<CourseDifferent> CourseDifferents = cs.getCourseDifferent();
        List<Coursestatus> Coursestatuss = cs.getCoursestatus();
        m.addObject("categorylists", categorylists);
        m.addObject("CourseDifferents", CourseDifferents);
        m.addObject("Coursestatuss", Coursestatuss);
        m.setViewName("coursecenter/coursecenterindex");
        return m;
    }


    @RequestMapping("/mystudy")
    public ModelAndView mycourse() {
        ModelAndView m = new ModelAndView();
        List<CategoryCourse> categorylists = cs.getCategorylist();
        List<CourseDifferent> CourseDifferents = cs.getCourseDifferent();
        List<Coursestatus> Coursestatuss = cs.getCoursestatus();
        m.setViewName("mystudycourse/mycourse");
        return m;
    }

    /**
     * 返回上一页
     * @return
     */
    @RequestMapping("/back")
    public String back() {
        return "redirect:/market/allcourse";
    }

    /**
     * 查看课程操作前判断条件
     */
    @RequestMapping("/decidecourse")
    @ResponseBody
    public int pancourse(String courseid) {
        int i = cs.ishavecontent(Integer.valueOf(courseid));
        return i;
    }

    @RequestMapping(value = "/intereditcourse/{courseid}", method = RequestMethod.GET)
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
        m.setViewName("coursecenter/seecourse");
        return m;
    }

    /**
     * 到达播放页面
     */
    @RequestMapping(value ="/addstudy", method = RequestMethod.POST)
    @ResponseBody
    public int aasavechapteraa(@NotNull String courseid) {
        //session中拿到userid
        Subject subject = SecurityUtils.getSubject();
        Session session = subject.getSession();
        UserInfo userInfo = (UserInfo) session.getAttribute("userinfo");
        int userid = userInfo.getUid();
        int i = courseCenterService.checkIsAddCourse(Integer.valueOf(courseid), userid);

        if(i>0){
            return 0;
        }else {
            i=courseCenterService.AddCourseStudy(Integer.parseInt(courseid),userid);
            return  i;
        }
    }

}
