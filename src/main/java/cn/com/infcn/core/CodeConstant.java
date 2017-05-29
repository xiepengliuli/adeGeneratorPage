package cn.com.infcn.core;

/**
 * 代码常量类
 * 
 * @author 杨彪
 * 
 */
public class CodeConstant {

	/**
	 * 超级管理员登陆用户名
	 */
	public static final String SUPER_USER_LOGIN_NAME = "admin";

	public static final String USER_STATE_NORMAL = "1";
	public static final String USER_STATE_FREEZE = "2";
	public static final String USER_STATE_UNCHECKED = "9";// 未审核用户
	public static final String AJAX_RESULT_FAIL = "0";// 失败
	public static final String AJAX_RESULT_SUCCESS = "1";// 失败
	public static final String ACCESABLE_IMAGE_TYPE = "1";// 失败

	/** 非内置角色标识 */
	public static final Integer ROLE_ISNOT_BUILDIN = 0;

	/** 内置角色标识 */
	public static final Integer ROLE_IS_BUILDIN = 1;

	public static final String QUERY_TYPE_LIKE = "0";
	public static final String QUERY_TYPE_EQUAL = "1";
	/**
	 * 中医标准
	 */
	public static final String STANDARD_TYPE_TCM = "0";
	/**
	 * 西医标准
	 */
	public static final String STANDARD_TYPE_MM = "1";
	/**
	 * 标准状态：新建
	 */
	public static final String STANDARD_STUTUS_NEW = "1";
	/**
	 * 标准状态：待审核
	 */
	public static final String STANDARD_STUTUS_AUDIT_WAIT = "2";
	/**
	 * 标准状态：审核通过
	 */
	public static final String STANDARD_STUTUS_AUDIT_PASSED = "3";
	/**
	 * 标准状态：审核未通过
	 */
	public static final String STANDARD_STUTUS_AUDIT_NOTPASSED = "4";
	/**
	 * 标准状态：发布通过
	 */
	public static final String STANDARD_STUTUS_PUBLISH_PASSED = "5";
	/**
	 * 标准状态：发布未通过
	 */
	public static final String STANDARD_STUTUS_PUBLISH_NOTPASSED = "6";
	/**
	 * 标准状态：发布撤销
	 */
	public static final String STANDARD_STUTUS_PUBLISH_CANCEL = "7";
	/**
	 * 标准是否删除：正常状态
	 */
	public static final String STANDARD_ISDEL_NORMAL = "0";
	/**
	 * 标准是否删除：回收站状态
	 */
	public static final String STANDARD_ISDEL_RECYCLEBIN = "1";

	/**
	 * 疾病权限：1： 编辑
	 */
	public static final String DISEASE_PRIVILEGE_CANEDIT = "1";

	/**
	 * 疾病权限：2： 审核
	 */
	public static final String DISEASE_PRIVILEGE_CANAUDIT = "2";

	/**
	 * 疾病权限：3： 发布
	 */
	public static final String DISEASE_PRIVILEGE_CANPUBLISH = "3";

	/**
	 * 意见类型：0提交
	 */
	public static final String SUGGEST_TYPE_SUBMIT = "0";
	/**
	 * 意见类型：1审核
	 */
	public static final String SUGGEST_TYPE_AUDIT = "1";
	/**
	 * 意见类型：2发布
	 */
	public static final String SUGGEST_TYPE_PUBLISH = "2";
	/**
	 * 意见类型：3撤销
	 */
	public static final String SUGGEST_TYPE_REVOKE = "3";
	/**
	 * 意见是否通过：1通过
	 */

	public static final String SUGGEST_ISPASS_PASS = "1";
	/**
	 * 意见是否通过：2未通过
	 */
	public static final String SUGGEST_ISPASS_FAIL = "2";

	/**
	 * 中文题名
	 */
	public static final String QUERY_cnTitle = "1";
	/**
	 * 发布机构
	 */
	public static final String QUERY_publishOrg = "2";
	/**
	 * 西医病名
	 */
	public static final String QUERY_mmDiseaseName = "3";
	/**
	 * 中医病名
	 */
	public static final String QUERY_tcmDiseaseName = "4";

	// 系统操作
	public static final String OPER_LOGIN = "登录";
	public static final String OPER_LOGOUT = "注销";
	public static final String OPER_QUERY = "查看";
	public static final String OPER_ADD = "增加";
	public static final String OPER_EDIT = "修改";
	public static final String OPER_EDIT_PWD = "修改密码";
	public static final String OPER_DEL = "删除";
	public static final String OPER_DISABLE = "停用";
	public static final String OPER_ACTIVATE = "启用";

	/**
	 * 疾病操作： 提交审核
	 */
	public static final String OPER_STANDARD_SUBMITAUDIT = "提交审核";
	/**
	 * 疾病操作： 审核
	 */
	public static final String OPER_STANDARD_AUDIT = "审核";
	/**
	 * 疾病操作： 发布
	 */
	public static final String OPER_STANDARD_PUBLISH = "发布";
	/**
	 * 疾病操作： 撤销发布
	 */
	public static final String OPER_STANDARD_REVOKE_PUBLISH = "撤销发布";
	/**
	 * 疾病操作： 删除到回收站
	 */
	public static final String OPER_STANDARD_DEL_TO_RECYCLEBIN = "删除到回收站";
	/**
	 * 疾病操作： 从回收站彻底删除
	 */
	public static final String OPER_STANDARD_DEL_FROM_RECYCLEBIN = "从回收站删除";
	/**
	 * 疾病操作： 从回收站还原
	 */
	public static final String OPER_STANDARD_RESTOREF_ROM_RECYCLEBIN = "从回收站还原";
	/**
	 * 疾病操作：授权
	 */
	public static final String OPER_DISEASE_AUTHORIZATION = "授权";

	/**
	 * 模块名称（日志用）： 前台用户管理
	 */
	public static final String MODELNAME_FOR_LOG_PORTALUSER = "前台用户管理";
	/**
	 * 模块名称（日志用）：疾病管理
	 */
	public static final String MODELNAME_FOR_LOG_DISEASE = "疾病管理";

	/**
	 * 模块名称（日志用）：标准管理
	 */
	public static final String MODELNAME_FOR_LOG_STANDARD = "标准管理";

	/**
	 * 未删除
	 */
	public static final String NOTDELETED = "0";

	/**
	 * 已删除
	 */
	public static final String DELETED = "1";

	/**
	 * 中医标准类型
	 */
	public static final String CNTYPE = "0";

	/**
	 * 西医标准类型
	 */
	public static final String ENTYPE = "1";

}
