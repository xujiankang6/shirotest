package com.jiankang.mapper;


import com.jiankang.bean.Message;
import com.jiankang.bean.RoleApply;
import com.jiankang.bean.User;
import com.jiankang.bean.UserInfo;
import org.apache.ibatis.annotations.Param;

import java.util.List;


public interface UserDao {


    public User querybyname(@Param("phone") String phone);


    /**
     * 根据uid 查询userinfo
     * @param uid
     * @return
     */
    public UserInfo queryuserinfo(@Param("uid")int uid);


    /**
     * 根据uid 修改图片路径
     * @param uid
     * @return
     */
    public int updateuserimg(@Param("uid")int uid, @Param("imgpath") String imgpath);

    /**
     * 根据uid修改userinfo
     */
    public int uppdateuserinfo(@Param("userInfo") UserInfo userInfo);

    /**
     * 验证手机哈的唯一性
     * select count(1) from `user` where phone='11e1'
     */
    public int solephone(@Param("phone") String phone);

    /**
     * 插入数据库新增的user信息
     */
    public int addUser(@Param("phone") String phone,@Param("upwd") String upwd);

    /**
     * 通过phone拿到uid，以便添加userinfo默认信息
     */
    public int getUidbyphone(@Param("phone") String phone);
    /**
     * 根据uid添加userinfo默认信息
     */
    public int adduserinfo(@Param("userInfo") UserInfo userInfo);

    /**
     * 根据uid添加u_r表
     */
    public int addU_R(@Param("uid") int uid,@Param("rid") int rid);

    /**
     * 保存提交的申请教员理由
     */
    public int saveApply(@Param("roleApply") RoleApply roleApply);


    /**
     * 根据uid查询申请教员表中是否有数据了
     */
    public int haveroleapplybyuid(@Param("uid") int uid);


    /**
     * 查询收件箱消息list
     * @param limit
     * @param page
     * @return
     */
    public List<Message> queryReceiveMessage(
            @Param("receiveid") int receiveid,
            @Param("limit") int limit,
            @Param("page") int page);
    /**
     * 查询收件箱消息数量
     * @return
     */
    public int queryReceiveMessageCount( @Param("receiveid") int receiveid);

    /**
     *更加消息id查询该条内容
     */
    public Message querymessagebymid(@Param("messageid") int messageid);


    /**
     * 将消息改为已读
     * @param messageid
     * @return
     */
    public int updatemessagestatus(@Param("messageid") int messageid);

    /**
     * 根据messageid删除消息
     */
    public int delmessagebyid(@Param("messageid") int messageid);


}
