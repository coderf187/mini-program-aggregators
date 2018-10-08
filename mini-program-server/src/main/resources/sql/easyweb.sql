/*
 Navicat Premium Data Transfer

 Source Server         : localhost_docker
 Source Server Type    : MySQL
 Source Server Version : 50640
 Source Host           : localhost
 Source Database       : easyweb

 Target Server Type    : MySQL
 Target Server Version : 50640
 File Encoding         : utf-8

 Date: 10/08/2018 15:11:21 PM
*/
CREATE DATABASE /*!32312 IF NOT EXISTS*/`easyweb` /*!40100 DEFAULT CHARACTER SET utf8mb4 */;

USE `easyweb`;

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
--  Table structure for `sys_authorities`
-- ----------------------------
DROP TABLE IF EXISTS `sys_authorities`;
CREATE TABLE `sys_authorities` (
  `authority_name` varchar(50) NOT NULL COMMENT '名称',
  `authority` varchar(50) NOT NULL COMMENT '授权标识',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`authority`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 CHECKSUM=1 DELAY_KEY_WRITE=1 ROW_FORMAT=DYNAMIC COMMENT='权限表';

-- ----------------------------
--  Records of `sys_authorities`
-- ----------------------------
BEGIN;
INSERT INTO `sys_authorities` VALUES ('移除角色权限', 'delete:/authorities/role', '2018-06-29 11:05:41'), ('删除角色', 'delete:/role/{id}', '2018-06-29 11:05:41'), ('查询所有权限', 'get:/authorities', '2018-06-29 11:05:41'), ('查询所有登录日志', 'get:/loginRecord', '2018-06-29 11:05:41'), ('查询所有角色', 'get:/role', '2018-06-29 11:05:41'), ('获取个人信息', 'get:/userInfo', '2018-06-29 11:05:41'), ('给角色添加权限', 'post:/authorities/role', '2018-06-29 11:05:41'), ('同步权限', 'post:/authorities/sync', '2018-06-29 11:05:41'), ('添加角色', 'post:/role', '2018-06-29 11:05:41'), ('添加用户', 'post:/user', '2018-06-29 11:05:41'), ('查询所有用户', 'post:/user/query', '2018-06-29 11:05:41'), ('修改角色', 'put:/role', '2018-06-29 11:05:41'), ('修改用户', 'put:/user', '2018-06-29 11:05:41'), ('修改自己密码', 'put:/user/psw', '2018-06-29 11:05:41'), ('重置密码', 'put:/user/psw/{id}', '2018-06-29 11:05:41'), ('修改用户状态', 'put:/user/state', '2018-06-29 11:05:41');
COMMIT;

-- ----------------------------
--  Table structure for `sys_dictionary`
-- ----------------------------
DROP TABLE IF EXISTS `sys_dictionary`;
CREATE TABLE `sys_dictionary` (
  `dict_code` varchar(32) NOT NULL COMMENT '字典主键',
  `dict_name` varchar(50) NOT NULL COMMENT '字典名称',
  `description` varchar(200) DEFAULT NULL COMMENT '描述',
  PRIMARY KEY (`dict_code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='字典';

-- ----------------------------
--  Records of `sys_dictionary`
-- ----------------------------
BEGIN;
INSERT INTO `sys_dictionary` VALUES ('1', '性别', null);
COMMIT;

-- ----------------------------
--  Table structure for `sys_dictionarydata`
-- ----------------------------
DROP TABLE IF EXISTS `sys_dictionarydata`;
CREATE TABLE `sys_dictionarydata` (
  `dictdata_code` varchar(32) NOT NULL COMMENT '字典项主键',
  `dict_code` varchar(32) NOT NULL COMMENT '字典主键',
  `dictdata_name` varchar(50) NOT NULL COMMENT '字典项值',
  `is_delete` int(11) NOT NULL DEFAULT '0' COMMENT '是否删除',
  `sort_number` int(11) NOT NULL COMMENT '排序',
  `description` varchar(200) DEFAULT NULL COMMENT '描述',
  PRIMARY KEY (`dictdata_code`),
  KEY `FK_Reference_36` (`dict_code`),
  CONSTRAINT `FK_Reference_36` FOREIGN KEY (`dict_code`) REFERENCES `sys_dictionary` (`dict_code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 CHECKSUM=1 DELAY_KEY_WRITE=1 ROW_FORMAT=DYNAMIC COMMENT='字典项';

-- ----------------------------
--  Records of `sys_dictionarydata`
-- ----------------------------
BEGIN;
INSERT INTO `sys_dictionarydata` VALUES ('1_1', '1', '男', '0', '0', null), ('1_2', '1', '女', '0', '1', null);
COMMIT;

-- ----------------------------
--  Table structure for `sys_login_record`
-- ----------------------------
DROP TABLE IF EXISTS `sys_login_record`;
CREATE TABLE `sys_login_record` (
  `id` varchar(32) NOT NULL COMMENT '主键',
  `user_id` varchar(32) NOT NULL COMMENT '用户id',
  `os_name` varchar(200) DEFAULT NULL COMMENT '操作系统',
  `device` varchar(200) DEFAULT NULL COMMENT '设备名',
  `browser_type` varchar(200) DEFAULT NULL COMMENT '浏览器类型',
  `ip_address` varchar(200) DEFAULT NULL COMMENT 'ip地址',
  `create_time` datetime NOT NULL COMMENT '登录时间',
  PRIMARY KEY (`id`),
  KEY `FK_sys_login_record_user` (`user_id`),
  CONSTRAINT `FK_sys_login_record_user` FOREIGN KEY (`user_id`) REFERENCES `sys_user` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 CHECKSUM=1 DELAY_KEY_WRITE=1 ROW_FORMAT=DYNAMIC COMMENT='登录日志表';

-- ----------------------------
--  Table structure for `sys_person`
-- ----------------------------
DROP TABLE IF EXISTS `sys_person`;
CREATE TABLE `sys_person` (
  `person_id` varchar(32) NOT NULL COMMENT '人员id',
  `true_name` varchar(20) DEFAULT NULL COMMENT '真实姓名',
  `department_id` varchar(32) DEFAULT NULL COMMENT '部门id',
  `position_id` varchar(32) DEFAULT NULL COMMENT '职位id',
  `birthday` date DEFAULT NULL COMMENT '出生日期',
  `id_card` varchar(20) DEFAULT NULL COMMENT '身份证号',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`person_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 CHECKSUM=1 DELAY_KEY_WRITE=1 ROW_FORMAT=DYNAMIC COMMENT='人员表';

-- ----------------------------
--  Table structure for `sys_role`
-- ----------------------------
DROP TABLE IF EXISTS `sys_role`;
CREATE TABLE `sys_role` (
  `role_id` varchar(32) NOT NULL COMMENT '角色id',
  `role_name` varchar(20) NOT NULL COMMENT '角色名称',
  `comments` varchar(100) DEFAULT NULL COMMENT '备注',
  `is_delete` int(11) NOT NULL DEFAULT '0' COMMENT '是否删除，0否，1是',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`role_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 CHECKSUM=1 DELAY_KEY_WRITE=1 ROW_FORMAT=DYNAMIC COMMENT='角色表';

-- ----------------------------
--  Records of `sys_role`
-- ----------------------------
BEGIN;
INSERT INTO `sys_role` VALUES ('admin', '管理员', '系统管理员', '0', '2018-02-23 08:31:22', '2018-10-08 06:42:43'), ('fjUQIDgP', 'dsadaad', 'asdsadadllll', '1', '2018-06-13 11:09:46', '2018-06-28 11:09:49'), ('user', '普通用户', '系统普通用户', '0', '2018-02-23 08:32:11', '2018-02-23 11:19:08');
COMMIT;

-- ----------------------------
--  Table structure for `sys_role_authorities`
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_authorities`;
CREATE TABLE `sys_role_authorities` (
  `id` varchar(32) NOT NULL,
  `role_id` varchar(32) NOT NULL COMMENT '角色id',
  `authority` varchar(50) NOT NULL COMMENT '权限id',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`),
  KEY `FK_sys_role_permission_pm` (`authority`),
  KEY `FK_sys_role_permission_role` (`role_id`),
  CONSTRAINT `FK_sys_role_permission_role` FOREIGN KEY (`role_id`) REFERENCES `sys_role` (`role_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 CHECKSUM=1 DELAY_KEY_WRITE=1 ROW_FORMAT=DYNAMIC COMMENT='角色权限关联表';

-- ----------------------------
--  Records of `sys_role_authorities`
-- ----------------------------
BEGIN;
INSERT INTO `sys_role_authorities` VALUES ('2HQfXFZg', 'admin', 'put:/role', '2018-06-27 10:00:19'), ('5b264dedd2f84ca9b613409e7222863b', 'admin', 'delete:/role/{id}', '2018-10-08 15:08:15'), ('8Rhrysco', 'admin', 'get:/authorities', '2018-06-27 10:00:15'), ('97eb30035d794d98a866193c21d3bb4c', 'admin', 'delete:/authorities/role', '2018-10-08 15:08:16'), ('9eXlTGTj', 'admin', 'post:/authorities/sync', '2018-06-27 10:00:17'), ('9qnTrDdm', 'admin', 'post:/user/query', '2018-06-27 10:00:19'), ('cAtC3FA0', 'admin', 'put:/user/psw', '2018-06-27 10:00:20'), ('csaHHw4z', 'admin', 'put:/user', '2018-06-27 10:00:19'), ('jmQ7CmsB', 'admin', 'get:/loginRecord', '2018-06-27 10:00:15'), ('NtEiuLtI', 'admin', 'post:/user', '2018-06-27 10:00:18'), ('PaUfq7fy', 'admin', 'put:/user/state', '2018-06-27 10:00:21'), ('uDNvJo9q', 'admin', 'put:/user/psw/{id}', '2018-06-27 10:00:20'), ('vJ19BZln', 'admin', 'get:/userInfo', '2018-06-27 10:00:16'), ('xnIJW6zG', 'admin', 'post:/role', '2018-06-27 10:00:18'), ('xyzNzrbs', 'admin', 'post:/authorities/role', null), ('ZDklBxUv', 'admin', 'get:/role', '2018-06-27 10:00:16');
COMMIT;

-- ----------------------------
--  Table structure for `sys_user`
-- ----------------------------
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user` (
  `user_id` varchar(32) NOT NULL COMMENT '用户id',
  `username` varchar(20) NOT NULL COMMENT '账号',
  `password` varchar(128) NOT NULL COMMENT '密码',
  `nick_name` varchar(20) NOT NULL COMMENT '昵称',
  `avatar` varchar(200) DEFAULT NULL COMMENT '头像',
  `sex` varchar(1) NOT NULL DEFAULT '男' COMMENT '性别',
  `phone` varchar(12) DEFAULT NULL COMMENT '手机号',
  `email` varchar(100) DEFAULT NULL COMMENT '邮箱',
  `email_verified` int(11) DEFAULT NULL COMMENT '邮箱是否验证，0未验证，1已验证',
  `person_id` varchar(32) DEFAULT NULL COMMENT '人员id',
  `state` int(1) NOT NULL DEFAULT '0' COMMENT '状态，0正常，1冻结',
  `create_time` datetime NOT NULL COMMENT '注册时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`user_id`),
  UNIQUE KEY `user_account` (`username`),
  KEY `FK_sys_user` (`person_id`),
  CONSTRAINT `FK_sys_user` FOREIGN KEY (`person_id`) REFERENCES `sys_person` (`person_id`) ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 CHECKSUM=1 DELAY_KEY_WRITE=1 ROW_FORMAT=DYNAMIC COMMENT='用户表';

-- ----------------------------
--  Records of `sys_user`
-- ----------------------------
BEGIN;
INSERT INTO `sys_user` VALUES ('29ogl979', 'demo', '{bcrypt}$2a$10$IH.Cr34n54mgdaCzPNGQSOyXooZtIW9e8o/lg/8.Ij6ZJWEtIX2uS', 'demo', null, '男', '13625436602', null, null, null, '0', '2018-06-28 16:19:32', '2018-09-29 07:50:03'), ('admin', 'admin', '{bcrypt}$2a$10$kr/D52QCMfWTb5VHRD7o3ekrQau1zyPbJYFUyrVrQekqbp7GNScom', '管理员', '', '女', '13125062807', 'whvcse@foxmail.com', null, null, '0', '2017-08-14 14:12:36', '2018-10-08 07:09:23');
COMMIT;

-- ----------------------------
--  Table structure for `sys_user_role`
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_role`;
CREATE TABLE `sys_user_role` (
  `id` varchar(32) NOT NULL,
  `user_id` varchar(32) NOT NULL COMMENT '用户id',
  `role_id` varchar(32) NOT NULL COMMENT '角色id',
  `create_time` datetime NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_sys_user_role` (`user_id`),
  KEY `FK_sys_user_role_role` (`role_id`),
  CONSTRAINT `FK_sys_user_role` FOREIGN KEY (`user_id`) REFERENCES `sys_user` (`user_id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `FK_sys_user_role_role` FOREIGN KEY (`role_id`) REFERENCES `sys_role` (`role_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 CHECKSUM=1 DELAY_KEY_WRITE=1 ROW_FORMAT=DYNAMIC COMMENT='用户角色关联表';

-- ----------------------------
--  Records of `sys_user_role`
-- ----------------------------
BEGIN;
INSERT INTO `sys_user_role` VALUES ('me4VSGf4', '29ogl979', 'admin', '2018-09-29 15:47:23'), ('SnDXi5kX', 'admin', 'admin', '2018-05-11 12:00:44');
COMMIT;

SET FOREIGN_KEY_CHECKS = 1;
