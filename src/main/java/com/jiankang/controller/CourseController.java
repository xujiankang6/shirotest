package com.jiankang.controller;


import com.jiankang.bean.*;
import com.jiankang.service.CourseService;
import com.jiankang.util.Layui;
import org.apache.commons.io.FileUtils;
import org.apache.ibatis.annotations.Param;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.ClassUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller

public class CourseController {


    @Autowired
    CourseService cs;

    /**
     * 跳转增加详情页面
     *
     * @return
     */
    @RequiresRoles("教员")
    @RequestMapping("/addcoursedescribe")
    public ModelAndView addc() {
        ModelAndView m = new ModelAndView();
        //从数据库取到类别数据和难度数据
        List<CategoryCourse> categoryCourses = cs.getCategorylist();
        List<CourseDifferent> courseDifferents = cs.getCourseDifferent();
        m.addObject("categorys", categoryCourses);
        m.addObject("courseDifferents", courseDifferents);
        m.setViewName("addcourse/addcoursedescribe");
        return m;
    }


    /**
     * 保存提交的课程新增详情信息
     */
    @RequestMapping(value = "/savecoursedescribe", method = RequestMethod.POST)
    public ModelAndView savecoursedescribe(MultipartFile img, String coursename,
                                           String price, String simpledescribe,
                                           String categoryid, String differentid) {
        ModelAndView m = new ModelAndView("/addcoursedescribe");
        //保存相关的信息
        //session中拿到userid
        Subject subject = SecurityUtils.getSubject();
        Session session = subject.getSession();
        UserInfo userInfo = (UserInfo) session.getAttribute("userinfo");
        int userid = userInfo.getUid();
        //拿到创建时间
        Date date = new Date();
        SimpleDateFormat sd1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String a = sd1.format(date);
        //保存课程图片
        String filename = null;
        String path = ClassUtils.getDefaultClassLoader().getResource("").getPath() + "static/img/courseimg";
        //给图片命名简单随机
        Random random = new Random();
        filename = System.currentTimeMillis() + random.nextInt(100000) * 100 + img.getOriginalFilename();
        File f = new File(path, filename);
        if (!f.exists()) {
            try {
                f.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try {
            img.transferTo(f);
        } catch (IllegalStateException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        //更新数据库的图片字段
        String imgpath = "/img/courseimg/" + filename;
        //添加数据库课程信息
        int i = cs.addcoursedescribe(userid, coursename, 1, simpledescribe,
                Integer.valueOf(categoryid), Double.valueOf(price), a, Integer.valueOf(differentid), imgpath);


        //添加课程成功后返回页面的数据
        List<CategoryCourse> categoryCourses = cs.getCategorylist();
        List<CourseDifferent> courseDifferents = cs.getCourseDifferent();
        String categoryname = cs.getcategoryname(Integer.valueOf(categoryid));
        String differentname = cs.getcatedifferentname(Integer.valueOf(differentid));
        m.addObject("imgpath", imgpath);
        m.addObject("categorys", categoryCourses);
        m.addObject("courseDifferents", courseDifferents);
        m.addObject("coursename", coursename);
        m.addObject("price", price);
        m.addObject("simpledescribe", simpledescribe);
        m.addObject("categoryname", categoryname);
        m.addObject("differentname", differentname);
        m.addObject("i", 1);
        m.setViewName("addcourse/addcoursedescribe");
        return m;
    }


    /**
     * 跳转增加章节页面
     *
     * @return
     */

    @RequestMapping("/addchapter")
    public ModelAndView addcs() {
        ModelAndView m = new ModelAndView();

        //session中拿到userid
        Subject subject = SecurityUtils.getSubject();
        Session session = subject.getSession();
        UserInfo userInfo = (UserInfo) session.getAttribute("userinfo");
        int userid = userInfo.getUid();
        //拿到新增的这条记录
        Course course = cs.getCourse(userid);

        m.addObject("course", course);
        m.setViewName("addcourse/addchapter");
        return m;
    }

    /**
     * 保存增加的章节信息
     */
    @RequestMapping("/savechaptername")
    public ModelAndView savechapter(String courseid, String[] chaptername) {
        ModelAndView m = new ModelAndView();
        for (int i = 0; i < chaptername.length; i++) {
            CourseChapter courseChapter = new CourseChapter();
            courseChapter.setChapterid(i + 1);
            courseChapter.setChaptername(chaptername[i]);
            courseChapter.setCourseid(Integer.valueOf(courseid));
            cs.addCourseChapter(courseChapter);
        }

//        保存成功后需要携带的数据
        //session中拿到userid
        Subject subject = SecurityUtils.getSubject();
        Session session = subject.getSession();
        UserInfo userInfo = (UserInfo) session.getAttribute("userinfo");
        int userid = userInfo.getUid();
        //拿到新增的这条记录
        Course course = cs.getCourse(userid);
        //拿到所有的此课程所有的章节课节信息
        //拿到章节名集合
        List<CourseChapter> chapternames = cs.getChaptername(Integer.valueOf(courseid));
        //拿到课节章节集合
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
        m.addObject("chapternames", chapternames);
        m.addObject("course", course);
        m.setViewName("addcourse/addcoursecontent");
        return m;

    }


    /**
     * 到达播放页面
     */
    @RequestMapping("/seevideo")
    public ModelAndView aasavechapteraa(String href) {
        ModelAndView m = new ModelAndView();
        m.addObject("href", href);
        m.setViewName("addcourse/seecoursevideo");
        return m;
    }

    /**
     * 保存上传的课节信息
     */
    @RequestMapping(value = "/savecoursecontent", method = RequestMethod.POST)
    public ModelAndView aasavechapteradfa(MultipartFile img, String contentname,
                                          String chapterid, String courseid) {
        ModelAndView m = new ModelAndView();
        Date date = new Date();
        SimpleDateFormat sd1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String contenttime = sd1.format(date);
        //保存课程图片
        String filename = null;
        String path = ClassUtils.getDefaultClassLoader().getResource("").getPath() + "static/video";
        //给图片命名简单随机
        Random random = new Random();
        filename = System.currentTimeMillis() + random.nextInt(100000) * 100 + img.getOriginalFilename();
        File f = new File(path, filename);
        if (!f.exists()) {
            try {
                f.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try {
            img.transferTo(f);
        } catch (IllegalStateException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        //更新数据库的图片字段
        String contentpath = "/video/" + filename;
        CourseContent courseContent = new CourseContent();
        courseContent.setChapterid(Integer.valueOf(chapterid));
        courseContent.setCourseid(Integer.valueOf(courseid));
        courseContent.setContentname(contentname);
        courseContent.setContenttime(contenttime);
        courseContent.setContenturl(contentpath);
        //将课节保存进去数据库
        int i = cs.savecoursecontent(courseContent);


        //        保存成功后需要携带的数据
        //session中拿到userid
        Subject subject = SecurityUtils.getSubject();
        Session session = subject.getSession();
        UserInfo userInfo = (UserInfo) session.getAttribute("userinfo");
        int userid = userInfo.getUid();
        //拿到新增的这条记录
        Course course = cs.getCourse(userid);
        //拿到所有的此课程所有的章节课节信息
        //拿到章节名集合
        List<CourseChapter> chapternames = cs.getChaptername(Integer.valueOf(courseid));
        //拿到课节章节集合
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
        m.addObject("chapternames", chapternames);
        m.addObject("course", course);

        //返回当前页面
        m.addObject("i", i);
        m.setViewName("addcourse/addcoursecontent");
        return m;

    }


    /**
     * 到达修改课程页面
     *
     * @return
     */
    @RequestMapping("/updatecourse")
    public ModelAndView updatecourse() {
        ModelAndView m = new ModelAndView();
        List<CategoryCourse> categorylists = cs.getCategorylist();
        List<CourseDifferent> CourseDifferents = cs.getCourseDifferent();
        List<Coursestatus> Coursestatuss = cs.getCoursestatus();
        m.addObject("categorylists", categorylists);
        m.addObject("CourseDifferents", CourseDifferents);
        m.addObject("Coursestatuss", Coursestatuss);
        m.setViewName("addcourse/updatecourseindex");
        return m;
    }

    /**
     * 重置教员的修改课程条件
     */
    @RequestMapping("/back")
    public String back() {
        return "redirect:updatecourse";
    }


    /**
     * 教员根据条件修改需要查询的课程
     */
    @RequestMapping("/educationquerycourse")
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
        //session中拿到userid
        Subject subject = SecurityUtils.getSubject();
        Session session = subject.getSession();
        UserInfo userInfo = (UserInfo) session.getAttribute("userinfo");
        int uid = userInfo.getUid();
        int page1 = (page - 1) * limit;
        List<Course> list = cs.querySelectCourse(coursename, coursestatusid1, categoryid1, differentid1, starttime, endtime, limit, page1, uid);
        int count = cs.queryCourseCount(coursename, coursestatusid1, categoryid1, differentid1, starttime, endtime, uid);
        Layui<Course> lists = new Layui<Course>();
        lists.setCode(0);
        lists.setCount(count);
        lists.setData(list);
        return lists;
    }

    /**
     * 删除课程
     */
    @RequestMapping("/delcourse")
    @ResponseBody
    public int delcourse(String courseid) {
        //拿到课程图片地址，并删除
        Course course = cs.getCoursebycourseid(Integer.valueOf(courseid));
        if (course != null) {
            String imgpath = course.getImgpath();
            String delpath = ClassUtils.getDefaultClassLoader().getResource("").getPath() + "static" + imgpath;
            File f1 = new File(delpath);
            f1.delete();
        }
        //删除章节信息
        int j = cs.delchapter(Integer.valueOf(courseid));
        //删除课节信息
        //1拿到所有的课节信息
        List<CourseContent> courseContents = cs.querycoursecontentlist(Integer.valueOf(courseid));
        for (int m = 0; m < courseContents.size(); m++) {
            CourseContent courseContent = courseContents.get(m);
            String contentpath = courseContent.getContenturl();
            String delpath2 = ClassUtils.getDefaultClassLoader().getResource("").getPath() + "static" + contentpath;
            File f2 = new File(delpath2);
            f2.delete();
        }
        //2、删除课节
        int k = cs.delcoursecontent(Integer.valueOf(courseid));
        //删除课程详情
        int i = cs.delCoursedescribe(Integer.valueOf(courseid));
        return 1;
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


    /**
     * 根据courseid查看详情
     */
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
        m.setViewName("addcourse/seecourse");
        return m;
    }

    /**
     * 根据课程id到达修改课程课节主页面
     *
     * @return
     */
    @RequestMapping(value = "/editcoursecontentindex/{courseid}", method = RequestMethod.GET)
    public ModelAndView updaatecourse(@PathVariable String courseid) {

        ModelAndView m = new ModelAndView();
        Course course = cs.getCoursebycourseid(Integer.valueOf(courseid));
        m.addObject("course", course);
        m.setViewName("addcourse/updatecoursecontentindex");
        return m;
    }


    /**
     * layui所需数据
     */
    @RequestMapping("/querycoursecontentbycourseid")
    @ResponseBody
    public Layui<CourseContent> querygnum2(@Param("courseid") String courseid,
                                           @Param("limit") int limit,
                                           @Param("page") int page) {
        int courseid1 = 0;
        if (courseid != null && !courseid.equals("")) {
            courseid1 = Integer.valueOf(courseid);
        }
        int page1 = (page - 1) * limit;
        List<CourseContent> list = cs.querySelectCourseContent(limit, page1, courseid1);
        int count = cs.queryCourseContentCount(courseid1);
        Layui<CourseContent> lists = new Layui<CourseContent>();
        lists.setCode(0);
        lists.setCount(count);
        lists.setData(list);
        return lists;
    }

    /**
     * ajax根据coursecontentid动态获取课程课节信息
     */
    @RequestMapping("getcontent2")
    @ResponseBody
    public CourseContent getfsgd(@Param("contentid") String contentid) {
        CourseContent courseContent = cs.getcontentbycontentid(Integer.valueOf(contentid));
        System.out.println("fdgg=====================================");
        return courseContent;
    }


    /**
     * 删除课程课节
     */
    @RequestMapping("/delcoursecontent")
    @ResponseBody
    public int delcoursecontent(@Param("contentid") String contentid) {
        CourseContent courseContent = cs.getcontentbycontentid(Integer.valueOf(contentid));


        //删除视频信息
        String contentpath = courseContent.getContenturl();
        String delpath2 = ClassUtils.getDefaultClassLoader().getResource("").getPath() + "static" + contentpath;
        File f2 = new File(delpath2);
        f2.delete();

        //2、删除课节
        int i = cs.delcoursecontentbyone(Integer.valueOf(contentid));

        return i;
    }


    /**
     * 到达课节修改页面
     */
    @RequestMapping("/tocontentpage")
    public ModelAndView tocontentpage(@Param("contentid") String contentid, @Param("i") String i) {
        ModelAndView m = new ModelAndView();
        //拿到课程课节和课程
        CourseContent courseContent = cs.getcontentbycontentid(Integer.valueOf(contentid));
        int courseid = courseContent.getCourseid();
        Course course = cs.getCoursebycourseid(courseid);
        //拿到视频名字
        String videourl = courseContent.getContenturl();
        String videoname = videourl.split("/")[2];


        //拿到该课程的所有章节
        List<CourseChapter> chapternames = cs.getChaptername(courseid);


        m.addObject("course", course);
        m.addObject("courseContent", courseContent);
        m.addObject("chapternames", chapternames);
        m.addObject("videoname", videoname);
        m.addObject("i", i);
        m.setViewName("addcourse/updatecoursecontent");
        return m;

    }


    /**
     * 下载mp4文件
     *
     * @param req
     * @return
     * @throws IOException
     */
    @RequestMapping(value = "/downloadvideo", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<byte[]> download1(String videourl, HttpServletRequest req) throws IOException {
        //拿到视频资源地址
        String delpath2 = ClassUtils.getDefaultClassLoader().getResource("").getPath() + "static" + videourl;
        File file = new File(delpath2);
        HttpHeaders headers = new HttpHeaders();
        String fname = new String(videourl.getBytes("utf-8"), "iso-8859-1");
        headers.setContentDispositionFormData("attachment", fname);
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        // 返回下载对象1、将文件转换为byte类型的数组
        // 2、headers 3、文件的保存格式
        return new ResponseEntity<byte[]>(FileUtils.readFileToByteArray(file), headers, HttpStatus.CREATED);
    }


    /**
     * 保存更新的课节信息
     */
    @RequestMapping(value = "/updatecoursecontent", method = RequestMethod.POST)
    public String aasavechaptfgeradfa(MultipartFile img, String contentname,
                                      String chapterid, String contentid) {

        //拿到原课节视频
        CourseContent courseContent = cs.getcontentbycontentid(Integer.valueOf(contentid));

        String contentpath = courseContent.getContenturl();
        String filename = null;
        if (img != null) {
            //保存视频

            String path = ClassUtils.getDefaultClassLoader().getResource("").getPath() + "static/video";
            //给图片命名简单随机
            Random random = new Random();
            filename = System.currentTimeMillis() + random.nextInt(100000) * 100 + img.getOriginalFilename();
            File f = new File(path, filename);
            if (!f.exists()) {
                try {
                    f.createNewFile();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            try {
                img.transferTo(f);
            } catch (IllegalStateException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                //删除视频信息
                String delpath2 = ClassUtils.getDefaultClassLoader().getResource("").getPath() + "static" + contentpath;
                File f2 = new File(delpath2);
                f2.delete();
                contentpath = "/video/" + filename;
            }
        }

        //将课节保存进去数据库
        int i = cs.updateCoursecontent(Integer.valueOf(chapterid), contentname, contentpath, Integer.valueOf(contentid));
        return "redirect:tocontentpage?i=" + i + "&contentid=" + contentid;
//

    }

    /**
     * 到达添加新章节页面
     *
     * @param courseid
     * @return
     */
    @RequestMapping("/addchapterbycourseid")
    public ModelAndView addcdds(@Param("courseid") String courseid,String i) {
        ModelAndView m = new ModelAndView();
        //根据courseid拿到对应的课程
        Course course = cs.getCoursebycourseid(Integer.valueOf(courseid));
        //根据courseid拿到对应的章节集合
        List<CourseChapter> courseChapters = cs.getChaptername(Integer.valueOf(courseid));

        m.addObject("courseChapters", courseChapters);
        m.addObject("course", course);
        m.addObject("i",i);
        m.setViewName("addcourse/addnewchapter");
        return m;
    }

    /**
     * 保存新章节
     *
     * @param courseid
     * @return
     */
    @RequestMapping(value = "/savenewchaptername",method = RequestMethod.POST)
    public String addrcdds(@Param("courseid") String courseid,@Param("chaptername") String chaptername) {

        //先查章节是否有，大于0的话，执行拿最大章节，没有给章节id赋值0；
       //拿到该课程最大的章节id
        int chapterid=cs.getmaxchapterid(Integer.valueOf(courseid));
        CourseChapter courseChapter=new CourseChapter();
        courseChapter.setCourseid(Integer.valueOf(courseid));
        courseChapter.setChaptername(chaptername);
        courseChapter.setChapterid(chapterid+1);
        int i=cs.addCourseChapter(courseChapter);
        return "redirect:addchapterbycourseid?i=" + i + "&courseid=" + courseid;
    }


    /**
     *到达新增课节页面
     * @param courseid
     * @return
     */
    @RequestMapping("/tosavecontentname")
    public ModelAndView tonewcontent(String courseid,String i) {
        ModelAndView m = new ModelAndView();
        //根据courseid拿到对应的课程
        Course course = cs.getCoursebycourseid(Integer.valueOf(courseid));
        //拿到所有的此课程所有的章节课节信息
        //拿到章节名集合
        List<CourseChapter> chapternames = cs.getChaptername(Integer.valueOf(courseid));
        //拿到课节章节集合
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
        m.addObject("chapternames", chapternames);
        m.addObject("course", course);
        m.addObject("i",i);
        m.setViewName("addcourse/addnewcoursecontent");
        return m;
    }

    /**
     * 保存新增的课节信息
     */
    @RequestMapping(value = "/savenewcoursecontent", method = RequestMethod.POST)
    @ResponseBody
    public int aasavechapteradfad(MultipartFile img, String contentname,
                                          String chapterid, String courseid) {
        ModelAndView m = new ModelAndView();
        Date date = new Date();
        SimpleDateFormat sd1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String contenttime = sd1.format(date);
        //保存课程图片
        String filename = null;
        String path = ClassUtils.getDefaultClassLoader().getResource("").getPath() + "static/video";
        //给图片命名简单随机
        Random random = new Random();
        filename = System.currentTimeMillis() + random.nextInt(100000) * 100 + img.getOriginalFilename();
        File f = new File(path, filename);
        if (!f.exists()) {
            try {
                f.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try {
            img.transferTo(f);
        } catch (IllegalStateException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        //更新数据库的图片字段
        String contentpath = "/video/" + filename;
        CourseContent courseContent = new CourseContent();
        courseContent.setChapterid(Integer.valueOf(chapterid));
        courseContent.setCourseid(Integer.valueOf(courseid));
        courseContent.setContentname(contentname);
        courseContent.setContenttime(contenttime);
        courseContent.setContenturl(contentpath);
        //将课节保存进去数据库
        int i = cs.savecoursecontent(courseContent);
        return i;
    }



    /**
     * 左导航发布课程/publishcourse---------------------------------------------------------------------------------------
     */

    @RequestMapping("/publishcourse")
    public ModelAndView updatecodfgurse() {
        ModelAndView m = new ModelAndView();
        List<CategoryCourse> categorylists = cs.getCategorylist();
        List<CourseDifferent> CourseDifferents = cs.getCourseDifferent();
        List<Coursestatus> Coursestatuss = cs.getCoursestatus();
        m.addObject("categorylists", categorylists);
        m.addObject("CourseDifferents", CourseDifferents);
        m.addObject("Coursestatuss", Coursestatuss);
        m.setViewName("addcourse/publishcourseindex");
        return m;
    }

    /**
     * 重置教员的修改课程条件
     */
    @RequestMapping("/backpublish")
    public String backpublish() {

        return "redirect:publishcourse";
    }


    /**
     * 教员根据条件修改需要查询的课程
     */
    @RequestMapping("/publisheducationquerycoursed")
    @ResponseBody
    public Layui<Course> querygnumf1(@Param("coursename") String coursename,
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
        //session中拿到userid
        Subject subject = SecurityUtils.getSubject();
        Session session = subject.getSession();
        UserInfo userInfo = (UserInfo) session.getAttribute("userinfo");
        int uid = userInfo.getUid();
        int page1 = (page - 1) * limit;
        List<Course> list = cs.querySelectCourse(coursename, coursestatusid1, categoryid1, differentid1, starttime, endtime, limit, page1, uid);
        int count = cs.queryCourseCount(coursename, coursestatusid1, categoryid1, differentid1, starttime, endtime, uid);
        Layui<Course> lists = new Layui<Course>();
        lists.setCode(0);
        lists.setCount(count);
        lists.setData(list);
        return lists;
    }

    /**
     * updatestatus对课程的状态操作
     */
    @RequestMapping("/updatestatus")
    @ResponseBody
    public int updatestatus(String courseid,String coursestatusid){
        System.out.println(courseid+"-------------------------------------------"+coursestatusid);
        int i=cs.updatecoursestatus(Integer.valueOf(courseid),Integer.valueOf(coursestatusid));
        return  i;
    }

    /**
     * 从看个人信息详情to根据courseid查看详情
     */
    @RequestMapping(value = "/seemessagetocourse", method = RequestMethod.GET)
    public ModelAndView addapfsp(String courseid) {
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
        m.setViewName("message/seecourse");
        return m;
    }

    /**
     * 看完课程详情后跳转个人信息主页面
     */
    @RequestMapping("/backseemessage")
    public String backseemessage()
    {
        return "redirect:seemessage";
    }



}
