/*
Navicat MySQL Data Transfer

Source Server         : localhost_3306
Source Server Version : 50529
Source Host           : localhost:3306
Source Database       : jiankangeducation

Target Server Type    : MYSQL
Target Server Version : 50529
File Encoding         : 65001

Date: 2019-05-05 17:48:24
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `categorycourse`
-- ----------------------------
DROP TABLE IF EXISTS `categorycourse`;
CREATE TABLE `categorycourse` (
  `categoryid` int(11) NOT NULL AUTO_INCREMENT,
  `categoryname` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`categoryid`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of categorycourse
-- ----------------------------
INSERT INTO `categorycourse` VALUES ('1', '计算机');
INSERT INTO `categorycourse` VALUES ('2', '英语');
INSERT INTO `categorycourse` VALUES ('3', '数学');

-- ----------------------------
-- Table structure for `course`
-- ----------------------------
DROP TABLE IF EXISTS `course`;
CREATE TABLE `course` (
  `courseid` int(11) NOT NULL AUTO_INCREMENT,
  `uid` int(11) DEFAULT NULL,
  `coursename` varchar(255) DEFAULT NULL,
  `rank` int(11) DEFAULT NULL,
  `coursestatusid` int(11) DEFAULT NULL,
  `simpledescribe` varchar(255) DEFAULT NULL,
  `categoryid` int(11) DEFAULT NULL,
  `studynumber` int(11) DEFAULT NULL,
  `price` double DEFAULT NULL,
  `coursetimelength` int(11) DEFAULT NULL,
  `createtime` datetime DEFAULT NULL,
  `motifytime` datetime DEFAULT NULL,
  `publishtime` datetime DEFAULT NULL,
  `differentid` int(11) DEFAULT NULL,
  `imgpath` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`courseid`)
) ENGINE=InnoDB AUTO_INCREMENT=56 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of course
-- ----------------------------
INSERT INTO `course` VALUES ('55', '2', 'rgt', null, '1', '11', '1', null, '11', null, '2019-04-30 08:55:40', null, null, '1', '/img/courseimg/1556588582779index.jpg');

-- ----------------------------
-- Table structure for `coursestatus`
-- ----------------------------
DROP TABLE IF EXISTS `coursestatus`;
CREATE TABLE `coursestatus` (
  `coursestatusid` int(11) NOT NULL AUTO_INCREMENT,
  `statusname` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`coursestatusid`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of coursestatus
-- ----------------------------
INSERT INTO `coursestatus` VALUES ('1', '待提交');
INSERT INTO `coursestatus` VALUES ('2', '待审核');
INSERT INTO `coursestatus` VALUES ('3', '审核未通过');
INSERT INTO `coursestatus` VALUES ('4', '可发布');
INSERT INTO `coursestatus` VALUES ('5', '已发布');

-- ----------------------------
-- Table structure for `course_chapter`
-- ----------------------------
DROP TABLE IF EXISTS `course_chapter`;
CREATE TABLE `course_chapter` (
  `ccid` int(11) NOT NULL AUTO_INCREMENT,
  `chapterid` int(11) NOT NULL,
  `courseid` int(11) DEFAULT NULL,
  `chaptername` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`ccid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of course_chapter
-- ----------------------------

-- ----------------------------
-- Table structure for `course_content`
-- ----------------------------
DROP TABLE IF EXISTS `course_content`;
CREATE TABLE `course_content` (
  `contentid` int(11) NOT NULL AUTO_INCREMENT,
  `courseid` int(11) DEFAULT NULL,
  `chapterid` int(11) DEFAULT NULL,
  `contentname` varchar(255) DEFAULT NULL,
  `contenturl` varchar(255) DEFAULT NULL,
  `contenttime` datetime DEFAULT NULL,
  PRIMARY KEY (`contentid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of course_content
-- ----------------------------

-- ----------------------------
-- Table structure for `course_different`
-- ----------------------------
DROP TABLE IF EXISTS `course_different`;
CREATE TABLE `course_different` (
  `differentid` int(11) NOT NULL AUTO_INCREMENT,
  `differentname` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`differentid`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of course_different
-- ----------------------------
INSERT INTO `course_different` VALUES ('1', '入门');
INSERT INTO `course_different` VALUES ('2', '中等');
INSERT INTO `course_different` VALUES ('3', '高级');

-- ----------------------------
-- Table structure for `message`
-- ----------------------------
DROP TABLE IF EXISTS `message`;
CREATE TABLE `message` (
  `messageid` int(11) NOT NULL AUTO_INCREMENT,
  `receiveid` int(11) DEFAULT NULL,
  `sendid` int(11) DEFAULT NULL,
  `sendtime` datetime DEFAULT NULL,
  `sendcontent` varchar(255) DEFAULT NULL,
  `messagestatus` int(11) DEFAULT NULL COMMENT '0表示未读，1表示已读',
  PRIMARY KEY (`messageid`)
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of message
-- ----------------------------
INSERT INTO `message` VALUES ('13', '16', '1', '2019-05-02 11:28:03', '您的申请未通过，请重新申请！', '0');

-- ----------------------------
-- Table structure for `permission`
-- ----------------------------
DROP TABLE IF EXISTS `permission`;
CREATE TABLE `permission` (
  `pid` int(11) NOT NULL AUTO_INCREMENT,
  `pname` varchar(255) DEFAULT NULL,
  `permission_group_id` int(11) DEFAULT NULL,
  `href` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`pid`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of permission
-- ----------------------------
INSERT INTO `permission` VALUES ('1', '审核教员', '1', '/handleeducationapply');
INSERT INTO `permission` VALUES ('3', '课程审核', '2', '/tocheckindex');
INSERT INTO `permission` VALUES ('5', '上传课程', '2', '/addcoursedescribe');
INSERT INTO `permission` VALUES ('6', '修改课程', '2', '/updatecourse');
INSERT INTO `permission` VALUES ('7', '信息修改', '3', '/updatemessage');
INSERT INTO `permission` VALUES ('8', '信息查看', '3', '/seemessage');
INSERT INTO `permission` VALUES ('9', '发布课程', '2', '/publishcourse');
INSERT INTO `permission` VALUES ('11', '消息管理', '3', '/tomessageadminindex');

-- ----------------------------
-- Table structure for `permission_group`
-- ----------------------------
DROP TABLE IF EXISTS `permission_group`;
CREATE TABLE `permission_group` (
  `permission_group_id` int(11) NOT NULL AUTO_INCREMENT,
  `permission_group_name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`permission_group_id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of permission_group
-- ----------------------------
INSERT INTO `permission_group` VALUES ('1', '用户管理');
INSERT INTO `permission_group` VALUES ('2', '课程管理');
INSERT INTO `permission_group` VALUES ('3', '个人信息管理');

-- ----------------------------
-- Table structure for `role`
-- ----------------------------
DROP TABLE IF EXISTS `role`;
CREATE TABLE `role` (
  `rid` int(11) NOT NULL AUTO_INCREMENT,
  `rname` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`rid`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of role
-- ----------------------------
INSERT INTO `role` VALUES ('1', '管理员');
INSERT INTO `role` VALUES ('2', '教员');
INSERT INTO `role` VALUES ('3', '普通用户');
INSERT INTO `role` VALUES ('4', 'vip教员');
INSERT INTO `role` VALUES ('5', 'vip用户');

-- ----------------------------
-- Table structure for `role_apply`
-- ----------------------------
DROP TABLE IF EXISTS `role_apply`;
CREATE TABLE `role_apply` (
  `uid` int(11) NOT NULL,
  `applytime` datetime NOT NULL,
  `applystatus` int(11) NOT NULL COMMENT '0为已提交 1为审核通过 2 为审核失败',
  `applydescribe` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of role_apply
-- ----------------------------

-- ----------------------------
-- Table structure for `r_p`
-- ----------------------------
DROP TABLE IF EXISTS `r_p`;
CREATE TABLE `r_p` (
  `rid` int(11) DEFAULT NULL,
  `pid` int(11) DEFAULT NULL,
  `beizhu` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of r_p
-- ----------------------------
INSERT INTO `r_p` VALUES ('1', '1', '管理员权限');
INSERT INTO `r_p` VALUES ('1', '2', '管理员黑名单');
INSERT INTO `r_p` VALUES ('1', '3', '管理员课程审核');
INSERT INTO `r_p` VALUES ('1', '4', '管理员评价审核');
INSERT INTO `r_p` VALUES ('2', '5', '教员自己上传课程');
INSERT INTO `r_p` VALUES ('2', '6', '教员查看操作自己的课程');
INSERT INTO `r_p` VALUES ('2', '7', '教员信息修改');
INSERT INTO `r_p` VALUES ('1', '7', '管理员信息修改');
INSERT INTO `r_p` VALUES ('1', '8', '管理员信息查看');
INSERT INTO `r_p` VALUES ('2', '8', '教员信息查看');
INSERT INTO `r_p` VALUES ('2', '9', '教员发布课程');
INSERT INTO `r_p` VALUES ('2', '10', '教员的课程评价');
INSERT INTO `r_p` VALUES ('1', '11', '管理员消息管理');
INSERT INTO `r_p` VALUES ('2', '11', '教员消息管理');
INSERT INTO `r_p` VALUES ('3', '7', '普通用户信息修改');
INSERT INTO `r_p` VALUES ('3', '8', '普通用户信息查看');
INSERT INTO `r_p` VALUES ('3', '11', '普通用户消息管理');
INSERT INTO `r_p` VALUES ('3', '12', '用户报名的课程');
INSERT INTO `r_p` VALUES ('2', '12', '教员报名的课程');
INSERT INTO `r_p` VALUES ('1', '12', '管理员报名的课程');

-- ----------------------------
-- Table structure for `user`
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `uid` int(11) NOT NULL AUTO_INCREMENT,
  `phone` varchar(255) DEFAULT NULL,
  `upwd` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`uid`)
) ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES ('1', '111', '111');
INSERT INTO `user` VALUES ('2', '222', '222');
INSERT INTO `user` VALUES ('16', '18438595560', '111');

-- ----------------------------
-- Table structure for `userinfo`
-- ----------------------------
DROP TABLE IF EXISTS `userinfo`;
CREATE TABLE `userinfo` (
  `userinfoid` int(11) NOT NULL AUTO_INCREMENT,
  `uname` varchar(255) DEFAULT NULL,
  `uid` int(11) DEFAULT NULL,
  `imgpath` varchar(255) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `nickname` varchar(255) DEFAULT NULL,
  `modifytime` datetime DEFAULT NULL,
  `introduce` varchar(255) DEFAULT NULL,
  `idcard` varchar(255) DEFAULT NULL,
  `createtime` datetime DEFAULT NULL,
  PRIMARY KEY (`userinfoid`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of userinfo
-- ----------------------------
INSERT INTO `userinfo` VALUES ('1', '徐健康', '1', '/img/1555339931347图片.jpg', '1241450485@qq.com', '爱你一万年', '2019-04-20 13:24:05', '我是一名高级人才。有车，有房，有对象。', '410521199712193514', '2019-04-01 17:51:18');
INSERT INTO `userinfo` VALUES ('2', '张彦涛', '2', '/img/1555835201594firstimg.jpg', '1234@qq.com', '我爱你', '2019-04-21 14:43:51', '我们是好朋友a', 'bbb', '2019-04-03 17:52:41');
INSERT INTO `userinfo` VALUES ('12', '新用户18438595560', '16', '/img/firstimg.jpg', '18438595560未提交有效邮箱信息', '新用户18438595560', null, '无', '无', '2019-05-02 06:38:27');

-- ----------------------------
-- Table structure for `u_r`
-- ----------------------------
DROP TABLE IF EXISTS `u_r`;
CREATE TABLE `u_r` (
  `uid` int(11) DEFAULT NULL,
  `rid` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of u_r
-- ----------------------------
INSERT INTO `u_r` VALUES ('1', '1');
INSERT INTO `u_r` VALUES ('2', '2');
INSERT INTO `u_r` VALUES ('16', '3');
