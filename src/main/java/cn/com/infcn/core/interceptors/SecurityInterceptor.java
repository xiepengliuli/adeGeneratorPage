package cn.com.infcn.core.interceptors;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import cn.com.infcn.core.pageModel.User;
import cn.com.infcn.core.util.IpUtil;
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
    private static int count = 0;
	/**
	 * 在调用controller具体方法前拦截
	 */
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object object) throws Exception {

		response.setHeader("Access-Control-Allow-Credentials","true");
		response.setHeader("Access-Control-Allow-Origin", "http://localhost:8080");
	    response.addHeader("Access-Control-Allow-Methods", "GET, POST");
        response.addHeader("Access-Control-Allow-Headers", "Origin, No-Cache, X-Requested-With, If-Modified-Since, Pragma, Last-Modified, Cache-Control, Expires, Content-Type, X-E4M-With");		
        //打印请求
        printPost(request);
        
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
	 * 
	 * 描述: 打印请求
	 *
	 * @param request void
	 */
    public void printPost(HttpServletRequest request) {
        // 只针对该IP打印日志
        List<String> printFromIPs = new ArrayList<String>();
//        printFromIPs.add("192.168.6.103");// xp
        printFromIPs.add("192.168.3.126");// xp
//        printFromIPs.add("192.168.6.244");// 陈欣欣
        String ipAddr = IpUtil.getIpAddr(request);
        if (!printFromIPs.contains(ipAddr) && !ipAddr.equals("本地")) {
            return ;
        }

        String requestPath =request.getRequestURI().substring(request.getContextPath().length());
        ++count;
        System.out.println(count + "_#############################" + ipAddr + ":接口start#######################");

        System.out.println(getSpace(1) + "地址：");
        System.out.println(getSpace(2) + requestPath);
        Enumeration parameterNames = request.getParameterNames();
        System.out.println(getSpace(1) + "参数：");
        String demo = request.getRequestURL().toString();
        boolean isFirst = true;
        String tab = " ";
        while (parameterNames.hasMoreElements()) {

            String key = (String) parameterNames.nextElement();
            String value = request.getParameter(key);
            System.out.println(getSpace(2) + key + ":" + value);
            if (isFirst) {
                demo += "?" + key + "=" + value;
                isFirst = false;
            } else {    
                demo += "&" + key + "=" + value;
            }

        }

        System.out.println(getSpace(1) + "测试：");
        System.out.println(getSpace(2) + demo);
        System.out.println(count + "_#############################" + ipAddr + ":接口end#######################");
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
	
    /**
     * 
     * 描述: 返回count个的空格
     *
     * @param count
     * @return String
     */
    public static String getSpace(int count) {
        String space = " ";
        while (count > 0) {
            space += space;
            count--;
        }
        return space;
    }
}
