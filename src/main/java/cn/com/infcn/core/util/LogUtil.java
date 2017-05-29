package cn.com.infcn.core.util;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import cn.com.infcn.ade.admin.service.AdminLogsService;
import cn.com.infcn.ade.admin.service.UserLogsService;
import cn.com.infcn.core.CodeConstant;
import cn.com.infcn.core.pageModel.AdminLogs;
import cn.com.infcn.core.pageModel.User;
import cn.com.infcn.core.pageModel.UserLogs;
import net.sf.json.util.JSONUtils;

/**
 * 日志工具类
 * 
 * @author NOLY DAKE
 * 
 */
public class LogUtil {

	private static final Logger LOG = Logger.getLogger(LogUtil.class);

	/**
	 * @author laixq
	 * @date 2015年11月26日 下午3:55:56
	 * @param message
	 *            记录信息
	 * @param logType
	 *            日志类型
	 * @param recordId
	 *            记录ID
	 * @param tableName
	 *            表名称
	 * @param request
	 *            请求
	 */
	public static void adminLogs(String message, String logType, String recordId, String tableName,
			HttpServletRequest request) {
		AdminLogsService newsLogInfoSysService = (AdminLogsService) SpringBeanUtil.getBean("adminLogsService");
		try {
			AdminLogs log = new AdminLogs();
			User user = UserUtil.getLoginUser(request);
			log.setContent(message);
			log.setLogType(logType);
			log.setUserId(user.getId());
			log.setIeType(BrowserUtils.checkBrowse(request));
			log.setIp(IpUtil.getIpAddr(request));
			newsLogInfoSysService.add(log, request);
		} catch (Throwable e) {
			e.printStackTrace();
		}
	}

	/**
	 * 业务日志添加
	 * 
	 * @author Guodong 2015-06-12 17:05:13
	 * @param
	 */
	public static void userLogs(String message, String logType, HttpServletRequest request) {
		UserLogsService newsLogInfoSysService = (UserLogsService) SpringBeanUtil.getBean("userLogsService");
		try {
			UserLogs log = new UserLogs();
			User user = UserUtil.getLoginUser(request);
			log.setContent(message);
			log.setUserId(user.getId());
			log.setIeType(BrowserUtils.checkBrowse(request));
			log.setIp(IpUtil.getIpAddr(request));
			newsLogInfoSysService.add(log, request);
		} catch (Throwable e) {
			e.printStackTrace();
		}
	}

	/**
	 * 描述: 用户日志
	 *
	 * @param moduleName
	 * @param logType
	 * @param recordId
	 * @param message
	 * @param request
	 *            void
	 */
	public static void userLogs(String moduleName, String logType, String recordId, Map<String, Object> message,
			HttpServletRequest request) {
		UserLogsService newsLogInfoSysService = (UserLogsService) SpringBeanUtil.getBean("userLogsService");
		try {
			UserLogs log = new UserLogs();
			User user = UserUtil.getLoginUser(request);

			String tempString = "";
			if (message != null && message.size() > 0) {
				for (String key : message.keySet()) {
					Object object = message.get(key);
					tempString += "," + key + ":\"" + object.toString() + "\"";
				}
			}

			String tempMessage = "" + moduleName + ":" + "【" + logType + "】id为\"" + recordId + "\"" + tempString
					+ "的记录。";

			log.setContent(tempMessage);
			// log.setModuleName(moduleName);

			log.setUserId(user.getUserName());
			log.setIeType(BrowserUtils.checkBrowse(request));
			log.setIp(IpUtil.getIpAddr(request));
			log.setLogType(logType);
			newsLogInfoSysService.add(log, request);
		} catch (Throwable e) {
			e.printStackTrace();
		}
	}

	/**
	 * 描述: 系统日志
	 *
	 * @param moduleName
	 * @param logType
	 * @param recordId
	 * @param message
	 * @param request
	 *            void
	 */
	public static void adminLogs(String moduleName, String logType, String recordId, Map<String, Object> message,
			HttpServletRequest request) {
		AdminLogsService newsLogInfoSysService = (AdminLogsService) SpringBeanUtil.getBean("adminLogsService");
		try {
			AdminLogs log = new AdminLogs();
			User user = UserUtil.getLoginUser(request);

			String tempString = "";
			if (message != null && message.size() > 0) {
				for (String key : message.keySet()) {
					Object object = message.get(key);
					tempString += "," + key + ":\"" + object.toString() + "\"";
				}
			}
			
			
			String tempMessage = "";

			tempMessage = "" + moduleName + ":" + "【" + logType + "】id为\"" + recordId + "\"" + tempString + "的记录。";

			log.setContent(tempMessage);
			log.setModuleName(moduleName);

			log.setUserId(user.getUserName());
			log.setIeType(BrowserUtils.checkBrowse(request));
			log.setIp(IpUtil.getIpAddr(request));
			log.setLogType(logType);
			newsLogInfoSysService.add(log, request);
		} catch (Throwable e) {
			e.printStackTrace();
		}
	}

	/**
	 * 
	 * 描述: 后台登陆
	 *
	 * @param userName
	 * @param request
	 *            void
	 */
	public static void adminLogsLogin(String userName, HttpServletRequest request) {
		adminLogsLoginAndLogout(userName, CodeConstant.OPER_LOGIN, request);
	}

	/**
	 * 
	 * 描述: 后台注销
	 *
	 * @param userName
	 * @param request
	 *            void
	 */
	public static void adminLogsLogout(String userName, HttpServletRequest request) {
		adminLogsLoginAndLogout(userName, CodeConstant.OPER_LOGOUT, request);
	}

	private static void adminLogsLoginAndLogout(String userName, String type, HttpServletRequest request) {
		AdminLogsService newsLogInfoSysService = (AdminLogsService) SpringBeanUtil.getBean("adminLogsService");
		try {
			AdminLogs log = new AdminLogs();
			User user = UserUtil.getLoginUser(request);

			log.setUserId(user.getUserName());
			log.setIeType(BrowserUtils.checkBrowse(request));
			log.setIp(IpUtil.getIpAddr(request));
			log.setLogType(type);
			log.setModuleName(type);

			String tempMessage = "";
			if (CodeConstant.OPER_LOGIN.equals(type)) {
				tempMessage = "用户【" + userName + "】使用IP【" + log.getIp() + "】登录系统";
			} else if (CodeConstant.OPER_LOGOUT.equals(type)) {
				tempMessage = "用户【" + userName + "】使用IP【" + log.getIp() + "】注销登录";
			}

			log.setContent(tempMessage);
			newsLogInfoSysService.add(log, request);
		} catch (Throwable e) {
			e.printStackTrace();
		}
	}
}
