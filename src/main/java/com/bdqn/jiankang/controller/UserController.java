package com.bdqn.jiankang.controller;


import com.bdqn.jiankang.bean.*;
import com.bdqn.jiankang.service.AdminService;
import com.bdqn.jiankang.service.CourseService;
import com.bdqn.jiankang.service.UserService;
import com.bdqn.jiankang.util.Layui;
import com.bdqn.jiankang.util.SmsService;
import io.swagger.models.auth.In;
import org.apache.ibatis.annotations.Param;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.crypto.hash.Sha384Hash;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.ClassUtils;
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

/**
 * 后端登录信息登录信息和左导航跳转的信息
 */

@Controller
public class UserController {


    @Autowired
    UserService sd;

    @Autowired
    CourseService cs;

    @Autowired
    AdminService as;


    /**
     * get登录
     *
     * @param phone
     * @param upwd
     * @return
     */
    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login(@Param("phone") String phone, @Param("upwd") String upwd) {
        System.out.println("qiantai:"+new Sha384Hash(String.valueOf(111)).toBase64()+"111111111111111111111111111");


        return "login";

    }

    /**
     * post登录
     *
     * @param phone
     * @param upwd
     * @return
     */
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public ModelAndView login2(@Param("phone") String phone, @Param("upwd") String upwd) {
        ModelAndView m = new ModelAndView();
        //添加用户认证信息
        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken uToken = new UsernamePasswordToken(phone, upwd);



            uToken.setRememberMe(true);

        try {
            //进行验证，报错返回首页，不报错到达成功页面。
            subject.login(uToken);

        } catch (UnknownAccountException e) {
            m.addObject("result", "用户不存在");
            m.setViewName("login");
            return m;
        } catch (IncorrectCredentialsException e) {
            m.addObject("result", "密码错误");
            m.setViewName("login");
            return m;
        }
        //将权限信息保存到session中
        User user = sd.querybyname(phone);

        List<Permission> permissions = new ArrayList<Permission>();
        for (Role role : user.getRoles()) {
            for (Permission permission : role.getPermissions()) {
                permissions.add(permission);
            }
        }
        Map<String, ArrayList<Permission>> map = new HashMap<String, ArrayList<Permission>>();
        for (Permission p : permissions) {
            String name = p.getPermission_group_name();
            if (!map.containsKey(name)) {
                ArrayList<Permission> mList = new ArrayList<Permission>();
                mList.add(p);
                map.put(name, mList);
            } else {
                ArrayList<Permission> pList = map.get(name);
                pList.add(p);
                map.put(name, pList);
            }
        }
//        创建session
        Session session = subject.getSession();
//        保存userinfo的基本信息
        int uid = user.getUid();
        UserInfo userInfo = sd.queryuserinfo(uid);
        //保存user信息
        session.setAttribute("user", user);
        List<Role> roles = user.getRoles();
        Role role = roles.get(0);
        String rolename = role.getRname();
        session.setAttribute("rolename", rolename);
        //创建session并保存权限的左导航内容
        session.setAttribute("userinfo", userInfo);
        session.setAttribute("maps", map);
//        保存用户的基本信息
        m.setViewName("index");
        return m;
    }

    /**
     * 到达注册页面
     */
    @RequestMapping("/register")
    public String register() {
        return "register";
    }

    /**
     * 到达首页
     */
    @RequestMapping("/index")
    public String toindex() {
        return "index";
    }


    /**
     * 注册时验证手机唯一性
     */
    @RequestMapping("/solephone")
    @ResponseBody
    public int solephone(String phone) {
        int i = sd.solephone(phone);
        return i;
    }

    /**
     * 后台获得验证码
     */
    @RequestMapping("/getcode")
    @ResponseBody
    public int getcode(String phone) {
        long l = System.currentTimeMillis();
        int k1 = (int) (l % 10000);
        String code = String.valueOf(k1);
        //session中保存我后台生成的code,为了将来拿出来与用户提交的进行比较。
        Subject subject = SecurityUtils.getSubject();
        Session session = subject.getSession();
        session.setAttribute("autocode", code);
        //成功返回0，失败返回1
        if (phone != null) {
            //把后台生成的code和所发送的手机号传进发送消息类，调用执行。
            SmsService.send(phone, code);
            return 0;
        } else {
            return 1;
        }
    }

    /**
     * 判断验证码的正确性
     */
    @RequestMapping("/comparecode")
    @ResponseBody
    public int authorization(String preauthcode) {
        Subject subject = SecurityUtils.getSubject();
        Session session = subject.getSession();
        String autocode = (String) session.getAttribute("autocode");
        //验证码不为空时，到后台进行比较，返回响应码，为1，提示请先获得验证码
        //为2，提示验证码错误
        //为3，验证码正确，无提示
        if (autocode == null) {
            return 1;
        } else if (autocode.equals(preauthcode)) {
            return 3;
        } else {
            return 2;
        }
    }

    /**
     * 保存注册信息
     *
     * @return
     */
    @RequestMapping("/saveregister")
    public ModelAndView saveregister(String phone, String password) {
        ModelAndView m = new ModelAndView();
        int i = sd.addUser(phone, new Sha384Hash(String.valueOf(password)).toBase64());
        //拿到新增的uid
        int uid = sd.getUidbyphone(phone);
        //为新用户添加一条默认的userinfo
        UserInfo userInfo1 = new UserInfo();
        userInfo1.setUid(uid);
        userInfo1.setUname("新用户" + phone);
        userInfo1.setEmail(phone + "未提交有效邮箱信息");
        userInfo1.setNickname("新用户" + phone);
        userInfo1.setIntroduce("无");
        userInfo1.setIdcard("无");
        Date date = new Date();
        SimpleDateFormat sd1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String a = sd1.format(date);
        userInfo1.setCreatetime(a);
        userInfo1.setImgpath("/img/firstimg.jpg");
        i = sd.adduserinfo(userInfo1);
        //给注册的用户添加权限
        i = sd.addU_R(uid, 3);
        m.addObject("i", i);
        m.setViewName("register");
        return m;

    }


    @RequiresPermissions("黑名单管理")
    @RequestMapping(value = "/heimingdan", method = RequestMethod.GET)
    public String loginaef() {
        return "heimingdan";
    }

    /**
     * 到达修改个人信息页面
     *
     * @return
     */

    @RequestMapping(value = "/updatemessage", method = RequestMethod.GET)
    public String a2() {
        return "message/updatemessage";
    }

    /**
     * 对修改的头像进行保存
     *
     * @return
     */
    @RequestMapping(value = "/touxiang", method = RequestMethod.POST)
    public String updateversion(MultipartFile img, HttpServletRequest req) {
        String filename = null;
        String path = ClassUtils.getDefaultClassLoader().getResource("").getPath() + "static/img";
        //session中拿到userid
        Subject subject = SecurityUtils.getSubject();
        Session session = subject.getSession();
        UserInfo userInfo = (UserInfo) session.getAttribute("userinfo");
        int userid = userInfo.getUid();
        //拿到要删除的图片地址
        String originimgpath = userInfo.getImgpath();
        String delpath = ClassUtils.getDefaultClassLoader().getResource("").getPath() + "static" + originimgpath;
        if (img != null) {
            //删除头像前先判断是否是默认图片，是的话不删
            if (!originimgpath.equals("/img/firstimg.jpg")) {
                //拿到图片地址，把本地图片删除*/
                File f1 = new File(delpath);
                f1.delete();
            }
            //给图片命名简单随机
            Random random = new Random();
            filename = System.currentTimeMillis() + random.nextInt(100000) * 100 + img.getOriginalFilename();
            System.out.println(filename + "===========================");
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
        }
        //更新数据库的图片字段
        String imgpath = "/img/" + filename;
        int i = sd.updateuserimg(userid, imgpath);
        req.setAttribute("i", i);
        //更改以后，把最新的userinfo保存到session中
        UserInfo userInfo1 = sd.queryuserinfo(userid);
        session.setAttribute("userinfo", userInfo1);
        return "message/updatemessage";
    }


    /**
     * 更新userinfo
     */

    @RequestMapping(value = "/updateuserinfo", method = RequestMethod.POST)
    public String updateuserinfo(String uname, String email, String nickname, String introduce, String idcard, HttpServletRequest req) {

        //session中拿到userid
        Subject subject = SecurityUtils.getSubject();
        Session session = subject.getSession();
        UserInfo userInfo = (UserInfo) session.getAttribute("userinfo");
        int uid = userInfo.getUid();
        UserInfo userInfo1 = new UserInfo();
        userInfo1.setUid(uid);
        userInfo1.setUname(uname);
        userInfo1.setEmail(email);
        userInfo1.setNickname(nickname);
        userInfo1.setIntroduce(introduce);
        userInfo1.setIdcard(idcard);
        Date date = new Date();
        SimpleDateFormat sd1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String a = sd1.format(date);
        userInfo1.setModifytime(a);
        int i = sd.uppdateuserinfo(userInfo1);
        req.setAttribute("i", i);
        UserInfo userInfo2 = sd.queryuserinfo(uid);
        session.setAttribute("userinfo", userInfo2);

        return "message/updatemessage";
    }


    @RequestMapping(value = "/seemessage", method = RequestMethod.GET)
    public ModelAndView a1() {
        ModelAndView m = new ModelAndView();


        //session中拿到userid
        Subject subject = SecurityUtils.getSubject();
        Session session = subject.getSession();
        UserInfo userInfo = (UserInfo) session.getAttribute("userinfo");
        int userid = userInfo.getUid();
        String rolename = (String) session.getAttribute("rolename");
        //要是普通用户我们查询的是他报名的课程，其他用户我们查询他上传的课程
        List<Course> courses = null;
        if (rolename.equals("普通用户")) {

        } else if (rolename.equals("教员")) {
            courses = cs.getCoursebyuid(userid);

        } else if (rolename.equals("管理员")) {
            courses = null;
        }
        m.addObject("courses", courses);
        m.setViewName("message/seemessage");
        return m;
    }


    /**
     * 跳转到申请成为教员页面
     */
    @RequestMapping("/applicationteacher")
    public ModelAndView toapply(String i) {
        ModelAndView m = new ModelAndView();
        m.addObject("i", i);
        m.setViewName("application/applyteacher");
        return m;
    }

    /**
     * 保存提交的申请理由
     */
    @RequestMapping(value = "/saveapply", method = RequestMethod.POST)
    public String saveapply(String uid, String describe) {

        //获取需要的roleapply
        int userid = Integer.valueOf(uid);
        int i = 0;
//        根据uid查询申请教员表中是否有数据了。有的话，就不添加，没有才添加
        int m = sd.haveroleapplybyuid(userid);
        if (m == 0) {
            Date date = new Date();
            SimpleDateFormat sd1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String applytime = sd1.format(date);
            int applystatus = 0;
            RoleApply roleApply = new RoleApply();
            roleApply.setApplydescribe(describe);
            roleApply.setApplystatus(applystatus);
            roleApply.setApplytime(applytime);
            roleApply.setUid(userid);
            //根据userid
            i = sd.saveApply(roleApply);
        }
        return "redirect:applicationteacher?i=" + i;
    }


//    消息处理页面

    @RequestMapping("/message")
    public ModelAndView aaa(String userid) {
        ModelAndView m = new ModelAndView();
        m.addObject("userid", userid);
        m.setViewName("index");
        return m;
    }


    /**
     * 跳转到消息管理页面
     */
    @RequestMapping("/tomessageadminindex")
    public ModelAndView tomessageadminindex() {
        ModelAndView m = new ModelAndView();

        m.setViewName("message/messageadminindex");
        return m;
    }


    /**
     * 查询自己收件箱的信息,到达消息管理
     */
    @RequestMapping("/receivemessage")
    @ResponseBody
    public Layui<Message> querygnum2341(@Param("limit") int limit, @Param("page") int page) {
        int page1 = (page - 1) * limit;
        //session中拿到userid作为收件人。
        Subject subject = SecurityUtils.getSubject();
        Session session = subject.getSession();
        UserInfo userInfo = (UserInfo) session.getAttribute("userinfo");
        int userid = userInfo.getUid();
        List<Message> list = sd.queryReceiveMessage(userid, limit, page1);

        int count = sd.queryReceiveMessageCount(userid);
        Layui<Message> lists = new Layui<Message>();
        lists.setCode(0);
        lists.setCount(count);
        lists.setData(list);
        return lists;
    }


    /**
     * 到达回复消息页面
     */
    @RequestMapping("/toreceive")
    public ModelAndView toreceive(String messageid, String i) {
        ModelAndView m = new ModelAndView();
        System.out.println(messageid + "---------------------------------------------------");
        int messageid1 = Integer.valueOf(messageid);
        //更改messageid1的状态为已读。
        sd.updatemessagestatus(messageid1);
        Message message = sd.querymessagebymid(messageid1);
        m.addObject("message", message);
        m.addObject("i", i);
        m.setViewName("message/receivemessage");
        return m;

    }

    /**
     * 保存回复的消息
     */
    @RequestMapping("/recemessage")
    public String avereceive(String sendid, String receiveid, String sendcontent, String messageid) {
        //2给消息表增加一条记录回复。
        //拿到session中的sendid
        Date date = new Date();
        SimpleDateFormat sd1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String sendtime = sd1.format(date);
        int messagestatus = 0;
        Message message = new Message();
        message.setSendid(Integer.valueOf(sendid));
        message.setReceiveid(Integer.valueOf(receiveid));
        message.setSendcontent(sendcontent);
        message.setSendtime(sendtime);
        message.setMessagestatus(messagestatus);
        int i = as.addmessage(message);
        return "redirect:toreceive?i=" + i + "&messageid=" + messageid;

    }

    /**
     * 删除消息
     */
    @RequestMapping("/delsinglemessage")
    @ResponseBody
    public int delcourse(String messageid) {

        int i = sd.delmessagebyid(Integer.valueOf(messageid));
        return 1;
    }

}
