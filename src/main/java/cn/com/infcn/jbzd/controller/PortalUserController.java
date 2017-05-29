package cn.com.infcn.jbzd.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.com.infcn.ade.core.controller.BaseController;
import cn.com.infcn.core.CodeConstant;
import cn.com.infcn.core.pageModel.EasyUIDataGrid;
import cn.com.infcn.core.pageModel.Json;
import cn.com.infcn.core.pageModel.PageHelper;
import cn.com.infcn.core.pageModel.User;
import cn.com.infcn.core.pageModelAdd.PortalUser;
import cn.com.infcn.core.util.LogUtil;
import cn.com.infcn.core.util.Tools;
import cn.com.infcn.core.util.UserUtil;
import cn.com.infcn.jbzd.service.PortalUserService;

/**
 * 描述: 前台用户管理
 *
 * @author xiep
 * @date 2016年5月9日 下午3:10:00
 */
@Controller
@RequestMapping("/admin/portalUser")
public class PortalUserController extends BaseController {

	@Autowired
	private PortalUserService portalUserService;

	/**
	 * 描述: 跳转管理页面
	 *
	 * @param request
	 * @return String
	 */
	@RequestMapping("/manager")
	public String manager(HttpServletRequest request) {
		return "/admin/portalUser/manager";

	}

	/**
	 * 描述: 获取管理数据表格
	 *
	 * @param PortalUser
	 * @param ph
	 * @return EasyUIDataGrid
	 */
	@RequestMapping("/dataGrid")
	@ResponseBody
	public EasyUIDataGrid dataGrid(PortalUser portalUser, PageHelper ph, String page, String rows) {
		if (StringUtils.isBlank(page)) {
			ph.setPage(1);
		}
		if (StringUtils.isBlank(rows)) {
			ph.setRows(10);
		}
		return portalUserService.dataGrid(portalUser, ph);
	}

	/**
	 * 描述: 跳转到添加页面
	 *
	 * @param request
	 * @return String
	 */
	@RequestMapping("/addPage")
	public String addPage(HttpServletRequest request) {
		return "/admin/portalUser/addPage";
	}

	/**
	 * 描述: 添加
	 *
	 * @param portalUser
	 * @param request
	 * @param session
	 * @return
	 * @throws Exception
	 *             Json
	 */
	@RequestMapping("/add")
	@ResponseBody
	public Json add(PortalUser portalUser, HttpServletRequest request, HttpSession session) throws Exception {

		Json j = new Json();
		try {
			portalUser.setId(Tools.getUUID());
			portalUserService.add(portalUser);
			j.setSuccess(true);
			j.setMsg("添加成功！");

			Map<String, Object> params = new HashMap<>();
			params.put("登录名", portalUser.getLoginName());
			LogUtil.adminLogs(CodeConstant.MODELNAME_FOR_LOG_PORTALUSER, CodeConstant.OPER_ADD, portalUser.getId(),
					params, request);

		} catch (Exception e) {
			j.setMsg(e.getMessage());
		} finally {

		}
		return j;
	}

	/**
	 * 描述: 跳转到发布管理修改页面
	 *
	 * @param request
	 * @param id
	 * @return String
	 */
	@RequestMapping("/editPage")
	public String editPage(HttpServletRequest request, String id) {
		request.setAttribute("id", id);
		return "/admin/portalUser/editPage";
	}

	@ResponseBody
	@RequestMapping("/editData")
	public PortalUser editData(HttpServletRequest request, String id) {
		PortalUser portalUser = portalUserService.get(id);
		portalUser.setPassword("");
		return portalUser;
	}

	/**
	 * 描述: 修改
	 *
	 * @param portalUser
	 * @param request
	 * @return Json
	 */
	@RequestMapping("/edit")
	@ResponseBody
	public Json edit(PortalUser portalUser, HttpServletRequest request) {
		Json j = new Json();
		try {
			portalUserService.edit(portalUser);
			j.setSuccess(true);
			j.setMsg("保存成功！");

			Map<String, Object> params = new HashMap<>();
			LogUtil.adminLogs(CodeConstant.MODELNAME_FOR_LOG_PORTALUSER, CodeConstant.OPER_EDIT, portalUser.getId(),
					params, request);

		} catch (Exception e) {
			j.setMsg(e.getMessage());
		}
		return j;
	}

	/**
	 * 描述: 删除
	 *
	 * @param id
	 * @param session
	 * @param request
	 * @return
	 * @throws Exception
	 *             Json
	 */
	@RequestMapping("/delete")
	@ResponseBody
	public Json delete(String id, HttpSession session, HttpServletRequest request) throws Exception {

		Json j = new Json();
		try {
			portalUserService.deleteByID(id);
			j.setMsg("删除成功！");
			j.setSuccess(true);
			Map<String, Object> params = new HashMap<>();
			LogUtil.adminLogs(CodeConstant.MODELNAME_FOR_LOG_PORTALUSER, CodeConstant.OPER_DEL, id, params, request);
		} catch (Exception e) {
			j.setMsg("删除失败！");
			j.setSuccess(true);
		}
		return j;
	}

	/**
	 * 描述: 批量删除
	 *
	 * @param ids
	 *            ('0','1','2')
	 * @param session
	 * @param request
	 * @return
	 * @throws Exception
	 *             Json
	 */
	@RequestMapping("/batchDelete")
	@ResponseBody
	public Json batchDelete(String ids, HttpSession session, HttpServletRequest request) throws Exception {
		Json j = new Json();
		if (ids != null && ids.length() > 0) {
			for (String id : ids.split(",")) {
				if (id != null) {
					this.delete(id, session, request);
				}
			}
		}
		j.setMsg("批量删除成功！");
		j.setSuccess(true);
		return j;
	}

}
