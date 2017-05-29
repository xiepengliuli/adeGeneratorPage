package cn.com.infcn.core.util;

import javax.servlet.http.HttpServletRequest;

import cn.com.infcn.core.pageModel.User;
import cn.com.infcn.core.pageModelAdd.PortalUser;

/**
 * 用户工具类 获得或设置登录的用户
 * 
 * @author NOLY DAKE
 * 
 */
public class UserUtil {

	/**
	 * 用户在session中保存使用的key值
	 */
	public static final String LOGIN_USER = "LOGIN_USER";
	/**
	 * 用户在session中保存使用的key值
	 */
	public static final String LOGIN_USER_PORTAL = "LOGIN_USER_PORTAL";

	/**
	 * 获得登录的用户，如果用户为空，则返回null
	 * 
	 * @param request
	 *            请求
	 * @return 当前登录的用户
	 */
	public static User getLoginUser(HttpServletRequest request) {
		return (User) request.getSession().getAttribute(LOGIN_USER);
	}

	/**
	 * 设置登录的用户，除了登录，请不要随意调用这个方法
	 * 
	 * @param request
	 *            请求
	 * @param user
	 *            当前登录的用户
	 */
	public static void setLoginUser(HttpServletRequest request, User user) {
		request.getSession().setAttribute(LOGIN_USER, user);
	}

	public static PortalUser getLoginUserPortal(HttpServletRequest request) {
		return (PortalUser) request.getSession().getAttribute(LOGIN_USER_PORTAL);
	}

	public static void setLoginUserPortal(HttpServletRequest request, PortalUser user) {
		request.getSession().setAttribute(LOGIN_USER_PORTAL, user);
	}
}
