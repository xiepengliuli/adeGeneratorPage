/*
Navicat MySQL Data Transfer

Source Server         : localhost
Source Server Version : 50522
Source Host           : localhost:3310
Source Database       : bzpt

Target Server Type    : MYSQL
Target Server Version : 50522
File Encoding         : 65001

Date: 2016-04-22 18:30:15
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for ade_admin_logs
-- ----------------------------
DROP TABLE IF EXISTS `ade_admin_logs`;
CREATE TABLE `ade_admin_logs` (
  `ID` varchar(36) NOT NULL,
  `CONTENT` varchar(100) NOT NULL,
  `IE_TYPE` varchar(36) NOT NULL,
  `IP` varchar(36) NOT NULL,
  `LOG_TYPE` int(11) NOT NULL,
  `MODULE_NAME` int(11) DEFAULT NULL,
  `OPERATE_TIME` datetime NOT NULL,
  `USER_ID` varchar(100) NOT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of ade_admin_logs
-- ----------------------------

-- ----------------------------
-- Table structure for ade_bug
-- ----------------------------
DROP TABLE IF EXISTS `ade_bug`;
CREATE TABLE `ade_bug` (
  `id` varchar(32) NOT NULL,
  `createdatetime` datetime DEFAULT NULL,
  `modifydatetime` datetime DEFAULT NULL,
  `name` varchar(100) DEFAULT NULL,
  `note` longtext,
  `bugtype_id` varchar(32) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_5nrv3udsxi17efrlf8eoan1np` (`bugtype_id`) USING BTREE,
  CONSTRAINT `ade_bug_ibfk_1` FOREIGN KEY (`bugtype_id`) REFERENCES `ade_bugtype` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of ade_bug
-- ----------------------------

-- ----------------------------
-- Table structure for ade_bugtype
-- ----------------------------
DROP TABLE IF EXISTS `ade_bugtype`;
CREATE TABLE `ade_bugtype` (
  `id` varchar(32) NOT NULL,
  `name` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of ade_bugtype
-- ----------------------------
INSERT INTO `ade_bugtype` VALUES ('1', '后台管理');

-- ----------------------------
-- Table structure for ade_dictionary
-- ----------------------------
DROP TABLE IF EXISTS `ade_dictionary`;
CREATE TABLE `ade_dictionary` (
  `ID` varchar(32) NOT NULL,
  `ZD_CODE` varchar(60) NOT NULL,
  `ZD_DESC` varchar(255) DEFAULT NULL,
  `ZD_NAME` varchar(100) NOT NULL,
  `ZD_SORT` int(11) DEFAULT NULL,
  `ZD_USE` varchar(2) NOT NULL,
  `ZD_TYPE_ID` varchar(32) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  KEY `FK_9l6p5uj5th2jlkyldo7by6tn2` (`ZD_TYPE_ID`) USING BTREE,
  CONSTRAINT `ade_dictionary_ibfk_1` FOREIGN KEY (`ZD_TYPE_ID`) REFERENCES `ade_dictionary_type` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of ade_dictionary
-- ----------------------------
INSERT INTO `ade_dictionary` VALUES ('015B7353165C43E3A29C92548314E4E7', '1', '', '没有或很少时间', '1', '可用', 'DA8AEBC8ABE846A0849C387E193E7F68');
INSERT INTO `ade_dictionary` VALUES ('030E911F576148D38146EF2646402603', 'srq20', '', 'SRQ20问卷', '1', '可用', 'D96D1500AB7D4FD9BC5EA37FB9EA88CB');
INSERT INTO `ade_dictionary` VALUES ('0592B499AC14483D9E1E53181A599BF1', '7', '', '任务七', '7', '可用', 'A23E6D6309A643D8B1B030D739DECB72');
INSERT INTO `ade_dictionary` VALUES ('060597549B86440EAE274F341AE91821', '0', '', '未知', '3', '可用', '2D81602F3F6D40ECA9C920762333DC69');
INSERT INTO `ade_dictionary` VALUES ('0B9FB8B64562429D9FCC696975FB61E0', '3', '', '空军', '3', '可用', 'B0BD3D34A81748E385A4D7B0C48371CB');
INSERT INTO `ade_dictionary` VALUES ('0D6D823C42114D68AE45EE71CD0505C5', 'levelType', '', '级别', '9', '可用', 'DC11AE8C8824478A8364F24D6946D76B');
INSERT INTO `ade_dictionary` VALUES ('0E605DDE7AEC490D9959054A75EE6D37', 'armyType', '', '军种', '8', '可用', 'DC11AE8C8824478A8364F24D6946D76B');
INSERT INTO `ade_dictionary` VALUES ('0F1265323BA742CCBCF1F7AB9CDFDC62', '3', '', '营', '3', '可用', '3244872A924D4DCF8154B9F0F5412E38');
INSERT INTO `ade_dictionary` VALUES ('0F14E564D4FF4E718F71489295570A4D', 'sas2', '', '2', '2', '可用', '49D684FD7F3142B2BC1E305A3C971BC1');
INSERT INTO `ade_dictionary` VALUES ('120C0383329B44BB8C78F46472B0BE3D', '3', '文件类型', '音频', '3', '可用', 'D0A5558868054FD699D774EE0C68F408');
INSERT INTO `ade_dictionary` VALUES ('1786AAA5F1E044DE886FD0B9DE41BCC6', 'srq3', '', '3', '3', '可用', '59DA0C5D4E8A4C61B67A91C54CE0278C');
INSERT INTO `ade_dictionary` VALUES ('1A006E79DFF045ED83D937B6D42C8AAC', 'sds', '', 'SDS问卷', '4', '可用', 'D96D1500AB7D4FD9BC5EA37FB9EA88CB');
INSERT INTO `ade_dictionary` VALUES ('1A1916EAE4124B378967858CD496279E', '1', '', '没有或偶尔', '1', '可用', 'EC9C8B5D2BC141BE820C73F3E75D1CD9');
INSERT INTO `ade_dictionary` VALUES ('1A678B050E7048CCA68275FB1891756B', '4', '文件类型', '图片', '4', '可用', 'D0A5558868054FD699D774EE0C68F408');
INSERT INTO `ade_dictionary` VALUES ('1CFBD4C18ACB4FC7B8FD938D34FE118E', '1', '', '排', '1', '可用', '3244872A924D4DCF8154B9F0F5412E38');
INSERT INTO `ade_dictionary` VALUES ('20455DAD989F40E5B97960FB0BB90446', 'pclc_c', '', 'PCLC', '1', '可用', 'EFFA9C09EAC3461CB38D052ABFA12790');
INSERT INTO `ade_dictionary` VALUES ('21C0DEAEAE48404D857665604CFE612A', '4', '', '武警', '4', '可用', 'B0BD3D34A81748E385A4D7B0C48371CB');
INSERT INTO `ade_dictionary` VALUES ('225CFA16CDDA4C78B759007570ACBCCF', 'srq1', '', '1', '1', '可用', '59DA0C5D4E8A4C61B67A91C54CE0278C');
INSERT INTO `ade_dictionary` VALUES ('22AE04DB270B4D81B734C124B48D1C83', '2', '', '高中', '2', '可用', 'F5D396962A3447238AD2BA23C9EFE2BD');
INSERT INTO `ade_dictionary` VALUES ('23DC272BD7EF4EB387184E9ABD18C3BF', '2', '', '任务二', '2', '可用', 'A23E6D6309A643D8B1B030D739DECB72');
INSERT INTO `ade_dictionary` VALUES ('24936B982E3F44578ED8C06A4DBBAFEA', 'srq_c', '', 'SRQ20', '2', '可用', 'EFFA9C09EAC3461CB38D052ABFA12790');
INSERT INTO `ade_dictionary` VALUES ('28F156BCE3BC4BE4801302B921FE189A', '0', '', '未知', '7', '可用', 'F5D396962A3447238AD2BA23C9EFE2BD');
INSERT INTO `ade_dictionary` VALUES ('2C758B11C3DD4BBF99019D27E0742854', '12', '', '12', '12', '可用', 'D079AB9DE85D4126A7B8C8FE775DDC41');
INSERT INTO `ade_dictionary` VALUES ('2E9F73B5D9254277A4AC463751C38E21', 'sds3', '', '3', '3', '可用', '631B249A2C3340A3AAF60965556B9FDD');
INSERT INTO `ade_dictionary` VALUES ('2F4F5EC86F284F50B51F29983C90AD11', '1', '', '1', '1', '可用', 'D079AB9DE85D4126A7B8C8FE775DDC41');
INSERT INTO `ade_dictionary` VALUES ('2FAB718FAFC1414C82F6F7B873835533', '5', '', '云服务资源', '5', '可用', '97A91409700B4DE888C4F333A8A759EC');
INSERT INTO `ade_dictionary` VALUES ('3AAFB4725F834FE9AA4B93A546B5C011', '4', '', '中性', '4', '可用', 'C72D5FDF32CF4A57BED0CA9B4EC4F059');
INSERT INTO `ade_dictionary` VALUES ('3B13267E5A794092BAD10AC2487C8A11', '1', '', '标准SAS指标', '1', '可用', 'A9A794427380454CB6DAEB13C2C1D115');
INSERT INTO `ade_dictionary` VALUES ('3B6C3960FC9D48F584C715FCC339FB11', '2', '', '有时', '2', '可用', 'EC9C8B5D2BC141BE820C73F3E75D1CD9');
INSERT INTO `ade_dictionary` VALUES ('3D1BC29E16194EC3804E94D7B80EB810', '2', '', '海军', '2', '可用', 'B0BD3D34A81748E385A4D7B0C48371CB');

-- ----------------------------
-- Table structure for ade_dictionary_type
-- ----------------------------
DROP TABLE IF EXISTS `ade_dictionary_type`;
CREATE TABLE `ade_dictionary_type` (
  `ID` varchar(32) NOT NULL,
  `ZD_TYPE_CODE` varchar(32) NOT NULL,
  `ZD_TYPE_DESC` varchar(255) NOT NULL,
  `ZD_TYPE_NAME` varchar(100) NOT NULL,
  `ZD_TYPE_SORT` int(11) DEFAULT NULL,
  `ZD_TYPE_USE` varchar(2) NOT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of ade_dictionary_type
-- ----------------------------
INSERT INTO `ade_dictionary_type` VALUES ('0C638047CD2F4222BD355741022DEFC2', 'srq_c_answer', '', 'SRQ20答案', '28', '可用');
INSERT INTO `ade_dictionary_type` VALUES ('29E89D282BFD431BAF222BAC0E1A7217', 'ptci_c', '', 'PTCI题号', '24', '可用');
INSERT INTO `ade_dictionary_type` VALUES ('2D81602F3F6D40ECA9C920762333DC69', 'sexType', '性别', '性别', '4', '可用');
INSERT INTO `ade_dictionary_type` VALUES ('3244872A924D4DCF8154B9F0F5412E38', 'levelType', '', '级别', '12', '可用');
INSERT INTO `ade_dictionary_type` VALUES ('35EAA45D61684D00BF74354804D56DAD', 'expert_type', '', '专家类型', '1', '1');
INSERT INTO `ade_dictionary_type` VALUES ('36F330F59F8E45F69C1D3AC1D236C78C', 'ptci', '', 'PTCI问卷', '17', '可用');
INSERT INTO `ade_dictionary_type` VALUES ('3D5BB994F9A94435AE1EFD50C69B4B7F', 'ifOnly', '是否独生子\r\n系统字典维护', '独生子', '7', '可用');
INSERT INTO `ade_dictionary_type` VALUES ('49D684FD7F3142B2BC1E305A3C971BC1', 'sas_c', '', 'SAS题号', '25', '可用');
INSERT INTO `ade_dictionary_type` VALUES ('59DA0C5D4E8A4C61B67A91C54CE0278C', 'srq_c', '', 'SRQ20题号', '23', '可用');
INSERT INTO `ade_dictionary_type` VALUES ('602CEF931B944D73913625566059F095', 'familyBackGround', '', '家庭背景', '6', '可用');
INSERT INTO `ade_dictionary_type` VALUES ('61551E5E14934D6BB7C43BE8C8449A7A', 'sds', '', 'SDS问卷', '18', '可用');
INSERT INTO `ade_dictionary_type` VALUES ('631B249A2C3340A3AAF60965556B9FDD', 'sds_c', '', 'SDS题号', '26', '可用');
INSERT INTO `ade_dictionary_type` VALUES ('8DB19D3D62A44231B8D2236F938BC3D2', 'pclc_c_answer', '', 'PCLC答案', '22', '可用');
INSERT INTO `ade_dictionary_type` VALUES ('92A74C672FDC41D3A5B5D5D1491AD7DC', 'bigCompany', '大单位', '大单位', '3', '可用');
INSERT INTO `ade_dictionary_type` VALUES ('97A91409700B4DE888C4F333A8A759EC', 'resourceGenre', '', '资源大类', '2', '可用');
INSERT INTO `ade_dictionary_type` VALUES ('99EB8D38893D4C6DB5E6CF387D41A7DC', 'marriage', '', '婚姻状况', '9', '可用');
INSERT INTO `ade_dictionary_type` VALUES ('A11260FF2C424F0D8BE6A0BFBE02C3FA', 'pclc', '', 'PCLC问卷', '15', '可用');
INSERT INTO `ade_dictionary_type` VALUES ('A23E6D6309A643D8B1B030D739DECB72', 'task', '', '任务', '31', '可用');
INSERT INTO `ade_dictionary_type` VALUES ('A9A794427380454CB6DAEB13C2C1D115', 'sas', '', 'SAS问卷', '19', '可用');
INSERT INTO `ade_dictionary_type` VALUES ('B0BD3D34A81748E385A4D7B0C48371CB', 'armyType', '', '军种', '10', '可用');
INSERT INTO `ade_dictionary_type` VALUES ('C72D5FDF32CF4A57BED0CA9B4EC4F059', 'ptci_c_answer', '', 'PTCI答案', '27', '可用');
INSERT INTO `ade_dictionary_type` VALUES ('D079AB9DE85D4126A7B8C8FE775DDC41', 'group_count', '', '分组个数', '32', '可用');
INSERT INTO `ade_dictionary_type` VALUES ('D0A5558868054FD699D774EE0C68F408', 'textType', '系统中资源添加时，文件类型字段维护', '文件类型', '34', '可用');
INSERT INTO `ade_dictionary_type` VALUES ('D96D1500AB7D4FD9BC5EA37FB9EA88CB', 'surveyType', '', '调查问卷类型', '14', '可用');
INSERT INTO `ade_dictionary_type` VALUES ('DA8AEBC8ABE846A0849C387E193E7F68', 'sas_c_answer', '', 'SAS答案', '29', '可用');
INSERT INTO `ade_dictionary_type` VALUES ('DC11AE8C8824478A8364F24D6946D76B', 'basicQuery', '', '基础查询', '13', '可用');
INSERT INTO `ade_dictionary_type` VALUES ('DDD7078C52F948348E422BE9EA4EFF4D', 'resFrom', '资源来源', '资源来源', '35', '在用');
INSERT INTO `ade_dictionary_type` VALUES ('E1C3FDA2520347668D148F6113B8492F', 'recordType', '', '记录类型', '5', '可用');
INSERT INTO `ade_dictionary_type` VALUES ('EC9C8B5D2BC141BE820C73F3E75D1CD9', 'sds_c_answer', '', 'SDS答案', '30', '可用');
INSERT INTO `ade_dictionary_type` VALUES ('ED4038C6115443FD979846FDE596B9B1', 'srq20', '', 'SRQ20问卷', '16', '可用');
INSERT INTO `ade_dictionary_type` VALUES ('EFFA9C09EAC3461CB38D052ABFA12790', 'course', '', '测试科目', '20', '可用');
INSERT INTO `ade_dictionary_type` VALUES ('F1B2B014260B411F8B9E61000CFCEC50', 'resourceClass', '平台资源横向选项卡部分调用', '资源分类', '1', '在用');
INSERT INTO `ade_dictionary_type` VALUES ('F596253A26794248BEC69BB4EAE32C17', 'divide_method', '分段统计', '划分方法', '33', '可用');
INSERT INTO `ade_dictionary_type` VALUES ('F5BF384C5EBF46A8BDD62371059C9644', 'pclc_c', '', 'PCLC题号', '21', '可用');
INSERT INTO `ade_dictionary_type` VALUES ('F5D396962A3447238AD2BA23C9EFE2BD', 'cultureType', '', '文化程度', '11', '可用');
INSERT INTO `ade_dictionary_type` VALUES ('F9ABEEA3E7B344489031A808724FD33A', 'politics', '', '政治面貌', '8', '可用');

-- ----------------------------
-- Table structure for ade_group
-- ----------------------------
DROP TABLE IF EXISTS `ade_group`;
CREATE TABLE `ade_group` (
  `ID` varchar(32) NOT NULL,
  `CREATE_DATE` datetime DEFAULT NULL,
  `GROUP_DESC` varchar(255) NOT NULL,
  `GROUP_FULL_NAME` varchar(200) NOT NULL,
  `GROUP_SHORT_NAME` varchar(200) NOT NULL,
  `GROUP_SORT` varchar(255) DEFAULT NULL,
  `LAST_MODIFY_DATE` datetime DEFAULT NULL,
  `GROUP_PID` varchar(32) DEFAULT NULL,
  `CREATE_USER` varchar(32) DEFAULT NULL,
  `LAST_MODIFY_USER` varchar(32) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  KEY `FK_17pra2yo53huwi5rmu3caaffl` (`GROUP_PID`) USING BTREE,
  KEY `FK_c7bfcx1bm8cy7y9062bn7r527` (`CREATE_USER`) USING BTREE,
  KEY `FK_std7kbn2hh1r2yworte564lev` (`LAST_MODIFY_USER`) USING BTREE,
  CONSTRAINT `ade_group_ibfk_1` FOREIGN KEY (`GROUP_PID`) REFERENCES `ade_group` (`ID`),
  CONSTRAINT `ade_group_ibfk_2` FOREIGN KEY (`CREATE_USER`) REFERENCES `ade_user` (`ID`),
  CONSTRAINT `ade_group_ibfk_3` FOREIGN KEY (`LAST_MODIFY_USER`) REFERENCES `ade_user` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of ade_group
-- ----------------------------

-- ----------------------------
-- Table structure for ade_module
-- ----------------------------
DROP TABLE IF EXISTS `ade_module`;
CREATE TABLE `ade_module` (
  `ID` varchar(32) NOT NULL,
  `MODULE_DESC` varchar(255) DEFAULT NULL,
  `MODULE_ICON` varchar(100) DEFAULT NULL,
  `MODULE_NAME` varchar(100) NOT NULL,
  `MODULE_SORT` int(11) DEFAULT NULL,
  `MODULE_URL` varchar(200) DEFAULT NULL,
  `MODULE_PID` varchar(32) DEFAULT NULL,
  `MODULE_TYPE_ID` varchar(32) NOT NULL,
  PRIMARY KEY (`ID`),
  KEY `FK_potycga2poaka2davotg8a11m` (`MODULE_PID`) USING BTREE,
  KEY `FK_7vcirr62lwiv4av32h0iy1rxu` (`MODULE_TYPE_ID`) USING BTREE,
  CONSTRAINT `ade_module_ibfk_1` FOREIGN KEY (`MODULE_TYPE_ID`) REFERENCES `ade_module_type` (`ID`),
  CONSTRAINT `ade_module_ibfk_2` FOREIGN KEY (`MODULE_PID`) REFERENCES `ade_module` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of ade_module
-- ----------------------------
INSERT INTO `ade_module` VALUES ('3D17B0355C42440B8B71E6C4233AEDD6', '内容发布管理', 'building_add', '发布管理', '100', '', null, '0');
INSERT INTO `ade_module` VALUES ('404D882B37DD47418C493575FF2FFD18', '疾病管理', '', '疾病管理', '101', '/admin/disease/manager', '3D17B0355C42440B8B71E6C4233AEDD6', '0');
INSERT INTO `ade_module` VALUES ('7C17F78FC0924899B29F8EE88468D1E1', '测试测试测试', '', '测试测试', '100', '111111', 'buggl', '1');
INSERT INTO `ade_module` VALUES ('9FAF885F07C8449A80CBA50A0DF85562', '前台用户管理', '', '前台用户管理', '100', '/admin/portalUser/manager', '3D17B0355C42440B8B71E6C4233AEDD6', '0');
INSERT INTO `ade_module` VALUES ('B043B3E837594283824407215B9142D4', '333333', 'folder_wrench', '3333', '100', '', 'zygl', '1');
INSERT INTO `ade_module` VALUES ('BAAE012B6AD84C87BD712B558BD96C83', '', 'asterisk_yellow', '字典管理', '100', '/admin/dictionary/manager', 'xtgl', '0');
INSERT INTO `ade_module` VALUES ('buggl', null, 'bug_bug', 'BUG管理', '4', '/admin/bug/manager', 'xtgl', '0');
INSERT INTO `ade_module` VALUES ('bugglAdd', null, 'bug_add', '添加BUG', '3', '/admin/bug/add', 'buggl', '1');
INSERT INTO `ade_module` VALUES ('bugglAddPage', null, 'bug_add', '添加BUG页面', '2', '/admin/bug/addPage', 'buggl', '1');
INSERT INTO `ade_module` VALUES ('bugglDateGrid', null, 'bug_link', 'BUG表格', '1', '/admin/bug/dataGrid', 'buggl', '1');
INSERT INTO `ade_module` VALUES ('bugglDelete', null, 'bug_delete', '删除BUG', '6', '/admin/bug/delete', 'buggl', '1');
INSERT INTO `ade_module` VALUES ('bugglEdit', null, 'bug_edit', '编辑BUG', '5', '/admin/bug/edit', 'buggl', '1');
INSERT INTO `ade_module` VALUES ('bugglEditPage', null, 'bug_edit', '编辑BUG页面', '4', '/admin/bug/editPage', 'buggl', '1');
INSERT INTO `ade_module` VALUES ('bugglView', null, 'bug_link', '查看BUG', '7', '/admin/bug/view', 'buggl', '1');
INSERT INTO `ade_module` VALUES ('FAC11905F8164D639068E0379A37B849', '', '', '权限管理', '101', '/admin/module/modulePermission', 'xtgl', '0');
INSERT INTO `ade_module` VALUES ('jsgl', null, 'tux', '角色管理', '2', '/admin/role/manager', 'xtgl', '0');
INSERT INTO `ade_module` VALUES ('jsglAdd', null, 'wrench', '添加角色', '3', '/admin/role/add', 'jsgl', '1');
INSERT INTO `ade_module` VALUES ('jsglAddPage', null, 'wrench', '添加角色页面', '2', '/admin/role/addPage', 'jsgl', '1');
INSERT INTO `ade_module` VALUES ('jsglDelete', null, 'wrench', '删除角色', '6', '/admin/role/delete', 'jsgl', '1');
INSERT INTO `ade_module` VALUES ('jsglEdit', null, 'wrench', '编辑角色', '5', '/admin/role/edit', 'jsgl', '1');
INSERT INTO `ade_module` VALUES ('jsglEditPage', null, 'wrench', '编辑角色页面', '4', '/admin/role/editPage', 'jsgl', '1');
INSERT INTO `ade_module` VALUES ('jsglGrant', null, 'wrench', '角色授权', '8', '/admin/role/grant', 'jsgl', '1');
INSERT INTO `ade_module` VALUES ('jsglGrantPage', null, 'wrench', '角色授权页面', '7', '/admin/role/grantPage', 'jsgl', '1');
INSERT INTO `ade_module` VALUES ('jsglTreeGrid', null, 'wrench', '角色表格', '1', '/admin/role/treeGrid', 'jsgl', '1');
INSERT INTO `ade_module` VALUES ('wjgl', null, 'server_database', '文件管理', '6', null, 'xtgl', '1');
INSERT INTO `ade_module` VALUES ('wjglUpload', null, 'server_database', '上传文件', '2', '/admin/file/upload', 'wjgl', '1');
INSERT INTO `ade_module` VALUES ('wjglView', null, 'server_database', '浏览服务器文件', '1', '/admin/file/fileManage', 'wjgl', '1');
INSERT INTO `ade_module` VALUES ('xtgl', null, 'plugin', '系统管理', '0', null, null, '0');
INSERT INTO `ade_module` VALUES ('yhgl', null, 'status_online', '用户管理', '3', '/admin/user/manager', 'xtgl', '0');
INSERT INTO `ade_module` VALUES ('yhglAdd', null, 'wrench', '添加用户', '3', '/admin/user/add', 'yhgl', '1');
INSERT INTO `ade_module` VALUES ('yhglAddPage', null, 'wrench', '添加用户页面', '2', '/admin/user/addPage', 'yhgl', '1');
INSERT INTO `ade_module` VALUES ('yhglBatchDelete', null, 'wrench', '批量删除用户', '7', '/admin/user/batchDelete', 'yhgl', '1');
INSERT INTO `ade_module` VALUES ('yhglDateGrid', null, 'wrench', '用户表格', '1', '/admin/user/dataGrid', 'yhgl', '1');
INSERT INTO `ade_module` VALUES ('yhglDelete', null, 'wrench', '删除用户', '6', '/admin/user/delete', 'yhgl', '1');
INSERT INTO `ade_module` VALUES ('yhglEdit', null, 'wrench', '编辑用户', '5', '/admin/user/edit', 'yhgl', '1');
INSERT INTO `ade_module` VALUES ('yhglEditPage', null, 'wrench', '编辑用户页面', '4', '/admin/user/editPage', 'yhgl', '1');
INSERT INTO `ade_module` VALUES ('yhglEditPwd', null, 'wrench', '用户修改密码', '11', '/admin/user/editPwd', 'yhgl', '1');
INSERT INTO `ade_module` VALUES ('yhglEditPwdPage', null, 'wrench', '用户修改密码页面', '10', '/admin/user/editPwdPage', 'yhgl', '1');
INSERT INTO `ade_module` VALUES ('yhglGrant', null, 'wrench', '用户授权', '9', '/admin/user/grant', 'yhgl', '1');
INSERT INTO `ade_module` VALUES ('yhglGrantPage', null, 'wrench', '用户授权页面', '8', '/admin/user/grantPage', 'yhgl', '1');
INSERT INTO `ade_module` VALUES ('zygl', '管理系统中所有的菜单或功能', 'database_gear', '模块管理', '1', '/admin/module/manager', 'xtgl', '0');
INSERT INTO `ade_module` VALUES ('zyglAdd', null, 'wrench', '添加模块', '4', '/admin/module/add', 'zygl', '1');
INSERT INTO `ade_module` VALUES ('zyglAddPage', null, 'wrench', '添加模块页面', '3', '/admin/module/addPage', 'zygl', '1');
INSERT INTO `ade_module` VALUES ('zyglDelete', null, 'wrench', '删除模块', '7', '/admin/module/delete', 'zygl', '1');
INSERT INTO `ade_module` VALUES ('zyglEdit', null, 'wrench', '编辑模块', '6', '/admin/module/edit', 'zygl', '1');
INSERT INTO `ade_module` VALUES ('zyglEditPage', null, 'wrench', '编辑模块页面', '5', '/admin/module/editPage', 'zygl', '1');
INSERT INTO `ade_module` VALUES ('zyglMenu', null, 'wrench', '功能菜单', '2', '/moduleController/tree', 'zygl', '1');
INSERT INTO `ade_module` VALUES ('zyglTreeGrid', '显示模块列表', 'wrench', '模块表格', '1', '/admin/module/treeGrid', 'zygl', '1');

-- ----------------------------
-- Table structure for ade_module_type
-- ----------------------------
DROP TABLE IF EXISTS `ade_module_type`;
CREATE TABLE `ade_module_type` (
  `ID` varchar(32) NOT NULL,
  `NAME` varchar(100) NOT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of ade_module_type
-- ----------------------------
INSERT INTO `ade_module_type` VALUES ('0', '系统菜单');
INSERT INTO `ade_module_type` VALUES ('1', '功能');

-- ----------------------------
-- Table structure for ade_role
-- ----------------------------
DROP TABLE IF EXISTS `ade_role`;
CREATE TABLE `ade_role` (
  `ID` varchar(32) NOT NULL,
  `ROLE_DESC` varchar(255) DEFAULT NULL,
  `ROLE_NAME` varchar(100) NOT NULL,
  `ROLE_SORT` int(11) DEFAULT NULL,
  `ROLE_PID` varchar(32) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  KEY `FK_fe6sykvjkvvg2oc0mpe286in9` (`ROLE_PID`) USING BTREE,
  CONSTRAINT `ade_role_ibfk_1` FOREIGN KEY (`ROLE_PID`) REFERENCES `ade_role` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of ade_role
-- ----------------------------
INSERT INTO `ade_role` VALUES ('0', '系统开发员角色，拥有系统中所有的模块访问权限', '系统开发员', '0', null);
INSERT INTO `ade_role` VALUES ('2458BE4F889B45AD9845897D38F6F23A', '维护系统中的所有内容管理信息', '信息管理员', '103', '0');
INSERT INTO `ade_role` VALUES ('2DC54FCBD7BF46DBA92B8B2DE0392B27', '能够操作系统中的所有的功能', '系统管理员', '100', '0');
INSERT INTO `ade_role` VALUES ('3340625951174C16A5938188D0FF0FC2', '数据征集管理员', '数据征集管理员', '102', '0');
INSERT INTO `ade_role` VALUES ('638CCDB7E8394465910F1423C2BA19FC', '反反复复', '呵呵', '100', null);
INSERT INTO `ade_role` VALUES ('6537E6FF69504E008DACBD0CED734841', '呜呜呜呜呜呜', '角色管理员', '100', null);
INSERT INTO `ade_role` VALUES ('66D9D2E49D1341DB8238BE8DE47240A2', '申报管理的日常维护者', '申报审核员', '101', '0');
INSERT INTO `ade_role` VALUES ('B74C3F6E59BB4BA6AC4644AE2548C0EF', '', '普通角色', '100', null);
INSERT INTO `ade_role` VALUES ('B99BC911F8E744F9B6B27A8604977713', '少时诵诗书', '日志管理', '100', null);
INSERT INTO `ade_role` VALUES ('BE08BECBE38645A58AE64614219FC774', '监管机构业务员', '监管机构业务员', '105', '0');
INSERT INTO `ade_role` VALUES ('F3353214A1E140FC9BA79E683A586AD9', '', '领导角色', '100', null);

-- ----------------------------
-- Table structure for ade_role_module
-- ----------------------------
DROP TABLE IF EXISTS `ade_role_module`;
CREATE TABLE `ade_role_module` (
  `ROLE_ID` varchar(32) NOT NULL,
  `MODULE_ID` varchar(32) NOT NULL,
  PRIMARY KEY (`MODULE_ID`,`ROLE_ID`),
  KEY `FK_s8ixvsib2601l6a4m1xpapkcb` (`MODULE_ID`) USING BTREE,
  KEY `FK_sb6kki3iapqtjrw0krucaya3d` (`ROLE_ID`) USING BTREE,
  CONSTRAINT `ade_role_module_ibfk_1` FOREIGN KEY (`MODULE_ID`) REFERENCES `ade_module` (`ID`),
  CONSTRAINT `ade_role_module_ibfk_2` FOREIGN KEY (`ROLE_ID`) REFERENCES `ade_role` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of ade_role_module
-- ----------------------------
INSERT INTO `ade_role_module` VALUES ('0', '3D17B0355C42440B8B71E6C4233AEDD6');
INSERT INTO `ade_role_module` VALUES ('6537E6FF69504E008DACBD0CED734841', '3D17B0355C42440B8B71E6C4233AEDD6');
INSERT INTO `ade_role_module` VALUES ('0', '404D882B37DD47418C493575FF2FFD18');
INSERT INTO `ade_role_module` VALUES ('6537E6FF69504E008DACBD0CED734841', '404D882B37DD47418C493575FF2FFD18');
INSERT INTO `ade_role_module` VALUES ('B74C3F6E59BB4BA6AC4644AE2548C0EF', '404D882B37DD47418C493575FF2FFD18');
INSERT INTO `ade_role_module` VALUES ('F3353214A1E140FC9BA79E683A586AD9', '404D882B37DD47418C493575FF2FFD18');
INSERT INTO `ade_role_module` VALUES ('0', '7C17F78FC0924899B29F8EE88468D1E1');
INSERT INTO `ade_role_module` VALUES ('6537E6FF69504E008DACBD0CED734841', '7C17F78FC0924899B29F8EE88468D1E1');
INSERT INTO `ade_role_module` VALUES ('B74C3F6E59BB4BA6AC4644AE2548C0EF', '7C17F78FC0924899B29F8EE88468D1E1');
INSERT INTO `ade_role_module` VALUES ('F3353214A1E140FC9BA79E683A586AD9', '7C17F78FC0924899B29F8EE88468D1E1');
INSERT INTO `ade_role_module` VALUES ('0', '9FAF885F07C8449A80CBA50A0DF85562');
INSERT INTO `ade_role_module` VALUES ('6537E6FF69504E008DACBD0CED734841', '9FAF885F07C8449A80CBA50A0DF85562');
INSERT INTO `ade_role_module` VALUES ('0', 'B043B3E837594283824407215B9142D4');
INSERT INTO `ade_role_module` VALUES ('6537E6FF69504E008DACBD0CED734841', 'B043B3E837594283824407215B9142D4');
INSERT INTO `ade_role_module` VALUES ('B74C3F6E59BB4BA6AC4644AE2548C0EF', 'B043B3E837594283824407215B9142D4');
INSERT INTO `ade_role_module` VALUES ('F3353214A1E140FC9BA79E683A586AD9', 'B043B3E837594283824407215B9142D4');
INSERT INTO `ade_role_module` VALUES ('0', 'BAAE012B6AD84C87BD712B558BD96C83');
INSERT INTO `ade_role_module` VALUES ('6537E6FF69504E008DACBD0CED734841', 'BAAE012B6AD84C87BD712B558BD96C83');
INSERT INTO `ade_role_module` VALUES ('0', 'buggl');
INSERT INTO `ade_role_module` VALUES ('B99BC911F8E744F9B6B27A8604977713', 'buggl');
INSERT INTO `ade_role_module` VALUES ('0', 'bugglAdd');
INSERT INTO `ade_role_module` VALUES ('0', 'bugglAddPage');
INSERT INTO `ade_role_module` VALUES ('0', 'bugglDateGrid');
INSERT INTO `ade_role_module` VALUES ('0', 'bugglDelete');
INSERT INTO `ade_role_module` VALUES ('0', 'bugglEdit');
INSERT INTO `ade_role_module` VALUES ('0', 'bugglEditPage');
INSERT INTO `ade_role_module` VALUES ('0', 'bugglView');
INSERT INTO `ade_role_module` VALUES ('0', 'FAC11905F8164D639068E0379A37B849');
INSERT INTO `ade_role_module` VALUES ('6537E6FF69504E008DACBD0CED734841', 'FAC11905F8164D639068E0379A37B849');
INSERT INTO `ade_role_module` VALUES ('B74C3F6E59BB4BA6AC4644AE2548C0EF', 'FAC11905F8164D639068E0379A37B849');
INSERT INTO `ade_role_module` VALUES ('F3353214A1E140FC9BA79E683A586AD9', 'FAC11905F8164D639068E0379A37B849');
INSERT INTO `ade_role_module` VALUES ('0', 'jsgl');
INSERT INTO `ade_role_module` VALUES ('6537E6FF69504E008DACBD0CED734841', 'jsgl');
INSERT INTO `ade_role_module` VALUES ('B99BC911F8E744F9B6B27A8604977713', 'jsgl');
INSERT INTO `ade_role_module` VALUES ('0', 'jsglAdd');
INSERT INTO `ade_role_module` VALUES ('0', 'jsglAddPage');
INSERT INTO `ade_role_module` VALUES ('0', 'jsglDelete');
INSERT INTO `ade_role_module` VALUES ('0', 'jsglEdit');
INSERT INTO `ade_role_module` VALUES ('0', 'jsglEditPage');
INSERT INTO `ade_role_module` VALUES ('0', 'jsglGrant');
INSERT INTO `ade_role_module` VALUES ('0', 'jsglGrantPage');
INSERT INTO `ade_role_module` VALUES ('0', 'jsglTreeGrid');
INSERT INTO `ade_role_module` VALUES ('0', 'wjgl');
INSERT INTO `ade_role_module` VALUES ('0', 'wjglUpload');
INSERT INTO `ade_role_module` VALUES ('0', 'wjglView');
INSERT INTO `ade_role_module` VALUES ('0', 'xtgl');
INSERT INTO `ade_role_module` VALUES ('B74C3F6E59BB4BA6AC4644AE2548C0EF', 'xtgl');
INSERT INTO `ade_role_module` VALUES ('0', 'yhgl');
INSERT INTO `ade_role_module` VALUES ('2DC54FCBD7BF46DBA92B8B2DE0392B27', 'yhgl');
INSERT INTO `ade_role_module` VALUES ('6537E6FF69504E008DACBD0CED734841', 'yhgl');
INSERT INTO `ade_role_module` VALUES ('B99BC911F8E744F9B6B27A8604977713', 'yhgl');
INSERT INTO `ade_role_module` VALUES ('0', 'yhglAdd');
INSERT INTO `ade_role_module` VALUES ('0', 'yhglAddPage');
INSERT INTO `ade_role_module` VALUES ('0', 'yhglBatchDelete');
INSERT INTO `ade_role_module` VALUES ('0', 'yhglDateGrid');
INSERT INTO `ade_role_module` VALUES ('0', 'yhglDelete');
INSERT INTO `ade_role_module` VALUES ('0', 'yhglEdit');
INSERT INTO `ade_role_module` VALUES ('0', 'yhglEditPage');
INSERT INTO `ade_role_module` VALUES ('0', 'yhglEditPwd');
INSERT INTO `ade_role_module` VALUES ('0', 'yhglEditPwdPage');
INSERT INTO `ade_role_module` VALUES ('0', 'yhglGrant');
INSERT INTO `ade_role_module` VALUES ('0', 'yhglGrantPage');
INSERT INTO `ade_role_module` VALUES ('0', 'zygl');
INSERT INTO `ade_role_module` VALUES ('B74C3F6E59BB4BA6AC4644AE2548C0EF', 'zygl');
INSERT INTO `ade_role_module` VALUES ('0', 'zyglAdd');
INSERT INTO `ade_role_module` VALUES ('0', 'zyglAddPage');
INSERT INTO `ade_role_module` VALUES ('B74C3F6E59BB4BA6AC4644AE2548C0EF', 'zyglAddPage');
INSERT INTO `ade_role_module` VALUES ('0', 'zyglDelete');
INSERT INTO `ade_role_module` VALUES ('0', 'zyglEdit');
INSERT INTO `ade_role_module` VALUES ('0', 'zyglEditPage');
INSERT INTO `ade_role_module` VALUES ('0', 'zyglMenu');
INSERT INTO `ade_role_module` VALUES ('0', 'zyglTreeGrid');

-- ----------------------------
-- Table structure for ade_user
-- ----------------------------
DROP TABLE IF EXISTS `ade_user`;
CREATE TABLE `ade_user` (
  `ID` varchar(32) NOT NULL,
  `LOGIN_NAME` varchar(100) NOT NULL,
  `USER_NAME` varchar(50) DEFAULT NULL,
  `NAME_LETTER` varchar(10) DEFAULT NULL,
  `PASSWORD` varchar(100) NOT NULL,
  `SEX` varchar(2) DEFAULT NULL,
  `EMAIL` varchar(50) DEFAULT NULL,
  `TELE_PHONE` varchar(30) DEFAULT NULL,
  `USER_PHOTO` varchar(255) DEFAULT NULL,
  `MOBILE_PHONE` varchar(30) DEFAULT NULL,
  `USER_DESC` varchar(255) DEFAULT NULL,
  `USER_STATE` varchar(2) DEFAULT NULL,
  `CREATE_DATE` datetime DEFAULT NULL,
  `IP` text COMMENT '多个ip用;隔开',
  `AGENCY_FLAG` varchar(1) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  UNIQUE KEY `UK_lub3dp8cwuw4mkn5e9nkw6c0i` (`LOGIN_NAME`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of ade_user
-- ----------------------------
INSERT INTO `ade_user` VALUES ('0', 'admin', null, null, 'e10adc3949ba59abbe56e057f20f883e', null, null, null, null, null, null, null, null, null, null);
INSERT INTO `ade_user` VALUES ('15AA9B8775044C478BE3D7306D515478', '222222', null, null, 'e3ceb5881a0a1fdaad01296d7554868d', null, null, null, null, '18622222222', '科研部/科研', null, null, null, null);
INSERT INTO `ade_user` VALUES ('255679B8A1544A0895D4D9620136351E', 'bububu', null, null, '098eb8ba2cc924fad0ec05acd869a4eb', null, null, null, null, null, null, '1', '2016-03-01 15:14:00', null, null);
INSERT INTO `ade_user` VALUES ('4366B45B3B334B62A413E8072E1DFCE1', '333333', '333333', '3', '1a100d2c0dab19c4430e7d73762b3423', null, '333@33.com', '010-33333333', null, '13033333333', null, '9', '2016-03-24 14:13:33', null, null);
INSERT INTO `ade_user` VALUES ('5B3664A05F12453A871E34EFAF894DD7', '777777', '777777', '7', 'f63f4fbc9f8c85d409f2f59f2b9e12d5', '女', '777777@qq.com', '010-77777777', null, '18077777777', '开发部/研发工程师', '9', '2016-03-09 17:51:41', null, null);
INSERT INTO `ade_user` VALUES ('5F36202A22FA420096E4A992B0626FCC', '555555', '555555', '5', '5b1b68a9abf4d2cd155c81a9225fd158', '男', '', '', null, '', null, '9', '2016-03-23 10:05:48', null, null);
INSERT INTO `ade_user` VALUES ('5FEEC743022D4DF1BBDB217ED2A7B774', 'agency1', 'agency1', 'A', 'e10adc3949ba59abbe56e057f20f883e', '男', '', '', null, '13022222222', '财务部/财务总监', '9', '2016-03-04 15:05:33', '192.168.3.104,192.168.3.105,192.168.11.11', null);
INSERT INTO `ade_user` VALUES ('6606BEF492DA45DCB0E528A79F156588', 'aaaaaa', 'aaaaaa', 'A', '0b4e7a0e5fe84ad35fb5f95b9ceeac79', null, 'aaa@22.com', '0311-1111111', null, '13022222222', null, '9', '2016-03-24 15:01:49', null, null);
INSERT INTO `ade_user` VALUES ('82B3C739800B4AF0924FB1659A0ED1C5', '232222', '222222', '2', 'e3ceb5881a0a1fdaad01296d7554868d', '女', '222222@22.com', '010-22222222', null, '13022222222', null, '9', '2016-03-08 10:04:03', null, null);
INSERT INTO `ade_user` VALUES ('B9A5DEEE9106411497E9D535FB5AF7EB', '666666', '666666', '6', 'd41d8cd98f00b204e9800998ecf8427e', '男', '', '', null, '', null, '9', '2016-03-09 17:50:30', null, null);
INSERT INTO `ade_user` VALUES ('BD4868573F27420BB744E6919F44927A', '999999', '999999', '9', '52c69e3a57331081823331c4e69d3f2e', '女', '99999@163.com', '010-999999', null, '18099999999', null, '9', '2016-03-23 14:32:24', null, null);
INSERT INTO `ade_user` VALUES ('C20D5D4593E5470A868C3425A25E2661', '111111', '111111', '1', '96e79218965eb72c92a549dd5a330112', '女', '111111@111.com', '010-11111111', null, '18011111111', null, '9', '2016-03-04 10:19:05', null, null);

-- ----------------------------
-- Table structure for ade_user_logs
-- ----------------------------
DROP TABLE IF EXISTS `ade_user_logs`;
CREATE TABLE `ade_user_logs` (
  `ID` varchar(36) NOT NULL,
  `CONTENT` varchar(255) NOT NULL,
  `IE_TYPE` varchar(36) NOT NULL,
  `IP` varchar(36) NOT NULL,
  `LOG_TYPE` int(11) NOT NULL,
  `OPERATE_TIME` datetime NOT NULL,
  `USER_ID` varchar(100) DEFAULT NULL,
  `VALUE1` varchar(100) DEFAULT NULL,
  `VALUE2` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of ade_user_logs
-- ----------------------------

-- ----------------------------
-- Table structure for ade_user_role
-- ----------------------------
DROP TABLE IF EXISTS `ade_user_role`;
CREATE TABLE `ade_user_role` (
  `USER_ID` varchar(32) NOT NULL,
  `ROLE_ID` varchar(32) NOT NULL,
  PRIMARY KEY (`ROLE_ID`,`USER_ID`),
  KEY `FK_8vmilpq0v3wkfbw2jiwl5h2jy` (`ROLE_ID`) USING BTREE,
  KEY `FK_fb1o8fuyirvvk9t5g7e4v0o5f` (`USER_ID`) USING BTREE,
  CONSTRAINT `ade_user_role_ibfk_1` FOREIGN KEY (`ROLE_ID`) REFERENCES `ade_role` (`ID`),
  CONSTRAINT `ade_user_role_ibfk_2` FOREIGN KEY (`USER_ID`) REFERENCES `ade_user` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of ade_user_role
-- ----------------------------
INSERT INTO `ade_user_role` VALUES ('0', '0');
INSERT INTO `ade_user_role` VALUES ('0', '6537E6FF69504E008DACBD0CED734841');
INSERT INTO `ade_user_role` VALUES ('255679B8A1544A0895D4D9620136351E', '6537E6FF69504E008DACBD0CED734841');
INSERT INTO `ade_user_role` VALUES ('0', 'B74C3F6E59BB4BA6AC4644AE2548C0EF');
INSERT INTO `ade_user_role` VALUES ('C20D5D4593E5470A868C3425A25E2661', 'B74C3F6E59BB4BA6AC4644AE2548C0EF');
INSERT INTO `ade_user_role` VALUES ('0', 'F3353214A1E140FC9BA79E683A586AD9');

-- ----------------------------
-- Table structure for biz_attach_standard
-- ----------------------------
DROP TABLE IF EXISTS `biz_attach_standard`;
CREATE TABLE `biz_attach_standard` (
  `id` varchar(32) NOT NULL,
  `disease_standard_id` varchar(32) DEFAULT NULL,
  `name` varchar(500) DEFAULT NULL COMMENT '附件名称',
  `url` varchar(500) DEFAULT NULL COMMENT '附件url',
  `size` varchar(50) DEFAULT NULL COMMENT '附件大小',
  `order_num` int(11) DEFAULT '0' COMMENT '排序号',
  `create_time` datetime DEFAULT NULL COMMENT '上传时间',
  `create_user` varchar(255) DEFAULT NULL COMMENT '上传人',
  `is_can_delete` varchar(2) DEFAULT NULL COMMENT '是否可以删除(0:可以删除,1不可以删除)',
  PRIMARY KEY (`id`),
  KEY `disease_standard_id` (`disease_standard_id`),
  CONSTRAINT `biz_attach_standard_ibfk_1` FOREIGN KEY (`disease_standard_id`) REFERENCES `biz_standard` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of biz_attach_standard
-- ----------------------------

-- ----------------------------
-- Table structure for biz_disease
-- ----------------------------
DROP TABLE IF EXISTS `biz_disease`;
CREATE TABLE `biz_disease` (
  `id` varchar(32) NOT NULL COMMENT '主键UUID',
  `first_letter` varchar(2) DEFAULT NULL COMMENT '首字符,分组字段',
  `code` varchar(10) DEFAULT NULL COMMENT '疾病代码',
  `name` varchar(100) DEFAULT NULL COMMENT '广安门医院命名的病名',
  `tcm_disease_name` varchar(100) DEFAULT NULL COMMENT '中医病名',
  `mm_disease_name` varchar(200) DEFAULT NULL COMMENT '西医病名',
  `en_name` varchar(200) DEFAULT NULL COMMENT '英文名称',
  `remark` varchar(500) DEFAULT NULL COMMENT '备注',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of biz_disease
-- ----------------------------
INSERT INTO `biz_disease` VALUES ('1', 'A', 'b1', '病1', '病1中文名称', '病1西药名称', '病1中文名称', null, null, null);
INSERT INTO `biz_disease` VALUES ('2', 'A', 'b2', '病2', '病2中文名称', '病2西药名称', '病2中文名称', null, null, null);
INSERT INTO `biz_disease` VALUES ('34609e8b5a154c5aa064ea08e413aa12', 'B', 'aa', '命名病名a', '中医病名a', '西医病名a', 'aaaa', 'aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa', '2016-04-19 14:53:43', null);
INSERT INTO `biz_disease` VALUES ('34609e8b5a154c5aa064ea08e413aa13', 'C', 'aa', '命名病名a', '中医病名a', '西医病名a', 'aaaa', 'aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa', '2016-04-19 14:53:43', null);
INSERT INTO `biz_disease` VALUES ('34609e8b5a154c5aa064ea08e413aa1b', 'B', 'aa', '命名病名a', '中医病名a', '西医病名a', 'aaaa', 'aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa', '2016-04-19 14:53:43', null);
INSERT INTO `biz_disease` VALUES ('d363feb2a2014a209e250c43a4bb8a9c', '1', '1', '中医1', '中医2', '中医3', '中医4', '2222', '2016-04-22 17:42:50', null);

-- ----------------------------
-- Table structure for biz_disease_privilege
-- ----------------------------
DROP TABLE IF EXISTS `biz_disease_privilege`;
CREATE TABLE `biz_disease_privilege` (
  `user_id` varchar(32) NOT NULL COMMENT '用户ID',
  `disease_id` varchar(32) NOT NULL COMMENT '疾病ID',
  `privilege_code_id` varchar(32) NOT NULL COMMENT '疾病的权限的ID',
  PRIMARY KEY (`user_id`,`privilege_code_id`,`disease_id`),
  KEY `disease_id` (`disease_id`) USING BTREE,
  KEY `privilege_code_id` (`privilege_code_id`) USING BTREE,
  CONSTRAINT `biz_disease_privilege_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `ade_user` (`ID`),
  CONSTRAINT `biz_disease_privilege_ibfk_2` FOREIGN KEY (`disease_id`) REFERENCES `biz_disease` (`id`),
  CONSTRAINT `biz_disease_privilege_ibfk_3` FOREIGN KEY (`privilege_code_id`) REFERENCES `biz_privilege_info` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of biz_disease_privilege
-- ----------------------------
INSERT INTO `biz_disease_privilege` VALUES ('0', '1', '1');
INSERT INTO `biz_disease_privilege` VALUES ('0', '1', '2');
INSERT INTO `biz_disease_privilege` VALUES ('0', '2', '1');
INSERT INTO `biz_disease_privilege` VALUES ('0', '2', '2');
INSERT INTO `biz_disease_privilege` VALUES ('0', '2', '3');

-- ----------------------------
-- Table structure for biz_portal_user
-- ----------------------------
DROP TABLE IF EXISTS `biz_portal_user`;
CREATE TABLE `biz_portal_user` (
  `id` varchar(32) NOT NULL,
  `loginName` varchar(40) DEFAULT NULL COMMENT '登录名',
  `password` varchar(40) DEFAULT NULL COMMENT '密码',
  `userName` varchar(40) DEFAULT NULL COMMENT '用户名',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  `IsDel` varchar(2) DEFAULT NULL COMMENT '是否删除：1删除，非1正常',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of biz_portal_user
-- ----------------------------
INSERT INTO `biz_portal_user` VALUES ('6f34c657cfd64d8f9680704e5989e184', '11234', '7215ee9c7d9dc229d2921a40e899ec5f', '111234', '2016-04-19 10:18:03', '2016-04-19 10:45:23', '0');
INSERT INTO `biz_portal_user` VALUES ('fe598abeefa842aea5e32eca75b63d38', '222', '310dcbbf4cce62f762a2aaa148d556bd', '1111', '2016-04-22 14:01:28', '2016-04-22 14:01:35', '0');

-- ----------------------------
-- Table structure for biz_privilege_info
-- ----------------------------
DROP TABLE IF EXISTS `biz_privilege_info`;
CREATE TABLE `biz_privilege_info` (
  `id` varchar(32) NOT NULL COMMENT '主键UUID',
  `code` varchar(50) DEFAULT NULL COMMENT '权限代码',
  `name` varchar(50) DEFAULT NULL COMMENT '权限中文名称',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of biz_privilege_info
-- ----------------------------
INSERT INTO `biz_privilege_info` VALUES ('1', 'edit', '编辑');
INSERT INTO `biz_privilege_info` VALUES ('2', 'auedit', '审核');
INSERT INTO `biz_privilege_info` VALUES ('3', 'publish', '发布');

-- ----------------------------
-- Table structure for biz_standard
-- ----------------------------
DROP TABLE IF EXISTS `biz_standard`;
CREATE TABLE `biz_standard` (
  `id` varchar(32) NOT NULL COMMENT '主键UUID',
  `disease_id` varchar(32) DEFAULT NULL COMMENT '疾病ID',
  `orderNum` varchar(50) DEFAULT NULL COMMENT '序号(T01代表疾病名称，_001代表标准顺序号，如：F04_001)',
  `type` varchar(2) DEFAULT NULL COMMENT '标准类型：0中医标准,1西医标准',
  `cn_title` varchar(100) DEFAULT NULL COMMENT '中文题名',
  `en_title` varchar(100) DEFAULT NULL COMMENT '英文题名',
  `publish_org` varchar(100) DEFAULT NULL COMMENT '发布机构',
  `publish_date` date DEFAULT NULL COMMENT '发布日期',
  `source` varchar(100) DEFAULT NULL COMMENT '来源',
  `language` varchar(50) DEFAULT NULL COMMENT '全文语种',
  `type_stag` varchar(500) DEFAULT NULL COMMENT '分型分期',
  `mm_standard` text COMMENT '西医诊断标准',
  `mm_disease_name` varchar(255) DEFAULT NULL COMMENT '西医病名',
  `tcm_disease_name` varchar(255) DEFAULT NULL COMMENT '中医病名',
  `tcm_disease_dialectical` text COMMENT '中医辨证',
  `tcm_standard` text COMMENT '中医诊断标准',
  `effect_target` varchar(255) DEFAULT NULL COMMENT '疗效指标',
  `effect_standard` text COMMENT '疗效判定标准',
  `remark` text COMMENT '备注',
  `status` varchar(2) DEFAULT NULL COMMENT '发布状态（1:新建，2:待审核,3:审核通过,4:审核未通过,5:发布通过,6:发布未通过,7:撤销发布）',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `create_userName` varchar(100) DEFAULT NULL COMMENT '创建人',
  `update_userName` varchar(100) DEFAULT NULL COMMENT '最后更新人',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`),
  KEY `disease_id` (`disease_id`) USING BTREE,
  CONSTRAINT `biz_standard_ibfk_1` FOREIGN KEY (`disease_id`) REFERENCES `biz_disease` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of biz_standard
-- ----------------------------
INSERT INTO `biz_standard` VALUES ('1', '1', null, '0', '中医内科常见病诊疗指南 ', 'Symptoms ', '肺病3', '2016-04-21', null, null, null, null, null, '肺心病', '1', null, null, null, null, null, null, null, null, null);
INSERT INTO `biz_standard` VALUES ('2', '1', null, '1', '临床诊疗指南(呼吸病学分册)1', 'Symptoms ', '肺病1', '2016-04-21', null, null, null, null, null, '肺原性心脏病1', '1', null, null, null, null, null, null, null, null, null);
INSERT INTO `biz_standard` VALUES ('3', '1', null, '1', '临床诊疗指南(呼吸病学分册)2', 'Symptoms ', '肺病2', '2016-04-21', null, null, null, null, null, '肺原性心脏病2', '1', null, null, null, null, null, null, null, null, null);
INSERT INTO `biz_standard` VALUES ('40922f6dae77459eaa4f8802de85a201', '1', '6666', '0', '6666', '', '666666', null, '', '', '', '6', '', '', '', '6', '', '6', '', null, '2016-04-22 18:27:38', null, null, null);
INSERT INTO `biz_standard` VALUES ('79dcaeb0cf7b40878c3305f23291741c', '1', 'bbbb', '0', 'bbbbb', '', '', null, '', '', '', 'bb', '', '', '', 'bb', '', 'b', '', null, '2016-04-22 15:34:52', null, null, null);
INSERT INTO `biz_standard` VALUES ('9d6c6d46176e4c599a919f7d5cbba89c', '1', '55', '0', '666', '', '', null, '', '', '', 'afd', '', '', '', 'afd', '', 'adfd', '', null, '2016-04-22 18:25:41', null, null, null);
INSERT INTO `biz_standard` VALUES ('a18e66d136e24d0bbcf83b9777184392', null, '序号', '0', '中文题名', '英文题名', '发布机构', null, '来源', '全文语种', '分型分期分型分期', '西医诊断标准西医诊断标准西医诊断标准西医诊断标准', '西医病名', '中医病名', '中医辨证', '中医诊断标准中医诊断标准中医诊断标准', '疗效指标疗效指标', '疗效判定标准疗效判定标准疗效判定标准', '备注备注备注备注备注', null, null, null, null, null);

-- ----------------------------
-- Table structure for biz_suggest
-- ----------------------------
DROP TABLE IF EXISTS `biz_suggest`;
CREATE TABLE `biz_suggest` (
  `id` varchar(32) NOT NULL COMMENT '主键UUID',
  `standard_id` varchar(32) NOT NULL COMMENT '标准ID',
  `type` varchar(2) DEFAULT NULL COMMENT '1:审核，2:发布',
  `content` varchar(500) DEFAULT NULL COMMENT '意见内容',
  `pubtime` datetime DEFAULT NULL COMMENT '发表时间',
  `pubUser` varchar(50) DEFAULT NULL COMMENT '发表人',
  PRIMARY KEY (`id`),
  KEY `biz_suggest_ibfk_1` (`standard_id`),
  CONSTRAINT `biz_suggest_ibfk_1` FOREIGN KEY (`standard_id`) REFERENCES `biz_standard` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of biz_suggest
-- ----------------------------

-- ----------------------------
-- Table structure for biz_symptom
-- ----------------------------
DROP TABLE IF EXISTS `biz_symptom`;
CREATE TABLE `biz_symptom` (
  `id` varchar(32) NOT NULL COMMENT '主键UUID',
  `name` varchar(100) DEFAULT NULL COMMENT '证侯名称',
  `disease_name` varchar(100) DEFAULT NULL COMMENT '疾病名称',
  `symptom_name` varchar(100) DEFAULT NULL COMMENT '症状名称',
  `literature_name` varchar(255) DEFAULT NULL COMMENT '文献题名',
  `journal_name` varchar(100) DEFAULT NULL COMMENT '期刊名称',
  `year_journal_page` varchar(60) DEFAULT NULL COMMENT '年期页',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of biz_symptom
-- ----------------------------
INSERT INTO `biz_symptom` VALUES ('21', '11', '111', '111', '11', '11', '11');
INSERT INTO `biz_symptom` VALUES ('31', '11', '111', '111', '11', '11', '11');
INSERT INTO `biz_symptom` VALUES ('41', '111', '111', '11', '11', null, '11');
INSERT INTO `biz_symptom` VALUES ('51', '111', '111', '11', '11', null, '11');
INSERT INTO `biz_symptom` VALUES ('61', '111', '111', '11', '11', null, '11');
