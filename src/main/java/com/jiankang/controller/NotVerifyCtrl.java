package com.jiankang.controller;

import com.jiankang.bean.UserInfo;
import com.jiankang.service.UserService;
import org.apache.shiro.crypto.hash.Sha384Hash;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * NotVerifyCtrl
 *
 * @author jiankang.xu
 * @date 2020/3/9
 */
@Controller
@RequestMapping("/notVerify")
public class NotVerifyCtrl {

    Logger logger = LoggerFactory.getLogger(NotVerifyCtrl.class);


    @Autowired
    UserService userService;

    /**
     * 修改密码
     */
    /**
     * 保存注册信息
     * @return
     */
    @RequestMapping("/updatePwd")
    public ModelAndView saveregister(String phone, String password) {
        ModelAndView m = new ModelAndView();
        int i = userService.updateUser(phone, new Sha384Hash(String.valueOf(password)).toBase64());
        //拿到新增的uid
          m.addObject("i", i);
        m.setViewName("forgetPwd");
        return m;
    }
}
