package cn.com.infcn.ade.admin.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.com.infcn.ade.admin.service.ModuleService;
import cn.com.infcn.ade.admin.service.RoleService;
import cn.com.infcn.ade.admin.service.UserService;
import cn.com.infcn.ade.core.controller.BaseController;
import cn.com.infcn.ade.core.model.EasyUIDataGrid;
import cn.com.infcn.ade.core.model.JsonResult;
import cn.com.infcn.core.CodeConstant;
import cn.com.infcn.core.pageModel.PageHelper;
import cn.com.infcn.core.pageModel.User;
import cn.com.infcn.core.util.CaptchaUtil;
import cn.com.infcn.core.util.JacksonUtil;
import cn.com.infcn.core.util.LogUtil;
import cn.com.infcn.core.util.StringUtil;
import cn.com.infcn.core.util.UserUtil;

/**
 * 用户控制器
 * 
 * @author 杨彪
 * 
 */
@Controller
@RequestMapping("/admin/user")
public class UserController extends BaseController {

	@Autowired
	private UserService userService;

	@Autowired
	private RoleService roleService;

	@Autowired
	private ModuleService moduleService;

	/**
	 * 用户登录操作
	 * 
	 * @param user
	 *            用户信息
	 * @param request
	 *            请求信息
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/login")
	public JsonResult login(User user, HttpServletRequest request) {

		// 返回结果
		JsonResult j = new JsonResult();

		String message = CaptchaUtil.isValidate(user.getCaptcha(), request.getSession());

		if (message != null) {
			j.setMsg(message + "，请重新输入验证码！");
			return j;
		}

		User u = userService.login(user);
		if (u != null) {

			u.setModuleList(userService.moduleList(user));

			UserUtil.setLoginUser(request, user);

			j.setSuccess(true);
			j.setMsg("登陆成功！");
			j.setObj(user);
			LogUtil.adminLogsLogin(user.getLoginName(), request);
		} else {
			j.setMsg("用户名或密码错误！");
		}

		return j;
	}

	/**
	 * 用户注册
	 * 
	 * @param user
	 *            用户对象
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/reg")
	public JsonResult reg(User user) {
		JsonResult j = new JsonResult();
		try {
			userService.reg(user);
			j.setSuccess(true);
			j.setMsg("注册成功！新注册的用户没有任何权限，请让管理员赋予权限后再使用本系统！");
			j.setObj(user);
		} catch (Exception e) {
			j.setMsg(e.getMessage());
		}
		return j;
	}

	/**
	 * 退出登录
	 * 
	 * @param session
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/logout")
	public JsonResult logout(HttpSession session,HttpServletRequest request) {
		User loginUser = UserUtil.getLoginUser(request);
		JsonResult j = new JsonResult();
		if (session != null) {
			LogUtil.adminLogsLogout(loginUser.getLoginName(), request);
			session.invalidate();
		}
		j.setSuccess(true);
		j.setMsg("注销成功！");
		return j;
	}

	/**
	 * 跳转到用户管理页面
	 * 
	 * @return
	 */
	@RequestMapping("/manager")
	public String manager() {
		return "/admin/user";
	}

	/**
	 * 获取用户数据表格
	 * 
	 * @param user
	 * @return
	 */
	@RequestMapping("/dataGrid")
	@ResponseBody
	public EasyUIDataGrid dataGrid(User user, PageHelper ph) {
		return userService.dataGrid(user, ph);
	}

	/**
	 * 跳转到添加用户页面
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping("/addPage")
	public String addPage(HttpServletRequest request) {
		User u = new User();
		u.setId(StringUtil.generateUUID());
		request.setAttribute("user", u);
		return "/admin/userAdd";
	}

	/**
	 * 添加用户
	 * 
	 * @return
	 */
	@RequestMapping("/add")
	@ResponseBody
	public JsonResult add(User user) {
		JsonResult j = new JsonResult();
		try {
			userService.add(user);
			j.setSuccess(true);
			j.setMsg("添加成功！");
			j.setObj(user);
		} catch (Exception e) {
			// e.printStackTrace();
			j.setMsg(e.getMessage());
		}
		return j;
	}

	/**
	 * 跳转到用户修改页面
	 * 
	 * @return
	 */
	@RequestMapping("/editPage")
	public String editPage(HttpServletRequest request, String id) {
		User u = userService.get(id);
		request.setAttribute("user", u);
		return "/admin/userEdit";
	}

	/**
	 * 修改用户
	 * 
	 * @param user
	 * @return
	 */
	@RequestMapping("/edit")
	@ResponseBody
	public JsonResult edit(User user) {
		JsonResult j = new JsonResult();
		try {
			userService.edit(user);
			j.setSuccess(true);
			j.setMsg("编辑成功！");
			j.setObj(user);
		} catch (Exception e) {
			// e.printStackTrace();
			j.setMsg(e.getMessage());
		}
		return j;
	}

	/**
	 * 删除用户
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping("/delete")
	@ResponseBody
	public JsonResult delete(String id, HttpServletRequest request) {

		User user = UserUtil.getLoginUser(request);

		JsonResult j = new JsonResult();
		if (id != null && !id.equalsIgnoreCase(user.getId())) {// 不能删除自己
			userService.delete(id);
		}
		j.setMsg("删除成功！");
		j.setSuccess(true);
		return j;
	}

	/**
	 * 批量删除用户
	 * 
	 * @param ids
	 *            ('0','1','2')
	 * @return
	 */
	@RequestMapping("/batchDelete")
	@ResponseBody
	public JsonResult batchDelete(String ids, HttpServletRequest request) {

		JsonResult j = new JsonResult();
		if (ids != null && ids.length() > 0) {
			for (String id : ids.split(",")) {
				if (id != null) {
					this.delete(id, request);
				}
			}
		}
		j.setMsg("批量删除成功！");
		j.setSuccess(true);
		return j;
	}

	/**
	 * 跳转到用户授权页面
	 * 
	 * @return
	 */
	@RequestMapping("/grantPage")
	public String grantPage(String ids, HttpServletRequest request) {
		request.setAttribute("ids", ids);
		if (ids != null && !ids.equalsIgnoreCase("") && ids.indexOf(",") == -1) {
			User u = userService.get(ids);
			request.setAttribute("user", u);
		}
		return "/admin/userGrant";
	}

	/**
	 * 用户授权
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping("/grant")
	@ResponseBody
	public JsonResult grant(String ids, User user) {
		JsonResult j = new JsonResult();
		userService.grant(ids, user);
		j.setSuccess(true);
		j.setMsg("授权成功！");
		return j;
	}

	/**
	 * 跳转到编辑用户密码页面
	 * 
	 * @param id
	 * @param request
	 * @return
	 */
	@RequestMapping("/editPwdPage")
	public String editPwdPage(String id, HttpServletRequest request) {
		User u = userService.get(id);
		request.setAttribute("user", u);
		return "/admin/userEditPwd";
	}

	/**
	 * 编辑用户密码
	 * 
	 * @param user
	 * @return
	 */
	@RequestMapping("/editPwd")
	@ResponseBody
	public JsonResult editPwd(User user) {
		JsonResult j = new JsonResult();
		userService.editPwd(user);
		j.setSuccess(true);
		j.setMsg("编辑成功！");
		return j;
	}

	/**
	 * 跳转到编辑自己的密码页面
	 * 
	 * @return
	 */
	@RequestMapping("/editCurrentUserPwdPage")
	public String editCurrentUserPwdPage() {
		return "/admin/user/userEditPwd";
	}

	/**
	 * 修改自己的密码
	 * 
	 * @param session
	 * @param pwd
	 * @return
	 */
	@RequestMapping("/editCurrentUserPwd")
	@ResponseBody
	public JsonResult editCurrentUserPwd(HttpServletRequest request, String id, String oldPwd, String pwd) {

		JsonResult j = new JsonResult();
		User user = UserUtil.getLoginUser(request);
		if (user != null) {
			id = user.getId();
			if (userService.editCurrentUserPwd(id, oldPwd, pwd)) {
				j.setSuccess(true);
				j.setMsg("编辑密码成功，下次登录生效！");
			} else {
				j.setMsg("原密码错误！");
			}
		} else {
			j.setMsg("登录超时，请重新登录！");
		}
		return j;
	}

	/**
	 * 跳转到显示用户角色页面
	 * 
	 * @return
	 */
	@RequestMapping("/currentUserRolePage")
	public String currentUserRolePage(HttpServletRequest request) {
		User user = UserUtil.getLoginUser(request);
		request.setAttribute("userRoles", JacksonUtil.toJSONString(roleService.tree(user)));
		return "/admin/user/userRole";
	}

	/**
	 * 跳转到显示用户权限页面
	 * 
	 * @return
	 */
	@RequestMapping("/currentUserResourcePage")
	public String currentUserResourcePage(HttpServletRequest request) {
		User user = UserUtil.getLoginUser(request);
		request.setAttribute("userResources", JacksonUtil.toJSONString(moduleService.allTree(user)));
		return "/admin/user/userResource";
	}
}
