/*
Navicat MySQL Data Transfer

Source Server         : localhost
Source Server Version : 50616
Source Host           : localhost:3306
Source Database       : ade302

Target Server Type    : MYSQL
Target Server Version : 50616
File Encoding         : 65001

Date: 2016-04-05 09:33:34
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for ade_admin_logs
-- ----------------------------
DROP TABLE IF EXISTS `ade_admin_logs`;
CREATE TABLE `ade_admin_logs` (
`ID`  varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL ,
`CONTENT`  varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL ,
`IE_TYPE`  varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL ,
`IP`  varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL ,
`LOG_TYPE`  int(11) NOT NULL ,
`MODULE_NAME`  int(11) NULL DEFAULT NULL ,
`OPERATE_TIME`  datetime NOT NULL ,
`USER_ID`  varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL ,
PRIMARY KEY (`ID`)
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci

;

-- ----------------------------
-- Records of ade_admin_logs
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for ade_bug
-- ----------------------------
DROP TABLE IF EXISTS `ade_bug`;
CREATE TABLE `ade_bug` (
`id`  varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL ,
`createdatetime`  datetime NULL DEFAULT NULL ,
`modifydatetime`  datetime NULL DEFAULT NULL ,
`name`  varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`note`  longtext CHARACTER SET utf8 COLLATE utf8_general_ci NULL ,
`bugtype_id`  varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
PRIMARY KEY (`id`),
FOREIGN KEY (`bugtype_id`) REFERENCES `ade_bugtype` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
INDEX `FK_5nrv3udsxi17efrlf8eoan1np` (`bugtype_id`) USING BTREE 
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci

;

-- ----------------------------
-- Records of ade_bug
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for ade_bugtype
-- ----------------------------
DROP TABLE IF EXISTS `ade_bugtype`;
CREATE TABLE `ade_bugtype` (
`id`  varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL ,
`name`  varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
PRIMARY KEY (`id`)
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci

;

-- ----------------------------
-- Records of ade_bugtype
-- ----------------------------
BEGIN;
INSERT INTO `ade_bugtype` (`id`, `name`) VALUES ('1', '后台管理');
COMMIT;

-- ----------------------------
-- Table structure for ade_dictionary
-- ----------------------------
DROP TABLE IF EXISTS `ade_dictionary`;
CREATE TABLE `ade_dictionary` (
`ID`  varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL ,
`ZD_CODE`  varchar(60) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL ,
`ZD_DESC`  varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`ZD_NAME`  varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL ,
`ZD_SORT`  int(11) NULL DEFAULT NULL ,
`ZD_USE`  varchar(2) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL ,
`ZD_TYPE_ID`  varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
PRIMARY KEY (`ID`),
FOREIGN KEY (`ZD_TYPE_ID`) REFERENCES `ade_dictionary_type` (`ID`) ON DELETE RESTRICT ON UPDATE RESTRICT,
INDEX `FK_9l6p5uj5th2jlkyldo7by6tn2` (`ZD_TYPE_ID`) USING BTREE 
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci

;

-- ----------------------------
-- Records of ade_dictionary
-- ----------------------------
BEGIN;
INSERT INTO `ade_dictionary` (`ID`, `ZD_CODE`, `ZD_DESC`, `ZD_NAME`, `ZD_SORT`, `ZD_USE`, `ZD_TYPE_ID`) VALUES ('015B7353165C43E3A29C92548314E4E7', '1', '', '没有或很少时间', '1', '可用', 'DA8AEBC8ABE846A0849C387E193E7F68'), ('030E911F576148D38146EF2646402603', 'srq20', '', 'SRQ20问卷', '1', '可用', 'D96D1500AB7D4FD9BC5EA37FB9EA88CB'), ('0592B499AC14483D9E1E53181A599BF1', '7', '', '任务七', '7', '可用', 'A23E6D6309A643D8B1B030D739DECB72'), ('060597549B86440EAE274F341AE91821', '0', '', '未知', '3', '可用', '2D81602F3F6D40ECA9C920762333DC69'), ('0B9FB8B64562429D9FCC696975FB61E0', '3', '', '空军', '3', '可用', 'B0BD3D34A81748E385A4D7B0C48371CB'), ('0D6D823C42114D68AE45EE71CD0505C5', 'levelType', '', '级别', '9', '可用', 'DC11AE8C8824478A8364F24D6946D76B'), ('0E605DDE7AEC490D9959054A75EE6D37', 'armyType', '', '军种', '8', '可用', 'DC11AE8C8824478A8364F24D6946D76B'), ('0F1265323BA742CCBCF1F7AB9CDFDC62', '3', '', '营', '3', '可用', '3244872A924D4DCF8154B9F0F5412E38'), ('0F14E564D4FF4E718F71489295570A4D', 'sas2', '', '2', '2', '可用', '49D684FD7F3142B2BC1E305A3C971BC1'), ('120C0383329B44BB8C78F46472B0BE3D', '3', '文件类型', '音频', '3', '可用', 'D0A5558868054FD699D774EE0C68F408'), ('1786AAA5F1E044DE886FD0B9DE41BCC6', 'srq3', '', '3', '3', '可用', '59DA0C5D4E8A4C61B67A91C54CE0278C'), ('1A006E79DFF045ED83D937B6D42C8AAC', 'sds', '', 'SDS问卷', '4', '可用', 'D96D1500AB7D4FD9BC5EA37FB9EA88CB'), ('1A1916EAE4124B378967858CD496279E', '1', '', '没有或偶尔', '1', '可用', 'EC9C8B5D2BC141BE820C73F3E75D1CD9'), ('1A678B050E7048CCA68275FB1891756B', '4', '文件类型', '图片', '4', '可用', 'D0A5558868054FD699D774EE0C68F408'), ('1CFBD4C18ACB4FC7B8FD938D34FE118E', '1', '', '排', '1', '可用', '3244872A924D4DCF8154B9F0F5412E38'), ('20455DAD989F40E5B97960FB0BB90446', 'pclc_c', '', 'PCLC', '1', '可用', 'EFFA9C09EAC3461CB38D052ABFA12790'), ('21C0DEAEAE48404D857665604CFE612A', '4', '', '武警', '4', '可用', 'B0BD3D34A81748E385A4D7B0C48371CB'), ('225CFA16CDDA4C78B759007570ACBCCF', 'srq1', '', '1', '1', '可用', '59DA0C5D4E8A4C61B67A91C54CE0278C'), ('22AE04DB270B4D81B734C124B48D1C83', '2', '', '高中', '2', '可用', 'F5D396962A3447238AD2BA23C9EFE2BD'), ('23DC272BD7EF4EB387184E9ABD18C3BF', '2', '', '任务二', '2', '可用', 'A23E6D6309A643D8B1B030D739DECB72'), ('24936B982E3F44578ED8C06A4DBBAFEA', 'srq_c', '', 'SRQ20', '2', '可用', 'EFFA9C09EAC3461CB38D052ABFA12790'), ('28F156BCE3BC4BE4801302B921FE189A', '0', '', '未知', '7', '可用', 'F5D396962A3447238AD2BA23C9EFE2BD'), ('2C758B11C3DD4BBF99019D27E0742854', '12', '', '12', '12', '可用', 'D079AB9DE85D4126A7B8C8FE775DDC41'), ('2E9F73B5D9254277A4AC463751C38E21', 'sds3', '', '3', '3', '可用', '631B249A2C3340A3AAF60965556B9FDD'), ('2F4F5EC86F284F50B51F29983C90AD11', '1', '', '1', '1', '可用', 'D079AB9DE85D4126A7B8C8FE775DDC41'), ('2FAB718FAFC1414C82F6F7B873835533', '5', '', '云服务资源', '5', '可用', '97A91409700B4DE888C4F333A8A759EC'), ('3AAFB4725F834FE9AA4B93A546B5C011', '4', '', '中性', '4', '可用', 'C72D5FDF32CF4A57BED0CA9B4EC4F059'), ('3B13267E5A794092BAD10AC2487C8A11', '1', '', '标准SAS指标', '1', '可用', 'A9A794427380454CB6DAEB13C2C1D115'), ('3B6C3960FC9D48F584C715FCC339FB11', '2', '', '有时', '2', '可用', 'EC9C8B5D2BC141BE820C73F3E75D1CD9'), ('3D1BC29E16194EC3804E94D7B80EB810', '2', '', '海军', '2', '可用', 'B0BD3D34A81748E385A4D7B0C48371CB');
COMMIT;

-- ----------------------------
-- Table structure for ade_dictionary_type
-- ----------------------------
DROP TABLE IF EXISTS `ade_dictionary_type`;
CREATE TABLE `ade_dictionary_type` (
`ID`  varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL ,
`ZD_TYPE_CODE`  varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL ,
`ZD_TYPE_DESC`  varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL ,
`ZD_TYPE_NAME`  varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL ,
`ZD_TYPE_SORT`  int(11) NULL DEFAULT NULL ,
`ZD_TYPE_USE`  varchar(2) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL ,
PRIMARY KEY (`ID`)
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci

;

-- ----------------------------
-- Records of ade_dictionary_type
-- ----------------------------
BEGIN;
INSERT INTO `ade_dictionary_type` (`ID`, `ZD_TYPE_CODE`, `ZD_TYPE_DESC`, `ZD_TYPE_NAME`, `ZD_TYPE_SORT`, `ZD_TYPE_USE`) VALUES ('0C638047CD2F4222BD355741022DEFC2', 'srq_c_answer', '', 'SRQ20答案', '28', '可用'), ('29E89D282BFD431BAF222BAC0E1A7217', 'ptci_c', '', 'PTCI题号', '24', '可用'), ('2D81602F3F6D40ECA9C920762333DC69', 'sexType', '性别', '性别', '4', '可用'), ('3244872A924D4DCF8154B9F0F5412E38', 'levelType', '', '级别', '12', '可用'), ('35EAA45D61684D00BF74354804D56DAD', 'expert_type', '', '专家类型', '1', '1'), ('36F330F59F8E45F69C1D3AC1D236C78C', 'ptci', '', 'PTCI问卷', '17', '可用'), ('3D5BB994F9A94435AE1EFD50C69B4B7F', 'ifOnly', '是否独生子\r\n系统字典维护', '独生子', '7', '可用'), ('49D684FD7F3142B2BC1E305A3C971BC1', 'sas_c', '', 'SAS题号', '25', '可用'), ('59DA0C5D4E8A4C61B67A91C54CE0278C', 'srq_c', '', 'SRQ20题号', '23', '可用'), ('602CEF931B944D73913625566059F095', 'familyBackGround', '', '家庭背景', '6', '可用'), ('61551E5E14934D6BB7C43BE8C8449A7A', 'sds', '', 'SDS问卷', '18', '可用'), ('631B249A2C3340A3AAF60965556B9FDD', 'sds_c', '', 'SDS题号', '26', '可用'), ('8DB19D3D62A44231B8D2236F938BC3D2', 'pclc_c_answer', '', 'PCLC答案', '22', '可用'), ('92A74C672FDC41D3A5B5D5D1491AD7DC', 'bigCompany', '大单位', '大单位', '3', '可用'), ('97A91409700B4DE888C4F333A8A759EC', 'resourceGenre', '', '资源大类', '2', '可用'), ('99EB8D38893D4C6DB5E6CF387D41A7DC', 'marriage', '', '婚姻状况', '9', '可用'), ('A11260FF2C424F0D8BE6A0BFBE02C3FA', 'pclc', '', 'PCLC问卷', '15', '可用'), ('A23E6D6309A643D8B1B030D739DECB72', 'task', '', '任务', '31', '可用'), ('A9A794427380454CB6DAEB13C2C1D115', 'sas', '', 'SAS问卷', '19', '可用'), ('B0BD3D34A81748E385A4D7B0C48371CB', 'armyType', '', '军种', '10', '可用'), ('C72D5FDF32CF4A57BED0CA9B4EC4F059', 'ptci_c_answer', '', 'PTCI答案', '27', '可用'), ('D079AB9DE85D4126A7B8C8FE775DDC41', 'group_count', '', '分组个数', '32', '可用'), ('D0A5558868054FD699D774EE0C68F408', 'textType', '系统中资源添加时，文件类型字段维护', '文件类型', '34', '可用'), ('D96D1500AB7D4FD9BC5EA37FB9EA88CB', 'surveyType', '', '调查问卷类型', '14', '可用'), ('DA8AEBC8ABE846A0849C387E193E7F68', 'sas_c_answer', '', 'SAS答案', '29', '可用'), ('DC11AE8C8824478A8364F24D6946D76B', 'basicQuery', '', '基础查询', '13', '可用'), ('DDD7078C52F948348E422BE9EA4EFF4D', 'resFrom', '资源来源', '资源来源', '35', '在用'), ('E1C3FDA2520347668D148F6113B8492F', 'recordType', '', '记录类型', '5', '可用'), ('EC9C8B5D2BC141BE820C73F3E75D1CD9', 'sds_c_answer', '', 'SDS答案', '30', '可用'), ('ED4038C6115443FD979846FDE596B9B1', 'srq20', '', 'SRQ20问卷', '16', '可用'), ('EFFA9C09EAC3461CB38D052ABFA12790', 'course', '', '测试科目', '20', '可用'), ('F1B2B014260B411F8B9E61000CFCEC50', 'resourceClass', '平台资源横向选项卡部分调用', '资源分类', '1', '在用'), ('F596253A26794248BEC69BB4EAE32C17', 'divide_method', '分段统计', '划分方法', '33', '可用'), ('F5BF384C5EBF46A8BDD62371059C9644', 'pclc_c', '', 'PCLC题号', '21', '可用'), ('F5D396962A3447238AD2BA23C9EFE2BD', 'cultureType', '', '文化程度', '11', '可用'), ('F9ABEEA3E7B344489031A808724FD33A', 'politics', '', '政治面貌', '8', '可用');
COMMIT;

-- ----------------------------
-- Table structure for ade_group
-- ----------------------------
DROP TABLE IF EXISTS `ade_group`;
CREATE TABLE `ade_group` (
`ID`  varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL ,
`CREATE_DATE`  datetime NULL DEFAULT NULL ,
`GROUP_DESC`  varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL ,
`GROUP_FULL_NAME`  varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL ,
`GROUP_SHORT_NAME`  varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL ,
`GROUP_SORT`  varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`LAST_MODIFY_DATE`  datetime NULL DEFAULT NULL ,
`GROUP_PID`  varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`CREATE_USER`  varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`LAST_MODIFY_USER`  varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
PRIMARY KEY (`ID`),
FOREIGN KEY (`GROUP_PID`) REFERENCES `ade_group` (`ID`) ON DELETE RESTRICT ON UPDATE RESTRICT,
FOREIGN KEY (`CREATE_USER`) REFERENCES `ade_user` (`ID`) ON DELETE RESTRICT ON UPDATE RESTRICT,
FOREIGN KEY (`LAST_MODIFY_USER`) REFERENCES `ade_user` (`ID`) ON DELETE RESTRICT ON UPDATE RESTRICT,
INDEX `FK_17pra2yo53huwi5rmu3caaffl` (`GROUP_PID`) USING BTREE ,
INDEX `FK_c7bfcx1bm8cy7y9062bn7r527` (`CREATE_USER`) USING BTREE ,
INDEX `FK_std7kbn2hh1r2yworte564lev` (`LAST_MODIFY_USER`) USING BTREE 
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci

;

-- ----------------------------
-- Records of ade_group
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for ade_module
-- ----------------------------
DROP TABLE IF EXISTS `ade_module`;
CREATE TABLE `ade_module` (
`ID`  varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL ,
`MODULE_DESC`  varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`MODULE_ICON`  varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`MODULE_NAME`  varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL ,
`MODULE_SORT`  int(11) NULL DEFAULT NULL ,
`MODULE_URL`  varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`MODULE_PID`  varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`MODULE_TYPE_ID`  varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL ,
PRIMARY KEY (`ID`),
FOREIGN KEY (`MODULE_TYPE_ID`) REFERENCES `ade_module_type` (`ID`) ON DELETE RESTRICT ON UPDATE RESTRICT,
FOREIGN KEY (`MODULE_PID`) REFERENCES `ade_module` (`ID`) ON DELETE RESTRICT ON UPDATE RESTRICT,
INDEX `FK_potycga2poaka2davotg8a11m` (`MODULE_PID`) USING BTREE ,
INDEX `FK_7vcirr62lwiv4av32h0iy1rxu` (`MODULE_TYPE_ID`) USING BTREE 
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci

;

-- ----------------------------
-- Records of ade_module
-- ----------------------------
BEGIN;
INSERT INTO `ade_module` (`ID`, `MODULE_DESC`, `MODULE_ICON`, `MODULE_NAME`, `MODULE_SORT`, `MODULE_URL`, `MODULE_PID`, `MODULE_TYPE_ID`) VALUES ('0C1443A88DF346F292587C18786F130A', '热点追踪', '', '热点追踪', '102', '/admin/publishInfo/manager?type=3', '3D17B0355C42440B8B71E6C4233AEDD6', '0'), ('1457C1B20EEC499D9F342CD95A0C497B', '', '', '视频', '102', '/admin/videoInfo/manager', '6AE3616C9971424094406486B14AAD38', '0'), ('3A23FFFE75FA46D18EED6DAB7E60A00B', '', '', '首页动态图片管理', '104', '/admin/publishPicture/manager', '3D17B0355C42440B8B71E6C4233AEDD6', '0'), ('3D17B0355C42440B8B71E6C4233AEDD6', '内容发布管理', 'building_add', '发布管理', '100', '', null, '0'), ('575F64BDD7244EBCA3D244A7C5D0FC10', '', '', '父节点', '100', '', '9DF52236988046B481D161CC7D4ED5E5', '0'), ('62CFA5B993184B97BD1783FD0C20BC65', '版权声明', '', '版权声明', '101', '/admin/publishInfo/manager/?type=2&amp;isOnlyOne=ture', '3D17B0355C42440B8B71E6C4233AEDD6', '0'), ('6AE3616C9971424094406486B14AAD38', '', 'cd_cd', '图书期刊视频', '102', '', null, '0'), ('7919AD61E6F94418A8A6D68C45BD9C45', '', '', '期刊', '101', '/admin/journals/manager', '6AE3616C9971424094406486B14AAD38', '0'), ('7C18968BF30F495188F9FB7618596F1A', '', '', '儿子一级', '100', '', '575F64BDD7244EBCA3D244A7C5D0FC10', '0'), ('7D28E2D858C34EEF8D9D8205B3075017', '', '', '图书', '100', '/admin/bookInfo/manager', '6AE3616C9971424094406486B14AAD38', '0'), ('8E9011739B5147D0BEBEB7731796255C', '', '', '父亲节点2', '100', '', '9DF52236988046B481D161CC7D4ED5E5', '0'), ('9DF52236988046B481D161CC7D4ED5E5', '', 'asterisk_yellow', '测试树级菜单', '100', '', null, '0'), ('9FAF885F07C8449A80CBA50A0DF85562', '平台简介', '', '平台简介', '100', '/admin/publishInfo/manager/?type=1&amp;isOnlyOne=ture', '3D17B0355C42440B8B71E6C4233AEDD6', '0'), ('B550A58E09C1406FAFEA1EC95EF972AC', '', '', '浏览排行', '104', '/admin/rankStatistics/manager', '6AE3616C9971424094406486B14AAD38', '0'), ('BAAE012B6AD84C87BD712B558BD96C83', '', 'asterisk_yellow', '字典管理', '100', '/admin/dictionary/manager', 'xtgl', '0'), ('buggl', null, 'bug_bug', 'BUG管理', '4', '/admin/bug/manager', 'xtgl', '0'), ('bugglAdd', null, 'bug_add', '添加BUG', '3', '/admin/bug/add', 'buggl', '1'), ('bugglAddPage', null, 'bug_add', '添加BUG页面', '2', '/admin/bug/addPage', 'buggl', '1'), ('bugglDateGrid', null, 'bug_link', 'BUG表格', '1', '/admin/bug/dataGrid', 'buggl', '1'), ('bugglDelete', null, 'bug_delete', '删除BUG', '6', '/admin/bug/delete', 'buggl', '1'), ('bugglEdit', null, 'bug_edit', '编辑BUG', '5', '/admin/bug/edit', 'buggl', '1'), ('bugglEditPage', null, 'bug_edit', '编辑BUG页面', '4', '/admin/bug/editPage', 'buggl', '1'), ('bugglView', null, 'bug_link', '查看BUG', '7', '/admin/bug/view', 'buggl', '1'), ('D8E647C493C04D3BAE273008CA23E84A', '', '', '儿子一级2', '100', '', '8E9011739B5147D0BEBEB7731796255C', '0'), ('jsgl', null, 'tux', '角色管理', '2', '/admin/role/manager', 'xtgl', '0'), ('jsglAdd', null, 'wrench', '添加角色', '3', '/admin/role/add', 'jsgl', '1'), ('jsglAddPage', null, 'wrench', '添加角色页面', '2', '/admin/role/addPage', 'jsgl', '1'), ('jsglDelete', null, 'wrench', '删除角色', '6', '/admin/role/delete', 'jsgl', '1'), ('jsglEdit', null, 'wrench', '编辑角色', '5', '/admin/role/edit', 'jsgl', '1'), ('jsglEditPage', null, 'wrench', '编辑角色页面', '4', '/admin/role/editPage', 'jsgl', '1'), ('jsglGrant', null, 'wrench', '角色授权', '8', '/admin/role/grant', 'jsgl', '1'), ('jsglGrantPage', null, 'wrench', '角色授权页面', '7', '/admin/role/grantPage', 'jsgl', '1'), ('jsglTreeGrid', null, 'wrench', '角色表格', '1', '/admin/role/treeGrid', 'jsgl', '1'), ('wjgl', null, 'server_database', '文件管理', '6', null, 'xtgl', '1'), ('wjglUpload', null, 'server_database', '上传文件', '2', '/admin/file/upload', 'wjgl', '1'), ('wjglView', null, 'server_database', '浏览服务器文件', '1', '/admin/file/fileManage', 'wjgl', '1'), ('xtgl', null, 'plugin', '系统管理', '0', null, null, '0'), ('yhgl', null, 'status_online', '用户管理', '3', '/admin/user/manager', 'xtgl', '0'), ('yhglAdd', null, 'wrench', '添加用户', '3', '/admin/user/add', 'yhgl', '1'), ('yhglAddPage', null, 'wrench', '添加用户页面', '2', '/admin/user/addPage', 'yhgl', '1'), ('yhglBatchDelete', null, 'wrench', '批量删除用户', '7', '/admin/user/batchDelete', 'yhgl', '1'), ('yhglDateGrid', null, 'wrench', '用户表格', '1', '/admin/user/dataGrid', 'yhgl', '1'), ('yhglDelete', null, 'wrench', '删除用户', '6', '/admin/user/delete', 'yhgl', '1'), ('yhglEdit', null, 'wrench', '编辑用户', '5', '/admin/user/edit', 'yhgl', '1'), ('yhglEditPage', null, 'wrench', '编辑用户页面', '4', '/admin/user/editPage', 'yhgl', '1'), ('yhglEditPwd', null, 'wrench', '用户修改密码', '11', '/admin/user/editPwd', 'yhgl', '1'), ('yhglEditPwdPage', null, 'wrench', '用户修改密码页面', '10', '/admin/user/editPwdPage', 'yhgl', '1'), ('yhglGrant', null, 'wrench', '用户授权', '9', '/admin/user/grant', 'yhgl', '1'), ('yhglGrantPage', null, 'wrench', '用户授权页面', '8', '/admin/user/grantPage', 'yhgl', '1'), ('zygl', '管理系统中所有的菜单或功能', 'database_gear', '模块管理', '1', '/admin/module/manager', 'xtgl', '0'), ('zyglAdd', null, 'wrench', '添加模块', '4', '/admin/module/add', 'zygl', '1'), ('zyglAddPage', null, 'wrench', '添加模块页面', '3', '/admin/module/addPage', 'zygl', '1'), ('zyglDelete', null, 'wrench', '删除模块', '7', '/admin/module/delete', 'zygl', '1'), ('zyglEdit', null, 'wrench', '编辑模块', '6', '/admin/module/edit', 'zygl', '1'), ('zyglEditPage', null, 'wrench', '编辑模块页面', '5', '/admin/module/editPage', 'zygl', '1'), ('zyglMenu', null, 'wrench', '功能菜单', '2', '/moduleController/tree', 'zygl', '1'), ('zyglTreeGrid', '显示模块列表', 'wrench', '模块表格', '1', '/admin/module/treeGrid', 'zygl', '1');
COMMIT;

-- ----------------------------
-- Table structure for ade_module_type
-- ----------------------------
DROP TABLE IF EXISTS `ade_module_type`;
CREATE TABLE `ade_module_type` (
`ID`  varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL ,
`NAME`  varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL ,
PRIMARY KEY (`ID`)
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci

;

-- ----------------------------
-- Records of ade_module_type
-- ----------------------------
BEGIN;
INSERT INTO `ade_module_type` (`ID`, `NAME`) VALUES ('0', '系统菜单'), ('1', '功能');
COMMIT;

-- ----------------------------
-- Table structure for ade_role
-- ----------------------------
DROP TABLE IF EXISTS `ade_role`;
CREATE TABLE `ade_role` (
`ID`  varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL ,
`ROLE_DESC`  varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`ROLE_NAME`  varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL ,
`ROLE_SORT`  int(11) NULL DEFAULT NULL ,
`ROLE_PID`  varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
PRIMARY KEY (`ID`),
FOREIGN KEY (`ROLE_PID`) REFERENCES `ade_role` (`ID`) ON DELETE RESTRICT ON UPDATE RESTRICT,
INDEX `FK_fe6sykvjkvvg2oc0mpe286in9` (`ROLE_PID`) USING BTREE 
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci

;

-- ----------------------------
-- Records of ade_role
-- ----------------------------
BEGIN;
INSERT INTO `ade_role` (`ID`, `ROLE_DESC`, `ROLE_NAME`, `ROLE_SORT`, `ROLE_PID`) VALUES ('0', '系统开发员角色，拥有系统中所有的模块访问权限', '系统开发员', '0', null), ('2458BE4F889B45AD9845897D38F6F23A', '维护系统中的所有内容管理信息', '信息管理员', '103', '0'), ('2DC54FCBD7BF46DBA92B8B2DE0392B27', '能够操作系统中的所有的功能', '系统管理员', '100', '0'), ('3340625951174C16A5938188D0FF0FC2', '数据征集管理员', '数据征集管理员', '102', '0'), ('638CCDB7E8394465910F1423C2BA19FC', '反反复复', '呵呵', '100', null), ('6537E6FF69504E008DACBD0CED734841', '呜呜呜呜呜呜', '角色管理员', '100', null), ('66D9D2E49D1341DB8238BE8DE47240A2', '申报管理的日常维护者', '申报审核员', '101', '0'), ('B74C3F6E59BB4BA6AC4644AE2548C0EF', '', '普通角色', '100', null), ('B99BC911F8E744F9B6B27A8604977713', '少时诵诗书', '日志管理', '100', null), ('BE08BECBE38645A58AE64614219FC774', '监管机构业务员', '监管机构业务员', '105', '0'), ('F3353214A1E140FC9BA79E683A586AD9', '', '领导角色', '100', null);
COMMIT;

-- ----------------------------
-- Table structure for ade_role_module
-- ----------------------------
DROP TABLE IF EXISTS `ade_role_module`;
CREATE TABLE `ade_role_module` (
`ROLE_ID`  varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL ,
`MODULE_ID`  varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL ,
PRIMARY KEY (`MODULE_ID`, `ROLE_ID`),
FOREIGN KEY (`MODULE_ID`) REFERENCES `ade_module` (`ID`) ON DELETE RESTRICT ON UPDATE RESTRICT,
FOREIGN KEY (`ROLE_ID`) REFERENCES `ade_role` (`ID`) ON DELETE RESTRICT ON UPDATE RESTRICT,
INDEX `FK_s8ixvsib2601l6a4m1xpapkcb` (`MODULE_ID`) USING BTREE ,
INDEX `FK_sb6kki3iapqtjrw0krucaya3d` (`ROLE_ID`) USING BTREE 
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci

;

-- ----------------------------
-- Records of ade_role_module
-- ----------------------------
BEGIN;
INSERT INTO `ade_role_module` (`ROLE_ID`, `MODULE_ID`) VALUES ('0', '0C1443A88DF346F292587C18786F130A'), ('6537E6FF69504E008DACBD0CED734841', '0C1443A88DF346F292587C18786F130A'), ('0', '1457C1B20EEC499D9F342CD95A0C497B'), ('6537E6FF69504E008DACBD0CED734841', '1457C1B20EEC499D9F342CD95A0C497B'), ('F3353214A1E140FC9BA79E683A586AD9', '1457C1B20EEC499D9F342CD95A0C497B'), ('0', '3A23FFFE75FA46D18EED6DAB7E60A00B'), ('6537E6FF69504E008DACBD0CED734841', '3A23FFFE75FA46D18EED6DAB7E60A00B'), ('0', '3D17B0355C42440B8B71E6C4233AEDD6'), ('6537E6FF69504E008DACBD0CED734841', '3D17B0355C42440B8B71E6C4233AEDD6'), ('0', '575F64BDD7244EBCA3D244A7C5D0FC10'), ('6537E6FF69504E008DACBD0CED734841', '575F64BDD7244EBCA3D244A7C5D0FC10'), ('B74C3F6E59BB4BA6AC4644AE2548C0EF', '575F64BDD7244EBCA3D244A7C5D0FC10'), ('F3353214A1E140FC9BA79E683A586AD9', '575F64BDD7244EBCA3D244A7C5D0FC10'), ('0', '62CFA5B993184B97BD1783FD0C20BC65'), ('6537E6FF69504E008DACBD0CED734841', '62CFA5B993184B97BD1783FD0C20BC65'), ('0', '6AE3616C9971424094406486B14AAD38'), ('6537E6FF69504E008DACBD0CED734841', '6AE3616C9971424094406486B14AAD38'), ('F3353214A1E140FC9BA79E683A586AD9', '6AE3616C9971424094406486B14AAD38'), ('0', '7919AD61E6F94418A8A6D68C45BD9C45'), ('6537E6FF69504E008DACBD0CED734841', '7919AD61E6F94418A8A6D68C45BD9C45'), ('F3353214A1E140FC9BA79E683A586AD9', '7919AD61E6F94418A8A6D68C45BD9C45'), ('0', '7C18968BF30F495188F9FB7618596F1A'), ('6537E6FF69504E008DACBD0CED734841', '7C18968BF30F495188F9FB7618596F1A'), ('B74C3F6E59BB4BA6AC4644AE2548C0EF', '7C18968BF30F495188F9FB7618596F1A'), ('F3353214A1E140FC9BA79E683A586AD9', '7C18968BF30F495188F9FB7618596F1A'), ('0', '7D28E2D858C34EEF8D9D8205B3075017'), ('6537E6FF69504E008DACBD0CED734841', '7D28E2D858C34EEF8D9D8205B3075017'), ('F3353214A1E140FC9BA79E683A586AD9', '7D28E2D858C34EEF8D9D8205B3075017'), ('0', '8E9011739B5147D0BEBEB7731796255C'), ('6537E6FF69504E008DACBD0CED734841', '8E9011739B5147D0BEBEB7731796255C'), ('B74C3F6E59BB4BA6AC4644AE2548C0EF', '8E9011739B5147D0BEBEB7731796255C'), ('F3353214A1E140FC9BA79E683A586AD9', '8E9011739B5147D0BEBEB7731796255C'), ('0', '9DF52236988046B481D161CC7D4ED5E5'), ('6537E6FF69504E008DACBD0CED734841', '9DF52236988046B481D161CC7D4ED5E5'), ('B74C3F6E59BB4BA6AC4644AE2548C0EF', '9DF52236988046B481D161CC7D4ED5E5'), ('F3353214A1E140FC9BA79E683A586AD9', '9DF52236988046B481D161CC7D4ED5E5'), ('0', '9FAF885F07C8449A80CBA50A0DF85562'), ('6537E6FF69504E008DACBD0CED734841', '9FAF885F07C8449A80CBA50A0DF85562'), ('0', 'B550A58E09C1406FAFEA1EC95EF972AC'), ('6537E6FF69504E008DACBD0CED734841', 'B550A58E09C1406FAFEA1EC95EF972AC'), ('F3353214A1E140FC9BA79E683A586AD9', 'B550A58E09C1406FAFEA1EC95EF972AC'), ('0', 'BAAE012B6AD84C87BD712B558BD96C83'), ('6537E6FF69504E008DACBD0CED734841', 'BAAE012B6AD84C87BD712B558BD96C83'), ('0', 'buggl'), ('B99BC911F8E744F9B6B27A8604977713', 'buggl'), ('0', 'bugglAdd'), ('0', 'bugglAddPage'), ('0', 'bugglDateGrid'), ('0', 'bugglDelete'), ('0', 'bugglEdit'), ('0', 'bugglEditPage'), ('0', 'bugglView'), ('0', 'D8E647C493C04D3BAE273008CA23E84A'), ('6537E6FF69504E008DACBD0CED734841', 'D8E647C493C04D3BAE273008CA23E84A'), ('B74C3F6E59BB4BA6AC4644AE2548C0EF', 'D8E647C493C04D3BAE273008CA23E84A'), ('F3353214A1E140FC9BA79E683A586AD9', 'D8E647C493C04D3BAE273008CA23E84A'), ('0', 'jsgl'), ('6537E6FF69504E008DACBD0CED734841', 'jsgl'), ('B99BC911F8E744F9B6B27A8604977713', 'jsgl'), ('0', 'jsglAdd'), ('0', 'jsglAddPage'), ('0', 'jsglDelete'), ('0', 'jsglEdit'), ('0', 'jsglEditPage'), ('0', 'jsglGrant'), ('0', 'jsglGrantPage'), ('0', 'jsglTreeGrid'), ('0', 'wjgl'), ('0', 'wjglUpload'), ('0', 'wjglView'), ('0', 'xtgl'), ('B74C3F6E59BB4BA6AC4644AE2548C0EF', 'xtgl'), ('0', 'yhgl'), ('2DC54FCBD7BF46DBA92B8B2DE0392B27', 'yhgl'), ('6537E6FF69504E008DACBD0CED734841', 'yhgl'), ('B99BC911F8E744F9B6B27A8604977713', 'yhgl'), ('0', 'yhglAdd'), ('0', 'yhglAddPage'), ('0', 'yhglBatchDelete'), ('0', 'yhglDateGrid'), ('0', 'yhglDelete'), ('0', 'yhglEdit'), ('0', 'yhglEditPage'), ('0', 'yhglEditPwd'), ('0', 'yhglEditPwdPage'), ('0', 'yhglGrant'), ('0', 'yhglGrantPage'), ('0', 'zygl'), ('B74C3F6E59BB4BA6AC4644AE2548C0EF', 'zygl'), ('0', 'zyglAdd'), ('0', 'zyglAddPage'), ('B74C3F6E59BB4BA6AC4644AE2548C0EF', 'zyglAddPage'), ('0', 'zyglDelete'), ('0', 'zyglEdit'), ('0', 'zyglEditPage'), ('0', 'zyglMenu'), ('0', 'zyglTreeGrid');
COMMIT;

-- ----------------------------
-- Table structure for ade_user
-- ----------------------------
DROP TABLE IF EXISTS `ade_user`;
CREATE TABLE `ade_user` (
`ID`  varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL ,
`LOGIN_NAME`  varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL ,
`USER_NAME`  varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`NAME_LETTER`  varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`PASSWORD`  varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL ,
`SEX`  varchar(2) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`EMAIL`  varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`TELE_PHONE`  varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`USER_PHOTO`  varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`MOBILE_PHONE`  varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`USER_DESC`  varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`USER_STATE`  varchar(2) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`CREATE_DATE`  datetime NULL DEFAULT NULL ,
`AGENCY_FlLAG`  varchar(1) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '是否是机构用户（1、是0、否）' ,
`IP`  text CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '多个ip用;隔开' ,
`AGENCY_FLAG`  varchar(1) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
PRIMARY KEY (`ID`),
UNIQUE INDEX `UK_lub3dp8cwuw4mkn5e9nkw6c0i` (`LOGIN_NAME`) USING BTREE 
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci

;

-- ----------------------------
-- Records of ade_user
-- ----------------------------
BEGIN;
INSERT INTO `ade_user` (`ID`, `LOGIN_NAME`, `USER_NAME`, `NAME_LETTER`, `PASSWORD`, `SEX`, `EMAIL`, `TELE_PHONE`, `USER_PHOTO`, `MOBILE_PHONE`, `USER_DESC`, `USER_STATE`, `CREATE_DATE`, `AGENCY_FlLAG`, `IP`, `AGENCY_FLAG`) VALUES ('0', 'admin', null, null, 'e10adc3949ba59abbe56e057f20f883e', null, null, null, null, null, null, null, null, null, null, null), ('15AA9B8775044C478BE3D7306D515478', '222222', null, null, 'e3ceb5881a0a1fdaad01296d7554868d', null, null, null, null, '18622222222', '科研部/科研', null, null, null, null, null), ('255679B8A1544A0895D4D9620136351E', 'bububu', null, null, '098eb8ba2cc924fad0ec05acd869a4eb', null, null, null, null, null, null, '1', '2016-03-01 15:14:00', null, null, null), ('4366B45B3B334B62A413E8072E1DFCE1', '333333', '333333', '3', '1a100d2c0dab19c4430e7d73762b3423', null, '333@33.com', '010-33333333', null, '13033333333', null, '9', '2016-03-24 14:13:33', null, null, null), ('5B3664A05F12453A871E34EFAF894DD7', '777777', '777777', '7', 'f63f4fbc9f8c85d409f2f59f2b9e12d5', '女', '777777@qq.com', '010-77777777', null, '18077777777', '开发部/研发工程师', '9', '2016-03-09 17:51:41', null, null, null), ('5F36202A22FA420096E4A992B0626FCC', '555555', '555555', '5', '5b1b68a9abf4d2cd155c81a9225fd158', '男', '', '', null, '', null, '9', '2016-03-23 10:05:48', null, null, null), ('5FEEC743022D4DF1BBDB217ED2A7B774', 'agency1', 'agency1', 'A', 'e10adc3949ba59abbe56e057f20f883e', '男', '', '', null, '13022222222', '财务部/财务总监', '9', '2016-03-04 15:05:33', '1', '192.168.3.104,192.168.3.105,192.168.11.11', null), ('6606BEF492DA45DCB0E528A79F156588', 'aaaaaa', 'aaaaaa', 'A', '0b4e7a0e5fe84ad35fb5f95b9ceeac79', null, 'aaa@22.com', '0311-1111111', null, '13022222222', null, '9', '2016-03-24 15:01:49', null, null, null), ('82B3C739800B4AF0924FB1659A0ED1C5', '232222', '222222', '2', 'e3ceb5881a0a1fdaad01296d7554868d', '女', '222222@22.com', '010-22222222', null, '13022222222', null, '9', '2016-03-08 10:04:03', null, null, null), ('B9A5DEEE9106411497E9D535FB5AF7EB', '666666', '666666', '6', 'd41d8cd98f00b204e9800998ecf8427e', '男', '', '', null, '', null, '9', '2016-03-09 17:50:30', null, null, null), ('BD4868573F27420BB744E6919F44927A', '999999', '999999', '9', '52c69e3a57331081823331c4e69d3f2e', '女', '99999@163.com', '010-999999', null, '18099999999', null, '9', '2016-03-23 14:32:24', null, null, null), ('C20D5D4593E5470A868C3425A25E2661', '111111', '111111', '1', '96e79218965eb72c92a549dd5a330112', '女', '111111@111.com', '010-11111111', null, '18011111111', null, '9', '2016-03-04 10:19:05', null, null, null);
COMMIT;

-- ----------------------------
-- Table structure for ade_user_logs
-- ----------------------------
DROP TABLE IF EXISTS `ade_user_logs`;
CREATE TABLE `ade_user_logs` (
`ID`  varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL ,
`CONTENT`  varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL ,
`IE_TYPE`  varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL ,
`IP`  varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL ,
`LOG_TYPE`  int(11) NOT NULL ,
`OPERATE_TIME`  datetime NOT NULL ,
`USER_ID`  varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`VALUE1`  varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`VALUE2`  varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
PRIMARY KEY (`ID`)
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci

;

-- ----------------------------
-- Records of ade_user_logs
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for ade_user_role
-- ----------------------------
DROP TABLE IF EXISTS `ade_user_role`;
CREATE TABLE `ade_user_role` (
`USER_ID`  varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL ,
`ROLE_ID`  varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL ,
PRIMARY KEY (`ROLE_ID`, `USER_ID`),
FOREIGN KEY (`ROLE_ID`) REFERENCES `ade_role` (`ID`) ON DELETE RESTRICT ON UPDATE RESTRICT,
FOREIGN KEY (`USER_ID`) REFERENCES `ade_user` (`ID`) ON DELETE RESTRICT ON UPDATE RESTRICT,
INDEX `FK_8vmilpq0v3wkfbw2jiwl5h2jy` (`ROLE_ID`) USING BTREE ,
INDEX `FK_fb1o8fuyirvvk9t5g7e4v0o5f` (`USER_ID`) USING BTREE 
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci

;

-- ----------------------------
-- Records of ade_user_role
-- ----------------------------
BEGIN;
INSERT INTO `ade_user_role` (`USER_ID`, `ROLE_ID`) VALUES ('0', '0'), ('0', '6537E6FF69504E008DACBD0CED734841'), ('255679B8A1544A0895D4D9620136351E', '6537E6FF69504E008DACBD0CED734841'), ('0', 'B74C3F6E59BB4BA6AC4644AE2548C0EF'), ('C20D5D4593E5470A868C3425A25E2661', 'B74C3F6E59BB4BA6AC4644AE2548C0EF'), ('0', 'F3353214A1E140FC9BA79E683A586AD9');
COMMIT;
