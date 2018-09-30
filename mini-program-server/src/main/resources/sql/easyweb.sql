/*
SQLyog 企业版 - MySQL GUI v8.14 
MySQL - 5.6.34-log : Database - easyweb
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`easyweb` /*!40100 DEFAULT CHARACTER SET utf8mb4 */;

USE `easyweb`;

/*Table structure for table `sys_authorities` */

DROP TABLE IF EXISTS `sys_authorities`;

CREATE TABLE `sys_authorities` (
  `authority_name` varchar(50) NOT NULL COMMENT '名称',
  `authority` varchar(50) NOT NULL COMMENT '授权标识',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`authority`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 CHECKSUM=1 DELAY_KEY_WRITE=1 ROW_FORMAT=DYNAMIC COMMENT='权限表';

/*Data for the table `sys_authorities` */

insert  into `sys_authorities`(`authority_name`,`authority`,`create_time`) values ('移除角色权限','delete:/authorities/role','2018-06-29 11:05:41'),('删除角色','delete:/role/{id}','2018-06-29 11:05:41'),('查询所有权限','get:/authorities','2018-06-29 11:05:41'),('查询所有登录日志','get:/loginRecord','2018-06-29 11:05:41'),('查询所有角色','get:/role','2018-06-29 11:05:41'),('获取个人信息','get:/userInfo','2018-06-29 11:05:41'),('给角色添加权限','post:/authorities/role','2018-06-29 11:05:41'),('同步权限','post:/authorities/sync','2018-06-29 11:05:41'),('添加角色','post:/role','2018-06-29 11:05:41'),('添加用户','post:/user','2018-06-29 11:05:41'),('查询所有用户','post:/user/query','2018-06-29 11:05:41'),('修改角色','put:/role','2018-06-29 11:05:41'),('修改用户','put:/user','2018-06-29 11:05:41'),('修改自己密码','put:/user/psw','2018-06-29 11:05:41'),('重置密码','put:/user/psw/{id}','2018-06-29 11:05:41'),('修改用户状态','put:/user/state','2018-06-29 11:05:41');

/*Table structure for table `sys_dictionary` */

DROP TABLE IF EXISTS `sys_dictionary`;

CREATE TABLE `sys_dictionary` (
  `dict_code` varchar(8) NOT NULL COMMENT '字典主键',
  `dict_name` varchar(50) NOT NULL COMMENT '字典名称',
  `description` varchar(200) DEFAULT NULL COMMENT '描述',
  PRIMARY KEY (`dict_code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='字典';

/*Data for the table `sys_dictionary` */

insert  into `sys_dictionary`(`dict_code`,`dict_name`,`description`) values ('1','性别',NULL);

/*Table structure for table `sys_dictionarydata` */

DROP TABLE IF EXISTS `sys_dictionarydata`;

CREATE TABLE `sys_dictionarydata` (
  `dictdata_code` varchar(8) NOT NULL COMMENT '字典项主键',
  `dict_code` varchar(8) NOT NULL COMMENT '字典主键',
  `dictdata_name` varchar(50) NOT NULL COMMENT '字典项值',
  `is_delete` int(11) NOT NULL DEFAULT '0' COMMENT '是否删除',
  `sort_number` int(11) NOT NULL COMMENT '排序',
  `description` varchar(200) DEFAULT NULL COMMENT '描述',
  PRIMARY KEY (`dictdata_code`),
  KEY `FK_Reference_36` (`dict_code`),
  CONSTRAINT `FK_Reference_36` FOREIGN KEY (`dict_code`) REFERENCES `sys_dictionary` (`dict_code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 CHECKSUM=1 DELAY_KEY_WRITE=1 ROW_FORMAT=DYNAMIC COMMENT='字典项';

/*Data for the table `sys_dictionarydata` */

insert  into `sys_dictionarydata`(`dictdata_code`,`dict_code`,`dictdata_name`,`is_delete`,`sort_number`,`description`) values ('1_1','1','男',0,0,NULL),('1_2','1','女',0,1,NULL);

/*Table structure for table `sys_login_record` */

DROP TABLE IF EXISTS `sys_login_record`;

CREATE TABLE `sys_login_record` (
  `id` varchar(8) NOT NULL COMMENT '主键',
  `user_id` varchar(8) NOT NULL COMMENT '用户id',
  `os_name` varchar(200) DEFAULT NULL COMMENT '操作系统',
  `device` varchar(200) DEFAULT NULL COMMENT '设备名',
  `browser_type` varchar(200) DEFAULT NULL COMMENT '浏览器类型',
  `ip_address` varchar(200) DEFAULT NULL COMMENT 'ip地址',
  `create_time` datetime NOT NULL COMMENT '登录时间',
  PRIMARY KEY (`id`),
  KEY `FK_sys_login_record_user` (`user_id`),
  CONSTRAINT `FK_sys_login_record_user` FOREIGN KEY (`user_id`) REFERENCES `sys_user` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 CHECKSUM=1 DELAY_KEY_WRITE=1 ROW_FORMAT=DYNAMIC COMMENT='登录日志表';

/*Data for the table `sys_login_record` */

/*Table structure for table `sys_person` */

DROP TABLE IF EXISTS `sys_person`;

CREATE TABLE `sys_person` (
  `person_id` varchar(8) NOT NULL COMMENT '人员id',
  `true_name` varchar(20) DEFAULT NULL COMMENT '真实姓名',
  `department_id` varchar(8) DEFAULT NULL COMMENT '部门id',
  `position_id` varchar(8) DEFAULT NULL COMMENT '职位id',
  `birthday` date DEFAULT NULL COMMENT '出生日期',
  `id_card` varchar(20) DEFAULT NULL COMMENT '身份证号',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`person_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 CHECKSUM=1 DELAY_KEY_WRITE=1 ROW_FORMAT=DYNAMIC COMMENT='人员表';

/*Data for the table `sys_person` */

/*Table structure for table `sys_role` */

DROP TABLE IF EXISTS `sys_role`;

CREATE TABLE `sys_role` (
  `role_id` varchar(8) NOT NULL COMMENT '角色id',
  `role_name` varchar(20) NOT NULL COMMENT '角色名称',
  `comments` varchar(100) DEFAULT NULL COMMENT '备注',
  `is_delete` int(11) NOT NULL DEFAULT '0' COMMENT '是否删除，0否，1是',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`role_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 CHECKSUM=1 DELAY_KEY_WRITE=1 ROW_FORMAT=DYNAMIC COMMENT='角色表';

/*Data for the table `sys_role` */

insert  into `sys_role`(`role_id`,`role_name`,`comments`,`is_delete`,`create_time`,`update_time`) values ('admin','管理员','系统管理员',0,'2018-02-23 08:31:22','2018-02-23 11:18:26'),('fjUQIDgP','dsadaad','asdsadadllll',1,'2018-06-13 11:09:46','2018-06-28 11:09:49'),('user','普通用户','系统普通用户',0,'2018-02-23 08:32:11','2018-02-23 11:19:08');

/*Table structure for table `sys_role_authorities` */

DROP TABLE IF EXISTS `sys_role_authorities`;

CREATE TABLE `sys_role_authorities` (
  `id` varchar(8) NOT NULL,
  `role_id` varchar(8) NOT NULL COMMENT '角色id',
  `authority` varchar(50) NOT NULL COMMENT '权限id',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`),
  KEY `FK_sys_role_permission_pm` (`authority`),
  KEY `FK_sys_role_permission_role` (`role_id`),
  CONSTRAINT `FK_sys_role_permission_role` FOREIGN KEY (`role_id`) REFERENCES `sys_role` (`role_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 CHECKSUM=1 DELAY_KEY_WRITE=1 ROW_FORMAT=DYNAMIC COMMENT='角色权限关联表';

/*Data for the table `sys_role_authorities` */

insert  into `sys_role_authorities`(`id`,`role_id`,`authority`,`create_time`) values ('1VhPjXCz','admin','delete:/authorities/role','2018-06-29 11:05:52'),('2HQfXFZg','admin','put:/role','2018-06-27 10:00:19'),('8Rhrysco','admin','get:/authorities','2018-06-27 10:00:15'),('9eXlTGTj','admin','post:/authorities/sync','2018-06-27 10:00:17'),('9qnTrDdm','admin','post:/user/query','2018-06-27 10:00:19'),('cAtC3FA0','admin','put:/user/psw','2018-06-27 10:00:20'),('csaHHw4z','admin','put:/user','2018-06-27 10:00:19'),('hPhTwVU6','admin','delete:/role/{id}','2018-06-28 11:09:04'),('jmQ7CmsB','admin','get:/loginRecord','2018-06-27 10:00:15'),('NtEiuLtI','admin','post:/user','2018-06-27 10:00:18'),('PaUfq7fy','admin','put:/user/state','2018-06-27 10:00:21'),('uDNvJo9q','admin','put:/user/psw/{id}','2018-06-27 10:00:20'),('vJ19BZln','admin','get:/userInfo','2018-06-27 10:00:16'),('xnIJW6zG','admin','post:/role','2018-06-27 10:00:18'),('xyzNzrbs','admin','post:/authorities/role',NULL),('ZDklBxUv','admin','get:/role','2018-06-27 10:00:16');

/*Table structure for table `sys_user` */

DROP TABLE IF EXISTS `sys_user`;

CREATE TABLE `sys_user` (
  `user_id` varchar(8) NOT NULL COMMENT '用户id',
  `username` varchar(20) NOT NULL COMMENT '账号',
  `password` varchar(128) NOT NULL COMMENT '密码',
  `nick_name` varchar(20) NOT NULL COMMENT '昵称',
  `avatar` varchar(200) DEFAULT NULL COMMENT '头像',
  `sex` varchar(1) NOT NULL DEFAULT '男' COMMENT '性别',
  `phone` varchar(12) DEFAULT NULL COMMENT '手机号',
  `email` varchar(100) DEFAULT NULL COMMENT '邮箱',
  `email_verified` int(11) DEFAULT NULL COMMENT '邮箱是否验证，0未验证，1已验证',
  `person_id` varchar(8) DEFAULT NULL COMMENT '人员id',
  `state` int(1) NOT NULL DEFAULT '0' COMMENT '状态，0正常，1冻结',
  `create_time` datetime NOT NULL COMMENT '注册时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`user_id`),
  UNIQUE KEY `user_account` (`username`),
  KEY `FK_sys_user` (`person_id`),
  CONSTRAINT `FK_sys_user` FOREIGN KEY (`person_id`) REFERENCES `sys_person` (`person_id`) ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 CHECKSUM=1 DELAY_KEY_WRITE=1 ROW_FORMAT=DYNAMIC COMMENT='用户表';

/*Data for the table `sys_user` */

insert  into `sys_user`(`user_id`,`username`,`password`,`nick_name`,`avatar`,`sex`,`phone`,`email`,`email_verified`,`person_id`,`state`,`create_time`,`update_time`) values ('29ogl979','demo','{bcrypt}$2a$10$kKqZaoluuMjfvusTuB5Z6e/RPhQgCXtkmBdhGokWPIi0RdhoWxD42','demo',NULL,'男','13625436602',NULL,NULL,NULL,0,'2018-06-28 16:19:32','2018-06-29 13:52:52'),('admin','admin','{bcrypt}$2a$10$qrL6p6FKEitfGQnVRa.PPO.PsOJ4Dj9BSjEXll6fnruqBXABb/51O','管理员','','女','13125062807','whvcse@foxmail.com',NULL,NULL,0,'2017-08-14 14:12:36','2018-06-29 17:51:13');

/*Table structure for table `sys_user_role` */

DROP TABLE IF EXISTS `sys_user_role`;

CREATE TABLE `sys_user_role` (
  `id` varchar(8) NOT NULL,
  `user_id` varchar(8) NOT NULL COMMENT '用户id',
  `role_id` varchar(8) NOT NULL COMMENT '角色id',
  `create_time` datetime NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_sys_user_role` (`user_id`),
  KEY `FK_sys_user_role_role` (`role_id`),
  CONSTRAINT `FK_sys_user_role` FOREIGN KEY (`user_id`) REFERENCES `sys_user` (`user_id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `FK_sys_user_role_role` FOREIGN KEY (`role_id`) REFERENCES `sys_role` (`role_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 CHECKSUM=1 DELAY_KEY_WRITE=1 ROW_FORMAT=DYNAMIC COMMENT='用户角色关联表';

/*Data for the table `sys_user_role` */

insert  into `sys_user_role`(`id`,`user_id`,`role_id`,`create_time`) values ('SnDXi5kX','admin','admin','2018-05-11 12:00:44'),('ZuOlgqZq','29ogl979','user','2018-06-28 16:25:23');

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
