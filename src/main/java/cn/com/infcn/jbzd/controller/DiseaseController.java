package cn.com.infcn.jbzd.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.com.infcn.ade.core.controller.BaseController;
import cn.com.infcn.core.CodeConstant;
import cn.com.infcn.core.pageModel.EasyUIDataGrid;
import cn.com.infcn.core.pageModel.Json;
import cn.com.infcn.core.pageModel.PageHelper;
import cn.com.infcn.core.pageModel.Select;
import cn.com.infcn.core.pageModel.Tree;
import cn.com.infcn.core.pageModel.User;
import cn.com.infcn.core.pageModelAdd.Disease;
import cn.com.infcn.core.util.LogUtil;
import cn.com.infcn.core.util.UserUtil;
import cn.com.infcn.jbzd.service.DiseaseService;

/**
 * 
 * 描述: 发布管理控制器
 *
 * @author xiep
 * @date 2016年5月10日 下午2:43:49
 */
@Controller
@RequestMapping("/admin/disease")
public class DiseaseController extends BaseController {

	
	@Autowired
	private DiseaseService diseaseService;

	/**
	 * 描述: 跳转管理页面
	 *
	 * @param request
	 * @return String
	 */
	@RequestMapping("/manager")
	public String manager(HttpServletRequest request) {

		return "/admin/disease/manager";

	}

	/**
	 * 描述: 获取管理数据表格
	 *
	 * @param Disease
	 * @param ph
	 * @return EasyUIDataGrid
	 */
	@RequestMapping("/dataGrid")
	@ResponseBody
	public EasyUIDataGrid dataGrid(Disease Disease, PageHelper ph) {
		if (ph.getRows() == 0) {
			ph.setRows(10);
		}
		if (ph.getPage() == 0) {
			ph.setPage(1);
		}
		return diseaseService.dataGrid(Disease, ph);
	}

	/**
	 * 描述: 跳转到添加页面
	 *
	 * @param request
	 * @return String
	 */
	@RequestMapping("/addPage")
	public String addPage(HttpServletRequest request) {

		int orderNum = diseaseService.getNextOrderNum();
		request.setAttribute("orderNum", orderNum);
		return "/admin/disease/addPage";
	}

	/**
	 * 描述: 添加
	 *
	 * @param disease
	 * @param request
	 * @param session
	 * @return
	 * @throws Exception
	 *             Json
	 */
	@RequestMapping("/add")
	@ResponseBody
	public Json add(Disease disease, HttpServletRequest request, HttpSession session) throws Exception {

		Json j = new Json();
		try {
			User loginUser = UserUtil.getLoginUser(request);
			disease.setCreateUser(loginUser.getLoginName());
			disease.setFirstLetter(disease.getFirstLetter().toUpperCase());
			diseaseService.add(disease);
			j.setSuccess(true);
			j.setMsg("添加成功！");
			j.setObj(disease);

			Map<String, Object> params = new HashMap<>();
			LogUtil.userLogs(CodeConstant.MODELNAME_FOR_LOG_DISEASE, CodeConstant.OPER_ADD, disease.getId(), params,
					request);

		} catch (Exception e) {
			j.setMsg(e.getMessage());
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
		return "/admin/disease/editPage";
	}

	@RequestMapping("/editData")
	@ResponseBody
	public Disease editData(HttpServletRequest request, String id) {

		Disease disease = diseaseService.get(id);

		return disease;
	}

	/**
	 * 描述: 修改
	 *
	 * @param disease
	 * @param request
	 * @return Json
	 */
	@RequestMapping("/edit")
	@ResponseBody
	public Json edit(Disease disease, HttpServletRequest request) {
		Json j = new Json();
		try {
			User loginUser = UserUtil.getLoginUser(request);
			disease.setUpdateUser(loginUser.getLoginName());
			diseaseService.edit(disease);
			j.setSuccess(true);
			j.setMsg("编辑成功！");
			j.setObj(disease);

			Map<String, Object> params = new HashMap<>();
			LogUtil.userLogs(CodeConstant.MODELNAME_FOR_LOG_DISEASE, CodeConstant.OPER_EDIT, disease.getId(), params,
					request);

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
			String msg = diseaseService.deleteByID(id);
			if (StringUtils.isBlank(msg)) {
				j.setMsg("删除成功！");
				j.setSuccess(true);
				Map<String, Object> params = new HashMap<>();
				LogUtil.userLogs(CodeConstant.MODELNAME_FOR_LOG_DISEASE, CodeConstant.OPER_DEL, id, params, request);

			} else {
				j.setMsg(msg);
			}
		} catch (Exception e) {
			j.setMsg(e.getMessage());
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
		try {
			if (ids != null && ids.length() > 0) {
				for (String id : ids.split(",")) {
					if (id != null) {
						this.delete(id, session, request);
					}
				}
			}
			j.setMsg("批量删除成功！");
			j.setSuccess(true);
			Map<String, Object> params = new HashMap<>();
			LogUtil.userLogs(CodeConstant.MODELNAME_FOR_LOG_DISEASE, CodeConstant.OPER_DEL, ids, params, request);
		} catch (Exception e) {
		}
		return j;
	}

	/**
	 * 
	 * 描述: 属性是否存在某个值
	 *
	 * @param property
	 *            属性的名称
	 * @param value
	 *            属性的值
	 * @return Json
	 */
	@RequestMapping("/isExitPropertyValue")
	@ResponseBody
	public Json isExitPropertyValue(String property, String value, @RequestParam(defaultValue = "") String id) {
		Json j = new Json();
		try {
			boolean isExit = diseaseService.getIsExitPropertyValue(property, value, id);
			if (isExit) {
				j.setSuccess(true);
				j.setMsg("存在！");
			}
		} catch (Exception e) {

		}
		return j;
	}

	@RequestMapping("/authorization")
	public String authorization(String tdiseaseId, HttpServletRequest request) {
		request.setAttribute("tdiseaseId", tdiseaseId);
		return "/admin/disease/authorization";
	}

	@RequestMapping("/authorizationDatagrid")
	@ResponseBody
	public EasyUIDataGrid authorizationDatagrid(String tdiseaseId,
			@RequestParam(defaultValue = "", value = "userNameOrloginName", required = false) String name,
			PageHelper ph) {
		if (ph.getRows() == 0) {
			ph.setRows(10);
		}
		if (ph.getPage() == 0) {
			ph.setPage(1);
		}
		return diseaseService.authorizationDatagrid(tdiseaseId, name, ph);
	}

	/**
	 * 
	 * @param tdiseaseId
	 * @param canEdit
	 * @param canAudit
	 * @param canPublist
	 * @return
	 */
	@RequestMapping("/saveAuthorization")
	@ResponseBody
	public Json saveAuthorization(String tdiseaseId, String canEdit, String canAudit, String canPublish,
			HttpServletRequest request) {
		Json json = new Json();
		try {
			diseaseService.saveAuthorization(tdiseaseId, canEdit, canAudit, canPublish);
			json.setSuccess(true);
			Map<String, Object> params = new HashMap<>();
			LogUtil.userLogs(CodeConstant.MODELNAME_FOR_LOG_DISEASE, CodeConstant.OPER_DISEASE_AUTHORIZATION,
					tdiseaseId, params, request);

		} catch (Exception e) {
			json.setMsg("保存失败");
		}
		return json;
	}

	/**
	 * 描述: 根据疾病获取病的菜单数据
	 *
	 * @param diseaseName
	 * @return Json
	 */
	@RequestMapping("/getMenuDataByName")
	@ResponseBody
	public Json getMenuDataByName(String diseaseName, HttpServletRequest request) {
		Json json = new Json();
		try {
			User loginUser = UserUtil.getLoginUser(request);
			Tree tree = diseaseService.getMenuDataByName(diseaseName, loginUser.getId());
			if (tree != null) {
				json.setObj(tree);
				json.setSuccess(true);
			} else {
				json.setMsg("该用户还没有【" + diseaseName + "】的权限！");
			}
		} catch (Exception e) {

		}
		return json;
	}

	@RequestMapping("/getFirstCharList")
	@ResponseBody
	public List<Select> getFirstCharList() {

		ArrayList<Select> firstCharList = new ArrayList<Select>();

		for (int i = 65; i <= 90; i++) {
			char a = (char) i;
			firstCharList.add(new Select(String.valueOf(a), String.valueOf(a)));
		}

		return firstCharList;
	}

}
