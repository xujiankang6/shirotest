package com.jiankang.service.impl;

import com.jiankang.bean.Message;
import com.jiankang.bean.RoleApply;
import com.jiankang.bean.User;
import com.jiankang.bean.UserInfo;
import com.jiankang.mapper.UserDao;
import com.jiankang.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserDao ud;


    @Override
    public User querybyname(String phone) {
        return ud.querybyname(phone);
    }

    @Override
    public UserInfo queryuserinfo(int uid) {
        return ud.queryuserinfo(uid);
    }

    @Override
    public int updateuserimg(int uid, String imgpath) {
        return ud.updateuserimg(uid, imgpath);
    }

    @Override
    public int uppdateuserinfo(UserInfo userInfo) {
        return ud.uppdateuserinfo(userInfo);
    }

    @Override
    public int solephone(String phone) {
        return ud.solephone(phone);
    }


    @Override
    public int addUser(String phone, String upwd) {
        return ud.addUser(phone, upwd);
    }

    @Override
    public int getUidbyphone(String phone) {
        return ud.getUidbyphone(phone);
    }

    @Override
    public int adduserinfo(UserInfo userInfo) {
        return ud.adduserinfo(userInfo);
    }

    @Override
    public int addU_R(int uid, int rid) {
        return ud.addU_R(uid, rid);
    }

    @Override
    public int saveApply(RoleApply roleApply) {
        return ud.saveApply(roleApply);
    }

    @Override
    public int haveroleapplybyuid(int uid) {
        return ud.haveroleapplybyuid(uid);
    }

    @Override
    public List<Message> queryReceiveMessage(int receiveid, int limit, int page) {
        return ud.queryReceiveMessage(receiveid, limit, page);
    }

    @Override
    public int queryReceiveMessageCount(int receiveid) {
        return ud.queryReceiveMessageCount(receiveid);
    }

    @Override
    public Message querymessagebymid(int messageid) {
        return ud.querymessagebymid(messageid);
    }

    @Override
    public int updatemessagestatus(int messageid) {
        return ud.updatemessagestatus(messageid);
    }

    @Override
    public int delmessagebyid(int messageid) {
        return ud.delmessagebyid(messageid);
    }

    @Override
    public int updateUser(String phone, String pwd) {
        return ud.updatePwd(phone, pwd);
    }


}
