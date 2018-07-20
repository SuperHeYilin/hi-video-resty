/*
Navicat MySQL Data Transfer

Source Server         : 本地服务器
Source Server Version : 50520
Source Host           : localhost:3306
Source Database       : xunjian

Target Server Type    : MYSQL
Target Server Version : 50520
File Encoding         : 65001

Date: 2017-03-07 16:51:56
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for client_person
-- ----------------------------
DROP TABLE IF EXISTS `client_person`;
CREATE TABLE `client_person` (
  `id` int(16) NOT NULL AUTO_INCREMENT,
  `user` int(16) DEFAULT NULL COMMENT '用户id',
  `avatar` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '头像',
  `nickname` varchar(30) COLLATE utf8_bin DEFAULT NULL COMMENT '用户昵称',
  `sex` char(1) COLLATE utf8_bin DEFAULT NULL COMMENT '性别',
  `company` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '公司',
  `education` varchar(30) COLLATE utf8_bin DEFAULT NULL COMMENT '学历',
  `age` int(10) DEFAULT NULL COMMENT '年龄',
  `birthday` varchar(30) COLLATE utf8_bin DEFAULT NULL COMMENT '生日',
  `hobby` varchar(100) COLLATE utf8_bin DEFAULT NULL COMMENT '爱好',
  `city` varchar(30) COLLATE utf8_bin DEFAULT NULL COMMENT '城市',
  `state` char(1) COLLATE utf8_bin DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `user` (`user`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- ----------------------------
-- Records of client_person
-- ----------------------------

-- ----------------------------
-- Table structure for client_user
-- ----------------------------
DROP TABLE IF EXISTS `client_user`;
CREATE TABLE `client_user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `nickname` varchar(30) COLLATE utf8_bin DEFAULT NULL COMMENT '用户名',
  `phonenum` varchar(20) COLLATE utf8_bin DEFAULT NULL COMMENT '手机号码',
  `email` varchar(30) COLLATE utf8_bin DEFAULT NULL COMMENT '邮箱地址',
  `realname` varchar(30) COLLATE utf8_bin DEFAULT NULL COMMENT '真实姓名',
  `pwd` varchar(200) COLLATE utf8_bin DEFAULT NULL COMMENT '登陆密码',
  `state` char(1) COLLATE utf8_bin DEFAULT NULL COMMENT '用户状态(0:正常 1:暂停 2:异常 3:停用)',
  `isvali` char(1) COLLATE utf8_bin DEFAULT NULL COMMENT '是否验证',
  `type` char(1) COLLATE utf8_bin DEFAULT NULL COMMENT '用户类型(0:普通用户 1:收衣人 2:社区主管)',
  `create_time` varchar(30) COLLATE utf8_bin DEFAULT NULL COMMENT '创建时间',
  `last_time` varchar(30) COLLATE utf8_bin DEFAULT NULL COMMENT '最后一次登陆时间',
  `online` char(1) COLLATE utf8_bin DEFAULT NULL COMMENT '在线状态(0:离线，1:在线)',
  `del` char(1) COLLATE utf8_bin DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- ----------------------------
-- Records of client_user
-- ----------------------------

-- ----------------------------
-- Table structure for client_user_login_log
-- ----------------------------
DROP TABLE IF EXISTS `client_user_login_log`;
CREATE TABLE `client_user_login_log` (
  `id` int(8) NOT NULL AUTO_INCREMENT,
  `ruid` int(8) DEFAULT NULL COMMENT '用户',
  `platform` char(1) COLLATE utf8_bin DEFAULT NULL COMMENT '平台类型 0苹果，1安卓',
  `device_id` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '设备id',
  `device_type` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '设备类型',
  `time` varchar(30) COLLATE utf8_bin DEFAULT NULL COMMENT '时间',
  `latitude` varchar(30) COLLATE utf8_bin DEFAULT NULL COMMENT '经度',
  `longitude` varchar(30) COLLATE utf8_bin DEFAULT NULL COMMENT '纬度',
  `location` varchar(50) COLLATE utf8_bin DEFAULT NULL COMMENT '地理位置',
  PRIMARY KEY (`id`),
  KEY `ruid` (`ruid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- ----------------------------
-- Records of client_user_login_log
-- ----------------------------

-- ----------------------------
-- Table structure for client_user_sms
-- ----------------------------
DROP TABLE IF EXISTS `client_user_sms`;
CREATE TABLE `client_user_sms` (
  `id` int(16) NOT NULL AUTO_INCREMENT,
  `phone` varchar(16) COLLATE utf8_bin DEFAULT NULL COMMENT '手机号',
  `code` varchar(20) COLLATE utf8_bin DEFAULT NULL COMMENT '短信验证码',
  `limit_time` int(8) DEFAULT NULL COMMENT '验证码有效期(单位分钟)',
  `create_time` varchar(30) COLLATE utf8_bin DEFAULT NULL COMMENT '创建时间',
  `vali_time` varchar(30) COLLATE utf8_bin DEFAULT NULL COMMENT '验证时间',
  `is_vali` char(1) COLLATE utf8_bin DEFAULT NULL COMMENT '是否已验证',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- ----------------------------
-- Records of client_user_sms
-- ----------------------------

-- ----------------------------
-- Table structure for pt_media
-- ----------------------------
DROP TABLE IF EXISTS `pt_media`;
CREATE TABLE `pt_media` (
  `id` int(8) NOT NULL AUTO_INCREMENT,
  `original_name` varchar(100) COLLATE utf8_bin DEFAULT NULL COMMENT '原文件名称',
  `file_name` varchar(100) COLLATE utf8_bin DEFAULT NULL COMMENT '本地文件名称',
  `size` int(10) DEFAULT NULL,
  `local_path` varchar(200) COLLATE utf8_bin DEFAULT NULL COMMENT '保存路径',
  `mime_type` varchar(50) COLLATE utf8_bin DEFAULT NULL COMMENT '文件类型',
  `create_time` varchar(30) COLLATE utf8_bin DEFAULT NULL COMMENT '创建时间',
  `del` char(1) COLLATE utf8_bin DEFAULT NULL COMMENT '0 可用 1不可用',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- ----------------------------
-- Records of pt_media
-- ----------------------------

-- ----------------------------
-- Table structure for pt_media_relation
-- ----------------------------
DROP TABLE IF EXISTS `pt_media_relation`;
CREATE TABLE `pt_media_relation` (
  `id` int(8) NOT NULL AUTO_INCREMENT,
  `fileID` int(8) DEFAULT NULL,
  `moduleKey` varchar(20) COLLATE utf8_bin DEFAULT NULL,
  `moduleID` varchar(20) COLLATE utf8_bin DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- ----------------------------
-- Records of pt_media_relation
-- ----------------------------

-- ----------------------------
-- Table structure for pt_menu
-- ----------------------------
DROP TABLE IF EXISTS `pt_menu`;
CREATE TABLE `pt_menu` (
  `id` bigint(16) NOT NULL AUTO_INCREMENT,
  `menu_name` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '菜单名称',
  `menu_icon` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '菜单图标',
  `menu_css` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '菜单样式',
  `menu_url` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '菜单路径',
  `menu_badge` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '菜单通知标记',
  `menu_index` int(10) DEFAULT NULL COMMENT '菜单索引',
  `p_menu` bigint(16) DEFAULT NULL COMMENT '父级菜单',
  `create_at` varchar(30) COLLATE utf8_bin DEFAULT NULL COMMENT '创建时间',
  `update_at` varchar(30) COLLATE utf8_bin DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=67 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- ----------------------------
-- Records of pt_menu
-- ----------------------------
INSERT INTO `pt_menu` VALUES ('18', '----上帝模式', 'A', null, '', 'asdasd', null, null, '2016-08-21 23:45:25', '2016-08-31 16:02:32');
INSERT INTO `pt_menu` VALUES ('19', '系统设置', 'A', null, '', '', null, '18', '2016-08-21 23:46:24', '2016-08-31 16:02:47');
INSERT INTO `pt_menu` VALUES ('20', '用户管理', '', null, 'html/platform/user/user_index.html', '', null, '19', '2016-08-21 23:46:35', '2016-08-31 16:01:48');
INSERT INTO `pt_menu` VALUES ('21', '角色管理', '', null, 'html/platform/role/role.html', '', null, '19', '2016-08-21 23:46:44', '2016-09-16 20:49:04');
INSERT INTO `pt_menu` VALUES ('22', '菜单管理', '', null, 'html/platform/menus/menus.html', '', null, '19', '2016-08-21 23:46:53', '2016-08-22 01:14:23');
INSERT INTO `pt_menu` VALUES ('23', '权限管理', 'B', null, '', '', null, '18', '2016-08-21 23:47:07', '2016-08-31 16:02:51');
INSERT INTO `pt_menu` VALUES ('24', '角色权限', '', null, 'html/platform/role/role_auth.html', '', null, '23', '2016-08-21 23:48:52', '2016-09-16 22:43:00');
INSERT INTO `pt_menu` VALUES ('26', '----后台功能', null, null, null, null, null, null, '2016-08-22 15:37:22', '2016-08-22 15:37:22');
INSERT INTO `pt_menu` VALUES ('28', '集团简介', '', null, 'html/platform/about/edit.html', '', null, '27', '2016-08-22 15:38:23', '2016-08-25 18:05:25');
INSERT INTO `pt_menu` VALUES ('29', '楼盘信息', '', null, 'html/platform/about/more/lplist.html', '', null, '27', '2016-08-22 15:38:33', '2016-08-25 19:43:31');
INSERT INTO `pt_menu` VALUES ('30', '集团公告', '', null, 'html/platform/about/more/jtlist.html', '', null, '27', '2016-08-22 15:38:40', '2016-08-25 22:03:07');
INSERT INTO `pt_menu` VALUES ('31', '招聘信息', '', null, 'html/platform/about/more/zplist.html', '', null, '27', '2016-08-22 15:38:47', '2016-08-25 22:14:00');
INSERT INTO `pt_menu` VALUES ('33', '活动信息', '', null, 'html/platform/activity/list.html', '', null, '32', '2016-08-22 15:39:21', '2016-10-10 14:19:34');
INSERT INTO `pt_menu` VALUES ('34', '中奖记录', '', null, 'http://qx.fellb.com/diffpi-qxwj-admin-html/html/dj/index.html', '', null, '32', '2016-08-22 15:39:29', '2016-10-22 00:08:11');
INSERT INTO `pt_menu` VALUES ('36', '社区公告', '', null, 'html/module/bulletin/bulletin_index.html', '', null, '35', '2016-08-22 15:39:55', '2016-08-31 16:54:43');
INSERT INTO `pt_menu` VALUES ('37', '便民电话', '', null, 'html/module/cviphone/cviphone_index.html', '', null, '35', '2016-08-22 15:40:06', '2016-09-01 16:50:35');
INSERT INTO `pt_menu` VALUES ('38', '物业报修', '', null, 'html/module/repair/repair_index.html', '', null, '35', '2016-08-22 15:40:13', '2016-09-12 23:30:48');
INSERT INTO `pt_menu` VALUES ('42', '轮播图设置', '', null, 'html/platform/homeimage/list.html', '', null, '41', '2016-09-02 13:18:28', '2016-09-02 13:18:41');
INSERT INTO `pt_menu` VALUES ('43', '开放平台', 'C', null, '', '', null, '18', '2016-09-18 17:22:37', '2016-09-18 17:23:02');
INSERT INTO `pt_menu` VALUES ('44', '微信', '', null, 'html/rop/wechat/account/account_index.html', '', null, '43', '2016-09-18 17:23:16', '2016-09-18 17:23:44');
INSERT INTO `pt_menu` VALUES ('45', '支付宝', '', null, 'html/rop/alipay/account/account_index.html', '', null, '43', '2016-09-18 17:23:55', '2016-09-18 17:25:03');
INSERT INTO `pt_menu` VALUES ('47', '费用管理', '', null, 'html/module/price/list.html', '', null, '46', '2016-12-05 17:47:29', '2016-12-05 17:48:09');
INSERT INTO `pt_menu` VALUES ('49', '会员缴费信息', 'D', null, 'html/module/userinfo/list.html', '', null, '48', '2016-12-07 10:26:59', '2016-12-07 10:28:32');
INSERT INTO `pt_menu` VALUES ('50', '会员购买记录', 'D', null, 'html/module/userpayinfo/list.html', '', null, '48', '2016-12-08 09:56:52', '2016-12-08 11:14:20');
INSERT INTO `pt_menu` VALUES ('54', '套餐管理', 'C', null, 'html\\module\\price\\price_pk_list.html', '', null, '46', '2016-12-20 10:16:06', '2016-12-20 10:16:38');
INSERT INTO `pt_menu` VALUES ('55', '会员信息', '', null, 'html/rop/wechat/account/account_user.html', '', null, '48', '2016-12-24 14:52:32', '2016-12-24 14:52:40');
INSERT INTO `pt_menu` VALUES ('60', '统计', '', null, 'html/module/statistics/statistics_index.html', '', null, '59', '2017-01-04 17:46:44', '2017-01-04 17:47:01');
INSERT INTO `pt_menu` VALUES ('62', '投票设置', '', null, 'html/module/vote/vote_list.html', '', null, '61', '2017-01-09 17:44:58', '2017-01-09 17:45:28');
INSERT INTO `pt_menu` VALUES ('63', '套餐管理', '', null, 'html/module/packagelist/package_list.html', '', null, '56', '2017-03-05 15:30:45', '2017-03-05 15:30:55');
INSERT INTO `pt_menu` VALUES ('64', '货卡统计', '', null, 'html\\module\\deliverycard\\deliverycard_statistics.html', '', null, '59', '2017-03-05 15:34:16', '2017-03-05 15:36:23');
INSERT INTO `pt_menu` VALUES ('66', '货卡管理', '', null, 'html\\module\\deliverycard\\deliverycard_index.html', '', null, '65', '2017-03-05 15:37:17', '2017-03-05 15:37:33');

-- ----------------------------
-- Table structure for pt_menu_role
-- ----------------------------
DROP TABLE IF EXISTS `pt_menu_role`;
CREATE TABLE `pt_menu_role` (
  `id` bigint(16) NOT NULL AUTO_INCREMENT,
  `role_id` bigint(20) DEFAULT NULL,
  `menu_id` bigint(16) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1649 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- ----------------------------
-- Records of pt_menu_role
-- ----------------------------
INSERT INTO `pt_menu_role` VALUES ('439', '9', '26');
INSERT INTO `pt_menu_role` VALUES ('440', '9', '32');
INSERT INTO `pt_menu_role` VALUES ('441', '9', '34');
INSERT INTO `pt_menu_role` VALUES ('753', '6', '18');
INSERT INTO `pt_menu_role` VALUES ('754', '6', '18');
INSERT INTO `pt_menu_role` VALUES ('755', '6', '19');
INSERT INTO `pt_menu_role` VALUES ('756', '6', '20');
INSERT INTO `pt_menu_role` VALUES ('757', '6', '21');
INSERT INTO `pt_menu_role` VALUES ('758', '6', '22');
INSERT INTO `pt_menu_role` VALUES ('759', '6', '23');
INSERT INTO `pt_menu_role` VALUES ('760', '6', '23');
INSERT INTO `pt_menu_role` VALUES ('761', '6', '24');
INSERT INTO `pt_menu_role` VALUES ('762', '6', '24');
INSERT INTO `pt_menu_role` VALUES ('763', '6', '26');
INSERT INTO `pt_menu_role` VALUES ('764', '6', '26');
INSERT INTO `pt_menu_role` VALUES ('765', '6', '46');
INSERT INTO `pt_menu_role` VALUES ('766', '6', '47');
INSERT INTO `pt_menu_role` VALUES ('767', '6', '54');
INSERT INTO `pt_menu_role` VALUES ('768', '6', '48');
INSERT INTO `pt_menu_role` VALUES ('769', '6', '49');
INSERT INTO `pt_menu_role` VALUES ('770', '6', '50');
INSERT INTO `pt_menu_role` VALUES ('771', '6', '55');
INSERT INTO `pt_menu_role` VALUES ('772', '6', '51');
INSERT INTO `pt_menu_role` VALUES ('773', '6', '52');
INSERT INTO `pt_menu_role` VALUES ('774', '6', '53');
INSERT INTO `pt_menu_role` VALUES ('1535', '8', '26');
INSERT INTO `pt_menu_role` VALUES ('1536', '8', '51');
INSERT INTO `pt_menu_role` VALUES ('1537', '8', '52');
INSERT INTO `pt_menu_role` VALUES ('1538', '8', '53');
INSERT INTO `pt_menu_role` VALUES ('1620', '7', '18');
INSERT INTO `pt_menu_role` VALUES ('1621', '7', '19');
INSERT INTO `pt_menu_role` VALUES ('1622', '7', '20');
INSERT INTO `pt_menu_role` VALUES ('1623', '7', '21');
INSERT INTO `pt_menu_role` VALUES ('1624', '7', '22');
INSERT INTO `pt_menu_role` VALUES ('1625', '7', '23');
INSERT INTO `pt_menu_role` VALUES ('1626', '7', '24');
INSERT INTO `pt_menu_role` VALUES ('1627', '7', '43');
INSERT INTO `pt_menu_role` VALUES ('1628', '7', '44');
INSERT INTO `pt_menu_role` VALUES ('1629', '7', '26');
INSERT INTO `pt_menu_role` VALUES ('1630', '7', '46');
INSERT INTO `pt_menu_role` VALUES ('1631', '7', '47');
INSERT INTO `pt_menu_role` VALUES ('1632', '7', '54');
INSERT INTO `pt_menu_role` VALUES ('1633', '7', '48');
INSERT INTO `pt_menu_role` VALUES ('1634', '7', '49');
INSERT INTO `pt_menu_role` VALUES ('1635', '7', '50');
INSERT INTO `pt_menu_role` VALUES ('1636', '7', '55');
INSERT INTO `pt_menu_role` VALUES ('1637', '7', '51');
INSERT INTO `pt_menu_role` VALUES ('1638', '7', '52');
INSERT INTO `pt_menu_role` VALUES ('1639', '7', '53');
INSERT INTO `pt_menu_role` VALUES ('1640', '7', '56');
INSERT INTO `pt_menu_role` VALUES ('1641', '7', '63');
INSERT INTO `pt_menu_role` VALUES ('1642', '7', '59');
INSERT INTO `pt_menu_role` VALUES ('1643', '7', '60');
INSERT INTO `pt_menu_role` VALUES ('1644', '7', '64');
INSERT INTO `pt_menu_role` VALUES ('1645', '7', '61');
INSERT INTO `pt_menu_role` VALUES ('1646', '7', '62');
INSERT INTO `pt_menu_role` VALUES ('1647', '7', '65');
INSERT INTO `pt_menu_role` VALUES ('1648', '7', '66');

-- ----------------------------
-- Table structure for pt_permission
-- ----------------------------
DROP TABLE IF EXISTS `pt_permission`;
CREATE TABLE `pt_permission` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(50) NOT NULL COMMENT '名称',
  `method` varchar(10) NOT NULL COMMENT '方法',
  `value` varchar(50) NOT NULL COMMENT '值',
  `url` varchar(255) DEFAULT NULL COMMENT 'url地址',
  `intro` varchar(255) DEFAULT NULL COMMENT '简介',
  `pid` bigint(20) DEFAULT '0' COMMENT '父级id',
  `created_at` varchar(30) DEFAULT NULL,
  `updated_at` varchar(30) DEFAULT NULL,
  `deleted_at` varchar(30) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8 COMMENT='权限';

-- ----------------------------
-- Records of pt_permission
-- ----------------------------
INSERT INTO `pt_permission` VALUES ('1', '订单', '*', 'P_ORDER', '/api/v1.0/orders/**', '订单访问权限', '0', '2015-04-20 16:00:38', null, null);
INSERT INTO `pt_permission` VALUES ('2', '销售', '*', 'P_SALE', '/api/v1.0/sales/**', '销售访问权限', '0', '2015-04-20 16:00:38', null, null);
INSERT INTO `pt_permission` VALUES ('3', '财务', '*', 'P_FINANCE', '/api/v1.0/finances/**', '财务访问权限', '0', '2015-04-20 16:00:38', null, null);
INSERT INTO `pt_permission` VALUES ('4', '仓库', '*', 'P_STORE', '/api/v1.0/stores/**', '仓库访问权限', '0', '2015-04-20 16:00:38', null, null);
INSERT INTO `pt_permission` VALUES ('5', '仓库录入', '*', 'P_STORE_WRITE', '/api/v1.0/stores/wirtes/**', '仓库访问权限', '0', '2015-04-20 16:00:38', null, null);
INSERT INTO `pt_permission` VALUES ('6', '设置', '*', 'P_SETTING', '/api/v1.0/settings/**', '设置访问权限', '0', '2015-04-20 16:00:38', null, null);

-- ----------------------------
-- Table structure for pt_role
-- ----------------------------
DROP TABLE IF EXISTS `pt_role`;
CREATE TABLE `pt_role` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(50) NOT NULL COMMENT '名称',
  `value` varchar(50) NOT NULL COMMENT '值',
  `intro` varchar(255) DEFAULT NULL COMMENT '简介',
  `pid` bigint(20) DEFAULT NULL COMMENT '父级id',
  `created_at` varchar(30) DEFAULT NULL,
  `updated_at` varchar(30) DEFAULT NULL,
  `deleted_at` varchar(30) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8 COMMENT='角色';

-- ----------------------------
-- Records of pt_role
-- ----------------------------
INSERT INTO `pt_role` VALUES ('6', 'MyGod', 'God', '上帝权限', null, '2016-09-17 22:12:16', '2016-09-17 22:12:16', null);
INSERT INTO `pt_role` VALUES ('7', '超级管理员', 'SuperAdmin', '超级管理员', null, '2016-09-17 22:12:52', '2016-09-17 22:13:12', null);
INSERT INTO `pt_role` VALUES ('8', '教师', '33', '教师', null, '2016-12-24 16:07:39', '2016-12-24 16:07:39', null);

-- ----------------------------
-- Table structure for pt_role_permission
-- ----------------------------
DROP TABLE IF EXISTS `pt_role_permission`;
CREATE TABLE `pt_role_permission` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `role_id` bigint(20) NOT NULL,
  `permission_id` bigint(20) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8 COMMENT='角色权限';

-- ----------------------------
-- Records of pt_role_permission
-- ----------------------------
INSERT INTO `pt_role_permission` VALUES ('1', '1', '1');
INSERT INTO `pt_role_permission` VALUES ('2', '1', '2');
INSERT INTO `pt_role_permission` VALUES ('3', '1', '3');
INSERT INTO `pt_role_permission` VALUES ('4', '1', '4');
INSERT INTO `pt_role_permission` VALUES ('5', '1', '5');
INSERT INTO `pt_role_permission` VALUES ('6', '1', '6');
INSERT INTO `pt_role_permission` VALUES ('7', '2', '1');
INSERT INTO `pt_role_permission` VALUES ('8', '2', '2');
INSERT INTO `pt_role_permission` VALUES ('9', '2', '4');
INSERT INTO `pt_role_permission` VALUES ('10', '3', '1');
INSERT INTO `pt_role_permission` VALUES ('11', '3', '2');
INSERT INTO `pt_role_permission` VALUES ('12', '3', '3');
INSERT INTO `pt_role_permission` VALUES ('13', '3', '4');
INSERT INTO `pt_role_permission` VALUES ('14', '4', '6');

-- ----------------------------
-- Table structure for pt_user
-- ----------------------------
DROP TABLE IF EXISTS `pt_user`;
CREATE TABLE `pt_user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(50) NOT NULL COMMENT '登录名',
  `providername` varchar(50) DEFAULT NULL COMMENT '提供者',
  `email` varchar(200) DEFAULT NULL COMMENT '邮箱',
  `mobile` varchar(50) DEFAULT NULL COMMENT '手机',
  `password` varchar(200) NOT NULL COMMENT '密码',
  `avatar_url` varchar(255) DEFAULT NULL COMMENT '头像',
  `age` int(10) DEFAULT NULL COMMENT '年龄',
  `sex` char(1) DEFAULT NULL COMMENT '性别（0：男 1:女）',
  `memo` text COMMENT '简介',
  `first_name` varchar(10) DEFAULT NULL COMMENT '名字',
  `last_name` varchar(10) DEFAULT NULL COMMENT '姓氏',
  `full_name` varchar(20) DEFAULT NULL COMMENT '全名',
  `created_at` varchar(30) DEFAULT NULL,
  `updated_at` varchar(30) DEFAULT NULL,
  `deleted_at` varchar(30) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8 COMMENT='用户';

-- ----------------------------
-- Records of pt_user
-- ----------------------------
INSERT INTO `pt_user` VALUES ('1', 'admin', 'dreampie', 'wangrenhui1990@gmail.com', '18611434500', '0b18dfe3a407f50bbcb4a6c351cf3ef197a4b572554f1eedbbf2e30af49195284b5572a710f19f7e1c90b8cbf676b37627f5700978e75450e3fa2590e5c537ad', '', '11', '0', null, '超级管理员', '', '超级管理员', '2015-04-21 16:00:38', '2016-08-24 22:09:17', null);
INSERT INTO `pt_user` VALUES ('9', 'test', null, 'one__l@outlook.com', '18290433510', '3c9909afec25354d551dae21590bb26e38d53f2173b8d3dc3eee4c047e7ab1c1eb8b85103e3be7ba613b31bb5c9c36214dc9f14a42fd7a2fdb84856bca5c44c2', null, '22', '0', '斯蒂芬·威廉·霍金（Stephen William Hawking），1942年1月8日出生于英国牛津，出童年的霍金童年的霍金生当天正好是伽利略逝世300年忌日。父亲法兰克是毕业于牛津大学的热带病专家，母亲伊莎贝尔1930年于牛津研究哲学、政治和经济。[1]', null, null, 'luo xun', '2016-08-24 22:02:14', '2016-08-31 15:28:40', null);
INSERT INTO `pt_user` VALUES ('10', 'May', null, null, null, 'cd4a4775fc33229d9211f53e0c8b96f7793e288cf41e735288f635ea0f681693852857b92fa9a9e112b628a2183382424a6ee90cc90bc3bcd2b2375a7ff36d4c', null, null, null, null, null, null, 'May', null, null, null);
INSERT INTO `pt_user` VALUES ('11', 'kate', null, '', '', '607d0a241e0d20c79f89f3cc31321e28ea6232706e6c0c2d03ebe6ca398197da4ddbb29bf5ac98c9bebc9bc0470c0992bca370213e73ca3f12db27dcd15c9282', null, null, '1', '', null, null, 'kate', null, '2017-01-09 22:56:28', null);
INSERT INTO `pt_user` VALUES ('12', 'soul', null, null, null, 'ba3253876aed6bc22d4a6ff53d8406c6ad864195ed144ab5c87621b6c233b548baeae6956df346ec8c17f5ea10f35ee3cbc514797ed7ddd3145464e2a0bab413', null, null, null, null, null, null, 'soul', null, null, null);
INSERT INTO `pt_user` VALUES ('13', 'DE', null, null, null, '812b5b2c2536e4fba216e296676f8d3f9ea024fccab38479b06fb45958dcaf17a4758e2a4b218e7645fe7d88e8455f623196c98f971b6f4d0afddd2fe8c118f2', null, null, null, null, null, null, 'DE', null, null, null);
INSERT INTO `pt_user` VALUES ('14', 'www', null, null, null, '0b1c2ce5324ecb06230282e51df48c1d8df52d080fd1797e71cc4bc2f2c28310106a0c65b4154de3c86ea8f64a7c3a487180119e8175111ae9e12a9867c13e60', null, null, null, null, null, null, 'www', null, null, null);
INSERT INTO `pt_user` VALUES ('15', 'test123', null, null, null, '3c9909afec25354d551dae21590bb26e38d53f2173b8d3dc3eee4c047e7ab1c1eb8b85103e3be7ba613b31bb5c9c36214dc9f14a42fd7a2fdb84856bca5c44c2', null, null, null, null, null, null, '测试', null, null, null);

-- ----------------------------
-- Table structure for pt_user_role
-- ----------------------------
DROP TABLE IF EXISTS `pt_user_role`;
CREATE TABLE `pt_user_role` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `user_id` bigint(20) NOT NULL,
  `role_id` bigint(20) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=46 DEFAULT CHARSET=utf8 COMMENT='用户角色';

-- ----------------------------
-- Records of pt_user_role
-- ----------------------------
INSERT INTO `pt_user_role` VALUES ('21', '8', '8');
INSERT INTO `pt_user_role` VALUES ('28', '11', '9');
INSERT INTO `pt_user_role` VALUES ('29', '1', '7');
INSERT INTO `pt_user_role` VALUES ('44', '14', '8');
INSERT INTO `pt_user_role` VALUES ('45', '11', '8');

-- ----------------------------
-- Table structure for rop_alipay_account
-- ----------------------------
DROP TABLE IF EXISTS `rop_alipay_account`;
CREATE TABLE `rop_alipay_account` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `alipay_name` varchar(30) COLLATE utf8_bin DEFAULT NULL COMMENT '支付宝应用名称',
  `app_id` varchar(30) COLLATE utf8_bin DEFAULT NULL COMMENT '应用id',
  `seller_id` varchar(30) COLLATE utf8_bin DEFAULT NULL COMMENT '商户id',
  `app_private_key` text COLLATE utf8_bin COMMENT '应用私钥',
  `app_public_key` text COLLATE utf8_bin COMMENT '应用公钥',
  `format` varchar(20) COLLATE utf8_bin DEFAULT NULL COMMENT '数据格式',
  `charset` varchar(20) COLLATE utf8_bin DEFAULT NULL COMMENT '字符集',
  `return_url` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '页面跳转路径',
  `notify_url` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '回调通知路径',
  `runtime` char(1) COLLATE utf8_bin DEFAULT NULL COMMENT '运行环境 0:正式环境 1:沙箱',
  `create_time` varchar(30) COLLATE utf8_bin DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- ----------------------------
-- Records of rop_alipay_account
-- ----------------------------
INSERT INTO `rop_alipay_account` VALUES ('2', '特首', '2088221780614014', '2088221780614014', 0x4D494943654149424144414E42676B71686B6947397730424151454641415343416D497767674A6541674541416F4742414C4D35304173354A313442694C325079573843627077454248662B596A7A4456725743654A6963422B55304D584F4E6C774756424574727072397330426C6D473871674E32365355362B522F71736E536961652F7379686F445A737855376C704A38566F3967797159376C3953464D76435A5851442F754D7832652F5541575154636975354D666876715A6B387046782F4B677650424F62554468626B4E334D464E4D67576E367573595441674D4241414543675942564963373670636C58552B3835363854493443546549754A6B68516277796643304179643568685153415872443471757036726A42676E3851536F57477177506B65626F61345A4B79774F78784C4261437864394E564C48476E7451736A664D416F5A7353642F527233385151514A714946486B6A7978622B4151753963575970596955366B7963677A4F634D674456646857496C45424649536F5864647A66396C6D306248397A4E77514A42414E6444717768636B3673554839566F46512B7A387565687345694F663630332B6F61326474646A4D51365556782B74564D5044546E2F6846694734544F2B6F38644F344E6A487A544C616E386331706C6A4456486F4D43515144564A456A504A515944556E51677248324F67414E364F51396861537273694E70516E6E442F5277752F5A4B36614A704168626343784A51795A69315544497235566179797A4A5A49424739626B6B50735372535578416B454178367A75486C42356259793652714E495067756D657250687144762B4E4C676A5041684F7A6C596C4D436672644F6538492B61726A7A5644365431697145416E4B55504C69615073656458704671676C642B434962774A42414A72487872666D777638576D593541744D304F3258656769444C4E4649624175776C67686435623059516A735566775353703963546B7A466E744B57326E7A69525375783738777756356377536B7176366C49593445435151436C56516B687A6D77687932495332664A6835742F2F765A6254374F356F2B3752647A74466558694F426E4D6D357259355A55307849466232386841356B633254572B3139746758386F595A4E4F6953425543704763, 0x4D4947664D413047435371475349623344514542415155414134474E4144434269514B426751436E786A2F3971775666676F55682F79325738394C36426B5241466C6A684E68675064795075425636346266514E4E31506A62437A6B494D367152644B426F4C50586D4B4B4D6946596E6B643672416F70726968332F50725145422F567357384F6F4D3866786E363755445975794254714132334D4D4C3971312B696C495A774243324151325542564F72465866466C373570362F42354B73694E47397A70676D4C435559754C6B78704C51494441514142, 'json', 'utf-8', 'http://baidu.com', 'http://baidu.com', '0', '2016-09-18 21:48:52');

-- ----------------------------
-- Table structure for rop_pay_bill
-- ----------------------------
DROP TABLE IF EXISTS `rop_pay_bill`;
CREATE TABLE `rop_pay_bill` (
  `id` int(16) NOT NULL AUTO_INCREMENT,
  `trade_no` varchar(50) COLLATE utf8_bin DEFAULT NULL COMMENT '交易号(唯一)',
  `subject` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '主题',
  `total_fee` double(10,2) DEFAULT NULL COMMENT '金额',
  `body` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '描述',
  `pay_type` char(1) COLLATE utf8_bin DEFAULT NULL COMMENT '支付方式(0:支付宝1:微信支付)',
  `pay_code` varchar(30) COLLATE utf8_bin DEFAULT NULL COMMENT '支付类型代码',
  `pay_status` char(1) COLLATE utf8_bin DEFAULT NULL COMMENT '支付状态(0:未支付，1：已支付 2：取消)',
  `craete_time` varchar(30) COLLATE utf8_bin DEFAULT NULL COMMENT '创建时间',
  `pay_time` varchar(30) COLLATE utf8_bin DEFAULT NULL COMMENT '支付时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- ----------------------------
-- Records of rop_pay_bill
-- ----------------------------

-- ----------------------------
-- Table structure for rop_wechat_account
-- ----------------------------
DROP TABLE IF EXISTS `rop_wechat_account`;
CREATE TABLE `rop_wechat_account` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '微信账号管理表',
  `wechat_name` varchar(20) COLLATE utf8_bin DEFAULT NULL COMMENT '账号名称',
  `wechat_number` varchar(20) COLLATE utf8_bin DEFAULT NULL COMMENT '账号号码',
  `app_id` varchar(200) COLLATE utf8_bin DEFAULT NULL COMMENT '开发者id',
  `app_secret` varchar(200) COLLATE utf8_bin DEFAULT NULL COMMENT '开发者密匙',
  `partner_key` varchar(200) COLLATE utf8_bin DEFAULT NULL COMMENT '商户key',
  `mch_id` varchar(200) COLLATE utf8_bin DEFAULT NULL COMMENT '商户号',
  `biz` varchar(20) COLLATE utf8_bin DEFAULT NULL COMMENT 'biz号',
  `uin` varchar(20) COLLATE utf8_bin DEFAULT NULL COMMENT 'uin号',
  `type` char(1) COLLATE utf8_bin DEFAULT NULL COMMENT '微信账号类型（0：服务号 1:订阅号 2：企业号）',
  `agentid` varchar(11) COLLATE utf8_bin DEFAULT NULL COMMENT '企业号应用id',
  `token` varchar(50) COLLATE utf8_bin DEFAULT NULL COMMENT '验证标记',
  `pay_notify_url` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '支付通知路径',
  `encodingAesKey` varchar(50) COLLATE utf8_bin DEFAULT NULL COMMENT '加密key',
  `create_time` varchar(30) COLLATE utf8_bin DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=86 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- ----------------------------
-- Records of rop_wechat_account
-- ----------------------------
INSERT INTO `rop_wechat_account` VALUES ('84', 'DIFFPI', '', 'wx7d9fd08af458a6a5', 'b006d453e0da00532a80bbacb01d7aeb', 'zxcvbnmasdfghjklqwertyuiop123456', '1420024202', null, null, '0', null, 'diffpi5277114', 'http://lcp52771314.xicp.net/api/m/v1.0/paybill/wxpay/notify', 'pfaVLqYz8wewwOfptMuWDiiNYimDMeM1C3PzZTRCW23', '2016-12-15 15:30:56');
INSERT INTO `rop_wechat_account` VALUES ('85', '领部英语', '', 'wx45d96852e98a0f19', '1733f63733808f7d62d026fd536a9e4f', '2f8cc2aa30a6fccbde142d7860a9a0f5', '1248531401', null, null, '0', null, '', 'http://eng.lingbu.me/api/m/v1.0/paybill/wxpay/notify', 'pfaVLqYz8wewwOfptMuWDiiNYimDMeM1C3PzZTRCW23', '2016-12-22 15:20:23');

-- ----------------------------
-- Table structure for rop_wechat_user
-- ----------------------------
DROP TABLE IF EXISTS `rop_wechat_user`;
CREATE TABLE `rop_wechat_user` (
  `id` int(20) NOT NULL AUTO_INCREMENT,
  `user` int(20) DEFAULT NULL COMMENT '用户id',
  `openid` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '用户的唯一标识',
  `nickname` varchar(50) COLLATE utf8_bin DEFAULT NULL COMMENT '用户昵称',
  `sex` char(1) COLLATE utf8_bin DEFAULT NULL COMMENT '用户的性别，值为1时是男性，值为2时是女性，值为0时是未知',
  `province` varchar(20) COLLATE utf8_bin DEFAULT NULL COMMENT '用户个人资料填写的省份',
  `city` varchar(20) COLLATE utf8_bin DEFAULT NULL COMMENT '普通用户个人资料填写的城市',
  `country` varchar(20) COLLATE utf8_bin DEFAULT NULL COMMENT '国家，如中国为CN',
  `headimgurl` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '用户头像',
  `create_time` varchar(30) COLLATE utf8_bin DEFAULT NULL COMMENT '关注时间',
  `unionid` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- ----------------------------
-- Records of rop_wechat_user
-- ----------------------------
