package cn.com.infcn.core.interceptors;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import cn.com.infcn.core.pageModel.User;
import cn.com.infcn.core.pageModelAdd.PortalUser;
import cn.com.infcn.core.util.UserUtil;

/**
 * 权限拦截器
 * 
 * @author infcn
 * 
 */
public class SecurityInterceptor implements HandlerInterceptor {

	// /**
	// * 日志对象
	// */
	// private static final Logger LOG =
	// Logger.getLogger(SecurityInterceptor.class);

	private List<String> excludeUrls;// 不需要拦截的资源

	public List<String> getExcludeUrls() {
		return excludeUrls;
	}

	public void setExcludeUrls(List<String> excludeUrls) {
		this.excludeUrls = excludeUrls;
	}

	/**
	 * 在调用controller具体方法前拦截
	 */
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object object) throws Exception {

		response.setHeader("Access-Control-Allow-Credentials","true");
		response.setHeader("Access-Control-Allow-Origin", "http://localhost:8081");
	    response.addHeader("Access-Control-Allow-Methods", "GET, POST");
        response.addHeader("Access-Control-Allow-Headers", "Origin, No-Cache, X-Requested-With, If-Modified-Since, Pragma, Last-Modified, Cache-Control, Expires, Content-Type, X-E4M-With");		

		String requestUri = request.getRequestURI();
		String contextPath = request.getContextPath();
		String url = requestUri.substring(contextPath.length());

		User user = UserUtil.getLoginUser(request);

		
		if (user == null) {
			
			if (url.startsWith("/admin/user/loginvue")||url.startsWith("/admin/user/logout")) {
				return true;
			}
	
			
			
			if (url.startsWith("/admin/")) {// 管理员相关的页面
				if (excludeUrls.indexOf(url) != -1) {
					return true;
				} else {
					response.getWriter().write("nosession");
					return false;
				}
			}
		}
		

		
		
		
		
//		String requestUri = request.getRequestURI();
//		String contextPath = request.getContextPath();
//		String url = requestUri.substring(contextPath.length());
//
//		User user = UserUtil.getLoginUser(request);
//		PortalUser loginUserPortal = UserUtil.getLoginUserPortal(request);
//
//		if (loginUserPortal == null) {
//			if (url.startsWith("/web/")) {
//				if (excludeUrls.indexOf(url) != -1) {
//					return true;
//				} else {
//					request.setAttribute("message", "请登录系统后，再进行操作！");
//					request.getRequestDispatcher("/web/toLogin").forward(request, response);
//					return false;
//				}
//			}
//		}
//
//		if (user == null) {
//			if (url.startsWith("/admin/")) {// 管理员相关的页面
//				if (excludeUrls.indexOf(url) != -1) {
//					return true;
//				} else {
//					request.setAttribute("message", "请登录系统后，再进行操作！");
//					request.getRequestDispatcher("/admin/index.jsp").forward(request, response);
//					return false;
//				}
//			}
//		}

		return true;
	}

	/**
	 * 完成页面的render后调用
	 */
	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object object,
			Exception exception) throws Exception {

	}

	/**
	 * 在调用controller具体方法后拦截
	 */
	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object object,
			ModelAndView modelAndView) throws Exception {

	}
}
